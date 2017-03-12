package com.nickming.familyfinancing.ui.financing;

import com.nickming.familyfinancing.adapter.multi.Visitable;
import com.nickming.familyfinancing.base.BasePresenter;
import com.nickming.familyfinancing.base.BaseView;
import com.nickming.familyfinancing.entity.FinancingEntity;

import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:15:59
 * E-mail:962570483@qq.com
 */

public interface FinancingContract {

    interface View extends BaseView<Presenter>
    {
        void showListData(List<Visitable> datas);

        void showMimeEarningAndExpend(int month,double earning,double expend);

        void showFinancingItemDetail(FinancingEntity financingEntity);

        void showCurrentBudget(String budget);
    }

    interface Presenter extends BasePresenter
    {
        void start();

        void getListDatas(String accountBookName);

        void requestCurrentMonthExpendAndEarning(String accountBookName);
    }
}
