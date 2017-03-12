package com.nickming.familyfinancing.ui.setting;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_toolbar)
    Toolbar mSettingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        SettingFragment settingFragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.fl_setting_container);
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_setting_container, settingFragment);
            fragmentTransaction.commit();
        }

        mSettingToolbar.setTitle("设置");
        mSettingToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mSettingToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSettingToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
