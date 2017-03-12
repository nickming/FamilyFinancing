package com.nickming.familyfinancing.ui.financing;

import com.nickming.familyfinancing.adapter.multi.Visitable;
import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.db.FinancingDbRepository;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.FinancingCountEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.util.SharePreferenceUtil;
import com.nickming.familyfinancing.util.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:15:46
 * E-mail:962570483@qq.com
 */

public class FinancingPresenter implements FinancingContract.Presenter {


    private static final String TAG = "FinancingPresenter";

    private FinancingContract.View mView;
    private FinancingDbRepository mFinancingDbRepository;

    public FinancingPresenter(final FinancingContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mFinancingDbRepository = FinancingDbRepository.getInstance(BaseApplication.getsContext());
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void start() {

    }

    @Override
    public void getListDatas(final String accountBookName) {
        mFinancingDbRepository.getAllFinancingItems()
                .map(new Func1<List<FinancingEntity>, List<Visitable>>() {
                    @Override
                    public List<Visitable> call(List<FinancingEntity> financingResult) {

                        List<FinancingEntity> financingEntities = new ArrayList<FinancingEntity>();
                        for (FinancingEntity entity : financingResult) {
                            if (entity.financingCategory().equals(accountBookName))
                                financingEntities.add(entity);
                        }

                        Logger.i("查询结果:" +accountBookName+":"+ financingEntities);

                        Collections.reverse(financingEntities);
                        List<Visitable> result = new ArrayList<Visitable>();
                        HashMap<Integer, FinancingCountEntity> hashMap = new HashMap<Integer, FinancingCountEntity>();
                        for (int i = 0; i < financingEntities.size(); i++) {
                            FinancingEntity entity = financingEntities.get(i);
                            if (hashMap.containsKey(entity.date())) {
                                FinancingCountEntity financingCountEntity = hashMap.get(entity.date());
                                if (TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                    financingCountEntity.setEarning(entity.money() + financingCountEntity.getEarning());
                                } else {
                                    financingCountEntity.setExpend(entity.money() + financingCountEntity.getExpend());
                                }
                                result.add(entity);
                            } else {
                                FinancingCountEntity financingCountEntity = new FinancingCountEntity(0, 0, entity.date());
                                if (TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                    financingCountEntity.setEarning(entity.money());
                                } else {
                                    financingCountEntity.setExpend(entity.money());
                                }
                                result.add(financingCountEntity);
                                result.add(entity);
                                hashMap.put(entity.date(), financingCountEntity);
                            }
                        }
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Visitable>>() {
                    @Override
                    public void call(List<Visitable> visitables) {
                        mView.showListData(visitables);
                        requestCurrentMonthExpendAndEarning(accountBookName);
                    }
                });


    }

    @Override
    public void requestCurrentMonthExpendAndEarning(final String accountBookName) {
        final int fromTime = Integer.valueOf(TimeUtil.getFirstDayOfMonth(TimeUtil.DATABASE_TIME_FORMAT));
        final int currentTime = Integer.valueOf(TimeUtil.getCurrentTime(TimeUtil.DATABASE_TIME_FORMAT));
        Observable.create(new Observable.OnSubscribe<double[]>() {
            @Override
            public void call(final Subscriber<? super double[]> subscriber) {
                mFinancingDbRepository.getFinacingItemsForDate(fromTime, currentTime)
                        .subscribe(new Action1<List<FinancingEntity>>() {
                            @Override
                            public void call(List<FinancingEntity> financingResult) {

                                List<FinancingEntity> financingEntities = new ArrayList<FinancingEntity>();
                                for (FinancingEntity entity : financingResult) {
                                    if (entity.financingCategory().equals(accountBookName))
                                        financingEntities.add(entity);
                                }

                                double[] result = new double[2];
                                double earning = 0, expend = 0;
                                for (FinancingEntity entity : financingEntities) {
                                    if (TypeIconFactory.isEarningOrExpendType(entity.type())) {
                                        expend += entity.money();
                                    } else {
                                        earning += entity.money();
                                    }
                                }
                                result[0] = expend;
                                result[1] = earning;
                                subscriber.onNext(result);
                                subscriber.onCompleted();
                            }
                        });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<double[]>() {
                    @Override
                    public void call(double[] doubles) {
                        mView.showMimeEarningAndExpend(Integer.valueOf(TimeUtil.getCurrentTime(TimeUtil.FORMAT_MONTH)),
                                doubles[1], doubles[0]);
                        double budget=Double.valueOf(SharePreferenceUtil.getString(Constant.BUDGET_SETTING,"0"));
                        String detal= String.valueOf(budget-doubles[0]);
                        mView.showCurrentBudget(detal);
                    }
                });
    }

}
