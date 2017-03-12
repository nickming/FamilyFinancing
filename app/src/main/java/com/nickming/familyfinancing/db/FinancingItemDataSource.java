package com.nickming.familyfinancing.db;

import android.support.annotation.NonNull;

import com.nickming.familyfinancing.entity.FinancingEntity;

import java.util.List;

import rx.Observable;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/16
 * Time:22:12
 * E-mail:962570483@qq.com
 */

public interface FinancingItemDataSource {

    Observable<List<FinancingEntity>> getAllFinancingItems();

    Observable<List<FinancingEntity>> getFinacingItemsForDate(int fromDate,int toDate);

    long saveFinancingItem(@NonNull FinancingEntity entity);

    long updateFinancingItem(@NonNull FinancingEntity entity);

    long deleteFinancingItem(@NonNull FinancingEntity entity);
}
