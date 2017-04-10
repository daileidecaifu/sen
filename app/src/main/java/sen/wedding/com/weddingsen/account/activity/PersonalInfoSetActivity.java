package sen.wedding.com.weddingsen.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;

import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.PersonalInfoSetBinding;
import sen.wedding.com.weddingsen.utils.PreferenceUtils;

/**
 * Created by lorin on 17/3/22.
 */

public class PersonalInfoSetActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    private PersonalInfoSetBinding binding;
    private int fromTag;
    private ApiRequest bindAlipayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info_set);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.set_personal_info));
        getTitleBar().setRightClickEvent(this);
        getTitleBar().setLeftClickEvent(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        fromTag = intent.getIntExtra("from", 1);
        switch (fromTag) {
            case Conts.FROM_LOGIN:
                getTitleBar().setCommonRightText(getString(R.string.next_step));
                binding.tvSkip.setVisibility(View.VISIBLE);
                getTitleBar().setLeftVisibility(View.GONE);
                break;

            case Conts.FROM_MAIN:

                getTitleBar().setCommonRightText(getString(R.string.save));
                binding.tvSkip.setVisibility(View.GONE);
                getTitleBar().setLeftVisibility(View.VISIBLE);

                break;
        }

        if(!TextUtils.isEmpty(BasePreference.getAlipayAccount()))
        {
            binding.etAlipayInput.setText(BasePreference.getAlipayAccount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                jumpToOtherActivity(MainActivity.class);
                break;
            case R.id.ll_right:
                setAlipayAccount();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void setAlipayAccount() {

        if (TextUtils.isEmpty(binding.etAlipayInput.getText().toString().trim())) {
            showToast(getString(R.string.alipay_empty));
            return;
        }

        bindAlipayRequest = new ApiRequest(URLCollection.URL_BIND_ALIPAY, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("alipay", binding.etAlipayInput.getText().toString().trim());
        bindAlipayRequest.setParams(param);
        getApiService().exec(bindAlipayRequest, this);
    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        ResultModel resultModel = resp.getResultModel();

        if (req == bindAlipayRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.bind_alipay_success));
                BasePreference.saveAlipayAccount(req.getParams().get("alipay"));
                if (fromTag == Conts.FROM_LOGIN) {
                    jumpToOtherActivity(MainActivity.class);
                    finish();
                } else if (fromTag == Conts.FROM_MAIN) {
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

}