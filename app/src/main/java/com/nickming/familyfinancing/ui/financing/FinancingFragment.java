package com.nickming.familyfinancing.ui.financing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.adapter.multi.MultiTypeAdapter;
import com.nickming.familyfinancing.adapter.multi.Visitable;
import com.nickming.familyfinancing.base.BaseFragment;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.engine.AccountBookManager;
import com.nickming.familyfinancing.entity.AccountBookEntity;
import com.nickming.familyfinancing.entity.FinancingEntity;
import com.nickming.familyfinancing.entity.NightModeEvent;
import com.nickming.familyfinancing.ui.chart.ChartActivity;
import com.nickming.familyfinancing.ui.news.NewsActivity;
import com.nickming.familyfinancing.ui.record.RecordActivity;
import com.nickming.familyfinancing.ui.setting.SettingActivity;
import com.nickming.familyfinancing.ui.shares.SharesActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:16:00
 * E-mail:962570483@qq.com
 */

public class FinancingFragment extends BaseFragment implements FinancingContract.View {


    @BindView(R.id.financing_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.financing_collapsing_toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.rv_financing)
    RecyclerView mRecyclerView;
    @BindView(R.id.financing_coordinator_layout)
    CoordinatorLayout mRootLayout;
    @BindView(R.id.tv_financing_mime_earning)
    TextView mFinancingMimeEarning;
    @BindView(R.id.tv_financing_mime_expend)
    TextView mFinancingMimeExpend;
    @BindView(R.id.tv_financing_mime_earning_name)
    TextView mFinancingMimeEarningName;
    @BindView(R.id.tv_financing_mime_expend_name)
    TextView mFinancingMimeExpendName;
    @BindView(R.id.iv_financing_header_bg)
    ImageView mHeaderBackground;

    private ImageView mChartIv;
    private ImageView mAddIv;
    private ImageView mSettingIv;
    private ImageView mNewsIv;
    private ImageView mSharesIv;

    private DrawerLayout mDrawerLayout;
    private AccountBookManager mAccountBookManager;

    private FinancingContract.Presenter mPresenter;
    private MultiTypeAdapter mAdapter;
    private List<Visitable> mFinancingItemList = new ArrayList<>();
    private WeakReference<FinancingActivity> mRootActivity;

    private String mCurrentAccountBookName = "mime";


    public static FinancingFragment newInstance() {

        Bundle args = new Bundle();

        FinancingFragment fragment = new FinancingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_financing, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showHeaderBackground();

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_financing);
        mAccountBookManager = new AccountBookManager(getActivity());

        mToolbarLayout.setTitle("");



        mNewsIv = (ImageView) getActivity().findViewById(R.id.iv_bottom_news);
        mNewsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);
            }
        });

        mSharesIv = (ImageView) getActivity().findViewById(R.id.iv_bottom_shares);
        mSharesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SharesActivity.class);
                startActivity(intent);
            }
        });

        mChartIv = (ImageView) getActivity().findViewById(R.id.iv_bottom_chart);
        mChartIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChartActivity.class);
                intent.putExtra(Constant.ACCOUNT_BOOK_NAME, mCurrentAccountBookName);
                startActivity(intent);
            }
        });
        mAddIv = (ImageView) getActivity().findViewById(R.id.iv_bottom_add);
        mAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecordActivity.class);
                intent.putExtra(Constant.ACCOUNT_BOOK_NAME, mCurrentAccountBookName);
                startActivity(intent);
            }
        });
        mSettingIv = (ImageView) getActivity().findViewById(R.id.iv_bottom_setting);
        mSettingIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        mAdapter = new MultiTypeAdapter(mFinancingItemList);
        mRecyclerView.setAdapter(mAdapter);

        mRootActivity = new WeakReference<FinancingActivity>((FinancingActivity) getActivity());
        mRootActivity.get().initToolbar(mToolbar);

        mPresenter.subscribe();
        mPresenter.getListDatas(mCurrentAccountBookName);
    }


    @Override
    public void showCurrentBudget(String budget) {
        mToolbarLayout.setTitle("当月剩余:" + budget);
    }

    @Override
    public void setPresenter(FinancingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        mRootActivity.clear();
        mAccountBookManager.release();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeHeaderBackground(NightModeEvent nightModeEvent) {
        showHeaderBackground();
    }

    private void showHeaderBackground() {
        boolean isNight = mSp.getBoolean(Constant.NIGHT_MODE, false);
        if (isNight) {
            mHeaderBackground.setImageResource(R.mipmap.financing_dark_bg);
        } else {
            mHeaderBackground.setImageResource(R.mipmap.financing_day_bg);
        }
    }

    @Override
    public void showListData(List<Visitable> datas) {
        mFinancingItemList.clear();
        mFinancingItemList.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMimeEarningAndExpend(int month, double earning, double expend) {
        mFinancingMimeEarningName.setText(month + "月收入");
        mFinancingMimeExpendName.setText(month + "月支出");
        mFinancingMimeExpend.setText("" + expend);
        mFinancingMimeEarning.setText("" + earning);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void showFinancingItemDetail(FinancingEntity financingEntity) {
        Intent intent = new Intent(getActivity(), FinancingDetailActivity.class);
        intent.putExtra(Constant.FINANCING_ITEM_ENTITY, (Parcelable) financingEntity);
        getActivity().startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCurrentAccountBook(AccountBookEntity accountBookEntity) {
        Logger.i(accountBookEntity.name());
        mCurrentAccountBookName = accountBookEntity.name();
        mPresenter.getListDatas(mCurrentAccountBookName);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
