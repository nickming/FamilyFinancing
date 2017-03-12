package com.nickming.familyfinancing.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nickming.familyfinancing.R;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:13:49
 * E-mail:962570483@qq.com
 */

public class EarningAndExpendChangeBox extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "EarningAndExpendChangeBox";

    private static final String GREEN_TEXT_COLOR = "#8bc34a";
    private static final String WHITE_TEXT_COLOR = "#ffffff";

    private View mRootView;

    private Button mEarningBtn;
    private Button mExpendBtn;

    private OnChangeStyleListener mOnChangeStyleListener;


    public EarningAndExpendChangeBox(Context context) {
        this(context, null);
    }

    public EarningAndExpendChangeBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EarningAndExpendChangeBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_change_box, this, false);
        addView(mRootView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEarningBtn = (Button) mRootView.findViewById(R.id.btn_change_box_earning);
        mEarningBtn.setOnClickListener(this);
        mExpendBtn = (Button) mRootView.findViewById(R.id.btn_change_box_expend);
        mExpendBtn.setOnClickListener(this);
    }

    public void setOnChnageStyleListener(OnChangeStyleListener mOnChangeStyleListener) {
        this.mOnChangeStyleListener = mOnChangeStyleListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_box_earning:
                changeToEarningStyle();
                break;
            case R.id.btn_change_box_expend:
                changeToExpendStyle();
                break;
            default:
                break;
        }
    }

    private void changeToExpendStyle() {
        mExpendBtn.setBackgroundResource(R.drawable.btn_right_switch);
        mExpendBtn.setTextColor(Color.parseColor(WHITE_TEXT_COLOR));
        mEarningBtn.setBackgroundResource(R.drawable.btn_left_switch_normal);
        mEarningBtn.setTextColor(Color.parseColor(GREEN_TEXT_COLOR));
        if (mOnChangeStyleListener !=null)
        {
            mOnChangeStyleListener.onChangeToExpend();
        }

    }

    private void changeToEarningStyle() {
        mExpendBtn.setBackgroundResource(R.drawable.btn_right_switch_normal);
        mExpendBtn.setTextColor(Color.parseColor(GREEN_TEXT_COLOR));
        mEarningBtn.setBackgroundResource(R.drawable.btn_left_switch);
        mEarningBtn.setTextColor(Color.parseColor(WHITE_TEXT_COLOR));
        if (mOnChangeStyleListener !=null)
        {
            mOnChangeStyleListener.onChangeToEarning();
        }
    }

    public interface OnChangeStyleListener
    {
        void onChangeToEarning();

        void onChangeToExpend();
    }
}
