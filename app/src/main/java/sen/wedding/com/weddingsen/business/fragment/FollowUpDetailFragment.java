package sen.wedding.com.weddingsen.business.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by lorin on 17/5/22.
 */

public class FollowUpDetailFragment extends BaseFragment implements View.OnClickListener,RequestHandler<ApiRequest, ApiResponse> {

    FollowUpFragmentBinding binding;
    private ReviewInfoAdapter adapter;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    private ApiRequest getOrderDetailRequest;
    private DetailResModel detailResModel;

    private String[] nextFollowUpItems;
    private List<BaseTypeModel> specifyModels;
    private String[] typeArray;
    private int actionType;
    private int orderId;
    private int orderStatus;

    public static FollowUpDetailFragment newInstance(int orderId,int orderStatus) {
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
        return binding.getRoot();

    }

    private void initData() {

        nextFollowUpItems = getResources().getStringArray(R.array.next_follow_up_item);

        typeArray = new String[Conts.getFollowActionStatusMap().size()];
        Conts.getFollowActionStatusMap().values().toArray(typeArray);

        specifyModels = Conts.getSpecifyTypeArray();

        sbType = new StringBuffer();
        sbType.append(StringUtil.createHtml(getString(R.string.specify_position), "#313133"));
        sbType.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemHotel = new StringBuffer();
        sbItemHotel.append(StringUtil.createHtml(getString(R.string.hotel), "#313133"));
        sbItemHotel.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemDistrict = new StringBuffer();
        sbItemDistrict.append(StringUtil.createHtml(getString(R.string.district), "#313133"));
        sbItemDistrict.append(StringUtil.createHtml("*", "#fa4b4b"));

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
        binding.llShowPhoneNumber.tvItemSelectRightText.setVisibility(View.VISIBLE);
        binding.llShowPhoneNumber.tvItemSelectRightText.setText(getString(R.string.contact_clients));
        binding.llShowPhoneNumber.tvItemSelectRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Call");
            }
        });
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

        initBottomView();
    }

    private void getFollowUp() {
        if (orderId != -1) {
            getOrderDetailRequest = new ApiRequest(URLCollection.URL_SHOW_GUEST_INFO_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("order_id", orderId + "");
            getOrderDetailRequest.setParams(param);
            getApiService().exec(getOrderDetailRequest,this);
        }else {
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

    private void showSelectAfterDays() {

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle(getString(R.string.select_next_follow_up)); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(nextFollowUpItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    private void switchShowAction(int type)
    {
        binding.llActionType.tvItemSelectContent.setText(typeArray[type]);
        switch (type)
        {
            case 0:
                binding.llFollowUpTime.getRoot().setVisibility(View.VISIBLE);
                binding.llFollowUpNote.setVisibility(View.VISIBLE);
                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = type;
                break;

            case 1:
                binding.llFollowUpTime.getRoot().setVisibility(View.GONE);
                binding.llFollowUpNote.setVisibility(View.VISIBLE);
                binding.tvFollowUpSubmit.setText(getString(R.string.confirm));
                actionType = type;
                break;

            case 2:
                binding.llFollowUpTime.getRoot().setVisibility(View.GONE);
                binding.llFollowUpNote.setVisibility(View.GONE);
                binding.tvFollowUpSubmit.setText(getString(R.string.submit_certificate));
                actionType = type;
                break;
        }
    }

    private void fillData(OrderItemModel orderItemModel) {
        binding.llShowName.tvItemSelectContent.setText(orderItemModel.getCustomerName());
        binding.llShowType.tvItemSelectContent.setText(Conts.getOrderStatusMap().get(orderItemModel.getOrderStatus()));
        binding.llShowPhoneNumber.tvItemSelectContent.setText(orderItemModel.getOrderPhone());

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

        long time = Long.parseLong(orderItemModel.getUseDate()) * 1000;
        binding.llShowTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D));

        binding.tvShowNote.setText(orderItemModel.getOrderDesc());
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
                fillData(detailResModel.getOrderItem());
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

        switch (v.getId())
        {
            case R.id.tv_follow_up_submit:

                switch (actionType)
                {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        jumpToOtherActivity(ContractInfoActivity.class);
                        break;
                }

                break;
        }

    }

    private void initBottomView()
    {
        switch (orderStatus){
            case 1:
                binding.llReviewProgress.setVisibility(View.GONE);
                binding.llToFollow.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvMessage.setText(getString(R.string.to_review_message));
                break;
            case 3:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvMessage.setText(getString(R.string.reviewed_message));
                break;
            case 4:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvMessage.setText(getString(R.string.settlemented_message));
                break;
            case 5:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvModifyAndSubmit.setVisibility(View.VISIBLE);
                binding.tvMessage.setText("一段审核日志");
                break;
            case 6:
                binding.llReviewProgress.setVisibility(View.VISIBLE);
                binding.llToFollow.setVisibility(View.GONE);
                binding.tvMessage.setText(getString(R.string.fail_message));
                break;
        }
    }

}
