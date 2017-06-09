package sen.wedding.com.weddingsen.business.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.VerifyGuestInfoBinding;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/25.
 */

public class VerifyGuestInfoActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    VerifyGuestInfoBinding binding;
    //    private String[] items;
    private List<BaseTypeModel> modelList;
    private BaseTypeModel selectOrderTypeModel;
    private ApiRequest verifyGuestRequest, verifyBuildRequest;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_guest_info);
        binding.setClickListener(this);
        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setCommonRightText(getString(R.string.next_step));
        getTitleBar().setTitle(getString(R.string.verify_guest_phone));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(this);

        modelList = Conts.getOrderTypeArray();
        selectOrderTypeModel = modelList.get(0);
        source = getIntent().getIntExtra("source", 0);
        initComponents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_type:
                showSelectType(modelList);
                break;
            case R.id.ll_verify_phone:
                hideSoftKeyBoard();
                break;

            case R.id.tv_verify_now:
                switch (source) {
                    case Conts.SOURCE_VERIFY_KEZI:
                        verifyGuest();
                        break;

                    case Conts.SOURCE_VERIFY_BUILD:

                        verifyBuild();
                        break;

                }
                break;

            case R.id.ll_left:
                finish();
                break;

        }
    }

    private void verifyGuest() {
        if (TextUtils.isEmpty(binding.llEditGuestPhone.etItemEditInput.getText().toString().trim())) {
            showToast(getString(R.string.phone_number_can_not_empty));
            return;
        }

        if (binding.llEditGuestPhone.etItemEditInput.getText().toString().trim().length() != 11) {
            showToast(getString(R.string.phone_number_wrong_format));
            return;
        }

        showProgressDialog(false);
        verifyGuestRequest = new ApiRequest(URLCollection.URL_VERIFY_GUEST_PHONE, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_type", selectOrderTypeModel.getType() + "");
        param.put("order_phone", binding.llEditGuestPhone.etItemEditInput.getText().toString().trim());

        verifyGuestRequest.setParams(param);
        getApiService().exec(verifyGuestRequest, this);
    }

    private void verifyBuild() {
        if (TextUtils.isEmpty(binding.llEditGuestPhone.etItemEditInput.getText().toString().trim())) {
            showToast(getString(R.string.phone_number_can_not_empty));
            return;
        }

        if (binding.llEditGuestPhone.etItemEditInput.getText().toString().trim().length() != 11) {
            showToast(getString(R.string.phone_number_wrong_format));
            return;
        }

        showProgressDialog(false);
        verifyBuildRequest = new ApiRequest(URLCollection.URL_VERIFY_BUILD_PHONE, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_type", selectOrderTypeModel.getType() + "");
        param.put("order_phone", binding.llEditGuestPhone.etItemEditInput.getText().toString().trim());

        verifyBuildRequest.setParams(param);
        getApiService().exec(verifyBuildRequest, this);
    }

    private void initComponents() {
        //类型
        binding.llSelectType.setClickListener(this);
        binding.llSelectType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llSelectType.tvItemSelectContent.setText(modelList.get(0).getValue());

        //手机号
        binding.llEditGuestPhone.tvItemEditTitle.setText(getString(R.string.phone_number));
        binding.llEditGuestPhone.etItemEditInput.setHint(getString(R.string.verify_phone_number_hint));
        binding.llEditGuestPhone.etItemEditInput.setInputType(InputType.TYPE_CLASS_PHONE);
        binding.llEditGuestPhone.etItemEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    private void showSelectType(final List<BaseTypeModel> models) {

        final String[] items = Conts.getShowContentArray(models);
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.select_order_type_hint)); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                selectOrderTypeModel = models.get(which);
                binding.llSelectType.tvItemSelectContent.setText(selectOrderTypeModel.getValue());
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

        if (req == verifyGuestRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.verify_success));

                Intent intent = new Intent(VerifyGuestInfoActivity.this, EditGuestInfoActivity.class);
                intent.putExtra("select_type", GsonConverter.toJson(selectOrderTypeModel));
                intent.putExtra("verify_phone", binding.llEditGuestPhone.etItemEditInput.getText().toString().trim());

                startActivity(intent);
                finish();
            } else {
                showToast(resultModel.message);
            }
        } else if (req == verifyBuildRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.verify_success));

                Intent intent = new Intent(VerifyGuestInfoActivity.this, EditBuildInfoActivity.class);
                intent.putExtra("select_type", GsonConverter.toJson(selectOrderTypeModel));
                intent.putExtra("verify_phone", binding.llEditGuestPhone.etItemEditInput.getText().toString().trim());

                startActivity(intent);
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
