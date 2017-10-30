package sen.wedding.com.weddingsen.account.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.account.model.PersonDetailModel;
import sen.wedding.com.weddingsen.account.model.PersonInfoModel;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;
import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.PersonalInfoSetBinding;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.PreferenceUtils;
import sen.wedding.com.weddingsen.utils.ScreenUtil;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/22.
 */

public class PersonalInfoSetActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    private PersonalInfoSetBinding binding;
    private int fromTag;
    private ApiRequest bindAlipayRequest;
    private boolean isAlipay = true;
    private String[] accountType;
    private PersonInfoModel personInfoModel;
    int yourChoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info_set);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.set_personal_info));
        getTitleBar().setRightClickEvent(this);
        getTitleBar().setLeftClickEvent(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        fromTag = intent.getIntExtra("from", 1);
        String bindInfo = intent.getStringExtra("bind_info");

        if (!TextUtils.isEmpty(bindInfo)) {
            personInfoModel = GsonConverter.decode(bindInfo, PersonInfoModel.class);
        } else {
            personInfoModel = new PersonInfoModel();
        }

        switch (fromTag) {
            case Conts.FROM_LOGIN:
                getTitleBar().setCommonRightText(getString(R.string.next_step));
                binding.tvSkip.setVisibility(View.VISIBLE);
                getTitleBar().setLeftVisibility(View.GONE);
                break;

            case Conts.FROM_MAIN:

                getTitleBar().setCommonRightText(getString(R.string.save));
                binding.tvSkip.setVisibility(View.GONE);
                getTitleBar().setLeftVisibility(View.VISIBLE);

                break;
        }

//        accountType[0] = getString(R.string.alipay_account);
//        accountType[1] = getString(R.string.bank_card_account);

        accountType = getResources().getStringArray(R.array.bind_account_type);

        //收款类型
        binding.llSelectAccountType.tvItemSelectTitle.setText(getString(R.string.set_receipt_type));
        binding.llSelectAccountType.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectType();

            }
        });

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        lp.setMargins(ScreenUtil.dip2px(this, 15), 0, 0, 0);
        //支付宝账号
        StringBuffer sbAliTitle = new StringBuffer();
        sbAliTitle.append(StringUtil.createHtml(getString(R.string.alipay), "#313133"));
        sbAliTitle.append(StringUtil.createHtml("*", "#fa4b4b"));

        binding.llAlipayAccount.tvItemEditTitle.setText(Html.fromHtml(sbAliTitle.toString()));
        binding.llAlipayAccount.etItemEditInput.setHint(getString(R.string.alipay_hint));
        //银行账号
        binding.llBankAccount.tvItemEditTitle.setText(getString(R.string.open_band_account));
        binding.llBankAccount.etItemEditInput.setHint(getString(R.string.open_band_account_tip));
        binding.llBankAccount.vDivider.setLayoutParams(lp);
        //开户行
        binding.llOpenBank.tvItemEditTitle.setText(getString(R.string.open_band));
        binding.llOpenBank.etItemEditInput.setHint(getString(R.string.open_band_tip));
        binding.llOpenBank.vDivider.setLayoutParams(lp);

        //开户名
        binding.llUserName.tvItemEditTitle.setText(getString(R.string.open_account_name));
        binding.llUserName.etItemEditInput.setHint(getString(R.string.open_account_name_tip));
        initData();
        swtichShowType();

    }

    private void initData() {
        if (fromTag == Conts.FROM_LOGIN) {
            isAlipay = true;
            yourChoice = 0;
        } else {
            if (!TextUtils.isEmpty(personInfoModel.getBankAccount())) {
                isAlipay = false;
                yourChoice = 1;

            } else {
                isAlipay = true;
                yourChoice = 0;

            }
        }

        binding.llAlipayAccount.etItemEditInput.setText(personInfoModel.getAlipay());
        binding.llBankAccount.etItemEditInput.setText(personInfoModel.getBankAccount());
        binding.llOpenBank.etItemEditInput.setText(personInfoModel.getBankName());
        binding.llUserName.etItemEditInput.setText(personInfoModel.getBankUser());
    }

    private void swtichShowType() {

        if (isAlipay) {
            binding.llBankAccount.getRoot().setVisibility(View.GONE);
            binding.llOpenBank.getRoot().setVisibility(View.GONE);
            binding.llUserName.getRoot().setVisibility(View.GONE);

            binding.llAlipayAccount.getRoot().setVisibility(View.VISIBLE);
            binding.llSelectAccountType.tvItemSelectContent.setText(getString(R.string.account_alipay));
        } else {
            binding.llAlipayAccount.getRoot().setVisibility(View.GONE);

            binding.llBankAccount.getRoot().setVisibility(View.VISIBLE);
            binding.llOpenBank.getRoot().setVisibility(View.VISIBLE);
            binding.llUserName.getRoot().setVisibility(View.VISIBLE);
            binding.llSelectAccountType.tvItemSelectContent.setText(getString(R.string.account_bank));

        }
    }

    private void showSelectType() {

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle(getString(R.string.select_order_type_hint)); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setSingleChoiceItems(accountType, yourChoice,
                null);
        builder.setItems(accountType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                yourChoice = which;
                if (which == 0) {
                    isAlipay = true;
                } else {
                    isAlipay = false;
                }
                swtichShowType();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
//                jumpToOtherActivity(HotelShowActivity.class);
                finish();
                break;
            case R.id.ll_right:
                setAccount();
                break;
            case R.id.ll_left:
                finish();
                break;
        }
    }

    private void setAccount() {

        if (isAlipay && TextUtils.isEmpty(binding.llAlipayAccount.etItemEditInput.getText().toString().trim())) {
            showToast(getString(R.string.alipay_empty));
            return;
        }

        if (!isAlipay
                && TextUtils.isEmpty(binding.llOpenBank.etItemEditInput.getText().toString().trim())
                && TextUtils.isEmpty(binding.llUserName.etItemEditInput.getText().toString().trim())
                && TextUtils.isEmpty(binding.llBankAccount.etItemEditInput.getText().toString().trim())) {
            showToast(getString(R.string.bank_info_empty));
            return;
        }

        bindAlipayRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_BIND_ALIPAY, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        if (isAlipay) {
            param.put("alipay", binding.llAlipayAccount.etItemEditInput.getText().toString().trim());
            param.put("bank_name", "");
            param.put("bank_user", "");
            param.put("bank_account", "");
        } else {
            param.put("alipay", "");
            param.put("bank_name", binding.llOpenBank.etItemEditInput.getText().toString());
            param.put("bank_user", binding.llUserName.etItemEditInput.getText().toString());
            param.put("bank_account", binding.llBankAccount.etItemEditInput.getText().toString());
        }
        bindAlipayRequest.setParams(param);
        getApiService().exec(bindAlipayRequest, this);
    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        ResultModel resultModel = resp.getResultModel();

        if (req == bindAlipayRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.bind_success));
                BasePreference.saveAlipayAccount(req.getParams().get("alipay"));
                BasePreference.saveBankAccount(req.getParams().get("bank_account"));
                if (fromTag == Conts.FROM_LOGIN) {
//                    jumpToOtherActivity(MainActivity.class);
                    finish();
                } else if (fromTag == Conts.FROM_MAIN) {
                    setResult(RESULT_OK);
                    finish();
                }
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