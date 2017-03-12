package com.nickming.familyfinancing.ui.lock;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.entity.ActivityCloseEvent;
import com.nickming.familyfinancing.ui.financing.FinancingActivity;
import com.nickming.familyfinancing.util.SharePreferenceUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/15
 * Time:21:40
 * E-mail:962570483@qq.com
 */

public class FinancingConfirmActivity extends ConfirmPatternActivity implements IBaseLockPatternClose {


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
    protected boolean isStealthModeEnabled() {
        return super.isStealthModeEnabled();
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String value = SharePreferenceUtil.getString(Constant.GESTURE_PASSWORD_VALUE, "");
        String password = PatternUtils.patternToSha1String(pattern);
        if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(password))
        {
            Logger.i("密码为:"+password+"\n"+"输入为:"+value);
            if (value.equals(password))
            {
                startActivity(new Intent(FinancingConfirmActivity.this, FinancingActivity.class));
                finish();
                return true;
            }
            else
                return false;
        }else
        {
            Logger.i("存在数据为空");
            return false;
        }
    }

    @Override
    protected void onForgotPassword() {
        super.onForgotPassword();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void closeSelf(ActivityCloseEvent closeEvent) {
        if (closeEvent.isClose)
            finish();
    }
}
