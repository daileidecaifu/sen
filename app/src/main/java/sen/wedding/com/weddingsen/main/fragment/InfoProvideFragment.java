package sen.wedding.com.weddingsen.main.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.business.activity.VerifyGuestInfoActivity;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.SwitchButton;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/5/8.
 */

public class InfoProvideFragment extends BaseFragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private GuestInfoListFragment guestInfoListFragment;
    private BuildInfoListFragment buildInfoListFragment;

    private int yourChoice = 0;

    public static InfoProvideFragment newInstance() {

        Bundle args = new Bundle();
        InfoProvideFragment fragment = new InfoProvideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_provide, null);
        fragmentManager = getChildFragmentManager();

        checkTitle(view);
        setTabSelection(0);
        return view;

    }

    private void checkTitle(View view) {
        switch (BasePreference.getUserType()) {
            case Conts.LOGIN_MODEL_PHONE:
                initTitle(view);
                break;

            case Conts.LOGIN_MODEL_ACCOUNT:
                initSwitchTitle(view);
                break;
        }
    }


    private void initSwitchTitle(View view) {

        List<String> tabTextList = Arrays.asList(getString(R.string.guest_info), getString(R.string.build_info));
        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_switch);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        SwitchButton switchButton = (SwitchButton) linearLayoutTitle.findViewById(R.id.sb_main_follower);

        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        switchButton.setText(tabTextList);
        switchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                showToast(""+position);
                setTabSelection(position);

            }
        });

        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectType();

            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        linearLayoutTitle.setVisibility(View.VISIBLE);
    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);
        LinearLayout layoutRight = (LinearLayout) linearLayoutTitle.findViewById(R.id.ll_right);
        LinearLayout layoutLeft = (LinearLayout) linearLayoutTitle.findViewById(R.id.ll_left);
//        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        textViewTitle.setText(getString(R.string.guest_info_list));
        layoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                intent.putExtra("source", Conts.SOURCE_VERIFY_KEZI);
                getActivity().startActivity(intent);

            }
        });

        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        linearLayoutTitle.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {

    }

    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:

                if (guestInfoListFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    guestInfoListFragment = GuestInfoListFragment.newInstance();
                    transaction.add(R.id.fl_replace, guestInfoListFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(guestInfoListFragment);
                }
                break;

            case 1:


                if (buildInfoListFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    buildInfoListFragment = BuildInfoListFragment.newInstance();
                    transaction.add(R.id.fl_replace, buildInfoListFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(buildInfoListFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (guestInfoListFragment != null) {
            transaction.hide(guestInfoListFragment);
        }

        if (buildInfoListFragment != null) {
            transaction.hide(buildInfoListFragment);
        }

    }

    private void showSelectType() {

        final String[] items = new String[]
                {getActivity().getString(R.string.create_kezi_info),
                        getActivity().getString(R.string.create_build_info)};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setSingleChoiceItems(items, yourChoice,
                null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourChoice = which;

                switch (which) {
                    case Conts.SOURCE_VERIFY_KEZI:
                        Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                        intent.putExtra("source", Conts.SOURCE_VERIFY_KEZI);
                        getActivity().startActivity(intent);

                        break;
                    case Conts.SOURCE_VERIFY_BUILD:
                        Intent intent1 = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                        intent1.putExtra("source", Conts.SOURCE_VERIFY_BUILD);
                        getActivity().startActivity(intent1);

                        break;
                }

                dialog.dismiss();
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}


