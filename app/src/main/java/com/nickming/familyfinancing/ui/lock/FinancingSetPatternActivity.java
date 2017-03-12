package com.nickming.familyfinancing.ui.lock;

import android.content.Intent;
import android.os.Bundle;

import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.entity.ActivityCloseEvent;
import com.nickming.familyfinancing.ui.financing.FinancingActivity;
import com.nickming.familyfinancing.util.SharePreferenceUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/15
 * Time:21:32
 * E-mail:962570483@qq.com
 */

public class FinancingSetPatternActivity extends SetPatternActivity implements IBaseLockPatternClose {

    private static final String TAG = "FinancingSetPatternActi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);
        String password = PatternUtils.patternToSha1String(pattern);
        SharePreferenceUtil.setString(Constant.GESTURE_PASSWORD_VALUE,password);
        Logger.i(password);
        startActivity(new Intent(this, FinancingActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void closeSelf(ActivityCloseEvent closeEvent) {
        if (closeEvent.isClose)
            finish();
    }
}
