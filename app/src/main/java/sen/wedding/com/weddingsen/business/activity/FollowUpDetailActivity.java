package sen.wedding.com.weddingsen.business.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.model.DetailResModel;
import sen.wedding.com.weddingsen.business.model.OrderItemModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FollowUpDetailBinding;
import sen.wedding.com.weddingsen.databinding.GuestInfoDetailBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/25.
 */

public class FollowUpDetailActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    private FollowUpDetailBinding binding;

    private ReviewInfoAdapter adapter;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    private List<BaseTypeModel> specifyModels;
    private BaseTypeModel selectOrderTypeModel;
    private String verifyPhone;

    private ApiRequest getOrderDetailRequest;
    private DetailResModel detailResModel;

    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow_up_detail);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.guest_info_detail));
        getTitleBar().setCommonRightText(getString(R.string.follow_log));
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(LogInfoActivity.class);
            }
        });
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
        initComponents();
//        getFollowUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {

        orderId = getIntent().getIntExtra("order_id", -1);

        String orderTypeStr = getIntent().getStringExtra("select_type");
        selectOrderTypeModel = GsonConverter.fromJson(orderTypeStr, BaseTypeModel.class);
        verifyPhone = getIntent().getStringExtra("verify_phone");

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
                showToast("Type");
            }
        });
        //下次跟进
        binding.llFollowUpTime.tvItemSelectTitle.setText(getString(R.string.next_follow));
        binding.llFollowUpTime.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Follow Up");
            }
        });
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

    @Override
    public void onClick(View v) {

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
}