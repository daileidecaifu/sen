package sen.wedding.com.weddingsen.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseFragment;

/**
 * Created by lorin on 17/5/8.
 */

public class ProvideFragment extends BaseFragment {

    public static ProvideFragment newInstance(int orderStatus) {

        Bundle args = new Bundle();
        ProvideFragment fragment = new ProvideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_list, null);
        return view;

    }
}
