package sen.wedding.com.weddingsen.business.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.AndroidLifecycleUtils;
import sen.wedding.com.weddingsen.R;

/**
 * Created by donglua on 15/5/31.
 */
public class ContractReviewAdapter extends RecyclerView.Adapter<ContractReviewAdapter.PhotoViewHolder> {

    private List<String> photoPaths = new ArrayList<String>();
    private LayoutInflater inflater;

    private Context mContext;

    public ContractReviewAdapter(Context mContext, List<String> photoPaths) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_common_show, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(photoPaths.get(position))
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.ivPhoto);

    }


    @Override
    public int getItemCount() {
        int count = photoPaths.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_show);
        }
    }

}
