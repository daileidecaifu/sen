package sen.wedding.com.weddingsen.main.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.ResetPasswordActivity;
import sen.wedding.com.weddingsen.account.activity.VerifyGuestInfoActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.SlidingTabLayout;
import sen.wedding.com.weddingsen.component.SwitchButton;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.MainActivityBinding;
import sen.wedding.com.weddingsen.main.fragment.FollowerFragment;
import sen.wedding.com.weddingsen.main.fragment.GuestInfoFragment;
import sen.wedding.com.weddingsen.main.fragment.OpenProjectNormalFragment;
import sen.wedding.com.weddingsen.main.fragment.ProvideFragment;

public class MainActivity extends BaseActivity
        implements View.OnClickListener {

    DrawerLayout drawer;
    MainActivityBinding mainActivityBinding;
    ProvideFragment provideFragment;
    FollowerFragment followerFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initBaseView();

    }


    private void initBaseView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        initSildMenu();
        setTabSelection(0);
//        initMainView();

    }

    private void initSildMenu() {
        getSupportFragmentManager();
        mainActivityBinding.llSliderMenu.setClickListener(this);
        mainActivityBinding.llSliderMenu.tvPhoneNumber.setText(BasePreference.getUserName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_logout:
                logout();
                break;

            case R.id.ll_user_feedback:
                jumpToOtherActivity(FeedbackActivity.class);
                break;

            case R.id.ll_person_info:
                jumpToOtherActivity(PersonalInfoSetActivity.class);
                break;

            case R.id.ll_info_provide:
                setTabSelection(0);
                break;
            case R.id.ll_info_follow:
                setTabSelection(1);
                break;
            case R.id.ll_password_reset:
                jumpToOtherActivity(ResetPasswordActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void logout() {
        BasePreference.saveAlipayAccount("");
        BasePreference.saveUserType("");
        BasePreference.saveToken("");
        BasePreference.saveUserName("");
        jumpToOtherActivity(LoginActivity.class);
        finish();
    }

    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);

    }

    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:

                if (provideFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    provideFragment = ProvideFragment.newInstance();
                    transaction.add(R.id.fl_content, provideFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(provideFragment);
                }
                break;
            case 1:

                if (followerFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    followerFragment = FollowerFragment.newInstance();
                    transaction.add(R.id.fl_content, followerFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(followerFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (provideFragment != null) {
            transaction.hide(provideFragment);
        }

        if (followerFragment != null) {
            transaction.hide(followerFragment);
        }

    }


}
