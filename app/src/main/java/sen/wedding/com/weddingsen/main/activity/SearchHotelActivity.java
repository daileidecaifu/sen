package sen.wedding.com.weddingsen.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.databinding.SearchHotelBinding;
import sen.wedding.com.weddingsen.main.adapter.SearchHistoryAdapter;

/**
 * Created by lorin on 17/7/26.
 */

public class SearchHotelActivity extends BaseActivity implements View.OnClickListener {

    SearchHotelBinding binding;
    SearchHistoryAdapter searchHistoryAdapter;

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

        List<String> data = new ArrayList<>();
        data.add("a");
        data.add("b");
        data.add("c");
        data.add("d");
        data.add("e");
        searchHistoryAdapter.notifyDataChanged(data);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_cancel:
                finish();
                break;
        }

    }
}
