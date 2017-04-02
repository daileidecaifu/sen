package sen.wedding.com.weddingsen.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.PersonalInfoSetBinding;

/**
 * Created by lorin on 17/3/22.
 */

public class PersonalInfoSetActivity extends BaseActivity implements View.OnClickListener {

    private PersonalInfoSetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info_set);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setCommonRightText(getString(R.string.next_step));
        getTitleBar().setTitle(getString(R.string.set_personal_info));
        getTitleBar().setLeftVisibility(View.GONE);
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(MainActivity.class);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                jumpToOtherActivity(MainActivity.class);
                break;


        }
    }
}
