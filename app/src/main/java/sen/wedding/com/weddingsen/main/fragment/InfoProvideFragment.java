package sen.wedding.com.weddingsen.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import sen.wedding.com.weddingsen.component.SwitchButton;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/5/8.
 */

public class InfoProvideFragment extends BaseFragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private KeziListFragment keziListFragment;

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
        initSwitchTitle(view);
        initTitle(view);
        addMainView();
        return view;

    }


    private void initSwitchTitle(View view) {

        List<String> tabTextList = Arrays.asList(getString(R.string.guest_info), getString(R.string.contract_review));
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
                showToast(""+position);
            }
        });

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
                ((MainActivity)getActivity()).openMenu();
            }
        });
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
                ((MainActivity)getActivity()).openMenu();
            }
        });

    }


    @Override
    public void onClick(View v) {

    }

    private void addMainView() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (keziListFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            keziListFragment = KeziListFragment.newInstance();
            transaction.add(R.id.fl_replace, keziListFragment);
        }
        transaction.commit();
    }

}


