package com.nickming.familyfinancing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.ChartCountEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/15
 * Time:11:34
 * E-mail:962570483@qq.com
 */

public class ChartDetailAdapter extends RecyclerView.Adapter<ChartDetailAdapter.ViewHolder> {

    private List<ChartCountEntity> mDatas = new ArrayList<>();
    private OnCLickItemClickListener mClickItemListener;
    private boolean isChannel = false;

    public ChartDetailAdapter(List<ChartCountEntity> mDatas) {
        this.mDatas = mDatas;
    }

    public boolean isChannel() {
        return isChannel;
    }

    public void setChannel(boolean channel) {
        isChannel = channel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chart_detail, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ChartCountEntity entity = mDatas.get(position);
        if (entity != null) {
            if (entity.getDatas().get(0) != null) {
                FinancingEntity financingEntity = entity.getDatas().get(0);
                RecordTypeEntity typeEntity=null;
                if (!isChannel)
                   typeEntity = TypeIconFactory.createTypeEntityForType(financingEntity.type());
                else
                    typeEntity=TypeIconFactory.createTypeEntityForType(financingEntity.payChannel());
                holder.background.setImageResource(typeEntity.color());
                holder.type.setImageResource(typeEntity.icon());
                holder.typename.setText(typeEntity.typeName());
                holder.percent.setText("" + entity.getPercent() + "%");
                holder.money.setText("" + entity.getSum());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mClickItemListener != null) {
                            mClickItemListener.clickItem(mDatas.get(position));
                        }
                    }
                });
            }


        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView background;
        ImageView type;
        TextView typename;
        TextView percent;
        TextView money;

        public ViewHolder(View itemView) {
            super(itemView);
            background = (CircleImageView) itemView.findViewById(R.id.iv_chart_item_icon_background);
            type = (ImageView) itemView.findViewById(R.id.iv_chart_item_icon);
            typename = (TextView) itemView.findViewById(R.id.tv_chart_item_type_name);
            percent = (TextView) itemView.findViewById(R.id.tv_chart_item_type_percent);
            money = (TextView) itemView.findViewById(R.id.tv_chart_item_money);
        }
    }

    public void setClickItemListener(OnCLickItemClickListener mClickItemListener) {
        this.mClickItemListener = mClickItemListener;
    }

    public interface OnCLickItemClickListener {
        void clickItem(ChartCountEntity chartCountEntity);
    }
}
