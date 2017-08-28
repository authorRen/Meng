package com.caiyi.mycalendar.compant;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.base.BaseActivity;

/**
 * @author Ren ZeQiang.
 * @since 2017/8/23.
 */

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        initToolbar();
//        initView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("设置");
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.button_layout, null);
        linearLayout.addView(view);
    }

}
