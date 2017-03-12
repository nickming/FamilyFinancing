package com.nickming.familyfinancing.adapter.multi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:27
 * E-mail:962570483@qq.com
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    private TypeFactory mTypeFactory;
    private List<Visitable> mDatas;

    public MultiTypeAdapter() {
        this.mDatas = new ArrayList<>();
        this.mTypeFactory=new com.nickming.familyfinancing.adapter.multi.FinancingTypeFactory();
    }

    public MultiTypeAdapter(List<Visitable> datas) {
        this.mDatas = datas;
        this.mTypeFactory=new com.nickming.familyfinancing.adapter.multi.FinancingTypeFactory();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View itemView=View.inflate(context,viewType,null);
        return mTypeFactory.createViewHolder(viewType,itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position),position,this);
    }

    @Override
    public int getItemCount() {
        if (mDatas==null)
            return 0;
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).type(mTypeFactory);
    }

    public void setDatas(List<Visitable> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}
