package com.caiyi.mycalendar.compant;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.base.BaseActivity;

/**
 * @author Ren ZeQiang
 * @since 2017/8/2
 */
public class MeiziMessageActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_message);
        initView();
    }

    private void initView() {
        Button btn = (Button) findViewById(R.id.aaa);
        LinearLayout myView = (LinearLayout) findViewById(R.id.myLayout);

        btn.setOnClickListener(this);
        btn.setOnTouchListener(this);

        myView.setOnClickListener(this);
        myView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.i("aaa", "OnTouchListener--onTouch-- action=" + motionEvent.getAction()+" --"+view);
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i("aaa", "OnClickListener--onClick--"+v);
    }
}
