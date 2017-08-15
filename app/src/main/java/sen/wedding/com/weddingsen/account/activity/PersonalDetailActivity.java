package sen.wedding.com.weddingsen.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.model.PersonDetailModel;
import sen.wedding.com.weddingsen.account.model.PersonInfoModel;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.SwitchCheckView;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.PersonDetailBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/31.
 */

public class PersonalDetailActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    PersonDetailBinding binding;
    private ApiRequest getPersonDetail, setSynchronizeRequest;
    private PersonDetailModel personDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.person_info));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(this);
        initView();
//        swtichShowType();
        getPersonInfo();
    }

    private void initView() {
        //总金额
        binding.llTotalCommission.tvItemSelectTitle.setText(getString(R.string.total_commission));
        binding.llTotalCommission.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llTotalCommission.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        //已发放
        binding.llReleased.tvItemSelectTitle.setText(getString(R.string.commission_released));
        binding.llReleased.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llReleased.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        //未发放
        binding.llNotReleased.tvItemSelectTitle.setText(getString(R.string.commission_not_released));
        binding.llNotReleased.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llNotReleased.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        //支付宝账号
        binding.llAlipayAccount.tvItemSelectTitle.setText(getString(R.string.alipay_account));
        binding.llAlipayAccount.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llAlipayAccount.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        //银行账号
        binding.llBankAccount.tvItemSelectTitle.setText(getString(R.string.open_band_account));
        binding.llBankAccount.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llBankAccount.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        //开户行
        binding.llOpenBank.tvItemSelectTitle.setText(getString(R.string.open_band));
        binding.llOpenBank.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llOpenBank.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        //开户名
        binding.llUserName.tvItemSelectTitle.setText(getString(R.string.open_account_name));
        binding.llUserName.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llUserName.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        if (BasePreference.getUserType().equals(Conts.LOGIN_MODEL_ACCOUNT)) {
            binding.llSynchronize.setVisibility(View.VISIBLE);

            if (BasePreference.getAutoType().equals(Conts.AUTO_TYPE_SYNCHRONIZE_OPEN)) {
                binding.scvAysn.setOpened(true);
            } else {
                binding.scvAysn.setOpened(false);
            }

            binding.scvAysn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isOpened = binding.scvAysn.isOpened();
                    if (isOpened) {
                        setSynchronize(Conts.AUTO_TYPE_SYNCHRONIZE_OPEN);
                    } else {
                        setSynchronize(Conts.AUTO_TYPE_SYNCHRONIZE_CLOSE);
                    }

                }
            });
        }

    }

    private void setSynchronize(String autoType) {

        setSynchronizeRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_SYNCHRONIZE, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("auto_type", autoType);

        setSynchronizeRequest.setParams(param);
        getApiService().exec(setSynchronizeRequest, this);

    }

    private void getPersonInfo() {
        showProgressDialog(false);
        getPersonDetail = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_PERSON_DETAIL, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());


        getPersonDetail.setParams(param);
        getApiService().exec(getPersonDetail, this);

    }

    private void swtichShowType(PersonInfoModel personInfoModel) {
        if (personInfoModel == null) {
            return;
        }

        if (!TextUtils.isEmpty(personInfoModel.getBankAccount())) {
            binding.llAccount.setVisibility(View.VISIBLE);

            binding.llAlipayAccount.getRoot().setVisibility(View.GONE);

            binding.llBankAccount.getRoot().setVisibility(View.VISIBLE);
            binding.llOpenBank.getRoot().setVisibility(View.VISIBLE);
            binding.llUserName.getRoot().setVisibility(View.VISIBLE);
            binding.tvAccountTitle.setText(getString(R.string.receipt_account_bank));
            binding.llBankAccount.tvItemSelectContent.setText(personInfoModel.getBankAccount());
            binding.llOpenBank.tvItemSelectContent.setText(personInfoModel.getBankName());
            binding.llUserName.tvItemSelectContent.setText(personInfoModel.getBankUser());

        } else {
            binding.llAccount.setVisibility(View.VISIBLE);

            binding.llBankAccount.getRoot().setVisibility(View.GONE);
            binding.llOpenBank.getRoot().setVisibility(View.GONE);
            binding.llUserName.getRoot().setVisibility(View.GONE);

            binding.llAlipayAccount.getRoot().setVisibility(View.VISIBLE);
            binding.tvAccountTitle.setText(getString(R.string.receipt_account_alipay));
            binding.llAlipayAccount.tvItemSelectContent.setText(personInfoModel.getAlipay());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_to_set:
//                if(isAlipay)
//                {
//                    isAlipay = false;
//                }else {
//                    isAlipay = true;
//                }
//                swtichShowType();
                Intent intent = new Intent(this, PersonalInfoSetActivity.class);
                intent.putExtra("bind_info", GsonConverter.toJson(personDetailModel.getMyAccount()));
                intent.putExtra("from", Conts.FROM_MAIN);
                startActivityForResult(intent, Conts.TO_BIND_ACCOUNT_INFO);

                break;

            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void fillData(PersonDetailModel personDetailModel) {

        binding.llTotalCommission.tvItemSelectContent.setText(getString(R.string.yuan)+personDetailModel.getMyMoney().getAll());
        binding.llReleased.tvItemSelectContent.setText(getString(R.string.yuan)+personDetailModel.getMyMoney().getPay());
        binding.llNotReleased.tvItemSelectContent.setText(getString(R.string.yuan)+personDetailModel.getMyMoney().getUnpay());

        swtichShowType(personDetailModel.getMyAccount());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Conts.TO_BIND_ACCOUNT_INFO:

                if (resultCode == RESULT_OK) {
                    getPersonInfo();
                }

                break;

            default:
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

        if (req == getPersonDetail) {
            closeProgressDialog();
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                personDetailModel = GsonConverter.decode(resultModel.data, PersonDetailModel.class);
                fillData(personDetailModel);
            } else {
                showToast(resultModel.message);
            }
        } else if (req == setSynchronizeRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                String currentAutoType = ((ApiRequest) req).getParams().get("auto_type");
                BasePreference.saveAutoType(currentAutoType);
                showToast(getString(R.string.update_success));
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        showToast(resp.getResultModel().message);

    }
}
