package sen.wedding.com.weddingsen.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.VerifyGuestInfoActivity;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.SlidingTabLayout;
import sen.wedding.com.weddingsen.component.SwitchButton;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/5/8.
 */

public class FollowerFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    public static FollowerFragment newInstance() {

        Bundle args = new Bundle();
        FollowerFragment fragment = new FollowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow, null);
        initMainView(view);
        return view;

    }

    private void initMainView(View view) {

        LinearLayout linearLayoutMain = (LinearLayout) view.findViewById(R.id.ll_app_bar_follow);

        initFollowerTitle(linearLayoutMain);
        //列表主体

        ViewPager viewPager = (ViewPager) linearLayoutMain.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(), getActivity()));
        viewPager.setOffscreenPageLimit(6);

        int selectColor = ContextCompat.getColor(getActivity(), R.color.theme_color);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) linearLayoutMain.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setTabTitleTextSize(14);//标题字体大小
        slidingTabLayout.setTitleTextColor(selectColor, ContextCompat.getColor(getActivity(), R.color.text_common));//标题字体颜色
        slidingTabLayout.setTabStripWidth(70);//滑动条宽度
        slidingTabLayout.setSelectedIndicatorColors(selectColor);//滑动条颜色
        slidingTabLayout.setForegroundGravity(Gravity.CENTER_VERTICAL);
        slidingTabLayout.setDistributeEvenly(true); //均匀平铺选项卡
        /**
         * 自定义tabview，设置左右padding可实现滑动，当前通过layout为wrap，且设置tabview的margin来动态设置
         */
        slidingTabLayout.setCustomTabView(R.layout.tv_tab_custom, 0);
        slidingTabLayout.setViewPager(viewPager);//最后调用此方

        linearLayoutMain.setOnClickListener(this);
        //空数据状态
//        mainActivityBinding.llAppBarMain.llListEmpty.setVisibility(View.GONE);
    }

    private void initFollowerTitle(LinearLayout linearLayoutMain) {

        List<String> tabTextList = Arrays.asList(getString(R.string.guest_info), getString(R.string.contract_review));
        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) linearLayoutMain.findViewById(R.id.title_follower);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        SwitchButton switchButton = (SwitchButton) linearLayoutTitle.findViewById(R.id.sb_main_follower);

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        switchButton.setText(tabTextList);
        switchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                showToast(""+position);
            }
        });

        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                getActivity().startActivity(intent);

            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openMenu();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private class TabViewPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabTitle = new String[]{Conts.getorderStatusMap().get(3),
                Conts.getorderStatusMap().get(4),
                Conts.getorderStatusMap().get(5),
                Conts.getorderStatusMap().get(6)};
        private Context mContext;
        private Fragment[] fragments = new Fragment[]{
                GuestInfoFragment2.newInstance(3),
                GuestInfoFragment2.newInstance(4),
                GuestInfoFragment2.newInstance(5),
                GuestInfoFragment2.newInstance(6)
        };

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

    @Override
    public void onClick(View v) {

    }
}


