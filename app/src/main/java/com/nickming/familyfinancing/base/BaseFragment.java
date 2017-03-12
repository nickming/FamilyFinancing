package com.nickming.familyfinancing.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Desc:
 * Author:nickming
 * Date:2017/1/13
 * Time:14:40
 * E-mail:962570483@qq.com
 */

public class BaseFragment extends Fragment {

    protected SharedPreferences mSp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSp= PreferenceManager.getDefaultSharedPreferences(BaseApplication.getsContext());
    }

    public String getFragmentName() {
        return "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
