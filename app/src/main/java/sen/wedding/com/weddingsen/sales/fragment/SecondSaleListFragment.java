package sen.wedding.com.weddingsen.sales.fragment;

import android.content.Context;
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
import android.widget.Button;
import android.widget.LinearLayout;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.SlidingTabLayout;

/**
 * Created by lorin on 17/5/8.
 */

public class SecondSaleListFragment extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    SlidingTabLayout slidingTabLayout;

    public static SecondSaleListFragment newInstance() {

        Bundle args = new Bundle();
        SecondSaleListFragment fragment = new SecondSaleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow_list, null);
        initMainView(view);
        return view;

    }

    private void initMainView(View view) {

        LinearLayout linearLayoutMain = (LinearLayout) view.findViewById(R.id.ll_app_bar_main);
        //列表主体

        ViewPager viewPager = (ViewPager) linearLayoutMain.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(), getActivity()));
        viewPager.setOffscreenPageLimit(6);

        int selectColor = ContextCompat.getColor(getActivity(), R.color.theme_color);
        slidingTabLayout = (SlidingTabLayout) linearLayoutMain.findViewById(R.id.sliding_tabs);
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
//        Button button = (Button) view.findViewById(R.id.bt_test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showToast("aaa");
//                String[] titles = new String[]{
//                        "aaa",
//                        "bbb",
//                        "ccc",
//                        "ddd"};
//                slidingTabLayout.utpdateTitles(titles);
//
//            }
//        });
    }

    public void updateTitle(int count, int index) {
        slidingTabLayout.updateTitle(Conts.getSecondSaleStatusMap().get(index) + "(" + count + ")", index);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private class TabViewPagerAdapter extends FragmentPagerAdapter {

        private String[] mTabTitle = new String[]{
                Conts.getSecondSaleStatusMap().get(1),
                Conts.getSecondSaleStatusMap().get(2),
                Conts.getSecondSaleStatusMap().get(3),
                Conts.getSecondSaleStatusMap().get(4)};
        private Context mContext;
        private Fragment[] fragments = new Fragment[]{
                SecondSaleFragment.newInstance(1),
                SecondSaleFragment.newInstance(2),
                SecondSaleFragment.newInstance(3),
                SecondSaleFragment.newInstance(4)
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


