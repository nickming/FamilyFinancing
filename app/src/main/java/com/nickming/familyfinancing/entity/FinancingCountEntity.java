package com.nickming.familyfinancing.entity;

import com.nickming.familyfinancing.adapter.multi.TypeFactory;
import com.nickming.familyfinancing.adapter.multi.Visitable;

import java.io.Serializable;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:23:17
 * E-mail:962570483@qq.com
 */
public class FinancingCountEntity implements Visitable,Serializable{

    private double earning=0;

    private double expend=0;

    private int date;

    public FinancingCountEntity() {
    }

    public FinancingCountEntity(double earning, double expend, int date) {
        this.earning = earning;
        this.expend = expend;
        this.date = date;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    public double getExpend() {
        return expend;
    }

    public void setExpend(double expend) {
        this.expend = expend;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
