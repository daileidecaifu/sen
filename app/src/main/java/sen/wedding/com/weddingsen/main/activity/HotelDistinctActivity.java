package sen.wedding.com.weddingsen.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.model.LogInfoModel;
import sen.wedding.com.weddingsen.component.LoadingView;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.HotelDistinctBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.HotelDistinctAdapter;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.main.model.HotelOptionModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/6/4.
 */

public class HotelDistinctActivity extends BaseActivity implements RequestHandler<ApiRequest, ApiResponse> {

    HotelDistinctBinding binding;
    private HotelDistinctAdapter hotelDistinctAdapter;

    private ArrayList<HotelOptionModel> selectedDistincts = new ArrayList<>();
    private ApiRequest getDistinctRequest;
    private LoadingView loadingView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String jsonResult = msg.obj.toString();
            HotelOptionModel hotelDistinctModel = GsonConverter.fromJson(jsonResult, HotelOptionModel.class);
            Intent intent = new Intent();
            intent.putExtra("select_id", hotelDistinctModel.getId());
            intent.putExtra("select_title", hotelDistinctModel.getTitle());

            setResult(RESULT_OK, intent);
            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_distinct);
        initConponents();
//        getFakeData();
        loadingView.showLoading();
        getDistincts();
    }

    private void initConponents() {
        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.choose_check_distinct));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hotelDistinctAdapter = new HotelDistinctAdapter(this, selectedDistincts, handler);
        binding.rvDictinctShow.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvDictinctShow.setAdapter(hotelDistinctAdapter);

        loadingView = (LoadingView) findViewById(R.id.loading_view);


        loadingView.setLoadingViewClickListener(new LoadingView.OnLoadingViewClickListener() {
            @Override
            public void OnLoadingFailedClick(View view) {
                loadingView.showLoading();
                getDistincts();
            }

            @Override
            public void OnLoadingEmptyClick(View view) {
                loadingView.showLoading();
                getDistincts();
            }
        });
    }

    private void getFakeData() {

        for (int i = 0; i < 15; i++) {
            HotelOptionModel hotelDistinctModel = new HotelOptionModel();
            hotelDistinctModel.setId(i + "");
            hotelDistinctModel.setTitle("title" + i);
            selectedDistincts.add(hotelDistinctModel);
        }

        hotelDistinctAdapter.notifyDataSetChanged();
    }

    private void getDistincts() {
        getDistinctRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_DISTINCTS, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();

        getDistinctRequest.setParams(param);
        getApiService().exec(getDistinctRequest, this);

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

        if (req == getDistinctRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();

                if (resultModel.data != null) {
                    loadingView.dismiss();
                    Map<String, String> logMap = GsonConverter.fromJson(resultModel.data.toString(),
                            new TypeToken<Map<String, String>>() {
                            }.getType());
                    selectedDistincts.clear();
                    for (Map.Entry<String, String> entry : logMap.entrySet()) {
                        HotelOptionModel model = new HotelOptionModel();
                        model.setId(entry.getKey());
                        model.setTitle(entry.getValue());
                        selectedDistincts.add(model);
                    }
                    if (selectedDistincts != null && selectedDistincts.size() > 0) {
                        hotelDistinctAdapter.notifyDataSetChanged();
                    } else {
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
        if (req == getDistinctRequest) {
            loadingView.showGuestInfoLoadingFailed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}
