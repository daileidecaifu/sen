package sen.wedding.com.weddingsen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;

import sen.wedding.com.weddingsen.R;

/**
 * Created by lorin on 17/6/24.
 */

public class NineGlideLoader implements NineGridView.ImageLoader{

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        System.out.print("Loading!!!!! Nine");
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .crossFade()
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
