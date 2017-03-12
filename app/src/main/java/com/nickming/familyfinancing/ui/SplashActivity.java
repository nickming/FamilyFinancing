package com.nickming.familyfinancing.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.base.Constant;
import com.nickming.familyfinancing.ui.financing.FinancingActivity;
import com.nickming.familyfinancing.ui.lock.FinancingConfirmActivity;
import com.nickming.familyfinancing.ui.lock.FinancingSetPatternActivity;
import com.nickming.familyfinancing.util.SharePreferenceUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Observable.create(new Observable.OnSubscribe<Boolean>() {

            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(true);
                subscriber.onCompleted();
                return;
            }
        }).delay(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        boolean needLock = sharedPreferences.getBoolean(Constant.GESTURE_PASSWORD, false);
                        if (needLock) {

                            String value = SharePreferenceUtil.getString(Constant.GESTURE_PASSWORD_VALUE, "");
                            if (!TextUtils.isEmpty(value)) {
                                startActivity(new Intent(SplashActivity.this, FinancingConfirmActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, FinancingSetPatternActivity.class));
                                finish();
                            }


                        } else {
                            startActivity(new Intent(SplashActivity.this, FinancingActivity.class));
                            finish();
                        }
                    }
                });
    }
}
