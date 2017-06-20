package sen.wedding.com.weddingsen.main.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.iwf.photopicker.adapter.PhotoPagerAdapter;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.HotelAlbumBinding;
import sen.wedding.com.weddingsen.main.adapter.HotelPagerAdapter;
import sen.wedding.com.weddingsen.main.model.BallroomModel;
import sen.wedding.com.weddingsen.main.model.HotelDetailModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/6/20.
 */

public class BallroomAlbumActivity extends BaseActivity {

    HotelAlbumBinding binding;
    HotelPagerAdapter hotelPagerAdapter;
    private int currentItem = 0;
    private int totalCount;
    private BallroomModel ballroomModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_album);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.restaurant_detail));
        getTitleBar().setBackground(getResources().getColor(R.color.__picker_black_40));
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ballroomModel = GsonConverter.decode(getIntent().getStringExtra("ballroom"),BallroomModel.class);
        totalCount = ballroomModel.getRoomImage().size();
        getTitleBar().setCommonRightText(1 + "/" + totalCount);

        hotelPagerAdapter = new HotelPagerAdapter(ballroomModel.getRoomImage(), this);
        binding.vpPhotos.setAdapter(hotelPagerAdapter);
        binding.vpPhotos.setCurrentItem(currentItem);
        binding.vpPhotos.setOffscreenPageLimit(5);

        binding.vpPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getTitleBar().setCommonRightText((position + 1) + "/" + totalCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
