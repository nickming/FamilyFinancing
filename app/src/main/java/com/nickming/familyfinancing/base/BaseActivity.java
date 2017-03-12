package com.nickming.familyfinancing.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.entity.ActivityCloseEvent;
import com.nickming.familyfinancing.entity.NightModeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:14:39
 * E-mail:962570483@qq.com
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected boolean isNight = false;

    protected SharedPreferences mSp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSp = PreferenceManager.getDefaultSharedPreferences(this);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            changeNightMode(new NightModeEvent());
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    /**
     * 更换夜间模式
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeNightMode(NightModeEvent nightModeEvent) {
        isNight = mSp.getBoolean(Constant.NIGHT_MODE, false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mSp.edit().putBoolean(Constant.NIGHT_MODE, true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            mSp.edit().putBoolean(Constant.NIGHT_MODE, false);
        }
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        this.recreate();
    }

    /**
     * 是否关闭自己activity，所有继承自这个的activity均会关闭
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeSelf(ActivityCloseEvent event) {
        if (event.isClose)
            finish();
    }

    private void showDrawingBitmapAnimation()
    {
        final View decorView=getWindow().getDecorView();
        Bitmap cacheBitmap=getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap!=null)
        {
            Log.i(TAG, "showDrawingBitmapAnimation: 执行了");
            final View view=new View(this);
            view.setBackground(new DrawableWrapper(new BitmapDrawable(getResources(),cacheBitmap)));
            ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup)decorView).addView(view,layoutParams);
            ObjectAnimator animator=ObjectAnimator.ofFloat(view,"alpha",1f,0f);
            animator.setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup)decorView).removeView(view);
                }
            });
//            animator.start();
        }
    }

    /**
     * 获取view 的缓存视图
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        boolean drawingCacheEnable = true;
        view.setDrawingCacheEnabled(drawingCacheEnable);
        view.buildDrawingCache(drawingCacheEnable);
        final Bitmap drawingBitmap = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingBitmap != null) {
            bitmap = Bitmap.createBitmap(drawingBitmap);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
