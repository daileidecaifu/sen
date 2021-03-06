package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.databinding.BallroomItemBinding;
import sen.wedding.com.weddingsen.databinding.HotelTypeItemBinding;
import sen.wedding.com.weddingsen.main.model.BallroomModel;
import sen.wedding.com.weddingsen.main.model.HotelOptionModel;


public class HotelTypeAdapter extends BaseAdapter {

    List<HotelOptionModel> list;
    private Context currentContext;
    private int selectPosition = 0;

    public HotelTypeAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<HotelOptionModel> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifySelectChanged(int selectPosition) {

        this.selectPosition = selectPosition;
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
        HotelTypeItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_type_show, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        if(selectPosition == position)
        {
            binding.tvShow.setTextColor(currentContext.getResources().getColor(R.color.theme_color));
            binding.tvShow.setBackgroundResource(R.drawable.bg_item_round_blue);
        }else
        {
            binding.tvShow.setTextColor(currentContext.getResources().getColor(R.color.text_common));
            binding.tvShow.setBackgroundResource(R.drawable.bg_item_round);

        }
        binding.tvShow.setText(list.get(position).getTitle());

        return binding.getRoot();

    }

    public List<HotelOptionModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}