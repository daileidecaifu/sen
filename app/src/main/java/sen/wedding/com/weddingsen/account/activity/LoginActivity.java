package sen.wedding.com.weddingsen.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.model.AccountInfoModel;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.databinding.LoginActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.utils.AppLog;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/3/20.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    private VerificationCountDownTimer verificationCountDownTimer;

    private LoginActivityBinding binding;

    private boolean canSendSMS = true;
    private ApiRequest getVerificationCodeRequest, loginRequest;
    private AccountInfoModel accountInfoModel;

    private int requestCodeTotal = 60 * 1000;//验证码间隔60秒

    private String loginType = Conts.LOGIN_MODEL_PHONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (verificationCountDownTimer != null) {
            verificationCountDownTimer.cancel();
            verificationCountDownTimer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_verification:
//                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();

                if (null == verificationCountDownTimer) {
                    verificationCountDownTimer = new VerificationCountDownTimer(requestCodeTotal, 1000);
                }

                if (canSendSMS) {
                    getVerificationCode();
                }
                break;

            case R.id.tv_login:
                if (loginType.equals(Conts.LOGIN_MODEL_PHONE)) {
                    login();
                } else if (loginType.equals(Conts.LOGIN_MODEL_ACCOUNT)) {
                    loginByAccount();
                }
//                jumpToOtherActivity(MainActivity.class);
                break;
            case R.id.ll_whole:
                hideSoftKeyBoard();
                break;

            case R.id.tv_switch:
                if (loginType.equals(Conts.LOGIN_MODEL_PHONE)) {
                    loginType = Conts.LOGIN_MODEL_ACCOUNT;
                    binding.llAccount.setVisibility(View.VISIBLE);
                    binding.llPassword.setVisibility(View.VISIBLE);
                    binding.llPhone.setVisibility(View.GONE);
                    binding.llProviderVerification.setVisibility(View.GONE);
                } else if (loginType.equals(Conts.LOGIN_MODEL_ACCOUNT)) {
                    loginType = Conts.LOGIN_MODEL_PHONE;
                    binding.llAccount.setVisibility(View.GONE);
                    binding.llPassword.setVisibility(View.GONE);
                    binding.llPhone.setVisibility(View.VISIBLE);
                    binding.llProviderVerification.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    private void getVerificationCode() {

        if (TextUtils.isEmpty(binding.etUserName.getText().toString().trim())) {
            showToast(getString(R.string.phone_number_can_not_empty));
            return;
        }

        if (binding.etUserName.getText().toString().trim().length() != 11) {
            showToast(getString(R.string.phone_number_wrong_format));
            return;
        }

        verificationCountDownTimer.start();
        canSendSMS = false;
        showProgressDialog(false);
        getVerificationCodeRequest = new ApiRequest(URLCollection.URL_GET_CODE, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("mobile", binding.etUserName.getText().toString());
        getVerificationCodeRequest.setParams(param);
        getApiService().exec(getVerificationCodeRequest, this);

    }

    private void login() {

        if (TextUtils.isEmpty(binding.etUserName.getText().toString().trim())) {
            showToast(getString(R.string.phone_number_can_not_empty));
            return;
        }

        if (binding.etUserName.getText().toString().trim().length() != 11) {
            showToast(getString(R.string.phone_number_wrong_format));
            return;
        }

        if (TextUtils.isEmpty(binding.etVerification.getText().toString().trim())) {
            showToast(getString(R.string.code_can_not_empty));
            return;
        }

        if (binding.etVerification.getText().toString().trim().length() != 4) {
            showToast(getString(R.string.code_wrong_format));
            return;
        }

        showProgressDialog(false);
        loginRequest = new ApiRequest(URLCollection.URL_LOGIN, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("phone", binding.etUserName.getText().toString());
        param.put("code", binding.etVerification.getText().toString());
        loginRequest.setParams(param);
        getApiService().exec(loginRequest, this);
    }

    private void loginByAccount() {
        if (TextUtils.isEmpty(binding.etAccount.getText().toString().trim())) {
            showToast(getString(R.string.account_can_not_empty));
            return;
        }

        if (TextUtils.isEmpty(binding.etAccount.getText().toString().trim())) {
            showToast(getString(R.string.password_can_not_empty));
            return;
        }

        showProgressDialog(false);
        loginRequest = new ApiRequest(URLCollection.URL_ACCOUNT_LOGIN, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("user_name", binding.etAccount.getText().toString());
        param.put("password", binding.etPassword.getText().toString());
        loginRequest.setParams(param);
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

        ResultModel resultModel = resp.getResultModel();
        closeProgressDialog();

        if (req == getVerificationCodeRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.verification_send_success));
            } else {
                showToast(resultModel.message);
            }
        } else if (req == loginRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                accountInfoModel = GsonConverter.decode(resultModel.data, AccountInfoModel.class);
                //保存token和username数据
                BasePreference.saveToken(accountInfoModel.getAccessToken());
                BasePreference.saveUserName(accountInfoModel.getNikeName());//返回nikename，实为username
                BasePreference.saveUserType(accountInfoModel.getUserType());
                BasePreference.saveAlipayAccount(accountInfoModel.getAlipayAccount());
                BasePreference.saveHotelId(accountInfoModel.getHotelId());
                BasePreference.saveHotelName(accountInfoModel.getHotelName());

                showToast(getString(R.string.login_success));

                if (TextUtils.isEmpty(accountInfoModel.getAlipayAccount())) {
                    jumpToPersonSetView();
                } else {
                    jumpToOtherActivity(MainActivity.class);
                    finish();

                }

            } else {
                showToast(resultModel.message);
            }
        }

    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        showToast(getString(R.string.request_error_tip));
    }

    private void jumpToPersonSetView() {
        Intent intent = new Intent(LoginActivity.this, PersonalInfoSetActivity.class);
        intent.putExtra("from", Conts.FROM_LOGIN);
        startActivity(intent);
        finish();
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
