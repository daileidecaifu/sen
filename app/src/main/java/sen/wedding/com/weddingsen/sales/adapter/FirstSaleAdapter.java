package sen.wedding.com.weddingsen.sales.adapter;

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
import sen.wedding.com.weddingsen.base.DBaseCallback;
import sen.wedding.com.weddingsen.databinding.MainSaleInfoBinding;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.DateUtil;


public class FirstSaleAdapter extends BaseAdapter {

    ArrayList<OrderInfoModel> list;
    private Context currentContext;
    //默认type为跟踪中
    private int infoType = 3;
    private DBaseCallback callback;

    public FirstSaleAdapter(Context context, int type, DBaseCallback callback) {
        this.currentContext = context;
        this.list = new ArrayList<>();
        this.infoType = type;
        this.callback = callback;
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
        MainSaleInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_main_info_sale, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        final OrderInfoModel model = list.get(position);

        if (position == 0) {
            binding.vReviewPlaceholderHead.setVisibility(View.VISIBLE);
        } else {
            binding.vReviewPlaceholderHead.setVisibility(View.GONE);

        }

        //testFake
//        binding.tvOrderTime.setText(model.getCreateTime());
        long time = Long.parseLong(model.getCreateTime()) * 1000;
        binding.tvOrderTime.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
        binding.tvOrderStatus.setText(Conts.getFollowStatusMap().get(model.getOrderStatus()));
        binding.tvContantPersonPhone.setText(model.getOrderPhone());
        binding.tvFollowerFaction.setText(model.getWatchUser());
        binding.tvSource.setText(model.getOrderFrom());
        switch (infoType) {
            case 1:
                binding.llTip.setVisibility(View.GONE);
                break;
            case 2:
                binding.llTip.setVisibility(View.GONE);
                break;
            case 3:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_build_waiting_for_settlement));
                binding.tvTip.setTextColor(currentContext.getResources().getColor(R.color.gray_2));
                break;
            case 4:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_build_settlemented));
                binding.tvTip.setTextColor(currentContext.getResources().getColor(R.color.gray_2));
                break;

            case 5:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.modify_and_resubmit));
                binding.llTip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.process(model.getId(),"");
                    }
                });
                binding.tvTip.setTextColor(currentContext.getResources().getColor(R.color.theme_color));
                break;

            case 6:
                binding.llTip.setVisibility(View.VISIBLE);
                binding.tvTip.setText(currentContext.getString(R.string.tip_build_cancel));
                binding.tvTip.setTextColor(currentContext.getResources().getColor(R.color.gray_2));

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