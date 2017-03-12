package com.nickming.familyfinancing.ui.record;

import com.nickming.familyfinancing.base.BasePresenter;
import com.nickming.familyfinancing.base.BaseView;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:11:15
 * E-mail:962570483@qq.com
 */

public interface RecordContract {

    interface Presenter extends BasePresenter
    {
        void start();

        void saveRecord(FinancingEntity financingEntity);
    }

    interface View extends BaseView<Presenter>
    {
        void exit();

        void showEarningList();

        void showExpendList();

        void showCurrentType(RecordTypeEntity entity);

        void showSum(String sum);

        void showRemarkDialog();
    }
}
