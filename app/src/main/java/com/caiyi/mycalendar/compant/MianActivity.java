package com.caiyi.mycalendar.compant;

import android.os.Bundle;
import android.view.View;

import com.caiyi.mycalendar.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Ren ZeQiang
 * @since 2017/7/28
 */
public class MianActivity extends BaseActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setViewClickListeners(R.id.btn_aroundService);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_aroundService:
                openActivity(AroundServiceActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
