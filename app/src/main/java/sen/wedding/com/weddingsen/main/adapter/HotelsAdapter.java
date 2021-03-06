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
import java.util.Date;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.databinding.HotelShowItemBinding;
import sen.wedding.com.weddingsen.databinding.MainInfoBinding;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.DateUtil;


public class HotelsAdapter extends BaseAdapter {

    ArrayList<HotelShowModel> list;
    private Context currentContext;

    public HotelsAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<HotelShowModel> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifyMoreDataChanged(ArrayList<HotelShowModel> dataList) {

        list.addAll(dataList);
        Toast.makeText(currentContext, list.size() + "", Toast.LENGTH_LONG).show();
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
        HotelShowItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_hotel_show, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        HotelShowModel model = list.get(position);

        Glide.with(currentContext)
                .load(model.getHotelImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .crossFade()
                .into(binding.ivImage);

        binding.tvHotelName.setText(model.getHotelName());
        binding.tvAddressContent.setText(model.getAreaShName() + " " + model.getHotelType());
        binding.tvPhoneNumber.setText(model.getHotelPhone());
        binding.tvUnitPrice.setText(currentContext.getString(R.string.rmb_symbol)
                + model.getHotelLow() + "-" + model.getHotelHigh() + "/" + currentContext.getString(R.string.table));
        binding.tvTableCount.setText(currentContext.getString(R.string.table_count_colon) + model.getHotelMaxDesk());

        return binding.getRoot();

    }

    public ArrayList<HotelShowModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}