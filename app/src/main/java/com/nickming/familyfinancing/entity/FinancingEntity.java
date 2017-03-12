package com.nickming.familyfinancing.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.nickming.familyfinancing.adapter.multi.TypeFactory;
import com.nickming.familyfinancing.adapter.multi.Visitable;
import com.nickming.familyfinancing.util.TimeUtil;

import java.io.Serializable;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:15
 * E-mail:962570483@qq.com
 */

@AutoValue
public abstract class FinancingEntity implements Visitable, Parcelable, Serializable {

    public abstract int type();

    public abstract double money();

    public abstract String financingCategory();

    public abstract String user();

    public abstract String remark();

    //规定格式为20170101
    public abstract int date();

    public abstract int id();

    public abstract int payChannel();

    public static FinancingEntity create(int id, int type, int payChannel, double money, String financingCategory, String user,
                                         String remark, int date) {
        return new AutoValue_FinancingEntity(type, money, financingCategory, user, remark, date, id, payChannel);
    }

    public static FinancingEntity create(int id, int type, int payChannel, double money, String financingCategory, String user,
                                         String remark) {
        int date = Integer.valueOf(TimeUtil.getCurrentTime(TimeUtil.DATABASE_TIME_FORMAT));
        return new AutoValue_FinancingEntity(type, money, financingCategory, user, remark, date, id, payChannel);
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
