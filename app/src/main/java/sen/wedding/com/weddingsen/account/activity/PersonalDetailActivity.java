package sen.wedding.com.weddingsen.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.model.BindInfoModel;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.PersonDetailBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.main.activity.MainActivity;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/31.
 */

public class PersonalDetailActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    PersonDetailBinding binding;
    private boolean isAlipay = true;

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
        swtichShowType();
    }

    private void initView() {
        //总金额
        binding.llTotalCommission.tvItemSelectTitle.setText(getString(R.string.total_commission));
        binding.llTotalCommission.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llTotalCommission.tvItemSelectContent.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        binding.llTotalCommission.tvItemSelectContent.setText("1000");

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

        binding.scvAysn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpened = binding.scvAysn.isOpened();
                showToast("" + isOpened);

            }
        });
    }

    private void swtichShowType()
    {
        if(isAlipay)
        {
            binding.llBankAccount.getRoot().setVisibility(View.GONE);
            binding.llOpenBank.getRoot().setVisibility(View.GONE);
            binding.llUserName.getRoot().setVisibility(View.GONE);

            binding.llAlipayAccount.getRoot().setVisibility(View.VISIBLE);
            binding.tvAccountTitle.setText(getString(R.string.receipt_account_alipay));

        }else
        {
            binding.llAlipayAccount.getRoot().setVisibility(View.GONE);

            binding.llBankAccount.getRoot().setVisibility(View.VISIBLE);
            binding.llOpenBank.getRoot().setVisibility(View.VISIBLE);
            binding.llUserName.getRoot().setVisibility(View.VISIBLE);
            binding.tvAccountTitle.setText(getString(R.string.receipt_account_bank));

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
                BindInfoModel bindInfoModel = new BindInfoModel();
                bindInfoModel.setAlipay("111");
                Intent intent = new Intent(this,PersonalInfoSetActivity.class);
                intent.putExtra("bind_info", GsonConverter.toJson(bindInfoModel));
                startActivityForResult(intent, Conts.TO_BIND_ACCOUNT_INFO);

                break;

            case R.id.ll_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {

    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }
}
