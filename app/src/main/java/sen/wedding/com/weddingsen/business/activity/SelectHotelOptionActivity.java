package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
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
import sen.wedding.com.weddingsen.business.adapter.SelectHotelAdapter;
import sen.wedding.com.weddingsen.business.adapter.SelectOptionAdapter;
import sen.wedding.com.weddingsen.business.model.HotelModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.SelectOptionBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectHotelOptionActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener {

    SelectOptionBinding binding;
    private SelectHotelAdapter adapter;
    private ArrayList<HotelModel> list = new ArrayList<>();
    private ArrayList<HotelModel> resultList = new ArrayList<>();

    private int multiCount = 0;

    private ApiRequest searchRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_option);
        binding.setClickListener(this);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.select));
        getTitleBar().setCommonRightText(getString(R.string.confirm));
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResultData();
                if (resultList.size() == 0) {
                    showToast("No Selected");
                } else {

                    String resultStr = GsonConverter.toJson(resultList);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("result", resultStr);
                    // 设置结果，并进行传送
                    setResult(RESULT_OK, mIntent);
                    finish();
                }


            }
        });

        initListView();
        initComponents();
        searchInfo();
    }

    private void initComponents() {

        binding.llMultiTitle.setVisibility(View.VISIBLE);
        binding.tvMultiTotal.setText("3");
        binding.tvSelectCount.setText("0");
    }


    @Override
    public void onClick(View v) {

    }

    private void searchInfo() {

        showProgressDialog(false);
        searchRequest = new ApiRequest(URLCollection.URL_GET_HOTEL_OR_AREAS, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("hotel_area_type", Conts.OPTION_HOTEL_SELECT + "");

        searchRequest.setParams(param);
        getApiService().exec(searchRequest, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getAdapter().getItem(position) instanceof HotelModel) {

            HotelModel modelMulti = (HotelModel) parent.getAdapter().getItem(position);
            if (modelMulti.isSelect()) {
                list.get(position).setSelect(false);
                multiCount = multiCount - 1;

            } else {
                if (multiCount == 3) {
                    showToast("No More Than 3");
                } else {
                    multiCount = multiCount + 1;
                    list.get(position).setSelect(true);

                }
            }
            binding.tvSelectCount.setText("" + multiCount);

            adapter.notifyDataChanged(list);
        }

    }

    private void initListView() {


        adapter = new SelectHotelAdapter(this);
        binding.lvOptions.setAdapter(adapter);
        binding.lvOptions.setOnItemClickListener(this);
//        adapter.notifyDataChanged(list);
    }

//    private void getFakeData() {
//
//        for (int i = 0; i < 5; i++) {
//            HotelModel reviewInfoModel = new HotelModel();
//            reviewInfoModel.setAreaName("Option" + i);
//            list.add(reviewInfoModel);
//        }
//    }

    private void clearList() {
        for (HotelModel model : list) {
            model.setSelect(false);
        }
    }

    private void getResultData() {
        for (HotelModel model : list) {

            if (model.isSelect()) {
                resultList.add(model);
            }
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

        if (req == searchRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                list = GsonConverter.fromJson(resultModel.data.toString(),
                        new TypeToken<List<HotelModel>>() {
                        }.getType());
                adapter.notifyDataChanged(list);

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
