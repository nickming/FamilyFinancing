package com.nickming.familyfinancing.ui.chart;

import com.nickming.familyfinancing.base.BasePresenter;
import com.nickming.familyfinancing.base.BaseView;
import com.nickming.familyfinancing.entity.ChartCountEntity;

import java.util.List;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:16:22
 * E-mail:962570483@qq.com
 */

public interface ChartContract {

    interface View extends BaseView<Presenter> {

        void showSelectDate();

        void showItemDetail(ChartCountEntity chartCountEntity);

        void showChartAndDetail(List<ChartCountEntity> chartCountEntities);

        void back();
    }

    interface Presenter extends BasePresenter {
        void requestChartData(String accountBookName,boolean isEarning, int fromDate, int toDate);

        void requestChannelChartData(String accountBookName,boolean isEarning, int fromDate, int toDate);
    }
}
