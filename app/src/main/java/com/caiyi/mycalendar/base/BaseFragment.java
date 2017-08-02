package com.caiyi.mycalendar.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.Utils.NetworkUtil;
import com.caiyi.mycalendar.Utils.StringUtil;

/**
 * @author Ren ZeQiang
 * @since 2017/8/2
 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context mContext;
    protected FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getContext();
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 检测网络状态
     *
     * @return is networkconneted
     */
    protected boolean isNetConneted() {
        if (NetworkUtil.isNetworkConnected()) {
            return true;
        }
        showToast(R.string.gjj_network_not_connected);
        return false;
    }

    /**
     * 默认采用short toast,
     *
     * @param toast str to toast
     */
    protected void showToast(String toast) {
        showToast(toast, null);
    }

    protected void showToast(String toast, String defaultToast) {
        if (!isAdded()) {
            return;
        }
        if (!StringUtil.isNullOrEmpty(toast)) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isNullOrEmpty(defaultToast)) {
            Toast.makeText(mContext, defaultToast, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showToast(@StringRes int resId) {
        if (isAdded()) {
            Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
        }
    }

    //没有findViewById的View使用
    protected void setViewClickListeners(View view, int... viewIds) {
        if (view == null) {
            return;
        }
        for (int id : viewIds) {
            if (id != 0) {
                view.findViewById(id).setOnClickListener(this);
            }
        }
    }

    //已经findViewById,直接设置
    protected void setViewClickListeners(View... views) {
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    protected void openActivity(Class<? extends Activity> cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }

    protected void openActivityForResult(Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        startActivityForResult(intent, requestCode);
    }
}
