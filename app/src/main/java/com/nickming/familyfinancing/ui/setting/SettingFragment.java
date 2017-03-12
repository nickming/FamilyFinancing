package com.nickming.familyfinancing.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.entity.NightModeEvent;
import com.nickming.familyfinancing.util.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/15
 * Time:15:51
 * E-mail:962570483@qq.com
 */

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    private FrameLayout mRootView;

    private Preference mBudgetSetting;
    private Preference mClearData;
    private Preference mClearCache;
    private SwitchPreference mNightMode;
    private SwitchPreference mGesturePassword;
    private SharedPreferences mSp;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        initPreference();
        initClickListener();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRootView = (FrameLayout) getActivity().findViewById(R.id.fl_setting_container);

        mSp=getPreferenceScreen().getSharedPreferences();
        mSp.registerOnSharedPreferenceChangeListener(this);
    }

    private void initClickListener() {
        mBudgetSetting.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title("预算设置")
                        .content("设置当前月份的预算")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("请输入预算金额", "", true, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                SharePreferenceUtil.setString(Constant.BUDGET_SETTING,input.toString());
                                mBudgetSetting.setSummary("当月预算金额为:"+SharePreferenceUtil.getString(Constant.BUDGET_SETTING,"当月暂无预算设置"));
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });
        mClearData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
        mClearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showClearCache();
                return false;
            }
        });
    }

    private void showClearCache() {
        final MaterialDialog progressDialog = new MaterialDialog.Builder(getActivity())
                .title("清楚缓存")
                .content("清除中")
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .build();
        progressDialog.show();
        Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    Thread.sleep(1500);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        progressDialog.dismiss();
                        Snackbar.make(mRootView, "已清除缓存", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void initPreference() {
        mBudgetSetting = getPreferenceManager().findPreference(Constant.BUDGET_SETTING);
        mBudgetSetting.setSummary("当月预算金额为:"+SharePreferenceUtil.getString(Constant.BUDGET_SETTING,"当月暂无预算设置"));
        mClearCache = getPreferenceManager().findPreference("clear_cache");
        mClearData = getPreferenceManager().findPreference("clear_all_data");
        mNightMode = (SwitchPreference) getPreferenceManager().findPreference(Constant.NIGHT_MODE);
        mGesturePassword = (SwitchPreference) getPreferenceManager().findPreference(Constant.GESTURE_PASSWORD);
    }

    @Override
    public void onDestroy() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key)
        {
            case Constant.NIGHT_MODE:
                EventBus.getDefault().post(new NightModeEvent());
                break;
            case Constant.GESTURE_PASSWORD:
                break;
            case Constant.BUDGET_SETTING:
                break;
        }

    }
}
