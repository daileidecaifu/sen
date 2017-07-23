package sen.wedding.com.weddingsen.sales.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FragmentTitleCommonBinding;
import sen.wedding.com.weddingsen.sales.fragment.FirstSaleListFragment;
import sen.wedding.com.weddingsen.sales.fragment.SecondSaleListFragment;

/**
 * Created by lorin on 17/6/10.
 */

public class BuildFollowAcrivity extends BaseActivity implements View.OnClickListener{

    FirstSaleListFragment firstSaleListFragment;
    SecondSaleListFragment secondSaleListFragment;
    private FragmentManager fragmentManager;
    FragmentTitleCommonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_with_fragment_common_title);
        binding.setClickListener(this);
        initTitle();
        checkSaleType();
    }

    private void checkSaleType()
    {
        String userType = BasePreference.getUserType();

        switch (userType)
        {
            case Conts.LOGIN_MODEL_FIRST_SALE:
                addFirstSaleFragmentView();
                break;

            case Conts.LOGIN_MODEL_SECOND_SALE:
                addSecondSaleFragmentView();
                break;
        }
    }

    private void addFirstSaleFragmentView() {
        fragmentManager = getSupportFragmentManager();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        if (firstSaleListFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            firstSaleListFragment = FirstSaleListFragment.newInstance();
            transaction.add(R.id.fl_content, firstSaleListFragment);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(firstSaleListFragment);
        }
        transaction.commit();
    }

    private void addSecondSaleFragmentView() {
        fragmentManager = getSupportFragmentManager();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        if (secondSaleListFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            secondSaleListFragment = SecondSaleListFragment.newInstance();
            transaction.add(R.id.fl_content, secondSaleListFragment);
        } else {
            // 如果MessageFragment不为空，则直接将它显示出来
            transaction.show(secondSaleListFragment);
        }
        transaction.commit();
    }

    private void initTitle() {

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setTitle(getString(R.string.build_follow));
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

