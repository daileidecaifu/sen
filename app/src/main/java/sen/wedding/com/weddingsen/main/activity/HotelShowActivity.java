package sen.wedding.com.weddingsen.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalDetailActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.ResetPasswordActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.databinding.HotelShowBinding;
import sen.wedding.com.weddingsen.main.fragment.HotelShowFragment;
import sen.wedding.com.weddingsen.sales.activity.BuildFollowAcrivity;
import sen.wedding.com.weddingsen.utils.ScreenUtil;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowActivity extends BaseActivity implements View.OnClickListener {

    HotelShowBinding binding;
    DrawerLayout drawer;
    private String userType;
    HotelShowFragment hotelShowFragment;
    private FragmentManager fragmentManager;
    private long currentBackPressedTime;
    private final long BACK_PRESSED_INTERVAL = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_show);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        binding.llSliderMenu.setClickListener(this);
        initSildMenu();
        addFragmentView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainReceiver(EventIntent eventIntent) {
        if (eventIntent.getActionId() == Conts.EVENT_INIT_MAIN_SLIDE) {
            initSildMenu();
        }
    }

    private void initSildMenu() {
        userType = BasePreference.getUserType();
        if (TextUtils.isEmpty(userType)) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        hideAllSlideItem();
        switch (userType) {
            case Conts.LOGIN_MODEL_PHONE:
                binding.llSliderMenu.llInfoProvide.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llPersonInfo.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llUserFeedback.setVisibility(View.VISIBLE);
                binding.llSliderMenu.ivAvatar.setImageResource(R.mipmap.avator_tg);
                binding.llSliderMenu.tvUserType.setText("（" + getString(R.string.provider) + "）");
                break;

            case Conts.LOGIN_MODEL_FIRST_SALE:
                binding.llSliderMenu.llInfoFollow.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llPasswordReset.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llUserFeedback.setVisibility(View.VISIBLE);

                binding.llSliderMenu.ivAvatar.setImageResource(R.mipmap.avator_sx);
                binding.llSliderMenu.tvUserType.setText("（" + getString(R.string.shou_xiao) + "）");
                break;
            case Conts.LOGIN_MODEL_SECOND_SALE:
                binding.llSliderMenu.llInfoFollow.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llPasswordReset.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llUserFeedback.setVisibility(View.VISIBLE);
                binding.llSliderMenu.ivAvatar.setImageResource(R.mipmap.avator_erxiao);
                binding.llSliderMenu.tvUserType.setText("（" + getString(R.string.er_xiao) + "）");
                break;
            case Conts.LOGIN_MODEL_ACCOUNT:
                binding.llSliderMenu.llInfoProvide.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llInfoFollow.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llPersonInfo.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llPasswordReset.setVisibility(View.VISIBLE);
                binding.llSliderMenu.llUserFeedback.setVisibility(View.VISIBLE);
                binding.llSliderMenu.ivAvatar.setImageResource(R.mipmap.avator_gz);
                binding.llSliderMenu.tvUserType.setText("（" + getString(R.string.follower) + "）");

                break;
        }

        binding.llSliderMenu.tvPhoneNumber.setText(BasePreference.getUserName());
    }

    private void hideAllSlideItem()
    {
        binding.llSliderMenu.llInfoProvide.setVisibility(View.GONE);
        binding.llSliderMenu.llInfoFollow.setVisibility(View.GONE);
        binding.llSliderMenu.llPersonInfo.setVisibility(View.GONE);
        binding.llSliderMenu.llPasswordReset.setVisibility(View.GONE);
        binding.llSliderMenu.llUserFeedback.setVisibility(View.GONE);

    }

    private void addFragmentView() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        if (hotelShowFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            hotelShowFragment = HotelShowFragment.newInstance();
            transaction.add(R.id.fl_content, hotelShowFragment);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(hotelShowFragment);
        }
        transaction.commit();
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
                jumpToOtherActivity(PersonalDetailActivity.class);
                break;

            case R.id.ll_info_provide:
                jumpToOtherActivity(InfoProvideActivity.class);
                break;
            case R.id.ll_info_follow:

                switch (userType) {

                    case Conts.LOGIN_MODEL_FIRST_SALE:
                    case Conts.LOGIN_MODEL_SECOND_SALE:
                        jumpToOtherActivity(BuildFollowAcrivity.class);
                        break;
                    case Conts.LOGIN_MODEL_ACCOUNT:
                        jumpToOtherActivity(InfoFollowUpActivity.class);
                        break;
                }
                break;
            case R.id.ll_password_reset:
                jumpToOtherActivity(ResetPasswordActivity.class);
                break;
        }
    }

    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);
    }

    private void logout() {

        BasePreference.clearAll();
        initSildMenu();
        if(hotelShowFragment!=null) {
            hotelShowFragment.initLeftTopIcon();
        }
        jumpToOtherActivity(LoginActivity.class);
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {

            currentBackPressedTime = System.currentTimeMillis();
            showToast("再按一次返回键退出程序");

        } else {

            // 退出

            finish();

        }
    }

}
