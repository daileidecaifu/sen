package sen.wedding.com.weddingsen.main.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;

public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        delayJump();

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
}
