package sen.wedding.com.weddingsen.business.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.databinding.ContractReviceBinding;

/**
 * Created by lorin on 17/5/22.
 */

public class ContractReviewFragment extends BaseFragment {

    ContractReviceBinding binding;

    public static ContractReviewFragment newInstance(int orderId) {
        Bundle args = new Bundle();
        ContractReviewFragment fragment = new ContractReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_contract_review, container, false);
        initConponents();
        return binding.getRoot();

    }

    private void initConponents()
    {
        //合同时间
        binding.llContractTime.tvItemEditTitle.setText(getString(R.string.contract_time));
        binding.llContractTime.etItemEditInput.setHint(getString(R.string.contract_time_tip));
        //签单时间
        binding.llSignUpTime.tvItemEditTitle.setText(getString(R.string.sign_up_time));
        binding.llSignUpTime.etItemEditInput.setHint(getString(R.string.sign_up_time_tip));
    }
}
