package com.nickming.familyfinancing.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.ChartDetailAdapter;
import com.nickming.familyfinancing.base.BaseApplication;
import com.nickming.familyfinancing.base.BaseFragment;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.ChartCountEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;
import com.nickming.familyfinancing.util.TimeUtil;
import com.nickming.familyfinancing.widget.EarningAndExpendChangeBox;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:16:20
 * E-mail:962570483@qq.com
 */

public class ChartFragment extends BaseFragment implements ChartContract.View {

    private static final String TAG = "ChartFragment";

    @BindView(R.id.iv_chart_back)
    ImageView mBack;
    @BindView(R.id.chart_change_bar)
    EarningAndExpendChangeBox mChartChangeBar;
    @BindView(R.id.tv_chart_from_date)
    TextView mFromDateTv;
    @BindView(R.id.tv_chart_to_date)
    TextView mToDateTv;
    @BindView(R.id.pie_chart)
    PieChartView mPieChart;
    @BindView(R.id.rv_chart)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_chart_change)
    TextView mChartChangeTv;

    private DatePickerDialog mDatePickerDialog;

    private ChartContract.Presenter mPresenter;
    private ChartDetailAdapter mAdapter;
    private List<ChartCountEntity> mChartCountList = new ArrayList<>();
    private int mCurrentType = ChartPresenter.TYPE_EXPEND;
    private int mCurrentFromDate;
    private int mCurrentToDate;
    private String mCurrentBookName = "mime";
    private boolean isChannel = false;

    public static ChartFragment newInstance() {

        Bundle args = new Bundle();

        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        if (getActivity().getIntent() != null) {
            mCurrentBookName = getActivity().getIntent().getStringExtra(Constant.ACCOUNT_BOOK_NAME);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = new ChartDetailAdapter(mChartCountList));
        mPieChart.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        mAdapter.setChannel(false);
        mAdapter.setClickItemListener(new ChartDetailAdapter.OnCLickItemClickListener() {
            @Override
            public void clickItem(ChartCountEntity chartCountEntity) {
                showItemDetail(chartCountEntity);
            }
        });

        mChartChangeBar.setOnChnageStyleListener(new EarningAndExpendChangeBox.OnChangeStyleListener() {
            @Override
            public void onChangeToEarning() {
                if (!isChannel)
                    mPresenter.requestChartData(mCurrentBookName, true, mCurrentFromDate, mCurrentToDate);
                else
                    mPresenter.requestChannelChartData(mCurrentBookName, true, mCurrentFromDate, mCurrentToDate);
            }

            @Override
            public void onChangeToExpend() {
                if (!isChannel)
                    mPresenter.requestChartData(mCurrentBookName, false, mCurrentFromDate, mCurrentToDate);
                else
                    mPresenter.requestChannelChartData(mCurrentBookName, false, mCurrentFromDate, mCurrentToDate);
            }
        });

        //日历选择器的初始化
        Calendar now = Calendar.getInstance();
        mDatePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,
                                          int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
                        Log.i(TAG, "onDateSet: " + dayOfMonth + "," + dayOfMonthEnd);
                        int fromDate = year * 10000 + (monthOfYear + 1) * 100 + dayOfMonth;
                        int toDate = yearEnd * 10000 + (monthOfYearEnd + 1) * 100 + dayOfMonthEnd;
                        Logger.i("from:" + fromDate);
                        Logger.i("to:" + toDate);
                        if (!isChannel) {
                            if (mCurrentType == ChartPresenter.TYPE_EXPEND) {
                                mPresenter.requestChartData(mCurrentBookName, false, fromDate, toDate);
                            } else {
                                mPresenter.requestChartData(mCurrentBookName, true, fromDate, toDate);
                            }
                        } else {
                            if (mCurrentType == ChartPresenter.TYPE_EXPEND) {
                                mPresenter.requestChannelChartData(mCurrentBookName, false, fromDate, toDate);
                            } else {
                                mPresenter.requestChannelChartData(mCurrentBookName, true, fromDate, toDate);
                            }
                        }

                        mCurrentFromDate = fromDate;
                        mCurrentToDate = toDate;
                        mFromDateTv.setText(convertIntToStr(mCurrentFromDate));
                        mToDateTv.setText(convertIntToStr(mCurrentToDate));
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        //选择日期
        mFromDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
        mToDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
        mCurrentFromDate = Integer.valueOf(TimeUtil.getFirstDayOfMonth(TimeUtil.DATABASE_TIME_FORMAT));
        mCurrentToDate = Integer.valueOf(TimeUtil.getCurrentTime(TimeUtil.DATABASE_TIME_FORMAT));
        mFromDateTv.setText(convertIntToStr(mCurrentFromDate));
        mToDateTv.setText(convertIntToStr(mCurrentToDate));
        mPresenter.requestChartData(mCurrentBookName, false, mCurrentFromDate, mCurrentToDate);

        mChartChangeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("更改")
                        .items(R.array.chart_list)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                if (text.equals("消费类型统计")) {
                                    isChannel = false;
                                    mAdapter.setChannel(false);
                                    mPresenter.requestChartData(mCurrentBookName, false, mCurrentFromDate, mCurrentToDate);
                                } else {
                                    mAdapter.setChannel(true);
                                    isChannel = true;
                                    mPresenter.requestChannelChartData(mCurrentBookName, false, mCurrentFromDate, mCurrentToDate);
                                }

                                return false;
                            }
                        })
                        .positiveText("确定")
                        .show();
            }
        });
    }

    private String convertIntToStr(int date) {
        String result = String.valueOf(date);
        return result.substring(4, 6) + "-" + result.substring(6, result.length());
    }

    @Override
    public void setPresenter(ChartContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showSelectDate() {
        mDatePickerDialog.show(getActivity().getFragmentManager(), TAG);
    }

    @Override
    public void showItemDetail(ChartCountEntity chartCountEntity) {

        Intent intent = new Intent(getActivity(), ChartDetailActivity.class);
        intent.putExtra(Constant.CHART_COUNT_ENTITY, chartCountEntity);
        intent.putExtra(Constant.CHART_COUNT_ENTITY_CHANNEL, isChannel);
        getActivity().startActivity(intent);

    }

    @Override
    public void showChartAndDetail(List<ChartCountEntity> chartCountEntities) {
        if (chartCountEntities != null && chartCountEntities.size() != 0) {
            List<SliceValue> sliceValueList = new ArrayList<>();
            int size = chartCountEntities.size();
            float sum = 0;
            for (int i = 0; i < size; i++) {
                ChartCountEntity entity = chartCountEntities.get(i);
                if (entity.getDatas() != null && entity.getDatas().size() != 0) {
                    RecordTypeEntity recordTypeEntity = null;
                    if (!isChannel)
                        recordTypeEntity = TypeIconFactory.createTypeEntityForType(entity.getDatas().get(0).type());
                    else
                        recordTypeEntity = TypeIconFactory.createTypeEntityForType(entity.getDatas().get(0).payChannel());
                    SliceValue sliceValue = new SliceValue(entity.getSum(), BaseApplication.getsContext().getResources()
                            .getColor(recordTypeEntity.color()));
                    sliceValueList.add(sliceValue);
                }
                sum += entity.getSum();
            }

            PieChartData charData = new PieChartData(sliceValueList);
            charData.setHasLabels(true);
            charData.setHasLabelsOutside(false);
            charData.setHasCenterCircle(true);
            charData.setCenterText1("" + sum);
            charData.setCenterText1FontSize(25);
            if (TypeIconFactory.isEarningOrExpendType(chartCountEntities.get(0).getDatas().get(0).type())) {
                charData.setCenterText2("总支出");
            } else {
                charData.setCenterText2("总收入");
            }
            mPieChart.setPieChartData(charData);
            mChartCountList.clear();
            mChartCountList.addAll(chartCountEntities);
            mAdapter.notifyDataSetChanged();

        } else {
            mChartCountList.clear();
            mAdapter.notifyDataSetChanged();
            mPieChart.setPieChartData(null);
        }
    }


    @Override
    public void back() {
        getActivity().finish();
    }

}
