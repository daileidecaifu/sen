package sen.wedding.com.weddingsen.base.acitivity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.View;

import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.SenApplication;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.SenSystemBinding;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/8/10.
 */

public class SenSystemActivity extends BaseActivity{

    SenSystemBinding binding;
    private String currentDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sen_system);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setCommonRightText("=.=");
        getTitleBar().setTitle("Console");
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.llSelectDomain.tvItemSelectContent.getText().toString().equals(currentDomain))
                {
                    SenApplication.getInstance().logout();
                }
            }
        });

        initComponents();

        currentDomain = URLCollection.URL_DOMAIN;

    }

    private void initComponents()
    {
        //Domain选择
        binding.llSelectDomain.tvItemSelectTitle.setText("Domain");
        binding.llSelectDomain.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectDomain.tvItemSelectContent.setText(URLCollection.URL_DOMAIN);
        binding.llSelectDomain.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDoMain();
            }
        });

        //Crash
        binding.llSelectCrash.tvItemSelectTitle.setText("Crash");
        binding.llSelectCrash.tvItemSelectIcon.setVisibility(View.VISIBLE);
        binding.llSelectCrash.tvItemSelectContent.setText("View Crash Info");
        binding.llSelectCrash.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(DebugCrashListActivity.class);
            }
        });
    }

    private void showSelectDoMain() {

        String[] items = {"Production","Dev"};
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which)
                {
                    case 0:
                        URLCollection.URL_DOMAIN = URLCollection.URL_DOMAIN_APP;
                        binding.llSelectDomain.tvItemSelectContent.setText(URLCollection.URL_DOMAIN);
                        break;

                    case 1:
                        URLCollection.URL_DOMAIN = URLCollection.URL_DOMAIN_DEV;
                        binding.llSelectDomain.tvItemSelectContent.setText(URLCollection.URL_DOMAIN);
                        break;
                }

            }
        });
        builder.create().show();
    }
}
