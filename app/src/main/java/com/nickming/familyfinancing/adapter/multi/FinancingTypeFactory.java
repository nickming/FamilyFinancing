package com.nickming.familyfinancing.adapter.multi;

import android.view.View;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.FinancingCountEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:18
 * E-mail:962570483@qq.com
 */

public class FinancingTypeFactory implements TypeFactory {

    private final int TYPE_RESOURCE_FINANCING_ITEM = R.layout.item_financing_item;

    private final int TYPE_RESOURCE_FINANCING_COUNT = R.layout.item_financing_count;

    @Override
    public int type(FinancingEntity financingEntity) {
        return TYPE_RESOURCE_FINANCING_ITEM;
    }

    @Override
    public int type(FinancingCountEntity financingCountEntity) {
        return TYPE_RESOURCE_FINANCING_COUNT;
    }

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {
        if (TYPE_RESOURCE_FINANCING_COUNT == type) {
            return new FinancingCountViewHolder(itemView);
        } else if (TYPE_RESOURCE_FINANCING_ITEM == type) {
            return new FinancingItemViewHolder(itemView);
        }
        return null;
    }
}
