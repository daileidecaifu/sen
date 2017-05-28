package sen.wedding.com.weddingsen.main.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.ResetPasswordActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.databinding.MainActivityBinding;
import sen.wedding.com.weddingsen.main.fragment.InfoProvideFragment;
import sen.wedding.com.weddingsen.main.fragment.InfoFollowUpFragment;
import sen.wedding.com.weddingsen.utils.ScreenUtil;

public class MainActivity extends BaseActivity
        implements View.OnClickListener {

    DrawerLayout drawer;
    MainActivityBinding mainActivityBinding;
    InfoFollowUpFragment infoFollowUpFragment;
    InfoProvideFragment infoProvideFragment;
    private FragmentManager fragmentManager;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initData();
        initBaseView();

    }

    private void initData() {
        userType = BasePreference.getUserType();
    }

    private void initBaseView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        initSildMenu();
        setTabSelection(0);

    }

    private void initSildMenu() {

        switch (userType) {
            case Conts.LOGIN_MODEL_PHONE:
                mainActivityBinding.llSliderMenu.llInfoFollow.setVisibility(View.GONE);
                mainActivityBinding.llSliderMenu.llInfoProvide.setVisibility(View.GONE);
                mainActivityBinding.llSliderMenu.llPasswordReset.setVisibility(View.GONE);
                ViewGroup.LayoutParams para = mainActivityBinding.llSliderMenu.vLogoutMarginTop.getLayoutParams();
                para.height = ScreenUtil.dip2px(this, 180);
                mainActivityBinding.llSliderMenu.vLogoutMarginTop.setLayoutParams(para);
                break;
        }

        mainActivityBinding.llSliderMenu.setClickListener(this);
        mainActivityBinding.llSliderMenu.tvPhoneNumber.setText(BasePreference.getUserName());
    }

    @Override
    public void onClick(View v) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
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

                if (infoProvideFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    infoProvideFragment = InfoProvideFragment.newInstance();
                    transaction.add(R.id.fl_content, infoProvideFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(infoProvideFragment);
                }
                break;

            case 1:


                if (infoFollowUpFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    infoFollowUpFragment = InfoFollowUpFragment.newInstance();
                    transaction.add(R.id.fl_content, infoFollowUpFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(infoFollowUpFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (infoFollowUpFragment != null) {
            transaction.hide(infoFollowUpFragment);
        }

        if (infoProvideFragment != null) {
            transaction.hide(infoProvideFragment);
        }

    }


}
