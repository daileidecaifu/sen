package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import java.util.List;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPreview;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.main.model.HotelOptionModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

import static me.iwf.photopicker.PhotoPreview.EXTRA_PHOTOS;
import static me.iwf.photopicker.PhotoPreview.EXTRA_SHOW_DELETE;

/**
 * Created by donglua on 15/5/31.
 */
public class HotelDistinctAdapter extends RecyclerView.Adapter<HotelDistinctAdapter.PhotoViewHolder> {

    private ArrayList<HotelOptionModel> distinctModels = new ArrayList<HotelOptionModel>();
    private LayoutInflater inflater;
    private Handler handler;
    private Context mContext;
    private int selectPosition = 0;

    public HotelDistinctAdapter(Context mContext, ArrayList<HotelOptionModel> distinctModels, Handler handler) {
        this.distinctModels = distinctModels;
        this.mContext = mContext;
        this.handler = handler;
        inflater = LayoutInflater.from(mContext);

    }

    public void notifySelectChanged(int selectPosition) {

        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_distinct_show, parent, false);
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        HotelOptionModel hotelDistinctModel = distinctModels.get(position);

        if(selectPosition == position)
        {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            holder.tvTitle.setBackgroundResource(R.drawable.bg_item_round_blue);
        }else
        {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.text_common));
            holder.tvTitle.setBackgroundResource(R.drawable.bg_item_round);
        }

        holder.tvTitle.setText(hotelDistinctModel.getTitle());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
//                String[] urls = mContext.getResources().getStringArray(R.array.image_urls);
//                ArrayList<String> images = new ArrayList<String>();
//
//                for (String url:urls) {
//                    images.add(url);
//                }
//
//                Intent intent = new Intent(mContext, PhotoPagerActivity.class);
//                intent.putStringArrayListExtra(PhotoPreview.EXTRA_PHOTOS,images);
//                intent.putExtra(PhotoPreview.EXTRA_SHOW_DELETE,false);
//                mContext.startActivity(intent);
                notifySelectChanged(position);
                Message message = new Message();

                message.obj = GsonConverter.toJson(distinctModels.get(position));
                handler.sendMessage(message);
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
