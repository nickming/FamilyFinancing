package com.nickming.familyfinancing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/25
 * Time:22:46
 * E-mail:962570483@qq.com
 */

public class ChartDetailListAdapter extends BaseQuickAdapter<FinancingEntity, BaseViewHolder> {

    private boolean isChannel = false;

    public ChartDetailListAdapter(List<FinancingEntity> data) {
        super(R.layout.item_chart_detail_with_date, data);
    }

    public boolean isChannel() {
        return isChannel;
    }

    public void setChannel(boolean channel) {
        isChannel = channel;
    }

    @Override
    protected void convert(BaseViewHolder helper, FinancingEntity item) {
        if (isChannel) {
            RecordTypeEntity recordTypeEntity = TypeIconFactory.createTypeEntityForType(item.type());
            helper.setText(R.id.tv_chart_detail_type, BaseApplication.getsContext().getResources().getString(recordTypeEntity.typeName()));
        } else {
            helper.setText(R.id.tv_chart_detail_type, "");
        }


        if (!TypeIconFactory.isEarningOrExpendType(item.type())) {
            helper.setTextColor(R.id.tv_chart_detail_money, BaseApplication.getsContext().getResources().getColor(R.color.colorPrimary));
        } else {
            helper.setTextColor(R.id.tv_chart_detail_money, BaseApplication.getsContext().getResources().getColor(R.color.primary_text_color));
        }
        helper.setText(R.id.tv_chart_detail_money, "" + item.money());
        helper.setText(R.id.tv_chart_detail_remark, "" + item.remark());
        String date = String.valueOf(item.date());
        helper.setText(R.id.tv_chart_detail_date, "" + date.subSequence(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日");
    }
}
