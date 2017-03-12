package com.nickming.familyfinancing.ui.record;

import android.os.Bundle;

import com.nickming.familyfinancing.R;
import com.nickming.familyfinancing.base.BaseActivity;
import com.nickming.familyfinancing.util.ActivityUtil;

public class RecordActivity extends BaseActivity {

    private RecordPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        RecordFragment fragment= (RecordFragment) getSupportFragmentManager().findFragmentById(R.id.fl_record_content);
        if (fragment==null)
        {
            fragment=RecordFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.fl_record_content);
        }
        mPresenter=new RecordPresenter(fragment);
    }
}
