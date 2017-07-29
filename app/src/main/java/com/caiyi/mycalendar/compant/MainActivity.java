package com.caiyi.mycalendar.compant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.base.BaseActivity;
import com.caiyi.mycalendar.fragment.HomeFragment;
import com.caiyi.mycalendar.fragment.SecondFragment;
import com.caiyi.mycalendar.fragment.ThirdFragment;
import com.caiyi.mycalendar.fragment.UserCenterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Ren ZeQiang
 * @since 2017/7/28
 */
public class MainActivity extends BaseActivity {

    private Unbinder unbinder;
    private ViewPager mViewPager;

//    @BindView(R.id.tv_home)
    private TextView mHomeTextView;
//    @BindView(R.id.tv_second)
    private TextView mSecondTextView;
//    @BindView(R.id.tv_third)
    private TextView mThirdTextView;
//    @BindView(R.id.tv_userCenter)
    private TextView mUserCenterTextView;

    private HomeFragment homeFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private UserCenterFragment usercenterFragment;

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager manager;

    private MyViewAdapter adapter;

    TextView[] tabs = new TextView[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();

        initData();

        setViewClickListeners(R.id.tv_home, R.id.tv_second, R.id.tv_third, R.id.tv_userCenter);
    }

    private void initView() {
        mHomeTextView = (TextView) findViewById(R.id.tv_home);
        mSecondTextView = (TextView) findViewById(R.id.tv_second);
        mThirdTextView = (TextView) findViewById(R.id.tv_third);
        mUserCenterTextView = (TextView) findViewById(R.id.tv_userCenter);
        tabs[0] = mHomeTextView;
        tabs[1] = mSecondTextView;
        tabs[2] = mThirdTextView;
        tabs[3] = mUserCenterTextView;

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                changeStatus(currentItem);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {
        fragments.add(new HomeFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        fragments.add(new UserCenterFragment());
        adapter = new MyViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER); // 去掉边缘阴影  
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                changeStatus(0);
                break;
            case R.id.tv_second:
                changeStatus(1);
                break;
            case R.id.tv_third:
                changeStatus(2);
                break;
            case R.id.tv_userCenter:
                changeStatus(3);
                break;
            default:
                break;
        }
    }

    private void changeStatus(int position) {
        int length = tabs.length;
        for (int i = 0; i < length; i++) {
            if (i == position) {
                tabs[position].setTextColor(getResources().getColor(R.color.gjj_white));
                tabs[position].setBackgroundColor(getResources().getColor(R.color.blue_5bacf5));
            } else {
                tabs[i].setTextColor(getResources().getColor(R.color.black));
                tabs[i].setBackgroundColor(getResources().getColor(R.color.gjj_white));
            }
        }
        mViewPager.setCurrentItem(position);
    }

    class MyViewAdapter extends FragmentStatePagerAdapter{

        public MyViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
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
