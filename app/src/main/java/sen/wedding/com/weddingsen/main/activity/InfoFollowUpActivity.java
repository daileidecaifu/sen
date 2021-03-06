package sen.wedding.com.weddingsen.main.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.databinding.FragmentCommonBinding;
import sen.wedding.com.weddingsen.main.fragment.InfoFollowUpFragment;

/**
 * Created by lorin on 17/6/7.
 */

public class InfoFollowUpActivity extends BaseActivity implements View.OnClickListener{

    InfoFollowUpFragment infoFollowUpFragment;
    private FragmentManager fragmentManager;
    FragmentCommonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with_fragment_common);
        binding.setClickListener(this);
        addFragmentView();
    }

    private void addFragmentView() {
        fragmentManager = getSupportFragmentManager();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        if (infoFollowUpFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            infoFollowUpFragment = InfoFollowUpFragment.newInstance();
            transaction.add(R.id.fl_content, infoFollowUpFragment);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(infoFollowUpFragment);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }
}
