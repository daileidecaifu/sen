package sen.wedding.com.weddingsen.business.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.model.DetailResModel;
import sen.wedding.com.weddingsen.databinding.ContractReviceBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/22.
 */

public class ContractReviewFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse> {

    ContractReviceBinding binding;
    private int orderId;
    private ApiRequest getContractReviewRequest;

    public static ContractReviewFragment newInstance(int orderId) {
        Bundle args = new Bundle();
        ContractReviewFragment fragment = new ContractReviewFragment();
        args.putInt("order_id", orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments().getInt("order_id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_contract_review, container, false);
        initConponents();
        return binding.getRoot();

    }

    private void initConponents() {
        //合同时间
        binding.llContractTime.tvItemSelectTitle.setText(getString(R.string.contract_money));
        binding.llContractTime.tvItemSelectIcon.setVisibility(View.GONE);
        //签单时间
        binding.llSignUpTime.tvItemSelectTitle.setText(getString(R.string.sign_up_time));
        binding.llSignUpTime.tvItemSelectIcon.setVisibility(View.GONE);
    }

    private void getFollowUp() {
        if (orderId != -1) {
            getContractReviewRequest = new ApiRequest(URLCollection.URL_SHOW_GUEST_INFO_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");
            getContractReviewRequest.setParams(param);
            getApiService().exec(getContractReviewRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        ResultModel resultModel = resp.getResultModel();
        closeProgressDialog();

        if (req == getContractReviewRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {

            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }
}
