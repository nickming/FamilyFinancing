package com.nickming.familyfinancing.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.ChartDetailListAdapter;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.ChartCountEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartDetailActivity extends BaseActivity {

    @BindView(R.id.rv_chart_detail)
    RecyclerView mChartDetailRv;
    @BindView(R.id.iv_chart_detail_back)
    ImageView mChartDetailBackIv;
    @BindView(R.id.tv_chart_detail_title_name)
    TextView mChartDetailTitleNameTv;
    @BindView(R.id.tv_chart_detail_percent)
    TextView mChartDetailPercentTv;
    @BindView(R.id.tv_chart_detail_type_name)
    TextView mChartDetailTypeNameTv;
    @BindView(R.id.tv_chart_detail_sum)
    TextView mChartDetailSumTv;

    private boolean isChannel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_detail);
        ButterKnife.bind(this);

        mChartDetailBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mChartDetailRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        if (intent.getSerializableExtra(Constant.CHART_COUNT_ENTITY) != null) {
            isChannel=getIntent().getBooleanExtra(Constant.CHART_COUNT_ENTITY_CHANNEL,false);
            ChartCountEntity chartCountEntity = (ChartCountEntity) intent.getSerializableExtra(Constant.CHART_COUNT_ENTITY);
            ChartDetailListAdapter adapter=new ChartDetailListAdapter(chartCountEntity.getDatas());
            adapter.setChannel(isChannel);
            mChartDetailRv.setAdapter(adapter);
            RecordTypeEntity recordTypeEntity = null;
            if (!isChannel)
                recordTypeEntity = TypeIconFactory.createTypeEntityForType(chartCountEntity.getDatas().get(0).type());
            else
                recordTypeEntity = TypeIconFactory.createTypeEntityForType(chartCountEntity.getDatas().get(0).payChannel());

            String typeName = getResources().getString(recordTypeEntity.typeName());

            mChartDetailTitleNameTv.setText("" + typeName);
            mChartDetailPercentTv.setText("" + chartCountEntity.getPercent() + "%");
            mChartDetailTypeNameTv.setText("" + typeName);
            mChartDetailSumTv.setText("" + chartCountEntity.getSum() + "元");
        }
    }


}
