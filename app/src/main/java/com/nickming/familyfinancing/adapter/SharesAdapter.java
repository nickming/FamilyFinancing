package com.nickming.familyfinancing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.SharesEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:15:05
 * E-mail:
 */

public class SharesAdapter extends BaseQuickAdapter<SharesEntity.ResultBean.DataBean, BaseViewHolder> {


    public SharesAdapter(List<SharesEntity.ResultBean.DataBean> data) {
        super(R.layout.item_shares, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SharesEntity.ResultBean.DataBean item) {
        helper.setText(R.id.tv_shares_amount, ""+item.getAmount());
        helper.setText(R.id.tv_shares_buy, ""+item.getBuy());
        helper.setText(R.id.tv_shares_changepercent,""+ item.getChangepercent());
        helper.setText(R.id.tv_shares_high,""+ item.getBuy());
        helper.setText(R.id.tv_shares_low, ""+item.getLow());
        helper.setText(R.id.tv_shares_name,""+ item.getName());
        helper.setText(R.id.tv_shares_open,""+ item.getOpen());
        helper.setText(R.id.tv_shares_pricechange,""+ item.getPricechange());
        helper.setText(R.id.tv_shares_sell,""+ item.getSell());
        helper.setText(R.id.tv_shares_settlement,""+ item.getSettlement());
        helper.setText(R.id.tv_shares_symbol,""+ item.getSymbol());
        helper.setText(R.id.tv_shares_ticktime,""+ item.getTicktime());
        helper.setText(R.id.tv_shares_trade,""+ item.getTrade());
        helper.setText(R.id.tv_shares_volume,""+ item.getVolume());
        helper.setText(R.id.tv_shares_high,""+ item.getHigh());
    }
}
