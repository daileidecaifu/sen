package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.databinding.BallroomItemBinding;
import sen.wedding.com.weddingsen.databinding.HotelShowItemBinding;
import sen.wedding.com.weddingsen.main.model.BallroomModel;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;


public class BallroomAdapter extends BaseAdapter {

    List<BallroomModel> list;
    private Context currentContext;

    public BallroomAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<BallroomModel> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        BallroomItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_ballroom, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        BallroomModel model = list.get(position);

//        binding.ivImage.setBackgroundColor();
        if(model.getRoomImage()!=null&&model.getRoomImage().size()>0) {
            Glide.with(currentContext)
                    .load(model.getRoomImage().get(0))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.__picker_ic_broken_image_black_48dp)
                    .crossFade()
                    .into(binding.ivImage);
        }
        binding.tvBallroomName.setText(model.getRoomName());
        binding.tvHighLevel.setText(model.getRoomHigh());
        binding.tvColumnCount.setText(model.getRoomLz());
        binding.tvTableCount.setText(model.getRoomMaxDesk());

        return binding.getRoot();

    }

    public List<BallroomModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}