package com.nickming.familyfinancing.ui.chart;

import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.db.FinancingDbRepository;
import com.nickming.familyfinancing.db.FinancingItemDataSource;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.ChartCountEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:16:22
 * E-mail:962570483@qq.com
 */

public class ChartPresenter implements ChartContract.Presenter {

    public static final int TYPE_EARNNING = 1;
    public static final int TYPE_EXPEND = 2;

    private ChartContract.View mView;
    private FinancingItemDataSource mFinancingDataSource;

    public ChartPresenter(ChartContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mFinancingDataSource = FinancingDbRepository.getInstance(BaseApplication.getsContext());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void requestChartData(final String accountBookName,final boolean isEarning, int fromDate, int toDate) {
        mFinancingDataSource.getFinacingItemsForDate(fromDate, toDate)
                .map(new Func1<List<FinancingEntity>, List<ChartCountEntity>>() {
                    @Override
                    public List<ChartCountEntity> call(List<FinancingEntity> financingResult) {

                        List<FinancingEntity> financingEntities = new ArrayList<FinancingEntity>();
                        for (FinancingEntity entity : financingResult) {
                            if (entity.financingCategory().equals(accountBookName))
                                financingEntities.add(entity);
                        }

                        List<ChartCountEntity> result = new ArrayList<ChartCountEntity>();
                        Logger.i(""+financingEntities);
                        HashMap<Integer, ChartCountEntity> earningMap = new HashMap<Integer, ChartCountEntity>();
                        HashMap<Integer, ChartCountEntity> expendMap = new HashMap<Integer, ChartCountEntity>();
                        for (int i = 0; i < financingEntities.size(); i++) {
                            FinancingEntity entity = financingEntities.get(i);
                            if (isEarning && !TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                if (earningMap.containsKey(entity.type())) {
                                    ChartCountEntity charCountEntitiy = earningMap.get(entity.type());
                                    charCountEntitiy.setSum((float) (charCountEntitiy.getSum() + entity.money()));
                                    charCountEntitiy.getDatas().add(entity);
                                } else {
                                    ChartCountEntity charCountEntity = new ChartCountEntity(0, 0);
                                    charCountEntity.setSum((float) entity.money());
                                    charCountEntity.getDatas().add(entity);
                                    earningMap.put(entity.type(), charCountEntity);
                                }
                            } else if (!isEarning && TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                if (expendMap.containsKey(entity.type())) {
                                    ChartCountEntity charCountEntitiy = expendMap.get(entity.type());
                                    charCountEntitiy.setSum((float) (charCountEntitiy.getSum() + entity.money()));
                                    charCountEntitiy.getDatas().add(entity);
                                } else {
                                    ChartCountEntity charCountEntity = new ChartCountEntity(0, 0);
                                    charCountEntity.setSum((float) entity.money());
                                    charCountEntity.getDatas().add(entity);
                                    expendMap.put(entity.type(), charCountEntity);
                                }
                            }

                        }
                        HashMap<Integer, ChartCountEntity> temp = new HashMap<Integer, ChartCountEntity>();
                        if (isEarning) {
                            temp = earningMap;
                        } else {
                            temp = expendMap;
                        }
                        float allFinancingSum = 0;
                        for (Map.Entry<Integer, ChartCountEntity> entry : temp.entrySet()) {
                            result.add(entry.getValue());
                            allFinancingSum += entry.getValue().getSum();
                        }
                        for (int i = 0; i < result.size(); i++) {
                            ChartCountEntity item = result.get(i);
                            item.setPercent((float)(Math.round((item.getSum()/allFinancingSum)*100)));
                        }
                        return result;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ChartCountEntity>>() {
                    @Override
                    public void call(List<ChartCountEntity> chartCountEntities) {
                        Logger.i(""+chartCountEntities);
                        mView.showChartAndDetail(chartCountEntities);
                    }
                });
    }

    @Override
    public void requestChannelChartData(final String accountBookName,final boolean isEarning, int fromDate, int toDate) {
        mFinancingDataSource.getFinacingItemsForDate(fromDate, toDate)
                .map(new Func1<List<FinancingEntity>, List<ChartCountEntity>>() {
                    @Override
                    public List<ChartCountEntity> call(List<FinancingEntity> financingResult) {

                        List<FinancingEntity> financingEntities = new ArrayList<FinancingEntity>();
                        for (FinancingEntity entity : financingResult) {
                            if (entity.financingCategory().equals(accountBookName))
                                financingEntities.add(entity);
                        }

                        List<ChartCountEntity> result = new ArrayList<ChartCountEntity>();
                        Logger.i(""+financingEntities);
                        HashMap<Integer, ChartCountEntity> earningMap = new HashMap<Integer, ChartCountEntity>();
                        HashMap<Integer, ChartCountEntity> expendMap = new HashMap<Integer, ChartCountEntity>();
                        for (int i = 0; i < financingEntities.size(); i++) {
                            FinancingEntity entity = financingEntities.get(i);
                            if (isEarning && !TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                if (earningMap.containsKey(entity.payChannel())) {
                                    ChartCountEntity charCountEntitiy = earningMap.get(entity.payChannel());
                                    charCountEntitiy.setSum((float) (charCountEntitiy.getSum() + entity.money()));
                                    charCountEntitiy.getDatas().add(entity);
                                } else {
                                    ChartCountEntity charCountEntity = new ChartCountEntity(0, 0);
                                    charCountEntity.setSum((float) entity.money());
                                    charCountEntity.getDatas().add(entity);
                                    earningMap.put(entity.payChannel(), charCountEntity);
                                }
                            } else if (!isEarning && TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                if (expendMap.containsKey(entity.payChannel())) {
                                    ChartCountEntity charCountEntitiy = expendMap.get(entity.payChannel());
                                    charCountEntitiy.setSum((float) (charCountEntitiy.getSum() + entity.money()));
                                    charCountEntitiy.getDatas().add(entity);
                                } else {
                                    ChartCountEntity charCountEntity = new ChartCountEntity(0, 0);
                                    charCountEntity.setSum((float) entity.money());
                                    charCountEntity.getDatas().add(entity);
                                    expendMap.put(entity.payChannel(), charCountEntity);
                                }
                            }
                        }
                        HashMap<Integer, ChartCountEntity> temp = new HashMap<Integer, ChartCountEntity>();
                        if (isEarning) {
                            temp = earningMap;
                        } else {
                            temp = expendMap;
                        }
                        float allFinancingSum = 0;
                        for (Map.Entry<Integer, ChartCountEntity> entry : temp.entrySet()) {
                            result.add(entry.getValue());
                            allFinancingSum += entry.getValue().getSum();
                        }
                        for (int i = 0; i < result.size(); i++) {
                            ChartCountEntity item = result.get(i);
                            item.setPercent((float)(Math.round((item.getSum()/allFinancingSum)*100)));
                        }
                        return result;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ChartCountEntity>>() {
                    @Override
                    public void call(List<ChartCountEntity> chartCountEntities) {
                        Logger.i(""+chartCountEntities);
                        mView.showChartAndDetail(chartCountEntities);
                    }
                });
    }

}
