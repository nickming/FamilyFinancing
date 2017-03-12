package com.nickming.familyfinancing.adapter.multi;

import android.view.View;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.FinancingCountEntity;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:44
 * E-mail:962570483@qq.com
 */

public class FinancingCountViewHolder extends BaseViewHolder<FinancingCountEntity> {


    public FinancingCountViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(FinancingCountEntity model, int position, MultiTypeAdapter adapter) {
        TextView date = (TextView) getView(R.id.tv_financing_item_date);
        date.setText(""+model.getDate());
        TextView earning = (TextView) getView(R.id.tv_financing_item_earning);
        earning.setText("收入: " + model.getEarning());
        TextView expend = (TextView) getView(R.id.tv_financing_item_expend);
        expend.setText("支出: " + model.getExpend());
    }
}
