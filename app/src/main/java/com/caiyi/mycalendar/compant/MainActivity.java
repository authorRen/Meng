package com.caiyi.mycalendar.compant;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caiyi.mycalendar.R;
import com.caiyi.mycalendar.Utils.GlobalConstants;
import com.caiyi.mycalendar.base.BaseActivity;
import com.caiyi.mycalendar.fragment.HomeFragment;
import com.caiyi.mycalendar.fragment.SecondFragment;
import com.caiyi.mycalendar.fragment.ThirdFragment;
import com.caiyi.mycalendar.fragment.UserCenterFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ren ZeQiang
 * @since 2017/7/28
 */
public class MainActivity extends BaseActivity {
    /** DrawerLayout容器 */
    public static DrawerLayout mDrawerLayout;

    private SimpleDraweeView mSlidingImage;

    private ViewPager mViewPager;

    private TextView mHomeTextView;
    private TextView mSecondTextView;
    private TextView mThirdTextView;
    private TextView mUserCenterTextView;

    private List<Fragment> fragments = new ArrayList<>();

    private MyViewAdapter adapter;
    TextView[] tabs = new TextView[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSlidingView();

        initData();

        setViewClickListeners(R.id.tv_home, R.id.tv_second, R.id.tv_third, R.id.tv_userCenter,
                R.id.sliding_tv_dayNight, R.id.sliding_tv_setting);
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

    private void initSlidingView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mSlidingImage = (SimpleDraweeView) findViewById(R.id.sliding_sdv_myPhoto);
        mSlidingImage.setImageURI(Uri.parse(GlobalConstants.GLOBAL_URL.PHOTO_URL));

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
            case R.id.sliding_tv_setting:
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.sliding_tv_dayNight:
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

}
