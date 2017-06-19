package sen.wedding.com.weddingsen.business.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.LogInfoAdapter;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.business.model.LogInfoModel;
import sen.wedding.com.weddingsen.component.LoadingView;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.LogInfoActivityBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/7.
 */

public class LogInfoActivity extends BaseActivity implements View.OnClickListener,
        RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener {

    LogInfoActivityBinding binding;
    LogInfoAdapter adapter;
    private ApiRequest getLogRequest;
    private int orderId;
    LoadingView loadingView;
    ArrayList<LogInfoModel> logList = new ArrayList<>();

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

        initData();
        initComponents();
        getLogs();
    }

    private void initData() {
        orderId = getIntent().getIntExtra("order_id", -1);
    }

    private void initComponents() {
        adapter = new LogInfoAdapter(this);
        binding.lvLog.setAdapter(adapter);
        binding.lvLog.setOnItemClickListener(this);

        loadingView = (LoadingView) findViewById(R.id.loading_view);
        loadingView.setLoadingViewClickListener(new LoadingView.OnLoadingViewClickListener() {
            @Override
            public void OnLoadingFailedClick(View view) {
                loadingView.showLoading();
                getLogs();
            }

            @Override
            public void OnLoadingEmptyClick(View view) {
                loadingView.showLoading();
                getLogs();
            }
        });
        loadingView.showLoading();
        getLogs();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }


    private void getLogs() {
        if (orderId != -1) {
            getLogRequest = new ApiRequest(URLCollection.URL_FOLLOW_LOG_LIST, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");

            getLogRequest.setParams(param);
            getApiService().exec(getLogRequest, this);
        } else {
            showToast("Order ID WRONG!");
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
        if (req == getLogRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();

                if (resultModel.data != null ) {
                    loadingView.dismiss();
                    logList = GsonConverter.fromJson(resultModel.data.toString(),
                            new TypeToken<List<LogInfoModel>>() {
                            }.getType());

                    if(logList != null && logList.size() > 0)
                    {
                        adapter.notifyDataChanged(logList);
                    }else {
                        loadingView.showLoadingEmpty();
                    }

                } else {
                    loadingView.showLoadingEmpty();
                }

            } else {
                showToast(resultModel.message);
                loadingView.showGuestInfoLoadingFailed();

            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        if (req == getLogRequest) {
            loadingView.showGuestInfoLoadingFailed();
        }
    }

//    private ArrayList<LogInfoModel> getFakeData() {
//
//        ArrayList<LogInfoModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            LogInfoModel logInfoModel = new LogInfoModel();
//            logInfoModel.setOrderFollowTime("1491532030");
//            logInfoModel.setOrderFollowDesc("desc...." + i);
//            logInfoModel.setOrderFollowCreateTime("1493736270");
//            fakeList.add(logInfoModel);
//        }
//
//
//        return fakeList;
//    }
}
