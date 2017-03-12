package com.nickming.familyfinancing.ui.record;

import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.db.FinancingDbRepository;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.orhanobut.logger.Logger;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:11:15
 * E-mail:962570483@qq.com
 */

public class RecordPresenter implements RecordContract.Presenter {

    private static final String TAG = "RecordPresenter";

    private RecordContract.View mView;

    private FinancingDbRepository mRepository;

    public RecordPresenter(RecordContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mRepository = FinancingDbRepository.getInstance(BaseApplication.getsContext());
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void start() {
        mView.showExpendList();
    }


    @Override
    public void saveRecord(FinancingEntity financingEntity) {
        long result = mRepository.saveFinancingItem(financingEntity);
        Logger.i("存储结果:"+result);
        mView.exit();
    }

}
