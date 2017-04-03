package sen.wedding.com.weddingsen.account.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Calendar;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.databinding.LoginActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.request.BasicHttpRequest;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.http.request.HttpRequest;
import sen.wedding.com.weddingsen.http.response.HttpResponse;
import sen.wedding.com.weddingsen.utils.AppLog;

/**
 * Created by lorin on 17/3/20.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    private VerificationCountDownTimer verificationCountDownTimer;

    private LoginActivityBinding binding;

    private boolean canSendSMS = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verificationCountDownTimer.cancel();
        verificationCountDownTimer = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_verification:
//                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();

                if (null == verificationCountDownTimer) {
                    verificationCountDownTimer = new VerificationCountDownTimer(10000, 1000);
                }

                if (canSendSMS) {
                    verificationCountDownTimer.start();
                    canSendSMS = false;
                }
                break;

            case R.id.tv_login:
//                showProgressDialog(false);
//                login();
                jumpToOtherActivity(PersonalInfoSetActivity.class);
                break;
            case R.id.ll_whole:
                hideSoftKeyBoard();
                break;

        }
    }

    private void login() {
        ApiRequest loginRequest = new ApiRequest("http://dev.meiui.me/index.php?m=app&c=user&f=getPhoneCode", HttpMethod.POST);
        getApiService().exec(loginRequest, this);

    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        AppLog.e("Status:"+resp.getResultModel().status);
        AppLog.e("Data:"+resp.getResultModel().data);
        AppLog.e("Message:"+resp.getResultModel().message);

    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        AppLog.e("Fail");

    }

    class VerificationCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public VerificationCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            canSendSMS = true;
            binding.tvGetVerification.setTextColor(getResources().getColor(R.color.theme_color));
            binding.tvGetVerification.setText(getString(R.string.get_verification_code));

        }

        @Override
        public void onTick(long millisUntilFinished) {
            binding.tvGetVerification.setTextColor(getResources().getColor(R.color.gray_1));
            binding.tvGetVerification.setText(millisUntilFinished / 1000 + getString(R.string.second));
        }
    }
}
