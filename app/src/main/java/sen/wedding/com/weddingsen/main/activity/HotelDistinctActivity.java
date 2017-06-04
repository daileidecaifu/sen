package sen.wedding.com.weddingsen.main.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.HotelDistinctBinding;
import sen.wedding.com.weddingsen.main.adapter.HotelDistinctAdapter;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.main.model.HotelDistinctModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;

/**
 * Created by lorin on 17/6/4.
 */

public class HotelDistinctActivity extends BaseActivity{

    HotelDistinctBinding binding;
    private HotelDistinctAdapter hotelDistinctAdapter;

    private ArrayList<HotelDistinctModel> selectedDistincts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_distinct);
        initConponents();
        getFakeData();
    }

    private void initConponents()
    {
        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.choose_check_distinct));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hotelDistinctAdapter = new HotelDistinctAdapter(this,selectedDistincts);
        binding.rvDictinctShow.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvDictinctShow.setAdapter(hotelDistinctAdapter);
    }

    private void getFakeData() {



        for (int i = 0; i < 15; i++) {
            HotelDistinctModel hotelDistinctModel = new HotelDistinctModel();
            hotelDistinctModel.setDistinctId(i+"");
            hotelDistinctModel.setTitle("title"+i);
            selectedDistincts.add(hotelDistinctModel);
        }

        hotelDistinctAdapter.notifyDataSetChanged();
    }}
