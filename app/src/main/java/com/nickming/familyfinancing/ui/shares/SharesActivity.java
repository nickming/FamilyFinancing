package com.nickming.familyfinancing.ui.shares;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.SharesAdapter;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.UrlContract;
import com.nickming.familyfinancing.entity.SharesEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SharesActivity extends BaseActivity {

    @BindView(R.id.rv_shares)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_shares_change)
    TextView mSharesChangeTv;
    @BindView(R.id.tv_shares_title)
    TextView mSharesTitle;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private Gson mGson;

    private SharesAdapter mAdapter;
    private int mTotalCount = 1;
    private int mCurrentIndex = 1;
    private int mCureentSharesType = 0;
    private boolean isEnableLoadMore = true;
    private String mCurrentUrl = UrlContract.SHARE_SHENZHEN;
    private List<SharesEntity.ResultBean.DataBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shares);
        ButterKnife.bind(this);
        mGson = new Gson();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new SharesAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreData();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        loadMoreData();

        mSharesChangeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SharesActivity.this)
                        .title("选择股市")
                        .items(R.array.shares_type)
                        .itemsCallbackSingleChoice(mCureentSharesType, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                mCureentSharesType = which;
                                changeSharesType(text);
                                return false;
                            }
                        })
                        .positiveText("确定")
                        .show();
            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SharesActivity.this, SharesDetailActivity.class);
                intent.putExtra(Constant.SHARE_DETAIL_SYMBOL, mDatas.get(position).getSymbol());
                intent.putExtra(Constant.SHARE_DETAIL_NAME, mDatas.get(position).getName());
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentIndex = 1;
                isEnableLoadMore = true;
                loadMoreData();
            }
        });
    }

    private void changeSharesType(CharSequence text) {
        if (text.equals("深圳股市"))
            mCurrentUrl = UrlContract.SHARE_SHENZHEN;
        else
            mCurrentUrl = UrlContract.SHARE_SHANGEHAI;

        mSharesTitle.setText(text);
        mCurrentIndex = 1;
        isEnableLoadMore = true;
        loadMoreData();
    }

    private void loadMoreData() {
        if (isEnableLoadMore) {
            OkGo.get(mCurrentUrl)
                    .params("key", "a2e49aee7305e5929834c8df53725ed4")
                    .params("page", mCurrentIndex)
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            SharesEntity entity = mGson.fromJson(s, SharesEntity.class);
                            mTotalCount = Integer.valueOf(entity.getResult().getTotalCount());
                            mCurrentIndex = Integer.valueOf(entity.getResult().getPage());
                            if ((mCurrentIndex + 1) > mTotalCount) {
                                isEnableLoadMore = false;
                                mAdapter.loadMoreEnd();
                            } else {
                                isEnableLoadMore = true;
                                mCurrentIndex += 1;
                            }
                            mDatas.clear();
                            mDatas.addAll(entity.getResult().getData());
                            mAdapter.notifyDataSetChanged();
                            mAdapter.loadMoreComplete();
                            refreshLayout.setRefreshing(false);
                        }
                    });
        } else {
            Toast.makeText(this, "没有更多了!", Toast.LENGTH_SHORT).show();
        }

    }

    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
