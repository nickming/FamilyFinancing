package com.nickming.familyfinancing.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.NewsAdapter;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.UrlContract;
import com.nickming.familyfinancing.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.rv_news)
    RecyclerView mNewsRv;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Gson mGson;
    private NewsAdapter mAdapter;
    private List<NewsEntity.ResultBean.DataBean> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        mGson = new Gson();

        mAdapter = new NewsAdapter(mDatas);
        mNewsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mNewsRv.setAdapter(mAdapter);

        mNewsRv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                intent.putExtra(Constant.NEWS_URL, mDatas.get(position).getUrl());
                intent.putExtra(Constant.NEWS_TITLE, mDatas.get(position).getTitle());
                startActivity(new Intent(intent));
            }
        });
        requestNewsData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNewsData();
            }
        });
    }

    public void back(View view) {
        finish();
    }

    private void requestNewsData() {
        OkGo.get(UrlContract.NEWS_URL)
                .tag(this)
                .cacheMode(CacheMode.DEFAULT)
                .params("key", "9c7a732a232c0817fc1263385eaaf7fa")
                .params("type", "caijing")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        NewsEntity newsEntity = mGson.fromJson(s, NewsEntity.class);
                        mDatas.clear();
                        mDatas.addAll(newsEntity.getResult().getData());
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
