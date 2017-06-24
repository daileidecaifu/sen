package sen.wedding.com.weddingsen.sales.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.activity.EditGuestInfoActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.FeedbackBinding;
import sen.wedding.com.weddingsen.databinding.ModifyRestTimeBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.DateUtil;

/**
 * Created by lorin on 17/6/19.
 */

public class ModifyRestTimeActivity extends BaseActivity implements  View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> , DatePickerDialog.OnDateSetListener{

    ModifyRestTimeBinding binding;

    private ApiRequest submitRequest;
    private int orderId;
    private String originalTime;
    private DatePickerDialog dpd;

    private String selectTimeContent;
    private long selectTime;
    //尾款修改状态
    private String signType = "4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_rest_time);
        binding.setClickListener(this);

        orderId = getIntent().getIntExtra("order_id",-1);
        originalTime = getIntent().getStringExtra("original_time");

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.apply_modify_rest_time));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.llOriginTime.tvItemSelectTitle.setText(getString(R.string.original_time));
        binding.llOriginTime.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llOriginTime.tvItemSelectContent.setText(originalTime);
        binding.llSignUpTime.tvItemSelectTitle.setText(getString(R.string.original_time));
        binding.llSignUpTime.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSignUpTime.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
    }

    private void showSelectDate() {
        if (dpd == null) {
            Calendar now = Calendar.getInstance();
            dpd = DatePickerDialog.newInstance(
                    ModifyRestTimeActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(getResources().getColor(R.color.theme_color));
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    private void submitModify() {
        showProgressDialog(false);
        if (orderId != -1) {
            submitRequest = new ApiRequest(URLCollection.URL_SECOND_SALE_SIGN, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_dajian_order_id", orderId + "");
            param.put("sign_using_time", selectTime + "");
            param.put("sign_type", signType);

            submitRequest.setParams(param);
            getApiService().exec(submitRequest, this);
        } else {
            closeProgressDialog();
            showToast("Order ID WRONG!");
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        selectTimeContent = year + "-" + DateUtil.formatValue(monthOfYear + 1) + "-" + dayOfMonth;
        //除以1000是为了符合php时间戳长度
        selectTime = DateUtil.convertStringToDate(selectTimeContent, DateUtil.FORMAT_COMMON_Y_M_D).getTime() / 1000;
        binding.llSignUpTime.tvItemSelectContent.setText(selectTimeContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit_modify:
                submitModify();
                break;
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

        if (req == submitRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.action_success));
                setResult(RESULT_OK);
                finish();
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        showToast(resp.getResultModel().message);
    }
}
