package sen.wedding.com.weddingsen.main.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.VerifyGuestInfoActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.SlidingTabLayout;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.MainActivityBinding;
import sen.wedding.com.weddingsen.main.fragment.GuestInfoFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    TitleBar titleBar;
    MainActivityBinding mainActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initBaseView();

    }


    private void initBaseView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        initSildMenu();
        initMainView();

    }

    private void initSildMenu() {
        mainActivityBinding.llSliderMenu.tvPersonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(PersonalInfoSetActivity.class);
                Toast.makeText(MainActivity.this, "PersonalInfo", Toast.LENGTH_LONG).show();
            }
        });
        mainActivityBinding.llSliderMenu.tvUserFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(FeedbackActivity.class);
                Toast.makeText(MainActivity.this, "UserFeedback", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initMainView() {
        LinearLayout linearLayoutMain = (LinearLayout) findViewById(R.id.ll_app_bar_main);

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) linearLayoutMain.findViewById(R.id.title_bar);
        titleBar = new TitleBar(linearLayoutTitle, TitleBar.Type.CUSTOM_1);
        titleBar.setTitle(getString(R.string.guest_info_list));
        titleBar.setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(VerifyGuestInfoActivity.class);

            }
        });

        titleBar.setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        //列表主体

        ViewPager viewPager = (ViewPager) linearLayoutMain.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getSupportFragmentManager(), this));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) linearLayoutMain.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setTabTitleTextSize(10);//标题字体大小
        slidingTabLayout.setTitleTextColor(Color.WHITE, 0xFFF8BBD0);//标题字体颜色
        slidingTabLayout.setTabStripWidth(70);//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(Color.WHITE);//滑动条颜色
        slidingTabLayout.setForegroundGravity(Gravity.CENTER_VERTICAL);
        slidingTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        /**
         * 自定义tabview，设置左右padding可实现滑动，当前通过layout为wrap，且设置tabview的margin来动态设置
         */
        slidingTabLayout.setCustomTabView(R.layout.tv_tab_custom,0);
        slidingTabLayout.setViewPager(viewPager);//最后调用此方
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private class TabViewPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabTitle = new String[]{"全部的", "待处理", "跟踪中", "待结算", "已结算", "已取消"};
        private Context mContext;
        private Fragment[] fragments = new Fragment[]{new GuestInfoFragment(), new GuestInfoFragment(), new GuestInfoFragment(), new GuestInfoFragment(),new GuestInfoFragment(), new GuestInfoFragment()};

        public TabViewPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitle[position];
        }
    }
}
