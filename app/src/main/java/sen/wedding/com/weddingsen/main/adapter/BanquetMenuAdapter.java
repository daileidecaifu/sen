package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.databinding.BanquetMenuItemBinding;
import sen.wedding.com.weddingsen.databinding.HotelShowItemBinding;
import sen.wedding.com.weddingsen.main.model.BanquetMenuModel;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;


public class BanquetMenuAdapter extends BaseAdapter {

    List<BanquetMenuModel> list;
    private Context currentContext;

    public BanquetMenuAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<BanquetMenuModel> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifyMoreDataChanged(ArrayList<BanquetMenuModel> dataList) {

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
        BanquetMenuItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_banquet_menu, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        BanquetMenuModel model = list.get(position);

//        binding.ivImage.setBackgroundColor();
        binding.llMenuInfo.tvItemSelectTitle.setText(model.getMenuName());
        binding.llMenuInfo.tvItemSelectContent.setText(model.getMenuMoney());
        binding.llMenuInfo.tvItemSelectIcon.setVisibility(View.GONE);

        return binding.getRoot();

    }

    public List<BanquetMenuModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}