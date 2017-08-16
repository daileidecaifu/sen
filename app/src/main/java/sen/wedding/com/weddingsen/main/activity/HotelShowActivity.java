package sen.wedding.com.weddingsen.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import me.iwf.photopicker.utils.PermissionsConstant;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.FeedbackActivity;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalDetailActivity;
import sen.wedding.com.weddingsen.account.activity.PersonalInfoSetActivity;
import sen.wedding.com.weddingsen.account.activity.ResetPasswordActivity;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.base.model.CheckVersionResModel;
import sen.wedding.com.weddingsen.component.service.DownloadService;
import sen.wedding.com.weddingsen.databinding.HotelShowBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.fragment.HotelShowFragment;
import sen.wedding.com.weddingsen.sales.activity.BuildFollowAcrivity;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.PermissionUtil;
import sen.wedding.com.weddingsen.utils.ScreenUtil;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    HotelShowBinding binding;
    DrawerLayout drawer;
    private String userType;
    HotelShowFragment hotelShowFragment;
    private FragmentManager fragmentManager;
    private long currentBackPressedTime;
    private final long BACK_PRESSED_INTERVAL = 1500;
    private ApiRequest checkUpdateRequest;
    private AlertDialog alertDialog;

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
        if (!Conts.hadVersionCheck) {
            checkVersionUpdate();
        }
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

    private void checkVersionUpdate() {
        checkUpdateRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_UPDATE_DATA, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("appver", Conts.APP_VERSION);
        checkUpdateRequest.setParams(param);

        getApiService().exec(checkUpdateRequest, this);
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

    private void hideAllSlideItem() {
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
        if (hotelShowFragment != null) {
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
            finish();

        }
    }



    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        ResultModel resultModel = resp.getResultModel();

        if (req == checkUpdateRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                Conts.hadVersionCheck = true;
                final CheckVersionResModel checkVersionResModel = GsonConverter.decode(resultModel.data, CheckVersionResModel.class);

                if (checkVersionResModel.getUpdateStatus().getUpdateNow().equals(Conts.APP_UPDATE_FORCED)) {
                    showAlertDialog(null, checkVersionResModel.getUpdateStatus().getUpdateMsg(), getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (StringUtil.isURLFormat(checkVersionResModel.getUrl())) {
                                startDownloadAction(checkVersionResModel.getUrl(),true);
                            }
                        }
                    });
                } else if (checkVersionResModel.getUpdateStatus().getUpdateNow().equals(Conts.APP_UPDATE_COMMON)) {
                    showAlertDialog(null, checkVersionResModel.getUpdateStatus().getUpdateMsg(), getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (StringUtil.isURLFormat(checkVersionResModel.getUrl())) {
                                startDownloadAction(checkVersionResModel.getUrl(),false);
                            }
                        }
                    }, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }, false);
                }

            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }

    private void showAlertDialog(String title, String message, String positiveBtnName, DialogInterface.OnClickListener positiveOnClick) {
        showAlertDialog(title, message, positiveBtnName, positiveOnClick, null, null, false);
    }

    private void showAlertDialog(String title, String message,
                                 String positiveBtnName, DialogInterface.OnClickListener positiveOnClick,
                                 String negativeBtnName, DialogInterface.OnClickListener negativeOnClick,
                                 boolean cancelable) {
//        if (alertDialog != null) {
//            return;
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        if (!TextUtils.isEmpty(positiveBtnName)) {

            builder.setPositiveButton(positiveBtnName, positiveOnClick);
        }
        if (!TextUtils.isEmpty(negativeBtnName)) {
            builder.setNegativeButton(negativeBtnName, negativeOnClick);
        }
        builder.setCancelable(cancelable);
        alertDialog = builder.create();
//        alertDialog.getWindow().setType(DLUtil.hasKitKat() ? WindowManager.LayoutParams.TYPE_TOAST :
//                WindowManager.LayoutParams.TYPE_PHONE);
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                alertDialog = null;
            }
        });
        alertDialog.show();

    }

    private void startDownloadAction(String url,boolean isForced)
    {
        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;
        if(writeStoragePermissionGranted)
        {
            Intent intent = new Intent(HotelShowActivity.this, DownloadService.class);
            intent.putExtra("apk_url", url);
            startService(intent);
        }else
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        if(isForced)
        {
            showAlertDialog(null, "下载中...", null, null);
        }
    }
}
