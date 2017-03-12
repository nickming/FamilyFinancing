package com.nickming.familyfinancing.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.util.Calculator;
import com.orhanobut.logger.Logger;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/14
 * Time:14:55
 * E-mail:962570483@qq.com
 */

public class FinancingNumberKeyBoardView extends LinearLayout {

    private static final String TAG = "FinancingNumberKeyBoard";

    private static final String NONE_STR = "0.00";

    @BindView(R.id.btn_keyboard_num7)
    Button btnKeyboardNum7;
    @BindView(R.id.btn_keyboard_num8)
    Button btnKeyboardNum8;
    @BindView(R.id.btn_keyboard_num9)
    Button btnKeyboardNum9;
    @BindView(R.id.btn_keyboard_del)
    Button btnKeyboardDel;
    @BindView(R.id.btn_keyboard_num4)
    Button btnKeyboardNum4;
    @BindView(R.id.btn_keyboard_num5)
    Button btnKeyboardNum5;
    @BindView(R.id.btn_keyboard_num6)
    Button btnKeyboardNum6;
    @BindView(R.id.btn_keyboard_num_plus)
    Button btnKeyboardNumPlus;
    @BindView(R.id.btn_keyboard_num1)
    Button btnKeyboardNum1;
    @BindView(R.id.btn_keyboard_num2)
    Button btnKeyboardNum2;
    @BindView(R.id.btn_keyboard_num3)
    Button btnKeyboardNum3;
    @BindView(R.id.btn_keyboard_num_sub)
    Button btnKeyboardNumSub;
    @BindView(R.id.btn_keyboard_point)
    Button btnKeyboardPoint;
    @BindView(R.id.btn_keyboard_num0)
    Button btnKeyboardNum0;
    @BindView(R.id.btn_keyboard_ok)
    Button btnKeyboardOk;


    private View mRootView;

    private Stack<Float> mNumberStack;
    private String mCurrentShowStr = "";
    private OnKeyBoardChangeListener mOnKeyBoardChangeListener;


    public FinancingNumberKeyBoardView(Context context) {
        super(context, null);
    }

    public FinancingNumberKeyBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinancingNumberKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_number_keyboard, this, false);
        ButterKnife.bind(this, mRootView);
        addView(mRootView);

        mNumberStack = new Stack<>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setOnKeyBoardChangeListener(OnKeyBoardChangeListener mOnKeyBoardChangeListener) {
        this.mOnKeyBoardChangeListener = mOnKeyBoardChangeListener;
    }

    @OnClick({R.id.btn_keyboard_num7, R.id.btn_keyboard_num8, R.id.btn_keyboard_num9,
            R.id.btn_keyboard_del, R.id.btn_keyboard_num4,
            R.id.btn_keyboard_num5, R.id.btn_keyboard_num6,
            R.id.btn_keyboard_num_plus, R.id.btn_keyboard_num1,
            R.id.btn_keyboard_num2, R.id.btn_keyboard_num3,
            R.id.btn_keyboard_num_sub, R.id.btn_keyboard_point, R.id.btn_keyboard_num0, R.id.btn_keyboard_ok})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_keyboard_num7:
                mCurrentShowStr += "7";
                break;
            case R.id.btn_keyboard_num8:
                mCurrentShowStr += "8";
                break;
            case R.id.btn_keyboard_num9:
                mCurrentShowStr += "9";
                break;
            case R.id.btn_keyboard_num4:
                mCurrentShowStr += "4";
                break;
            case R.id.btn_keyboard_num5:
                mCurrentShowStr += "5";
                break;
            case R.id.btn_keyboard_num6:
                mCurrentShowStr += "6";
                break;
            case R.id.btn_keyboard_num1:
                mCurrentShowStr += "1";
                break;
            case R.id.btn_keyboard_num2:
                mCurrentShowStr += "2";
                break;
            case R.id.btn_keyboard_num3:
                mCurrentShowStr += "3";
                break;
            case R.id.btn_keyboard_num_sub:
                mCurrentShowStr += "-";
                break;
            case R.id.btn_keyboard_point:
                mCurrentShowStr += ".";
                break;
            case R.id.btn_keyboard_del:
                if (mCurrentShowStr.length() > 0)
                    mCurrentShowStr = mCurrentShowStr.substring(0, mCurrentShowStr.length() - 1);
                else
                    mCurrentShowStr = "";
                break;
            case R.id.btn_keyboard_num_plus:
                mCurrentShowStr += "+";
                break;
            case R.id.btn_keyboard_num0:
                mCurrentShowStr += "0";
                break;
            case R.id.btn_keyboard_ok:
                if (btnKeyboardOk.getText().equals("OK")) {
                    if (mOnKeyBoardChangeListener != null) {
                        mOnKeyBoardChangeListener.onClickOk(mCurrentShowStr);
                    }
                } else {
                    mCurrentShowStr = String.valueOf(Calculator.conversion(mCurrentShowStr));
                }
                break;
        }
        if (mCurrentShowStr.contains("+") || mCurrentShowStr.contains("-")) {
            btnKeyboardOk.setText("=");
        } else {
            btnKeyboardOk.setText("OK");
        }

        if (mOnKeyBoardChangeListener != null)
            mOnKeyBoardChangeListener.onChange(mCurrentShowStr);
    }

    public interface OnKeyBoardChangeListener {

        void onChange(String result);

        void onClickOk(String result);

    }
}
