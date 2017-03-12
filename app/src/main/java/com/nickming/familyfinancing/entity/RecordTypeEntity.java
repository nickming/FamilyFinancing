package com.nickming.familyfinancing.entity;

import com.google.auto.value.AutoValue;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:16:00
 * E-mail:962570483@qq.com
 */

@AutoValue
public abstract class RecordTypeEntity{

    public abstract int color();

    public abstract int type();

    public abstract int typeName();

    public abstract int icon();

    public static RecordTypeEntity create(int color,int icon,int typeName,int type)
    {
        return new AutoValue_RecordTypeEntity(color,type,typeName,icon);
    }
}
