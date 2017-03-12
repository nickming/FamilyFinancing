package com.nickming.familyfinancing.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/15
 * Time:15:15
 * E-mail:962570483@qq.com
 */
public class ChartCountEntity implements Serializable{

    private float percent=0;

    private float sum=0;

    private   List<FinancingEntity> datas=new ArrayList<>();

    public ChartCountEntity() {
    }

    public ChartCountEntity(float percent, float sum) {
        this.percent = percent;
        this.sum = sum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public List<FinancingEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<FinancingEntity> datas) {
        this.datas = datas;
    }
}
