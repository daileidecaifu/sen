package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.business.model.ReviewInfoModel;
import sen.wedding.com.weddingsen.databinding.MainInfoBinding;
import sen.wedding.com.weddingsen.databinding.ReviewInfoBinding;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.DateUtil;


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
//        Toast.makeText(currentContext,list.size()+"",Toast.LENGTH_LONG).show();
        notifyDataSetChanged();
    }

    public void notifyMoreDataChanged(ArrayList<OrderInfoModel> dataList) {

        list.addAll(dataList);
        Toast.makeText(currentContext,list.size()+"",Toast.LENGTH_LONG).show();
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

        if(position==0)
        {
            binding.vReviewPlaceholderHead.setVisibility(View.VISIBLE);
        }else
        {
            binding.vReviewPlaceholderHead.setVisibility(View.GONE);

        }

//        long time = Long.parseLong(model.getCreateTime()) * 1000;
//        binding.tvOrderTime.setText(DateUtil.convertDateToString(new Date(time),DateUtil.FORMAT_COMMON_Y_M_D));
        //testFake
        binding.tvOrderTime.setText(model.getCreateTime());
        binding.tvOrderStatus.setText(Conts.getorderStatusMap().get(model.getOrderStatus()));
        binding.tvContantPersonPhone.setText(model.getOrderPhone());
        binding.tvFollowerFaction.setText(model.getWatchUser());

        return binding.getRoot();

    }

    public ArrayList<OrderInfoModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}