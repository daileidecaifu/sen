package sen.wedding.com.weddingsen.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.ResetPasswordActivity;
import sen.wedding.com.weddingsen.account.activity.VerifyGuestInfoActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.databinding.HotelShowBinding;
import sen.wedding.com.weddingsen.main.fragment.HotelShowFragment;
import sen.wedding.com.weddingsen.main.fragment.InfoFollowUpFragment;
import sen.wedding.com.weddingsen.main.fragment.InfoProvideFragment;
import sen.wedding.com.weddingsen.utils.ScreenUtil;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowActivity extends BaseActivity implements View.OnClickListener{

    HotelShowBinding binding;
    DrawerLayout drawer;
    private String userType;
    HotelShowFragment hotelShowFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_show);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        fragmentManager = getSupportFragmentManager();
        userType = BasePreference.getUserType();
        initSildMenu();
        addFragmentView();
    }

    private void initSildMenu() {

        switch (userType) {
            case Conts.LOGIN_MODEL_PHONE:
                binding.llSliderMenu.llInfoFollow.setVisibility(View.GONE);
                binding.llSliderMenu.llInfoProvide.setVisibility(View.GONE);
                binding.llSliderMenu.llPasswordReset.setVisibility(View.GONE);
                ViewGroup.LayoutParams para = binding.llSliderMenu.vLogoutMarginTop.getLayoutParams();
                para.height = ScreenUtil.dip2px(this, 180);
                binding.llSliderMenu.vLogoutMarginTop.setLayoutParams(para);
                break;
        }

        binding.llSliderMenu.setClickListener(this);
        binding.llSliderMenu.tvPhoneNumber.setText(BasePreference.getUserName());
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
                jumpToOtherActivity(PersonalInfoSetActivity.class);
                break;

            case R.id.ll_info_provide:
                break;
            case R.id.ll_info_follow:
                break;
            case R.id.ll_password_reset:
                jumpToOtherActivity(ResetPasswordActivity.class);
                break;
        }
    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        textViewTitle.setText(getString(R.string.guest_info_list));
        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

    }
    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);
    }
    private void logout() {
        BasePreference.saveAlipayAccount("");
        BasePreference.saveUserType("");
        BasePreference.saveToken("");
        BasePreference.saveUserName("");
        jumpToOtherActivity(LoginActivity.class);
        finish();
    }
}
