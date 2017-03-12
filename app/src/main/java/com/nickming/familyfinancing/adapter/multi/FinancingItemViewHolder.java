package com.nickming.familyfinancing.adapter.multi;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:43
 * E-mail:962570483@qq.com
 */

public class FinancingItemViewHolder extends BaseViewHolder<FinancingEntity> {

    public FinancingItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setUpView(final FinancingEntity model, int position, MultiTypeAdapter adapter) {
        RecordTypeEntity recordTypeEntity = TypeIconFactory.createTypeEntityForType(model.type());
        CircleImageView circleImageView = (CircleImageView) getView(R.id.iv_financing_item_type_background);
        circleImageView.setImageResource(recordTypeEntity.color());
        ImageView icon = (ImageView) getView(R.id.iv_financing_item_type_icon);
        icon.setImageResource(recordTypeEntity.icon());
        TextView typeName = (TextView) getView(R.id.tv_financing_item_type);
        typeName.setText(recordTypeEntity.typeName());
        TextView money = (TextView) getView(R.id.tv_financing_item_money);
        money.setText("" + model.money());
        if (TypeIconFactory.isEarningOrExpendType(model.type()))
            money.setTextColor(Color.parseColor("#212121"));
        else
            money.setTextColor(Color.parseColor("#4caf50"));

        getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(model);
            }
        });
    }
}
