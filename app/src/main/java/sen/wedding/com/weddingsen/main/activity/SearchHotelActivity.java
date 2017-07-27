package sen.wedding.com.weddingsen.main.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.databinding.SearchHotelBinding;
import sen.wedding.com.weddingsen.main.adapter.SearchHistoryAdapter;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/7/26.
 */

public class SearchHotelActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    SearchHotelBinding binding;
    SearchHistoryAdapter searchHistoryAdapter;
    List<String> selectHistoryList = new ArrayList<>();

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

            }
        });

        searchHistoryAdapter.notifyDataChanged(getLocalData());

        binding.etSearch.setOnEditorActionListener(this);
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
                selectHistoryList.clear();

                List<String> localHistory = getLocalData();
                if (localHistory.size() == 10) {
                    localHistory.remove(9);
                }
                selectHistoryList.add(binding.etSearch.getText().toString());
                selectHistoryList.addAll(localHistory);
                BasePreference.saveHotelSearchHistory(GsonConverter.toJson(selectHistoryList));
                searchHistoryAdapter.notifyDataChanged(selectHistoryList);
                hideSoftKeyboard();
                break;

        }
        return false;
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

        return tempList;
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 获取软键盘的显示状态
        boolean isOpen = imm.isActive();

        // 如果软键盘已经显示，则隐藏，反之则显示
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void clearHistory()
    {
        BasePreference.saveHotelSearchHistory("");
        searchHistoryAdapter.notifyDataClear();

    }

}
