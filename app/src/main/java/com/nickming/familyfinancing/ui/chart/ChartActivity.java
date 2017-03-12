package com.nickming.familyfinancing.ui.chart;

import android.os.Bundle;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.util.ActivityUtil;

public class ChartActivity extends BaseActivity {

    private ChartPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        ChartFragment chartFragment = (ChartFragment) getSupportFragmentManager().findFragmentById(R.id.fl_chart_content);
        if (chartFragment == null) {
            chartFragment = ChartFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chartFragment, R.id.fl_chart_content);
        }
        mPresenter = new ChartPresenter(chartFragment);

    }
}
