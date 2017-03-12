package com.nickming.familyfinancing.ui.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.PayTypeAdapter;
import com.nickming.familyfinancing.adapter.RecordAddAdapter;
import com.nickming.familyfinancing.base.BaseFragment;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;
import com.nickming.familyfinancing.widget.EarningAndExpendChangeBox;
import com.nickming.familyfinancing.widget.FinancingNumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:11:15
 * E-mail:962570483@qq.com
 */

public class RecordFragment extends BaseFragment implements RecordContract.View {

    @BindView(R.id.iv_record_back)
    ImageView mBackArrow;
    @BindView(R.id.record_change_bar)
    EarningAndExpendChangeBox mRecordChangeBar;
    @BindView(R.id.iv_record_type_icon)
    ImageView mTypeIcon;
    @BindView(R.id.tv_record_sum)
    TextView mRecordSum;
    @BindView(R.id.tv_record_type_name)
    TextView mTypeName;
    @BindView(R.id.rv_record)
    RecyclerView mRecyclerView;
    @BindView(R.id.num_keyboard_view)
    FinancingNumberKeyBoardView mKeyboardView;
    @BindView(R.id.iv_record_type_icon_background)
    CircleImageView mRecordTypeIconBackground;
    @BindView(R.id.tv_record_remark)
    TextView mRecordRemarkTv;
    @BindView(R.id.rv_record_use_type)
    RecyclerView mPayTypeRv;

    private RecordContract.Presenter mPresenter;
    private List<RecordTypeEntity> mRecordTypeList = new ArrayList<>();
    private List<RecordTypeEntity> mPayTypeList = new ArrayList<>();
    private RecordAddAdapter mAdapter;
    private PayTypeAdapter mPayAdapter;
    private String mCurrentRemark = "";

    public static RecordFragment newInstance() {

        Bundle args = new Bundle();
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record_page, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecordChangeBar.setOnChnageStyleListener(new EarningAndExpendChangeBox.OnChangeStyleListener() {
            @Override
            public void onChangeToEarning() {
                showEarningList();
            }

            @Override
            public void onChangeToExpend() {
                showExpendList();
            }
        });
        mKeyboardView.setOnKeyBoardChangeListener(new FinancingNumberKeyBoardView.OnKeyBoardChangeListener() {
            @Override
            public void onChange(String result) {
                showSum(result);
            }

            @Override
            public void onClickOk(String result) {
                String accountBookName = getActivity().getIntent().getStringExtra(Constant.ACCOUNT_BOOK_NAME);
                mPresenter.saveRecord(FinancingEntity.create(0, mAdapter.getCurrentType(),mPayAdapter.getCurrentType(), Double.valueOf(result),
                        accountBookName, "nickming", mCurrentRemark));
            }
        });

        mRecordRemarkTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("请输入备注")
                        .content("需要的备注信息")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入备注", mCurrentRemark, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                mCurrentRemark = input.toString();
                            }
                        }).show();
            }
        });


        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new RecordAddAdapter(mRecordTypeList);

        mPayTypeRv.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mPayTypeList.addAll(TypeIconFactory.getPayTypeList());
        mPayAdapter = new PayTypeAdapter(mPayTypeList);
        mPayTypeRv.setAdapter(mPayAdapter);
        mPayTypeRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordTypeEntity recordTypeEntity = mPayTypeList.get(position);
                mPayAdapter.setCurrentType(recordTypeEntity.type());
                mPayAdapter.notifyDataSetChanged();
            }
        });

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter.enableDragItem(itemTouchHelper, R.id.ll_record_item_container, true);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordTypeEntity recordTypeEntity = mRecordTypeList.get(position);
                showCurrentType(recordTypeEntity);
                mAdapter.setCurrentType(recordTypeEntity.type());
                mAdapter.notifyDataSetChanged();
            }
        });
        mPresenter.start();
    }

    @Override
    public void setPresenter(RecordContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void exit() {
        getActivity().finish();
    }

    @Override
    public void showEarningList() {
        mRecordTypeList.clear();
        mRecordTypeList.addAll(TypeIconFactory.getEarningList());
        mAdapter.notifyDataSetChanged();
        showCurrentType(mRecordTypeList.get(0));
    }

    @Override
    public void showExpendList() {
        mRecordTypeList.clear();
        mRecordTypeList.addAll(TypeIconFactory.getExpendList());
        mAdapter.notifyDataSetChanged();
        showCurrentType(mRecordTypeList.get(0));
    }

    @Override
    public void showCurrentType(RecordTypeEntity entity) {
        mRecordTypeIconBackground.setImageResource(entity.color());
        mTypeIcon.setImageResource(entity.icon());
        mTypeName.setText(entity.typeName());
    }

    @Override
    public void showSum(String sum) {
        mRecordSum.setText(sum);
    }

    @Override
    public void showRemarkDialog() {

    }
}
