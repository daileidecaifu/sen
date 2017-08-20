package com.lzy.ninegrid.album;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.R;

import java.util.ArrayList;


/**
 * Created by lorin on 17/6/20.
 */

public class ImageAlbumActivity extends Activity {

    CommonPagerAdapter commonPagerAdapter;
    private int index = 0;
    private int totalCount;
    ArrayList<String> infoList = new ArrayList<String>();
    TextView tvTitle;
    TextView tvRight;
    LinearLayout llLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ViewPager vp = (ViewPager) findViewById(R.id.vp_photos);
        llLeft = (LinearLayout) findViewById(R.id.ll_left);
        tvTitle = (TextView) findViewById(R.id.tv_title_title);
        tvRight = (TextView) findViewById(R.id.tv_right);


        infoList = getIntent().getStringArrayListExtra("info_list");
        index = getIntent().getIntExtra("index", 0);
        if (infoList == null || infoList.size() <= 0) {
            finish();
        } else {

            totalCount = infoList.size();
            tvRight.setText((index+1) + "/" + totalCount);

            llLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tvTitle.setText(getString(R.string.image_detail));
            commonPagerAdapter = new CommonPagerAdapter(infoList, this);
            vp.setAdapter(commonPagerAdapter);
            vp.setCurrentItem(index);
            vp.setOffscreenPageLimit(5);

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tvRight.setText((position + 1) + "/" + totalCount);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }

    }

}
