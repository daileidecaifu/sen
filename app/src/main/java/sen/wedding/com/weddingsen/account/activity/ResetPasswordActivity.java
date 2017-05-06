package sen.wedding.com.weddingsen.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.ResetActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;

/**
 * Created by lorin on 17/5/7.
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    ResetActivityBinding binding;

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

    private void initComponents()
    {
        binding.llEditPasswordOriginal.tvItemEditTitle.setText(getString(R.string.password_original));
        binding.llEditPasswordOriginal.etItemEditInput.setHint(getString(R.string.password_original_hint));

        binding.llEditPasswordNew.tvItemEditTitle.setText(getString(R.string.password_new));
        binding.llEditPasswordNew.etItemEditInput.setHint(getString(R.string.password_new_hint));

        binding.llEditPasswordConfirm.tvItemEditTitle.setText(getString(R.string.password_confirm));
        binding.llEditPasswordConfirm.etItemEditInput.setHint(getString(R.string.password_confirm_hint));
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
