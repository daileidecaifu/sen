package sen.wedding.com.weddingsen.business.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.ContractReviewAdapter;
import sen.wedding.com.weddingsen.business.model.ContractReviewModel;
import sen.wedding.com.weddingsen.databinding.ContractReviceBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/22.
 */

public class ContractReviewFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse> {

    ContractReviceBinding binding;
    private ContractReviewAdapter contractReviewAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    private int orderId;
    private ApiRequest getContractReviewRequest;
    private ContractReviewModel contractReviewModel;

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
        getFollowUp();
        return binding.getRoot();

    }

    private void initConponents() {
        //合同时间
        binding.llContractMoney.tvItemSelectTitle.setText(getString(R.string.contract_money));
        binding.llContractMoney.tvItemSelectIcon.setVisibility(View.GONE);
        //签单时间
        binding.llSignUpTime.tvItemSelectTitle.setText(getString(R.string.sign_up_time));
        binding.llSignUpTime.tvItemSelectIcon.setVisibility(View.GONE);

        contractReviewAdapter = new ContractReviewAdapter(getContext(), selectedPhotos);
        binding.rvPicShow.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvPicShow.setAdapter(contractReviewAdapter);
    }

    private void getFollowUp() {
        showProgressDialog(false);
        if (orderId != -1) {
            getContractReviewRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_SHOW_ORDER_SIGN_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");
            getContractReviewRequest.setParams(param);
            getApiService().exec(getContractReviewRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void fillData(ContractReviewModel model) {

        if (null != model.getSignUsingTime()) {
            long currentTimestamp = Long.parseLong(model.getSignUsingTime()) * 1000;
            binding.llSignUpTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(currentTimestamp), DateUtil.FORMAT_COMMON_Y_M_D));
        }
        binding.llContractMoney.tvItemSelectContent.setText(model.getOrderMoney());

        if (null != model.getSignPic()) {
            String[] imgs = model.getSignPic().split(",");
            for (String str : imgs) {
                selectedPhotos.add(str);
            }
            contractReviewAdapter.notifyDataSetChanged();
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
                contractReviewModel = GsonConverter.decode(resultModel.data, ContractReviewModel.class);
                if (contractReviewModel != null) {
                    fillData(contractReviewModel);
                } else {
                    showToast(getString(R.string.data_error_tip));
                }
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        if (req == getContractReviewRequest) {
            showToast(resp.getResultModel().message);
        }
    }
}
