package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.fragment.ContractReviewFragment;
import sen.wedding.com.weddingsen.business.fragment.FollowUpDetailFragment;
import sen.wedding.com.weddingsen.component.SwitchButton;
import sen.wedding.com.weddingsen.databinding.FollowUpDetailBinding;

/**
 * Created by lorin on 17/3/25.
 */

public class FollowUpDetailActivity extends BaseActivity {

    private FollowUpDetailBinding binding;
    private int orderId;
    private int orderStatus;
    private FragmentManager fragmentManager;
    private ContractReviewFragment contractReviewFragment;
    private FollowUpDetailFragment followUpDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow_up_detail);
        fragmentManager = getSupportFragmentManager();

        orderId = getIntent().getIntExtra("order_id", -1);
        orderStatus = getIntent().getIntExtra("order_status", 1);
        checkTitle();
        setTabSelection(0);
//        checkTitle();
//        initData();
//        initComponents();
//        getFollowUp();
    }

    private void checkTitle() {
        switch (orderStatus) {
            case 1:
                initTitle();
                break;

            default:
                initSwitchTitle();
                break;
        }
    }

    private void initSwitchTitle() {

        List<String> tabTextList = Arrays.asList(getString(R.string.guest_info_detail), getString(R.string.contract_review));
        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) findViewById(R.id.title_switch);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        SwitchButton switchButton = (SwitchButton) linearLayoutTitle.findViewById(R.id.sb_main_follower);

        textViewRight.setText(getString(R.string.follow_log));
        switchButton.setText(tabTextList);
        switchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                showToast("" + position);
                setTabSelection(position);
            }
        });

        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowUpDetailActivity.this, LogInfoActivity.class);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearLayoutTitle.setVisibility(View.VISIBLE);
    }

    private void initTitle() {
        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);

        textViewRight.setText(getString(R.string.follow_log));
        textViewTitle.setText(getString(R.string.guest_info_detail));
        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowUpDetailActivity.this, LogInfoActivity.class);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();            }
        });

        linearLayoutTitle.setVisibility(View.VISIBLE);

    }

    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {

            case 0:

                if (followUpDetailFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    followUpDetailFragment = FollowUpDetailFragment.newInstance(orderId, orderStatus);
                    transaction.add(R.id.fl_content, followUpDetailFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(followUpDetailFragment);
                }
                break;

            case 1:

                if (contractReviewFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    contractReviewFragment = ContractReviewFragment.newInstance(orderId);
                    transaction.add(R.id.fl_content, contractReviewFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(contractReviewFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (contractReviewFragment != null) {
            transaction.hide(contractReviewFragment);
        }

        if (followUpDetailFragment != null) {
            transaction.hide(followUpDetailFragment);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}