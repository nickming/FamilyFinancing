package com.nickming.familyfinancing.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import static android.R.attr.id;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:10:14
 * E-mail:962570483@qq.com
 */
@AutoValue
public abstract class AccountBookEntity implements Parcelable{

    public abstract String name();

    public abstract int date();

    public static AccountBookEntity create(String name,int date)
    {
        return new AutoValue_AccountBookEntity(name,date);
    }

//    public static AccountBookEntity create(String name)
//    {
//        return new AutoValue_AccountBookEntity(name,System.currentTimeMillis());
//    }
}
