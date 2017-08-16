package sen.wedding.com.weddingsen.main.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Window;

import me.iwf.photopicker.utils.PermissionsConstant;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.utils.PermissionUtil;

public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(PermissionUtil.checkWriteStoragePermission(this)){
            delayJump();
        }
//        delayJump();

    }

    private void autoLoginCheck() {
//        if (!TextUtils.isEmpty(BasePreference.getToken())) {
//            jumpToOtherActivity(MainActivity.class);
//        } else {
//            jumpToOtherActivity(LoginActivity.class);
//        }
        jumpToOtherActivity(HotelShowActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler = null;
    }

    private void delayJump() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                autoLoginCheck();
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionsConstant.REQUEST_EXTERNAL_WRITE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被用户同意，可以去放肆了。
            } else {
                // 权限被用户拒绝了，洗洗睡吧。
                showToast("Permission fail");

            }
        }
        delayJump();

    }
}
