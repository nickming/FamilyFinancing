package com.nickming.familyfinancing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.engine.FinancingTypeContract;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import java.util.List;

/**
 * Desc:
 * Author:
 * Date:
 * Time:11:16
 * E-mail:
 */

public class PayTypeAdapter extends BaseQuickAdapter<RecordTypeEntity, BaseViewHolder> {

    private int mCurrentType = FinancingTypeContract.PayType.TYPE_CASH;


    public PayTypeAdapter(List<RecordTypeEntity> data) {
        super(R.layout.item_record_type, data);
    }

    public int getCurrentType() {
        return mCurrentType;
    }

    public void setCurrentType(int mCurrentType) {
        this.mCurrentType = mCurrentType;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordTypeEntity entity) {
        if (entity != null) {
            if (entity.color() != 0) {
                helper.setImageResource(R.id.iv_record_item_icon_background, entity.color());
                helper.setImageResource(R.id.iv_record_item_icon, entity.icon());
                helper.setText(R.id.tv_record_item_type_name, entity.typeName());
                if (entity.type() == mCurrentType) {
                    helper.setTextColor(R.id.tv_record_item_type_name, BaseApplication.getsContext().getResources().getColor(R.color.colorPrimary));
                } else {
                    helper.setTextColor(R.id.tv_record_item_type_name, BaseApplication.getsContext().getResources().getColor(R.color.primary_text_color));
                }

            }
        }
    }
}
