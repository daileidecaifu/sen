package sen.wedding.com.weddingsen.sales.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FeedbackBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;

/**
 * Created by lorin on 17/6/19.
 */

public class ModifyRestTimeActivity extends BaseActivity implements  View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    FeedbackBinding binding;

    private ApiRequest feedbackRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.user_feedback));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {

    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }
}
