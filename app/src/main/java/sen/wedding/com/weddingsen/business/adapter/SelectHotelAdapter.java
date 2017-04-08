package sen.wedding.com.weddingsen.business.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.business.model.HotelModel;
import sen.wedding.com.weddingsen.databinding.ItemOptionBinding;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectHotelAdapter extends BaseAdapter {

    Context context;
    ArrayList<HotelModel> list;

    public SelectHotelAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<HotelModel> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemOptionBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_select_option, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        HotelModel model = list.get(position);

        binding.tvOptionContent.setText(model.getHotelName());
        if (model.isSelect()) {
            binding.tvIconSelect.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.icon_check));

        } else {
            binding.tvIconSelect.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        }

        return binding.getRoot();
    }
}
