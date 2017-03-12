package com.nickming.familyfinancing.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.NewsEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:09:51
 * E-mail:
 */

public class NewsAdapter extends BaseQuickAdapter<NewsEntity.ResultBean.DataBean, BaseViewHolder> {


    public NewsAdapter(List<NewsEntity.ResultBean.DataBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsEntity.ResultBean.DataBean item) {
        LoaderTextView date = helper.getView(R.id.tv_news_date);
        LoaderTextView content = helper.getView(R.id.tv_news_content);
        LoaderImageView thumb = helper.getView(R.id.iv_news_thumb);
        date.setText(item.getDate());
        content.setText(item.getTitle());
        Glide.with(helper.getConvertView().getContext()).load(item.getThumbnail_pic_s()).into(thumb);
    }
}
