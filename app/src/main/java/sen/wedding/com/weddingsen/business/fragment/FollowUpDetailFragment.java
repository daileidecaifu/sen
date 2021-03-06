package sen.wedding.com.weddingsen.business.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
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
import sen.wedding.com.weddingsen.business.activity.ContractInfoActivity;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.model.DetailResModel;
import sen.wedding.com.weddingsen.business.model.OrderItemModel;
import sen.wedding.com.weddingsen.databinding.ContractReviceBinding;
import sen.wedding.com.weddingsen.databinding.FollowUpDetailBinding;
import sen.wedding.com.weddingsen.databinding.FollowUpFragmentBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

/**
 * Created by lorin on 17/5/22.
 */

public class FollowUpDetailFragment extends BaseFragment implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    FollowUpFragmentBinding binding;
    private ReviewInfoAdapter adapter;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    private ApiRequest getOrderDetailRequest, submitFollowRequest;
    private DetailResModel detailResModel;

    private String[] nextFollowUpItems;
    private List<BaseTypeModel> specifyModels;
    private String[] typeArray;
    private int actionType;
    private int orderId;
    private int orderStatus;
    private int afterDays = -1;
    private Map<Integer, String> typeMap;
    OrderItemModel orderItemModel;
    private int yourActionChoice, yourAfterDaysChoice = 0;

    public static FollowUpDetailFragment newInstance(int orderId, int orderStatus) {
        Bundle args = new Bundle();
        FollowUpDetailFragment fragment = new FollowUpDetailFragment();
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
                R.layout.fragment_followup_detail, container, false);
        binding.setClickListener(this);
        initData();
        initComponents();
        getFollowUp();
        return binding.getRoot();

    }

    private void initData() {

        nextFollowUpItems = getResources().getStringArray(R.array.next_follow_up_item);

        typeMap = Conts.getFollowActionStatusMap();
        typeArray = new String[typeMap.size()];
//        Conts.getFollowActionStatusMap().values().toArray(typeArray);

        for (int i = 0; i < 3; i++) {
            typeArray[i] = typeMap.get(i + 1);
        }

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
        //姓名
        binding.llShowName.tvItemSelectTitle.setText(getString(R.string.name));
        binding.llShowName.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //类型
        binding.llShowType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llShowType.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //手机号
        binding.llShowPhoneNumber.tvItemSelectTitle.setText(getString(R.string.phone_number));
        binding.llShowPhoneNumber.tvItemSelectIcon.setVisibility(View.GONE);

        //指定类型
        binding.llShowSpecifyType.tvItemSelectTitle.setText(Html.fromHtml(sbType.toString()));
        binding.llShowSpecifyType.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //指定item
        binding.llShowSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
        binding.llShowSpecifyItem.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //桌数
        binding.llShowTableCount.tvItemSelectTitle.setText(getString(R.string.table_count));
        binding.llShowTableCount.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //预算
        binding.llShowBudget.tvItemSelectTitle.setText(getString(R.string.budget));
        binding.llShowBudget.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //时间
        binding.llShowTime.tvItemSelectTitle.setText(getString(R.string.time));
        binding.llShowTime.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //类型
        binding.llActionType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llActionType.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActionType();
            }
        });
        binding.llActionType.tvItemSelectContent.setText(typeArray[0]);

        //下次跟进
        binding.llFollowUpTime.tvItemSelectTitle.setText(getString(R.string.next_follow));
        binding.llFollowUpTime.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectAfterDays();
            }
        });

        binding.llFollowUpTime.tvItemSelectContent.setText(nextFollowUpItems[0]);
        actionType = Conts.FOLLOW_UP_INFO_EFFECTIVE;
        afterDays = 1;

        StringBuffer sb = new StringBuffer();
        sb.append(StringUtil.createHtml(getString(R.string.note), "#313133"));
        sb.append(StringUtil.createHtml("*", "#fa4b4b"));
        binding.tvFollowNote.setText(Html.fromHtml(sb.toString()));

    }

    private void getFollowUp() {
        if (orderId != -1) {
            getOrderDetailRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_SHOW_GUEST_INFO_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("order_id", orderId + "");
            param.put("detail_type", Conts.KEZI_DETAIL_SOURCE_FOLLOWER + "");

            getOrderDetailRequest.setParams(param);
            getApiService().exec(getOrderDetailRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void submitFollowUp() {


        if (TextUtils.isEmpty(binding.etEditNote.getText().toString())) {
            showToast(getString(R.string.note_can_not_be_empty));
            return;
        }

        if (orderId != -1) {
            submitFollowRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_ORDER_FOLLOW, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");
            param.put("user_order_status", actionType + "");
            if (actionType == Conts.FOLLOW_UP_INFO_EFFECTIVE) {
                long afterTimestamp = (System.currentTimeMillis() + 1000 * 60 * 60 * 24 * afterDays) / 1000;
                param.put("follow_time", afterTimestamp + "");
            }
            param.put("follow_desc", binding.etEditNote.getText().toString());

            submitFollowRequest.setParams(param);
            getApiService().exec(submitFollowRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void showActionType() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setSingleChoiceItems(typeArray, yourActionChoice,
                null);
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(typeArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yourActionChoice = which;
                dialog.dismiss();
                switchShowAction(which);

            }
        });
//        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        builder.create().show();
    }

    private void showSelectAfterDays() {

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle(getString(R.string.select_next_follow_up)); //设置标题
        builder.setSingleChoiceItems(typeArray, yourAfterDaysChoice,
                null);
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(nextFollowUpItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                yourAfterDaysChoice = which;
                afterDays = which + 1;
                binding.llFollowUpTime.tvItemSelectContent.setText(nextFollowUpItems[which]);

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
                binding.llFollowUpNote.setVisibility(View.VISIBLE);
                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = Conts.FOLLOW_UP_INFO_EFFECTIVE;
                break;

            case 1:
                binding.llFollowUpTime.getRoot().setVisibility(View.GONE);
                binding.llFollowUpNote.setVisibility(View.VISIBLE);
                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = Conts.FOLLOW_UP_INFO_INVALID;
                break;

            case 2:
                binding.llFollowUpTime.getRoot().setVisibility(View.GONE);
                binding.llFollowUpNote.setVisibility(View.GONE);
                binding.tvFollowUpSubmit.setText(getString(R.string.submit_certificate));
                actionType = Conts.FOLLOW_UP_COMFIRM_SIGN;
                break;
        }
    }

    private void fillData(DetailResModel detailResModel) {

        orderItemModel = detailResModel.getOrderItem();
        binding.llShowName.tvItemSelectContent.setText(orderItemModel.getCustomerName());
        binding.llShowType.tvItemSelectContent.setText(Conts.getOrderStatusMap().get(orderItemModel.getOrderStatus()));
        binding.llShowPhoneNumber.tvItemSelectContent.setText(orderItemModel.getOrderPhone());
        binding.llShowPhoneNumber.tvItemSelectRightIcon.setVisibility(View.VISIBLE);
        binding.llShowPhoneNumber.tvItemSelectRightIcon.setBackgroundResource(R.mipmap.icon_call);
        binding.llShowPhoneNumber.tvItemSelectRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(orderItemModel.getOrderPhone())) {
                    call(StringUtil.selectNumber(orderItemModel.getOrderPhone()));
                }
            }
        });
        switch (orderItemModel.getOrderAreaHotelType()) {
            case Conts.OPTION_DISTRICT_SELECT:
                binding.llShowSpecifyType.tvItemSelectContent.setText(specifyModels.get(0).getValue());
                binding.llShowSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
                break;

            case Conts.OPTION_HOTEL_SELECT:
                binding.llShowSpecifyType.tvItemSelectContent.setText(specifyModels.get(1).getValue());
                binding.llShowSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemHotel.toString()));
                break;
        }
        binding.llShowSpecifyItem.tvItemSelectContent.setText(orderItemModel.getOrderAreaHotelName());
        binding.llShowTableCount.tvItemSelectContent.setText(orderItemModel.getDestCount());
        binding.llShowBudget.tvItemSelectContent.setText(orderItemModel.getOrderMoney());

        if (!TextUtils.isEmpty(orderItemModel.getUseDate()) && !orderItemModel.getUseDate().equals("0")) {
            long time = Long.parseLong(orderItemModel.getUseDate()) * 1000;
            binding.llShowTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D));
        }
        binding.tvShowNote.setText(orderItemModel.getOrderDesc());

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

                switch (actionType - 1) {
                    case 0:
                        submitFollowUp();
                        break;
                    case 1:
                        submitFollowUp();
                        break;
                    case 2:
//                        jumpToOtherActivity(ContractInfoActivity.class);
                        Intent intent = new Intent(getActivity(), ContractInfoActivity.class);
                        intent.putExtra("order_id", orderId);
                        startActivityForResult(intent, Conts.TO_SUBMIT_CONTRACT_REVIEW);

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
//                binding.tvMessage.setText(getString(R.string.settlemented_message));
                binding.tvMessage.setText(content);

                break;
            case 5:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvModifyAndSubmit.setVisibility(View.VISIBLE);
                binding.tvModifyAndSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ContractInfoActivity.class);
                        intent.putExtra("order_id", orderId);
                        intent.putExtra("type", Conts.SOURCE_MODIFY);
                        startActivityForResult(intent, Conts.TO_MODIFY_DATA);
                    }
                });
                binding.tvMessage.setText(content);
                break;
            case 6:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
//                binding.tvMessage.setText(getString(R.string.fail_message));
                binding.tvMessage.setText(content);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Conts.TO_SUBMIT_CONTRACT_REVIEW) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }


}
