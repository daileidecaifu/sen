package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.business.model.ReviewInfoModel;
import sen.wedding.com.weddingsen.databinding.MainInfoBinding;
import sen.wedding.com.weddingsen.databinding.ReviewInfoBinding;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;


public class ListViewAdapter extends BaseAdapter {

    ArrayList<OrderInfoModel> list;

    private Context currentContext;

    public ListViewAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<OrderInfoModel> dataList) {

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
        MainInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_main_info, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        OrderInfoModel model = list.get(position);

        binding.tvOrderTime.setText(model.getTime());
        binding.tvOrderStatus.setText(model.getStatus());
        binding.tvContantPersonPhone.setText(model.getContactPersonPhone());
        binding.tvFollowerFaction.setText(model.getFollowerFaction());

        return binding.getRoot();

    }

    class ViewHolder {
        TextView share_app_text;

    }
}