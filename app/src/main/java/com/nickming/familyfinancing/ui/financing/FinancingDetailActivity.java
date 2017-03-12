package com.nickming.familyfinancing.ui.financing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.db.FinancingDbRepository;
import com.nickming.familyfinancing.db.FinancingItemDataSource;
import com.nickming.familyfinancing.engine.TypeIconFactory;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.RecordTypeEntity;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinancingDetailActivity extends BaseActivity {

    @BindView(R.id.iv_financing_detail_back)
    ImageView mFinancingDetailBack;
    @BindView(R.id.tv_chart_detail_title_name)
    TextView mChartDetailTitleName;
    @BindView(R.id.tv_financing_detail_money)
    TextView mFinancingDetailMoney;
    @BindView(R.id.tv_financing_detail_type)
    TextView mFinancingDetailType;
    @BindView(R.id.tv_financing_detail_account_book)
    TextView mFinancingDetailAccountBook;
    @BindView(R.id.tv_financing_detail_user)
    TextView mFinancingDetailUser;
    @BindView(R.id.tv_financing_detail_date)
    TextView mFinancingDetailDate;
    @BindView(R.id.tv_financing_detail_remark)
    TextView mFinancingDetailRemark;
    @BindView(R.id.btn_financing_detail_edit)
    Button mFinancingDetailEdit;
    @BindView(R.id.btn_financing_detail_delete)
    Button mFinancingDetailDelete;
    @BindView(R.id.tv_financing_detail_paychannel)
    TextView mFinancingDetailpayChannel;

    private FinancingEntity mFinancingEntity;
    private FinancingItemDataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_detail);
        ButterKnife.bind(this);
        mDataSource = FinancingDbRepository.getInstance(this);
        initViews();
        initListener();


    }

    private void initListener() {
        mFinancingDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mFinancingDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mFinancingDetailDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(FinancingDetailActivity.this)
                        .title("提示")
                        .content("是否删除数据?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                long result = mDataSource.deleteFinancingItem(mFinancingEntity);
                                Logger.i("" + result);
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void initViews() {
        if (getIntent() != null) {
            mFinancingEntity = (FinancingEntity) getIntent().getSerializableExtra(Constant.FINANCING_ITEM_ENTITY);
            mFinancingDetailMoney.setText("" + mFinancingEntity.money());
            mFinancingDetailAccountBook.setText("" + mFinancingEntity.financingCategory());
            String date = String.valueOf(mFinancingEntity.date());
            mFinancingDetailDate.setText("" + date.subSequence(0, 4) + "年" + date.substring(4, 6) + "月" + date.substring(6, 8) + "日");
            mFinancingDetailUser.setText("" + mFinancingEntity.user());
            mFinancingDetailRemark.setText("" + mFinancingEntity.remark());

            RecordTypeEntity recordTypeEntity = TypeIconFactory.createTypeEntityForType(mFinancingEntity.type());
            mFinancingDetailType.setText("" + getApplicationContext().getResources()
                    .getString(recordTypeEntity.typeName()));
            mFinancingDetailpayChannel.setText(TypeIconFactory.getChannelName(mFinancingEntity.payChannel()));
        }
    }
}
