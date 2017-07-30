package sen.wedding.com.weddingsen.business.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.business.model.HotelModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.EditGuestInfoBinding;
import sen.wedding.com.weddingsen.base.Conts;
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

public class EditGuestInfoActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse>, DatePickerDialog.OnDateSetListener {

    private EditGuestInfoBinding binding;

    private ReviewInfoAdapter adapter;

    private List<BaseTypeModel> specifyModels;
    private BaseTypeModel selectTypeModel;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    List<AreaModel> selectAreaList = null;
    List<HotelModel> selectHotelList = null;

    private BaseTypeModel selectOrderTypeModel;
    private String verifyPhone;

    private long selectTime;
    private String selectTimeContent;
    int yourChoice = 0;

    private DatePickerDialog dpd;

    private ApiRequest createGuestInfoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_guest_info);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.edit_guest_info));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        initListView();
        initData();
        initComponents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {

        String orderTypeStr = getIntent().getStringExtra("select_type");
        selectOrderTypeModel = GsonConverter.fromJson(orderTypeStr, BaseTypeModel.class);
        verifyPhone = getIntent().getStringExtra("verify_phone");

        specifyModels = Conts.getSpecifyTypeArray();
        selectTypeModel = specifyModels.get(1);

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
        binding.llEditName.tvItemEditTitle.setText(getString(R.string.name));
        binding.llEditName.etItemEditInput.setHint(getString(R.string.edit_name_hint));
        binding.llEditName.etItemEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //类型
        binding.llSelectType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llSelectType.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectType.tvItemSelectContent.setText(selectOrderTypeModel.getValue());

        //手机号
        binding.llSelectPhoneNumber.tvItemSelectTitle.setText(getString(R.string.phone_number));
        binding.llSelectPhoneNumber.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectPhoneNumber.tvItemSelectContent.setText(verifyPhone);

        if(BasePreference.getUserType().equals(Conts.LOGIN_MODEL_ACCOUNT))
        {
            binding.llSelectSpecifyType.tvItemSelectTitle.setText(Html.fromHtml(sbType.toString()));
            binding.llSelectSpecifyType.tvItemSelectContent.setText(selectTypeModel.getValue());

            selectHotelList = new ArrayList<>();
            HotelModel hotelModel = new HotelModel();
            hotelModel.setHotelId(BasePreference.getHotelId());
            hotelModel.setHotelName(BasePreference.getHotelName());
            selectHotelList.add(hotelModel);
            binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemHotel.toString()));
            binding.llSelectSpecifyItem.tvItemSelectContent.setText(hotelModel.getHotelName());


        }else if(BasePreference.getUserType().equals(Conts.LOGIN_MODEL_PHONE))
        {
            //指定类型
            binding.llSelectSpecifyType.tvItemSelectTitle.setText(Html.fromHtml(sbType.toString()));
            binding.llSelectSpecifyType.tvItemSelectContent.setText(selectTypeModel.getValue());
            binding.llSelectSpecifyType.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSelectDistrict(specifyModels);
                }
            });

            //指定item

            binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
//        binding.llSelectSpecifyItem.tvItemSelectContent.setText(hotelModels.get(0).getValue());
            binding.llSelectSpecifyItem.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (selectTypeModel.getType()) {
                        case Conts.OPTION_DISTRICT_SELECT:
                            Intent intent = new Intent(EditGuestInfoActivity.this, SelectAreaOptionActivity.class);
                            startActivityForResult(intent, Conts.SELECT_OPTION_REQUEST_CODE);
                            break;

                        case Conts.OPTION_HOTEL_SELECT:
                            Intent intent2 = new Intent(EditGuestInfoActivity.this, SelectHotelOptionActivity.class);
                            startActivityForResult(intent2, Conts.SELECT_OPTION_REQUEST_CODE);
                            break;
                    }

                }
            });
        }

        //桌数
        binding.llEditTableCount.tvItemEditTitle.setText(getString(R.string.table_count));
        binding.llEditTableCount.etItemEditInput.setHint(getString(R.string.table_count_hint));
        binding.llEditTableCount.etItemEditInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        //预算
        binding.llEditBudget.tvItemEditTitle.setText(getString(R.string.budget));
        binding.llEditBudget.etItemEditInput.setHint(getString(R.string.budget_hint));

        //时间
        binding.llSelectTime.tvItemSelectTitle.setText(getString(R.string.time));
        binding.llSelectTime.tvItemSelectContent.setHint(getString(R.string.time_hint));
        binding.llSelectTime.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectTime.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
//        //跟踪者
//        binding.llEditFollower.tvItemEditTitle.setText(getString(R.string.follower));
//        binding.llEditFollower.etItemEditInput.setHint(getString(R.string.follower_hint));
    }

//    private void initListView() {
//        adapter = new ReviewInfoAdapter(this);
//        binding.lvReviewInfo.setAdapter(adapter);
//        adapter.notifyDataChanged(getFakeData());
//    }
//
//    private ArrayList<ReviewInfoModel> getFakeData() {
//        ArrayList<ReviewInfoModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            ReviewInfoModel reviewInfoModel = new ReviewInfoModel();
//            reviewInfoModel.setFollowTime("2017-02-0" + i);
//            reviewInfoModel.setStatus("状态" + i);
//            reviewInfoModel.setNote("Test Note " + i + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            reviewInfoModel.setHint("It's a hint " + i + "!!!!!!!!!!!!!!");
//            fakeList.add(reviewInfoModel);
//        }
//        return fakeList;
//    }

    private void showSelectDistrict(final List<BaseTypeModel> districts) {

        final String[] items = Conts.getShowContentArray(districts);
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setSingleChoiceItems(items, yourChoice,
                null);
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                yourChoice = which;
                binding.llSelectSpecifyType.tvItemSelectContent.setText(items[which]);
                BaseTypeModel tempTypeModel = specifyModels.get(which);
                //必须将选项和districts array一一按照顺序匹配
                if (tempTypeModel.getType() != selectTypeModel.getType()) {
                    binding.llSelectSpecifyItem.tvItemSelectContent.setText("");
                }
                selectTypeModel = tempTypeModel;
                switch (selectTypeModel.getType()) {
                    case Conts.OPTION_DISTRICT_SELECT:
                        binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
                        break;

                    case Conts.OPTION_HOTEL_SELECT:
                        binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemHotel.toString()));
                        break;
                }

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

    private void showSelectDate() {
        if (dpd == null) {
            Calendar now = Calendar.getInstance();
            dpd = DatePickerDialog.newInstance(
                    EditGuestInfoActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(getResources().getColor(R.color.theme_color));
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    private void createGusetInfo() {

        if (selectAreaList == null && selectHotelList == null) {
            showToast(getString(R.string.area_or_hotel_not_select));
            return;
        }

        createGuestInfoRequest = new ApiRequest(URLCollection.URL_CREATE_GUEST_INFO, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_type", selectOrderTypeModel.getType()+"");
        param.put("order_phone", verifyPhone);

        param.put("order_area_hotel_type", selectTypeModel.getType()+"");

        switch (selectTypeModel.getType()) {
            case Conts.OPTION_DISTRICT_SELECT:

                param.put("order_area_hotel_id", selectAreaList.get(0).getAreaId());
                break;

            case Conts.OPTION_HOTEL_SELECT:
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < selectHotelList.size(); i++) {
                    sb.append(selectHotelList.get(i).getHotelId());
                    if (i != (selectHotelList.size() - 1)) {
                        sb.append(",");
                    }
                }
                param.put("order_area_hotel_id", sb.toString());
                break;
        }

        param.put("customer_name", binding.llEditName.etItemEditInput.getText().toString());
        param.put("desk_count", binding.llEditTableCount.etItemEditInput.getText().toString());
        param.put("use_date", selectTime+"");
        param.put("order_money", binding.llEditBudget.etItemEditInput.getText().toString());
        param.put("order_desc", binding.etEditNote.getText().toString());

        createGuestInfoRequest.setParams(param);
        getApiService().exec(createGuestInfoRequest, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit_submit:
                createGusetInfo();
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        selectTimeContent = year + "-" + DateUtil.formatValue(monthOfYear + 1) + "-" + dayOfMonth;
        //除以1000是为了符合php时间戳长度
        selectTime = DateUtil.convertStringToDate(selectTimeContent, DateUtil.FORMAT_COMMON_Y_M_D).getTime() / 1000;
        binding.llSelectTime.tvItemSelectContent.setText(selectTimeContent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Conts.SELECT_OPTION_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    String resultJsonStr = data.getStringExtra("result");
                    receiveDataAndProcess(resultJsonStr);
                }

                break;

            default:
                break;
        }
    }

    private void receiveDataAndProcess(String str) {
        switch (selectTypeModel.getType()) {
            case Conts.OPTION_DISTRICT_SELECT:
                showAreaContent(str);
                break;

            case Conts.OPTION_HOTEL_SELECT:
                showHotelContent(str);
                break;
        }
    }

    private void showAreaContent(String str) {
        selectAreaList = GsonConverter.fromJson(str,
                new TypeToken<List<AreaModel>>() {
                }.getType());

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < selectAreaList.size(); i++) {
            sb.append(selectAreaList.get(i).getAreaName());
            if (i != (selectAreaList.size() - 1)) {
                sb.append(",");
            }
        }

        binding.llSelectSpecifyItem.tvItemSelectContent.setText(sb.toString());
    }

    private void showHotelContent(String str) {
        selectHotelList = GsonConverter.fromJson(str,
                new TypeToken<List<HotelModel>>() {
                }.getType());

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < selectHotelList.size(); i++) {
            sb.append(selectHotelList.get(i).getHotelName());
            if (i != (selectHotelList.size() - 1)) {
                sb.append(",");
            }
        }

        binding.llSelectSpecifyItem.tvItemSelectContent.setText(sb.toString());
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

        if (req == createGuestInfoRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.create_success));
                finish();
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