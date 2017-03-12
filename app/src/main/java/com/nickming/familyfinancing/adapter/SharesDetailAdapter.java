package com.nickming.familyfinancing.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.SharesDetailEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:16:13
 * E-mail:
 */

public class SharesDetailAdapter extends BaseQuickAdapter<SharesDetailEntity.ResultBean.Item, BaseViewHolder> {


    public SharesDetailAdapter(List<SharesDetailEntity.ResultBean.Item> data) {
        super(R.layout.item_shares_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SharesDetailEntity.ResultBean.Item item) {
        LoaderImageView imageView = helper.getView(R.id.iv_shares_k_line);
        helper.setText(R.id.tv_shares_k_title, item.getTitle());
        Glide.with(helper.getConvertView().getContext()).load(item.getUrl()).asGif().into(imageView);
    }
}
