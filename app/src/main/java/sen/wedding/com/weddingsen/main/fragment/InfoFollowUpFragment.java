package sen.wedding.com.weddingsen.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.main.activity.MainActivity;

/**
 * Created by lorin on 17/5/8.
 */

public class InfoFollowUpFragment extends BaseFragment implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private FollowListFragment followListFragment;

    public static InfoFollowUpFragment newInstance() {

        Bundle args = new Bundle();
        InfoFollowUpFragment fragment = new InfoFollowUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_follow, null);

        fragmentManager = getChildFragmentManager();
        initTitle(view);
        addMainView();
        return view;

    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);
        LinearLayout layoutLeft = (LinearLayout) linearLayoutTitle.findViewById(R.id.ll_left);
        textViewRight.setVisibility(View.GONE);

        textViewTitle.setText(getString(R.string.kezi_follow));

        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    private void addMainView() {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (followListFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            followListFragment = FollowListFragment.newInstance();
            transaction.add(R.id.fl_replace, followListFragment);
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {

    }
}
