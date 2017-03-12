package com.nickming.familyfinancing.engine;

import android.app.Activity;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.AccountBookAdapter;
import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.db.AccountBookDataSource;
import com.nickming.familyfinancing.db.AccountBookRepository;
import com.nickming.familyfinancing.entity.AccountBookEntity;
import com.nickming.familyfinancing.entity.ActivityCloseEvent;
import com.nickming.familyfinancing.util.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:09:59
 * E-mail:962570483@qq.com
 */

public class AccountBookManager implements View.OnClickListener {

    private static final String TAG = "AccountBookManager";

    private WeakReference<Activity> mActivity;
    private AccountBookDataSource mAccountBookDataSource;
    private RecyclerView mRecyclerView;
    private AccountBookAdapter mAdapter;
    private List<AccountBookEntity> mAccountBookList = new ArrayList<>();
    private Button mNewBtn;
    private Button mExitBtn;

    private String mNewAccountBookName = "";

    public AccountBookManager(Activity activity) {

        mActivity = new WeakReference<Activity>(activity);
        initViews();
    }

    private void initViews() {
        mAccountBookDataSource = AccountBookRepository.getInstance(BaseApplication.getsContext());
        mNewBtn = (Button) mActivity.get().findViewById(R.id.btn_drawer_new);
        mNewBtn.setOnClickListener(this);
        mExitBtn = (Button) mActivity.get().findViewById(R.id.btn_drawer_exit);
        mExitBtn.setOnClickListener(this);
        this.mRecyclerView = (RecyclerView) mActivity.get().findViewById(R.id.rv_account);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity.get(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new AccountBookAdapter(mAccountBookList);
        ItemDragAndSwipeCallback callback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mAdapter.enableDragItem(itemTouchHelper, R.id.ll_account_book_item_container, true);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                changeFinancingList(mAccountBookList.get(position));
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, final View parentView, final int position) {
                new MaterialDialog.Builder(mActivity.get())
                        .title("其他操作")
                        .itemsColor(mActivity.get().getResources().getColor(R.color.primary_text_color))
                        .items(R.array.account_book_selection)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                deleteAccountBookEntity(parentView, position);
                            }
                        })
                        .show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        getDatas();
    }

    private void changeFinancingList(AccountBookEntity accountBookEntity) {
        EventBus.getDefault().post(accountBookEntity);
    }

    private void deleteAccountBookEntity(View view, int position) {
        AccountBookEntity accountBookEntity = mAccountBookList.get(position);
        if (accountBookEntity.name().equals("mime")) {
            Snackbar.make(view, "系统默认账本不能删除!", Snackbar.LENGTH_SHORT).show();
        } else {
            long result = mAccountBookDataSource.deleteAccountBook(accountBookEntity);
            Log.i(TAG, "deleteAccountBookEntity: " + result);
            getDatas();
        }
    }

    private void getDatas() {
        mAccountBookDataSource.getAllBooks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AccountBookEntity>>() {
                    @Override
                    public void call(List<AccountBookEntity> accountBookEntities) {
                        mAccountBookList.clear();
                        mAccountBookList.addAll(accountBookEntities);
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void release() {
        mActivity.clear();
        mRecyclerView = null;
        mAdapter = null;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_drawer_new:
                new MaterialDialog.Builder(mActivity.get())
                        .title("新建账本")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .content("账本名称")
                        .input("账本名", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull final MaterialDialog dialog, CharSequence input) {
                                mNewAccountBookName = input.toString();
                                final AccountBookEntity accountBookEntity = AccountBookEntity.create(mNewAccountBookName,
                                        Integer.valueOf(TimeUtil.getCurrentTime(TimeUtil.DATABASE_TIME_FORMAT)));
                                mAccountBookDataSource.addAccountBook(accountBookEntity);
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.btn_drawer_exit:
                EventBus.getDefault().post(new ActivityCloseEvent(true));
                Process.killProcess(Process.myPid());
                break;
            default:
                break;
        }
    }
}
