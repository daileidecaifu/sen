package sen.wedding.com.weddingsen.account.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.activity.EditGuestInfoActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.VerifyGuestInfoBinding;
import sen.wedding.com.weddingsen.utils.Conts;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/25.
 */

public class VerifyGuestInfoActivity extends BaseActivity implements View.OnClickListener {

    VerifyGuestInfoBinding binding;
//    private String[] items;
    private List<BaseTypeModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_guest_info);
        binding.setClickListener(this);
        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setCommonRightText(getString(R.string.next_step));
        getTitleBar().setTitle(getString(R.string.set_personal_info));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modelList = Conts.getGuestInfoArray();
        binding.tvVerifyType.setText(modelList.get(0).getValue());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_type:
                showSelectType(modelList);
                break;

            case R.id.tv_verify_now:
                jumpToOtherActivity(EditGuestInfoActivity.class);
                break;

        }
    }

    private void showSelectType(List<BaseTypeModel> models) {

        final String[] items = Conts.getShowContentArray(modelList);
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showToast(modelList.get(which).getValue());
                binding.tvVerifyType.setText(items[which]);
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
}
