package sen.wedding.com.weddingsen.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.ResetActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;

/**
 * Created by lorin on 17/5/7.
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    ResetActivityBinding binding;
    private ApiRequest resetPasswordRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.password_reset));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initComponents();

    }

    private void initComponents() {
        binding.llEditPasswordOriginal.tvItemEditTitle.setText(getString(R.string.password_original));
        binding.llEditPasswordOriginal.etItemEditInput.setHint(getString(R.string.password_original_hint));

        binding.llEditPasswordNew.tvItemEditTitle.setText(getString(R.string.password_new));
        binding.llEditPasswordNew.etItemEditInput.setHint(getString(R.string.password_new_hint));

        binding.llEditPasswordConfirm.tvItemEditTitle.setText(getString(R.string.password_confirm));
        binding.llEditPasswordConfirm.etItemEditInput.setHint(getString(R.string.password_confirm_hint));
    }

    private void resetPassword() {

        String passwordOriginal = binding.llEditPasswordOriginal.etItemEditInput.getText().toString().trim();

        if (TextUtils.isEmpty(passwordOriginal)) {
            showToast(getString(R.string.original_password_can_not_empty));
            return;
        }
        String passwordNew = binding.llEditPasswordNew.etItemEditInput.getText().toString().trim();

        if (TextUtils.isEmpty(passwordNew)) {
            showToast(getString(R.string.new_password_can_not_empty));
            return;
        }

        String passwordConfirm = binding.llEditPasswordConfirm.etItemEditInput.getText().toString().trim();

        if (TextUtils.isEmpty(passwordConfirm)) {
            showToast(getString(R.string.new_password_can_not_empty));
            return;
        }


        if (!passwordNew.equals(passwordConfirm)) {
            showToast(getString(R.string.password_can_not_match));
            return;
        }

        showProgressDialog(false);
        resetPasswordRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_RESET_PASSWORD, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("old_password", passwordOriginal);
        param.put("password", passwordNew);
        param.put("re_password", passwordConfirm);

        resetPasswordRequest.setParams(param);
        getApiService().exec(resetPasswordRequest, this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_reset:
                resetPassword();
                break;
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
        closeProgressDialog();

        if (req == resetPasswordRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.reset_password_success));
                finish();
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        showToast(resp.getResultModel().message);

    }
}
