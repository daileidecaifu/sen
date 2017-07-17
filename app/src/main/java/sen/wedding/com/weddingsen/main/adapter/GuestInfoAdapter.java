package sen.wedding.com.weddingsen.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
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
import sen.wedding.com.weddingsen.utils.StringUtil;


public class GuestInfoAdapter extends BaseAdapter {

    ArrayList<OrderInfoModel> list;
    private Context currentContext;
    //默认type为跟踪中
    private int infoType = 3;

    public GuestInfoAdapter(Context context, int type) {
        this.currentContext = context;
        this.list = new ArrayList<>();
        this.infoType = type;
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
        MainInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_main_info, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        OrderInfoModel model = list.get(position);

        if (position == 0) {
            binding.vReviewPlaceholderHead.setVisibility(View.VISIBLE);
        } else {
            binding.vReviewPlaceholderHead.setVisibility(View.GONE);

        }

        //testFake
//        binding.tvOrderTime.setText(model.getCreateTime());
        long time = Long.parseLong(model.getCreateTime()) * 1000;
        binding.tvOrderTime.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
        binding.tvOrderStatus.setText(Conts.getOrderStatusMap().get(model.getOrderStatus()));
        binding.tvContantPersonPhone.setText(model.getOrderPhone());
        binding.tvFollowerFaction.setText(model.getWatchUser());

        switch (infoType) {
            case 1:
                binding.llTip.setVisibility(View.GONE);
                break;

            case 2:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_order_waiting_for_settlement));
                break;

            case 3:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_order_settlemented));
                break;

            case 4:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_order_cancel));
                break;
        }

        return binding.getRoot();

    }

    public ArrayList<OrderInfoModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}