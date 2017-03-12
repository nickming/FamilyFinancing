package com.nickming.familyfinancing.ui.news;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity {

    @BindView(R.id.tv_news_detail_title)
    TextView tvNewsDetailTitle;
    @BindView(R.id.webview_news)
    WebView webviewNews;

    private String title;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        if (getIntent()!=null)
        {
            title=getIntent().getStringExtra(Constant.NEWS_TITLE);
            url=getIntent().getStringExtra(Constant.NEWS_URL);
            tvNewsDetailTitle.setText(title);
            WebSettings settings=webviewNews.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            webviewNews.loadUrl(url);

        }
    }

    public void back(View view)
    {
        finish();
    }
}
