package sen.wedding.com.weddingsen.business.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.business.model.LogInfoModel;
import sen.wedding.com.weddingsen.databinding.LogInfoBinding;
import sen.wedding.com.weddingsen.databinding.MainInfoBinding;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.DateUtil;

/**
 * Created by lorin on 17/5/7.
 */

public class LogInfoAdapter extends BaseAdapter {

    ArrayList<LogInfoModel> list;

    private Context currentContext;

    public LogInfoAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<LogInfoModel> dataList) {

        list.clear();
        list.addAll(dataList);
//        Toast.makeText(currentContext,list.size()+"",Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

    public void notifyMoreDataChanged(ArrayList<LogInfoModel> dataList) {

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
        LogInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_log_info, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        LogInfoModel model = list.get(position);

        long timeNextTime = Long.parseLong(model.getOrderFollowTime()) * 1000;
        binding.tvNextTime.setText(DateUtil.convertDateToString(new Date(timeNextTime),DateUtil.FORMAT_COMMON_Y_M_D));
        binding.tvNoteContent.setText(model.getOrderFollowDesc());
        long timeFollowCreate = Long.parseLong(model.getOrderFollowCreateTime()) * 1000;
        binding.tvNoteTime.setText(DateUtil.convertDateToString(new Date(timeFollowCreate),DateUtil.FORMAT_COMMON_Y_M_D));


        return binding.getRoot();

    }

    public ArrayList<LogInfoModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}