package com.nickming.familyfinancing.adapter;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.AccountBookEntity;

import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:10:14
 * E-mail:962570483@qq.com
 */

public class AccountBookAdapter extends BaseItemDraggableAdapter<AccountBookEntity,BaseViewHolder> {

    private String mCurrentBookName = "";


    public AccountBookAdapter(List<AccountBookEntity> datas) {
        super(R.layout.item_account_book, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, AccountBookEntity item) {
        viewHolder.setText(R.id.tv_account_book_item_name, item.name());
        if (mCurrentBookName.equals(item.name())) {
            viewHolder.setImageResource(R.id.iv_account_book_item_icon, R.mipmap.book_select);
        } else {
            viewHolder.setImageResource(R.id.iv_account_book_item_icon, R.mipmap.book_normal);
        }
    }
}
