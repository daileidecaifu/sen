package sen.wedding.com.weddingsen.main.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.GlideImageLoader;
import sen.wedding.com.weddingsen.databinding.HotelDetailBinding;
import sen.wedding.com.weddingsen.main.adapter.BallroomAdapter;
import sen.wedding.com.weddingsen.main.adapter.BanquetMenuAdapter;
import sen.wedding.com.weddingsen.main.model.BallroomModel;
import sen.wedding.com.weddingsen.main.model.BanquetMenuModel;

/**
 * Created by lorin on 17/5/28.
 */

public class HotelDetailActivity extends BaseActivity implements OnBannerListener {

    HotelDetailBinding binding;
    private List<String> images;
    BallroomAdapter ballroomAdapter;
    BanquetMenuAdapter banquetMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_detail);
        initComponents();
        initData();
    }

    private void initComponents()
    {
        String[] urls = getResources().getStringArray(R.array.image_urls);
        images = Arrays.asList(urls);
        binding.banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        binding.banner.setOnBannerListener(this);

        ballroomAdapter = new BallroomAdapter(this);
        binding.lvBallroom.setAdapter(ballroomAdapter);

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

    private void initData()
    {
        ballroomAdapter.notifyDataChanged(getFakeBallroomData());
        banquetMenuAdapter.notifyDataChanged(getFakeBanquetMenuData());
    }

    private ArrayList<BallroomModel> getFakeBallroomData() {

        ArrayList<BallroomModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            BallroomModel hotelShowModel = new BallroomModel();
            hotelShowModel.setColumnCount("10");
            hotelShowModel.setFloor("3F");
            hotelShowModel.setHighLevel("5m");
            hotelShowModel.setTableCount("20桌");
            hotelShowModel.setRoomName("XXX厅");

            fakeList.add(hotelShowModel);
        }
        return fakeList;
    }

    private ArrayList<BanquetMenuModel> getFakeBanquetMenuData() {

        ArrayList<BanquetMenuModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            BanquetMenuModel banquetMenuModel = new BanquetMenuModel();
           banquetMenuModel.setUnitPrice("$ 5000/桌");
            banquetMenuModel.setMenuName("X套餐"+i);
            fakeList.add(banquetMenuModel);
        }
        return fakeList;
    }


    @Override
    public void OnBannerClick(int position) {
        showToast("Position:"+position);
    }
}
