package sen.wedding.com.weddingsen.sales.fragment;

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
import java.util.List;

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
import sen.wedding.com.weddingsen.databinding.FirstSaleContractReviceFragmentBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.sales.model.FirstSaleSignDetailModel;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/22.
 */

public class FirstSaleContractFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse> {

    FirstSaleContractReviceFragmentBinding binding;
    private ContractReviewAdapter contractReviewAdapter;
    private List<String> selectedPhotos = new ArrayList<>();

    private int orderId;
    private ApiRequest getContractReviewRequest;
    private FirstSaleSignDetailModel firstSaleSignDetailModel;

    public static FirstSaleContractFragment newInstance(int orderId) {
        Bundle args = new Bundle();
        FirstSaleContractFragment fragment = new FirstSaleContractFragment();
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
                R.layout.fragment_first_sale_contract_review, container, false);
        initConponents();
        getFollowUp();
        return binding.getRoot();

    }

    private void initConponents() {
        //合同时间
        binding.llContractMoney.tvItemSelectTitle.setText(getString(R.string.contract_money));
        binding.llContractMoney.tvItemSelectIcon.setVisibility(View.GONE);
        //签单时间
        binding.llSignUpTime.tvItemSelectTitle.setText(getString(R.string.held_time));
        binding.llSignUpTime.tvItemSelectIcon.setVisibility(View.GONE);

        //首付金额
        binding.llFirstSaleAmount.tvItemSelectTitle.setText(getString(R.string.first_sale_amount));
        binding.llFirstSaleAmount.tvItemSelectIcon.setVisibility(View.GONE);


        //首付时间
        binding.llFirstSaleTime.tvItemSelectTitle.setText(getString(R.string.first_sale_time));
        binding.llFirstSaleTime.tvItemSelectIcon.setVisibility(View.GONE);

        //支付时间
        binding.llNextPayTime.tvItemSelectTitle.setText(getString(R.string.middle_time));
        binding.llNextPayTime.tvItemSelectIcon.setVisibility(View.GONE);

        contractReviewAdapter = new ContractReviewAdapter(getContext(), selectedPhotos);
        binding.rvPicShow.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvPicShow.setAdapter(contractReviewAdapter);
    }

    private void getFollowUp() {
        showProgressDialog(false);
        if (orderId != -1) {
            getContractReviewRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_FIRST_SALE_SIGN_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_dajian_order_id", orderId + "");
            getContractReviewRequest.setParams(param);
            getApiService().exec(getContractReviewRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void fillData(FirstSaleSignDetailModel model) {

        if (model != null && model.getOrderMoney() != null) {
            long currentTimestamp = Long.parseLong(model.getSignUsingTime()) * 1000;
            binding.llSignUpTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(currentTimestamp), DateUtil.FORMAT_COMMON_Y_M_D));

            binding.llContractMoney.tvItemSelectContent.setText(model.getOrderMoney());
            binding.llFirstSaleAmount.tvItemSelectContent.setText(model.getFirstOrderMoney());

            long firstSaleTime = Long.parseLong(model.getFirstOrderUsingTime()) * 1000;
            binding.llFirstSaleTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(firstSaleTime), DateUtil.FORMAT_COMMON_Y_M_D));

            long nextPayTime = Long.parseLong(model.getNextPayTime()) * 1000;
            binding.llNextPayTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(nextPayTime), DateUtil.FORMAT_COMMON_Y_M_D));

            if(null!=model.getSignPic()) {
                for (String str : model.getSignPic()) {
                    selectedPhotos.add(str);
                }
                contractReviewAdapter.notifyDataSetChanged();
            }
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
                firstSaleSignDetailModel = GsonConverter.decode(resultModel.data, FirstSaleSignDetailModel.class);
                fillData(firstSaleSignDetailModel);
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
