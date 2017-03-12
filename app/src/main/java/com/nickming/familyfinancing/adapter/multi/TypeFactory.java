package com.nickming.familyfinancing.adapter.multi;

import android.view.View;

import com.nickming.familyfinancing.entity.FinancingCountEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:22:56
 * E-mail:962570483@qq.com
 */

public interface TypeFactory {

    int type(FinancingEntity financingEntity);

    int type(FinancingCountEntity financingCountEntity);

    BaseViewHolder createViewHolder(int type, View itemView);
}
