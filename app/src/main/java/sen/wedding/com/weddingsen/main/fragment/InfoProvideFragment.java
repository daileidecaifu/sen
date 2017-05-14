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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.VerifyGuestInfoActivity;
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
        addMainView();
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

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        switchButton.setText(tabTextList);
        switchButton.setOnSwitchListener(new SwitchButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                showToast("" + position);
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
                ((MainActivity) getActivity()).openMenu();
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

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_create));

        textViewTitle.setText(getString(R.string.guest_info_list));
        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                getActivity().startActivity(intent);

            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openMenu();
            }
        });

        linearLayoutTitle.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {

    }

    private void addMainView() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (guestInfoListFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            guestInfoListFragment = GuestInfoListFragment.newInstance();
            transaction.add(R.id.fl_replace, guestInfoListFragment);
        }
        transaction.commit();
    }

    private void showSelectType() {

        final String[] items = new String[]
                {getActivity().getString(R.string.create_kezi_info),
                        getActivity().getString(R.string.create_build_info)};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
                        getActivity().startActivity(intent);

                        break;
                    case 1:

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


