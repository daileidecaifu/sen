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
import sen.wedding.com.weddingsen.databinding.SearchHistoryItemBinding;
import sen.wedding.com.weddingsen.main.model.BallroomModel;


public class SearchHistoryAdapter extends BaseAdapter {

    List<String> list;
    private Context currentContext;

    public SearchHistoryAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<String> dataList) {

        list.clear();
        list.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifyDataClear() {

        list.clear();
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
        SearchHistoryItemBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_search_history, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }

        binding.tvHistory.setText(list.get(position));

        return binding.getRoot();

    }

    public List<String> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}