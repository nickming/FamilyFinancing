package com.nickming.familyfinancing.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:23
 * E-mail:962570483@qq.com
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        this.mItemView = itemView;
    }

    public View getView(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = mItemView.findViewById(resId);
            mViews.put(resId, view);
        }
        return view;
    }

    protected View getItemView() {
        if (mItemView != null)
            return this.mItemView;
        else
            return null;
    }

    public abstract void setUpView(T model, int position, MultiTypeAdapter adapter);
}
