package sen.wedding.com.weddingsen.main.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

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
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.component.LoadingView;
import sen.wedding.com.weddingsen.databinding.SearchHotelBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.HotelsAdapter;
import sen.wedding.com.weddingsen.main.adapter.SearchHistoryAdapter;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/7/26.
 */

public class SearchHotelActivity extends BaseActivity implements View.OnClickListener,
        TextView.OnEditorActionListener,
        AdapterView.OnItemClickListener,
        RequestHandler<ApiRequest, ApiResponse> {

    SearchHotelBinding binding;
    SearchHistoryAdapter searchHistoryAdapter;
    List<String> selectHistoryList = new ArrayList<>();
    HotelsAdapter hotelsAdapter;
    ArrayList<HotelShowModel> hotelShowModels = new ArrayList<>();
    private ApiRequest getHotelListRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_hotel);
        binding.setClickListener(this);

        searchHistoryAdapter = new SearchHistoryAdapter(this);
        binding.lvHistory.setAdapter(searchHistoryAdapter);
        binding.lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showToast(position+"");
                showHotelView(selectHistoryList.get(position));
                binding.etSearch.setText(selectHistoryList.get(position));
                binding.etSearch.setSelection(selectHistoryList.get(position).length());
            }
        });

        searchHistoryAdapter.notifyDataChanged(getLocalData());
        binding.etSearch.setOnEditorActionListener(this);

        hotelsAdapter = new HotelsAdapter(this);
        binding.lvHotels.setAdapter(hotelsAdapter);
        binding.lvHotels.setOnItemClickListener(this);

        binding.loadingView.setLoadingViewClickListener(new LoadingView.OnLoadingViewClickListener() {
            @Override
            public void OnLoadingFailedClick(View view) {
                binding.loadingView.showLoading();
                getHotelList("");
            }

            @Override
            public void OnLoadingEmptyClick(View view) {
                binding.loadingView.showLoading();
                getHotelList("");
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0)
                {
                    binding.llHotel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.etSearch.setDeleteIcon(R.mipmap.icon_white_clear);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.tv_clear:
                clearHistory();
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        switch (actionId) {
            case EditorInfo.IME_ACTION_SEARCH:
                String searchText = binding.etSearch.getText().toString();
                if(searchText.equals(""))
                {
                    return false;
                }

                for(int i=0;i<selectHistoryList.size();i++)
                {
                    if(searchText.equals(selectHistoryList.get(i))){
                        selectHistoryList.remove(i);
                    }
                }

                List<String> tempHistory = new ArrayList<>();
                if (selectHistoryList.size() == 10) {
                    selectHistoryList.remove(9);
                }
                tempHistory.add(searchText);
                tempHistory.addAll(selectHistoryList);
                BasePreference.saveHotelSearchHistory(GsonConverter.toJson(tempHistory));
                searchHistoryAdapter.notifyDataChanged(getLocalData());
                hideSoftKeyboard();

                showHotelView(binding.etSearch.getText().toString());
                binding.etSearch.setClearIconVisible(true);
                break;

        }
        return false;
    }

    private void showHotelView(String keyword)
    {
        binding.llHotel.setVisibility(View.VISIBLE);
        binding.loadingView.showLoading();
        getHotelList(keyword);
    }

    private void getHotelList(String keyword) {
        getHotelListRequest = new ApiRequest(URLCollection.URL_GET_HOTEL_LIST, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("list_type", "3");
        param.put("search_input", keyword);


        getHotelListRequest.setParams(param);
        getApiService().exec(getHotelListRequest, this);

    }

    private List<String> getLocalData() {
        List<String> tempList;
        String historyArrayJson = BasePreference.getHotelSearchHistory();

        if (historyArrayJson.equals("")) {
            tempList = new ArrayList<>();
        } else {
            tempList = GsonConverter.fromJson(historyArrayJson,
                    new TypeToken<List<String>>() {
                    }.getType());
        }
        selectHistoryList.clear();
        selectHistoryList.addAll(tempList);
        return tempList;
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();

        // 如果软键盘已经显示，则隐藏，反之则显示
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void clearHistory() {
        selectHistoryList.clear();
        BasePreference.saveHotelSearchHistory("");
        searchHistoryAdapter.notifyDataClear();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, HotelDetailActivity.class);
        intent.putExtra("hotel_id", hotelShowModels.get(position).getHotelId());
//        jumpToOtherActivity(HotelDetailActivity.class);
        startActivity(intent);
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

        if (req == getHotelListRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();

                if (resultModel.data != null) {
                    binding.loadingView.dismiss();
                    hotelShowModels = GsonConverter.fromJson(resultModel.data.toString(),
                            new TypeToken<List<HotelShowModel>>() {
                            }.getType());

                    if (hotelShowModels != null && hotelShowModels.size() > 0) {
                        hotelsAdapter.notifyDataChanged(hotelShowModels);
                    } else {
                        binding.loadingView.showEmptyWithNoAction(getString(R.string.distinct_no_hotel));
                    }

                } else {
                    binding.loadingView.showEmptyWithNoAction(getString(R.string.distinct_no_hotel));
                }

            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        if (req == getHotelListRequest) {
            binding.loadingView.showGuestInfoLoadingFailed();
        }
    }
}
