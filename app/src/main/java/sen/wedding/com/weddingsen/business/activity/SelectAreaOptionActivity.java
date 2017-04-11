package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.model.AccountInfoModel;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.SelectOptionAdapter;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.business.model.AreaOrHotelResModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.SelectOptionBinding;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectAreaOptionActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener {

    SelectOptionBinding binding;
    private SelectOptionAdapter adapter;
    private ArrayList<AreaModel> list = new ArrayList<>();
    private ArrayList<AreaModel> resultList = new ArrayList<>();

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
        binding.llMultiTitle.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

    }

    private void searchInfo() {

        showProgressDialog(false);
        searchRequest = new ApiRequest(URLCollection.URL_GET_HOTEL_OR_AREAS, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("hotel_area_type", Conts.OPTION_DISTRICT_SELECT + "");

        searchRequest.setParams(param);
        getApiService().exec(searchRequest, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getAdapter().getItem(position) instanceof AreaModel) {

            clearList();
            list.get(position).setSelect(true);

            adapter.notifyDataChanged(list);
        }

    }

    private void initListView() {


        adapter = new SelectOptionAdapter(this);
        binding.lvOptions.setAdapter(adapter);
        binding.lvOptions.setOnItemClickListener(this);
//        adapter.notifyDataChanged(list);
    }

//    private void getFakeData() {
//
//        for (int i = 0; i < 5; i++) {
//            AreaModel reviewInfoModel = new AreaModel();
//            reviewInfoModel.setAreaName("Option" + i);
//            list.add(reviewInfoModel);
//        }
//    }

    private void clearList() {
        for (AreaModel model : list) {
            model.setSelect(false);
        }
    }

    private void getResultData() {
        for (AreaModel model : list) {

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

                AreaOrHotelResModel areaOrHotelResModel = GsonConverter.decode(resultModel.data, AreaOrHotelResModel.class);

                list = areaOrHotelResModel.getAreaList();
                if(list!=null&&list.size()>0) {
                    adapter.notifyDataChanged(list);
                }else
                {
                    showToast(getString(R.string.empty_data));
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
