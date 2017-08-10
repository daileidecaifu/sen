package sen.wedding.com.weddingsen.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import sen.wedding.com.weddingsen.base.acitivity.DebugCrashListActivity;
import sen.wedding.com.weddingsen.base.acitivity.SenSystemActivity;
import sen.wedding.com.weddingsen.business.activity.EditGuestInfoActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FeedbackBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/3/30.
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    FeedbackBinding binding;

    private ApiRequest feedbackRequest;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.user_feedback));
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setCommonRightText("         ");
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                if (count == 10) {
                    jumpToOtherActivity(SenSystemActivity.class);
                    count = 0;
                }
            }
        });
        initComponents();

    }

    private void initComponents() {
        if (BasePreference.getUserType().equals(Conts.LOGIN_MODEL_PHONE)) {
            binding.etFeedbackPhone.setText(BasePreference.getUserName());
        }
    }

    private void submitFeedback() {

        if (TextUtils.isEmpty(binding.etFeedbackContent.getText().toString().trim())) {
            showToast(getString(R.string.feedback_content_empty));
            return;
        }

        if (!TextUtils.isEmpty(binding.etFeedbackPhone.getText().toString()) && binding.etFeedbackPhone.getText().toString().trim().length() != 11) {
            showToast(getString(R.string.phone_number_wrong_format));
            return;
        }

        showProgressDialog(false);
        feedbackRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_FEEDBACK, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("phone", binding.etFeedbackPhone.getText().toString());
        param.put("content", binding.etFeedbackContent.getText().toString());
        param.put("access_token", BasePreference.getToken());

        feedbackRequest.setParams(param);
        getApiService().exec(feedbackRequest, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback_submit:
                submitFeedback();
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

        if (req == feedbackRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.feedback_success));
                finish();
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
