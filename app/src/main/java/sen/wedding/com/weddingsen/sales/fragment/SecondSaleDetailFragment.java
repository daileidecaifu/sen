package sen.wedding.com.weddingsen.sales.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.model.DetailResModel;
import sen.wedding.com.weddingsen.business.model.OrderItemModel;
import sen.wedding.com.weddingsen.databinding.FirstSaleDetailBinding;
import sen.wedding.com.weddingsen.databinding.SecondSaleDetailBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.sales.activity.FirstSaleContractActivity;
import sen.wedding.com.weddingsen.sales.activity.ModifyRestTimeActivity;
import sen.wedding.com.weddingsen.sales.activity.SecondSaleContractActivity;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

/**
 * Created by lorin on 17/5/22.
 */

public class SecondSaleDetailFragment extends BaseFragment implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    SecondSaleDetailBinding binding;
    private ReviewInfoAdapter adapter;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    private ApiRequest getOrderDetailRequest, submitFollowRequest;
    private DetailResModel detailResModel;

    private List<BaseTypeModel> specifyModels;
    private String[] typeArray;
    private int actionType;
    private int orderId;
    private int orderStatus;
    private int afterDays = -1;
    private String orginTime;

    public static SecondSaleDetailFragment newInstance(int orderId, int orderStatus) {
        Bundle args = new Bundle();
        SecondSaleDetailFragment fragment = new SecondSaleDetailFragment();
        args.putInt("order_id", orderId);
        args.putInt("order_status", orderStatus);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments().getInt("order_id");
        orderStatus = getArguments().getInt("order_status");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_second_sale_detail, container, false);
        binding.setClickListener(this);
        initData();
        initComponents();
        getFollowUp();
        return binding.getRoot();

    }

    private void initData() {

        typeArray = getResources().getStringArray(R.array.second_sale_detail_action);
//        Conts.getFollowActionStatusMap().values().toArray(typeArray);

        sbType = new StringBuffer();
        sbType.append(StringUtil.createHtml(getString(R.string.specify_position), "#313133"));
        sbType.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemHotel = new StringBuffer();
        sbItemHotel.append(StringUtil.createHtml(getString(R.string.hotel), "#313133"));
        sbItemHotel.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemDistrict = new StringBuffer();
        sbItemDistrict.append(StringUtil.createHtml(getString(R.string.district), "#313133"));
        sbItemDistrict.append(StringUtil.createHtml("*", "#fa4b4b"));

        specifyModels = Conts.getSpecifyTypeArray();

    }

    private void initComponents() {

//        binding.svAll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//        binding.svAll.setFocusable(true);
//        binding.svAll.setFocusableInTouchMode(true);
//        binding.svAll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.requestFocusFromTouch();
//                return false;
//            }
//        });

        //姓名
        binding.llShowName.tvItemSelectTitle.setText(getString(R.string.name));
        binding.llShowName.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //类型
        binding.llShowType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llShowType.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //手机号
        binding.llShowPhoneNumber.tvItemSelectTitle.setText(getString(R.string.phone_number));
        binding.llShowPhoneNumber.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llShowPhoneNumber.tvItemSelectRightText.setVisibility(View.VISIBLE);
        binding.llShowPhoneNumber.tvItemSelectRightText.setText(getString(R.string.contact_clients));
        binding.llShowPhoneNumber.tvItemSelectRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Call");
            }
        });

        //指定item
        binding.llShowSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
        binding.llShowSpecifyItem.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //预算
        binding.llShowBudget.tvItemSelectTitle.setText(getString(R.string.budget));
        binding.llShowBudget.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //时间
        binding.llShowTime.tvItemSelectTitle.setText(getString(R.string.time));
        binding.llShowTime.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //类型
        binding.llActionType.tvItemSelectTitle.setText(getString(R.string.process));
        binding.llActionType.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionType();
            }
        });
        binding.llActionType.tvItemSelectContent.setText(typeArray[0]);

        //下次跟进
        binding.llFollowUpTime.tvItemSelectTitle.setText(getString(R.string.original_time));
        binding.llFollowUpTime.tvItemSelectIcon.setVisibility(View.GONE);

        actionType = Conts.SECOND_FOLLOW_UP_MIDDLE;
        afterDays = 1;
    }

    private void getFollowUp() {
        if (orderId != -1) {
            getOrderDetailRequest = new ApiRequest(URLCollection.URL_SHOW_BUILD_INFO_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("order_id", orderId + "");
            getOrderDetailRequest.setParams(param);
            getApiService().exec(getOrderDetailRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void showActionType() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle(getString(R.string.hint)); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(typeArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switchShowAction(which);

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


    private void switchShowAction(int type) {
        binding.llActionType.tvItemSelectContent.setText(typeArray[type]);
        switch (type) {
            case 0:
                binding.llFollowUpTime.getRoot().setVisibility(View.VISIBLE);
//                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = Conts.SECOND_FOLLOW_UP_MIDDLE;
                break;

            case 1:
                binding.llFollowUpTime.getRoot().setVisibility(View.VISIBLE);
//                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = Conts.SECOND_FOLLOW_UP_ADDITIONAL;
                break;

            case 2:
                binding.llFollowUpTime.getRoot().setVisibility(View.GONE);
//                binding.tvFollowUpSubmit.setText(getString(R.string.submit_certificate));
                actionType = Conts.SECOND_FOLLOW_UP_MODIFY;
                break;

            case 3:
                binding.llFollowUpTime.getRoot().setVisibility(View.VISIBLE);
//                binding.tvFollowUpSubmit.setText(getString(R.string.submit_certificate));
                actionType = Conts.SECOND_FOLLOW_UP_REST;
                break;
        }
    }

    private void fillData(DetailResModel detailResModel) {
        OrderItemModel orderItemModel = detailResModel.getOrderItem();
        binding.llShowName.tvItemSelectContent.setText(orderItemModel.getCustomerName());
        binding.llShowType.tvItemSelectContent.setText(Conts.getOrderTypeMap().get(orderItemModel.getOrderType()));
        binding.llShowPhoneNumber.tvItemSelectContent.setText(orderItemModel.getOrderPhone());
        binding.llShowSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));


        binding.llShowSpecifyItem.tvItemSelectContent.setText(orderItemModel.getOrderAreaHotelName());
        binding.llShowBudget.tvItemSelectContent.setText(orderItemModel.getOrderMoney());

        long time = Long.parseLong(orderItemModel.getCreateTime()) * 1000;
        binding.llShowTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D));

        binding.tvShowNote.setText(orderItemModel.getOrderDesc());

        long heldTime = Long.parseLong(orderItemModel.getUseDate()) * 1000;
        orginTime = DateUtil.convertDateToString(new Date(heldTime), DateUtil.FORMAT_COMMON_Y_M_D);
        binding.llFollowUpTime.tvItemSelectContent.setText(orginTime);

        initBottomView(detailResModel.getHandleNote());


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

        if (req == getOrderDetailRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {

                detailResModel = GsonConverter.decode(resultModel.data, DetailResModel.class);
                fillData(detailResModel);
            } else {
                showToast(resultModel.message);
            }
        } else if (req == submitFollowRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.action_success));
                getActivity().finish();
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        showToast(getString(R.string.request_error_tip));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_follow_up_submit:

                switch (actionType) {
                    case Conts.SECOND_FOLLOW_UP_MIDDLE:
                    case Conts.SECOND_FOLLOW_UP_ADDITIONAL:
                    case Conts.SECOND_FOLLOW_UP_REST:
                        Intent intent = new Intent(getActivity(), SecondSaleContractActivity.class);
                        intent.putExtra("order_id", orderId);
                        intent.putExtra("action_type", actionType);
                        startActivityForResult(intent, Conts.TO_SUBMIT_CONTRACT_REVIEW);
                        break;

                    case Conts.SECOND_FOLLOW_UP_MODIFY:
                        Intent intent2 = new Intent(getActivity(), ModifyRestTimeActivity.class);
                        intent2.putExtra("order_id", orderId);
                        intent2.putExtra("original_time", orginTime);
                        startActivityForResult(intent2, Conts.TO_SUBMIT_CONTRACT_REVIEW);
                        break;
                }

                break;
        }

    }

    private void initBottomView(String content) {
        switch (orderStatus) {
            case 1:
                binding.llReviewProgress.setVisibility(View.GONE);
                binding.llToFollow.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
//                binding.tvMessage.setText(getString(R.string.to_review_message));
                binding.tvMessage.setText(content);
                break;
            case 3:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
//                binding.tvMessage.setText(getString(R.string.reviewed_message));
                binding.tvMessage.setText(content);
                break;
            case 4:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvModifyAndSubmit.setVisibility(View.VISIBLE);
//                binding.tvMessage.setText(getString(R.string.settlemented_message));
                binding.tvMessage.setText(content);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Conts.TO_SUBMIT_CONTRACT_REVIEW) {
            if (resultCode == Activity.RESULT_OK) {
                EventBus.getDefault().post(new EventIntent(Conts.EVENT_SECOND_SALE_LIST_REFRESH, ""));
                getActivity().finish();
            }
        }
    }
}
