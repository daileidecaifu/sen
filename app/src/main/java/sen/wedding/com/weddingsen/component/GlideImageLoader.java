package sen.wedding.com.weddingsen.component;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

import sen.wedding.com.weddingsen.R;

/**
 * Created by lorin on 17/5/28.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
//        Glide.with(context).load(path).into(imageView);

    }
}
