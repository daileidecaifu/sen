package sen.wedding.com.weddingsen.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FeedbackBinding;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/3/30.
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener{

    FeedbackBinding binding;

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
}
