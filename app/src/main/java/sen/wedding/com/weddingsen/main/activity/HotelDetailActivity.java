package sen.wedding.com.weddingsen.main.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.youth.banner.listener.OnBannerListener;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.GlideImageLoader;
import sen.wedding.com.weddingsen.databinding.HotelDetailBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.BallroomAdapter;
import sen.wedding.com.weddingsen.main.adapter.BanquetMenuAdapter;
import sen.wedding.com.weddingsen.main.model.HotelDetailModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/28.
 */

public class HotelDetailActivity extends BaseActivity implements RequestHandler<ApiRequest, ApiResponse>, OnBannerListener, View.OnClickListener {

    HotelDetailBinding binding;
    //    private List<String> images;
    BallroomAdapter ballroomAdapter;
    BanquetMenuAdapter banquetMenuAdapter;
    private ApiRequest getHotelDetailRequest;
    private HotelDetailModel hotelDetailModel;
    private String hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_detail);
        binding.setClickListener(this);
        hotelId = getIntent().getStringExtra("hotel_id");
        initComponents();
//        initData();
        getHotelDetail();
    }

    private void initComponents() {
        binding.lvBallroom.setFocusable(false);
        binding.lvBanquetMenu.setFocusable(false);

//        String[] urls = getResources().getStringArray(R.array.image_urls);
//        images = Arrays.asList(urls);


        ballroomAdapter = new BallroomAdapter(this);
        binding.lvBallroom.setAdapter(ballroomAdapter);
        binding.lvBallroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HotelDetailActivity.this, BallroomAlbumActivity.class);
                intent.putExtra("ballroom", GsonConverter.toJson(hotelDetailModel.getRoomList().get(position)));
                startActivity(intent);
            }
        });

        banquetMenuAdapter = new BanquetMenuAdapter(this);
        binding.lvBanquetMenu.setAdapter(banquetMenuAdapter);

        //类型
        binding.llHotelType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llHotelType.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //地址
        binding.llHotelAddress.tvItemSelectTitle.setText(getString(R.string.address));
        binding.llHotelAddress.tvItemSelectIcon.setVisibility(View.INVISIBLE);

        //电话
        binding.llHotelPhoneNumber.tvItemSelectTitle.setText(getString(R.string.phone));
        binding.llHotelPhoneNumber.tvItemSelectIcon.setVisibility(View.INVISIBLE);

    }

    private void fillData(HotelDetailModel hotelDetailModel) {
        binding.banner.setImages(hotelDetailModel.getHotelImages()).setImageLoader(new GlideImageLoader()).start();
        binding.banner.setOnBannerListener(this);

        binding.tvHotelName.setText(hotelDetailModel.getHotelName());
        binding.tvExtraInfo.setText("(" + getString(R.string.rmb_symbol) + hotelDetailModel.getHotelLow() + "-" + hotelDetailModel.getHotelHigh() + "/"
                + getString(R.string.table)
                + getString(R.string.table_count_colon)
                + hotelDetailModel.getHotelMaxDesk()
                + ")");

        binding.llHotelType.tvItemSelectContent.setText(hotelDetailModel.getHotelType());
        binding.llHotelPhoneNumber.tvItemSelectContent.setText(hotelDetailModel.getHotelPhone());
        binding.llHotelAddress.tvItemSelectContent.setText(hotelDetailModel.getHotelAddress());

        if (hotelDetailModel.getRoomList() != null && hotelDetailModel.getRoomList().size() > 0) {
            ballroomAdapter.notifyDataChanged(hotelDetailModel.getRoomList());
        }

        if (hotelDetailModel.getMenuList() != null && hotelDetailModel.getMenuList().size() > 0) {
            banquetMenuAdapter.notifyDataChanged(hotelDetailModel.getMenuList());
        }

    }

    private void getHotelDetail() {
        getHotelDetailRequest = new ApiRequest(URLCollection.URL_HOTEL_DETAIL, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("hotel_id", hotelId);

        getHotelDetailRequest.setParams(param);
        getApiService().exec(getHotelDetailRequest, this);

    }

//    private void initData()
//    {
//        ballroomAdapter.notifyDataChanged(getFakeBallroomData());
//        banquetMenuAdapter.notifyDataChanged(getFakeBanquetMenuData());
//    }

//    private ArrayList<BallroomModel> getFakeBallroomData() {
//
//        ArrayList<BallroomModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            BallroomModel hotelShowModel = new BallroomModel();
//            hotelShowModel.setColumnCount("10");
//            hotelShowModel.setFloor("3F");
//            hotelShowModel.setHighLevel("5m");
//            hotelShowModel.setTableCount("20桌");
//            hotelShowModel.setRoomName("XXX厅");
//
//            fakeList.add(hotelShowModel);
//        }
//        return fakeList;
//    }
//
//    private ArrayList<BanquetMenuModel> getFakeBanquetMenuData() {
//
//        ArrayList<BanquetMenuModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            BanquetMenuModel banquetMenuModel = new BanquetMenuModel();
//           banquetMenuModel.setUnitPrice("$ 5000/桌");
//            banquetMenuModel.setMenuName("X套餐"+i);
//            fakeList.add(banquetMenuModel);
//        }
//        return fakeList;
//    }


    @Override
    public void OnBannerClick(int position) {
        showToast("Position:" + position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
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

        if (req == getHotelDetailRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();
                if (resultModel.data != null) {
                    hotelDetailModel = GsonConverter.decode(resultModel.data, HotelDetailModel.class);
                    fillData(hotelDetailModel);
                } else {
                }

            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        if (req == getHotelDetailRequest) {
        }
    }
}
