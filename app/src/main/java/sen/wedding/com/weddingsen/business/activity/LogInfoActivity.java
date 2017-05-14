package sen.wedding.com.weddingsen.business.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.LogInfoAdapter;
import sen.wedding.com.weddingsen.business.model.LogInfoModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.LogInfoActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;

/**
 * Created by lorin on 17/5/7.
 */

public class LogInfoActivity extends BaseActivity implements View.OnClickListener,
        RequestHandler<ApiRequest, ApiResponse>,AdapterView.OnItemClickListener {

    LogInfoActivityBinding binding;
    LogInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_info);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.follow_log));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initComponents();
    }

    private void initComponents()
    {
        adapter = new LogInfoAdapter(this);
        binding.lvLog.setAdapter(adapter);
        binding.lvLog.setOnItemClickListener(this);

        adapter.notifyDataChanged(getFakeData());
//        binding.loadingView.showLoading();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }

    private ArrayList<LogInfoModel> getFakeData() {

        ArrayList<LogInfoModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            LogInfoModel logInfoModel = new LogInfoModel();
            logInfoModel.setOrderFollowTime("1491532030");
            logInfoModel.setOrderFollowDesc("desc...."+i);
            logInfoModel.setOrderFollowCreateTime("1493736270");
            fakeList.add(logInfoModel);
        }


        return fakeList;
    }
}
