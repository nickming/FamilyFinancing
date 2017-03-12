package com.nickming.familyfinancing.ui.shares;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.SharesDetailAdapter;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.UrlContract;
import com.nickming.familyfinancing.entity.SharesDetailEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SharesDetailActivity extends BaseActivity {

    @BindView(R.id.tv_shares_detail_title)
    TextView mSharesDetailTitleTv;
    @BindView(R.id.rv_shares_detail)
    RecyclerView mSharesDetailRv;

    private Gson mGson;
    private String mSymbol;
    private SharesDetailAdapter mAdapter;
    private List<SharesDetailEntity.ResultBean.Item> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shares_detail);
        ButterKnife.bind(this);

        mGson = new Gson();
        if (getIntent() != null) {
            mSymbol = getIntent().getStringExtra(Constant.SHARE_DETAIL_SYMBOL);
            mSharesDetailTitleTv.setText(getIntent().getStringExtra(Constant.SHARE_DETAIL_NAME));
            mSharesDetailRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mAdapter = new SharesDetailAdapter(mDatas);
            mSharesDetailRv.setAdapter(mAdapter);
            OkGo.get(UrlContract.SHARE_DETAIL)
                    .tag(this)
                    .params("gid", mSymbol)
                    .params("key", "a2e49aee7305e5929834c8df53725ed4")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            SharesDetailEntity detailEntity = mGson.fromJson(s, SharesDetailEntity.class);
                            SharesDetailEntity.ResultBean.GopictureBean gopicture = detailEntity.getResult().get(0).getGopicture();
                            mDatas.add(new SharesDetailEntity.ResultBean.Item("分时k线图", gopicture.getMinurl()));
                            mDatas.add(new SharesDetailEntity.ResultBean.Item("日k线图", gopicture.getDayurl()));
                            mDatas.add(new SharesDetailEntity.ResultBean.Item("周k线图", gopicture.getWeekurl()));
                            mDatas.add(new SharesDetailEntity.ResultBean.Item("月k线图", gopicture.getMonthurl()));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    public void back(View view) {
        finish();
    }
}
