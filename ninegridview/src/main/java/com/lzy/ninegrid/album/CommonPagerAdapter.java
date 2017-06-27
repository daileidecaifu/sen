package com.lzy.ninegrid.album;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by donglua on 15/6/21.
 */
public class CommonPagerAdapter extends PagerAdapter {

    private List<String> paths = new ArrayList<>();
    private Context context;

    public CommonPagerAdapter(List<String> paths, Context context) {
        this.paths = paths;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_common_album, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_pager);

//        Glide.with(context)
//                .load(paths.get(position))
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(sen.wedding.com.weddingsen.R.drawable.__picker_ic_broken_image_black_48dp)
//                .crossFade()
//                .into(imageView);

        Glide.with(context).load(paths.get(position))
                .thumbnail(0.1f)
                .dontAnimate()
                .dontTransform()
                .override(800, 800)
                .into(imageView);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (context instanceof Activity) {
//                    if (!((Activity) context).isFinishing()) {
//                        ((Activity) context).onBackPressed();
//                    }
//                }
//            }
//        });

        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return paths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        Glide.clear((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
