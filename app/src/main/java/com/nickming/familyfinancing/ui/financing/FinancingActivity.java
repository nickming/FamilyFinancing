package com.nickming.familyfinancing.ui.financing;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.util.ActivityUtil;

public class FinancingActivity extends BaseActivity {

    private FinancingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_financing);

        FinancingFragment financingFragment = (FinancingFragment) getSupportFragmentManager().findFragmentById(R.id.fl_index_content);
        if (financingFragment == null) {
            financingFragment = FinancingFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), financingFragment, R.id.fl_index_content);
        }

        mPresenter = new FinancingPresenter(financingFragment);
    }

    public void initToolbar(Toolbar toolbar)
    {
        if (toolbar==null)
            return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawerlayout= (DrawerLayout)findViewById(R.id.drawer_financing);
        ActionBarDrawerToggle mDrawerToggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,
                R.string.open,R.string.close);
        mDrawerToggle.syncState();
        drawerlayout.addDrawerListener(mDrawerToggle);
    }
}
