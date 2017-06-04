package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPreview;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.main.model.HotelDistinctModel;

import static me.iwf.photopicker.PhotoPreview.EXTRA_PHOTOS;
import static me.iwf.photopicker.PhotoPreview.EXTRA_SHOW_DELETE;

/**
 * Created by donglua on 15/5/31.
 */
public class HotelDistinctAdapter extends RecyclerView.Adapter<HotelDistinctAdapter.PhotoViewHolder> {

    private ArrayList<HotelDistinctModel> distinctModels = new ArrayList<HotelDistinctModel>();
    private LayoutInflater inflater;

    private Context mContext;

    public HotelDistinctAdapter(Context mContext, ArrayList<HotelDistinctModel> distinctModels) {
        this.distinctModels = distinctModels;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_distinct_show, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        HotelDistinctModel hotelDistinctModel = distinctModels.get(position);
        holder.tvTitle.setText(hotelDistinctModel.getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                String[] urls = mContext.getResources().getStringArray(R.array.image_urls);
                ArrayList<String> images = new ArrayList<String>();

                for (String url:urls) {
                    images.add(url);
                }

                Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                intent.putStringArrayListExtra(PhotoPreview.EXTRA_PHOTOS,images);
                intent.putExtra(PhotoPreview.EXTRA_SHOW_DELETE,false);
                mContext.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        int count = distinctModels.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_show);
        }
    }

}
