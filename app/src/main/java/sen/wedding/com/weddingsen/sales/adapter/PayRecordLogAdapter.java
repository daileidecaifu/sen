package sen.wedding.com.weddingsen.sales.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.lzy.ninegrid.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.databinding.PayLogInfoBinding;
import sen.wedding.com.weddingsen.sales.model.PayRecordLogModel;
import sen.wedding.com.weddingsen.utils.DateUtil;

/**
 * Created by lorin on 17/5/7.
 */

public class PayRecordLogAdapter extends BaseAdapter {

    List<PayRecordLogModel> list;

    private Context currentContext;

    public PayRecordLogAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<PayRecordLogModel> dataList) {

        list.clear();
        list.addAll(dataList);

        notifyDataSetChanged();
    }

    public void notifyMoreDataChanged(ArrayList<PayRecordLogModel> dataList) {

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
        PayLogInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(currentContext), R.layout.item_pay_log_info, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        PayRecordLogModel model = list.get(position);


        binding.llSelect1.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect2.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect3.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect4.tvItemSelectIcon.setVisibility(View.GONE);

        switch (Integer.parseInt(model.getSignType())) {
            case Conts.SECOND_FOLLOW_UP_FIRST_SALE:
                //首款
                binding.llRow1.setVisibility(View.VISIBLE);
                binding.llRow2.setVisibility(View.VISIBLE);
                binding.llRow3.setVisibility(View.VISIBLE);
                binding.llRow4.setVisibility(View.VISIBLE);
                binding.ngImage.setVisibility(View.VISIBLE);

                binding.llSelect1.tvItemSelectTitle.setText(currentContext.getString(R.string.contract_money));
                binding.llSelect2.tvItemSelectTitle.setText(currentContext.getString(R.string.held_time));
                binding.llSelect3.tvItemSelectTitle.setText(currentContext.getString(R.string.first_sale_amount));
                binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.pay_time));

                binding.tvItemTitle.setText(currentContext.getString(R.string.first_pay_detail_tip));

                binding.llSelect3.tvItemSelectContent.setText(model.getOrderMoney());
                long time = Long.parseLong(model.getOrderTime()) * 1000;
                binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
                binding.ngImage.setAdapter(new NineGridViewClickAdapter(currentContext, model.getOrderSignPic()));
                binding.llSelect1.tvItemSelectContent.setText(model.getFirstOrderMoney());
                if (!TextUtils.isEmpty(model.getFirstOrderUsingTime())) {
                    long time1 = Long.parseLong(model.getFirstOrderUsingTime()) * 1000;
                    binding.llSelect2.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time1), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
                }
                break;

            case Conts.SECOND_FOLLOW_UP_MIDDLE:
                //中款
                randerCommonItem(binding, model, currentContext.getString(R.string.middle_pay_detail_tip));
                break;

            case Conts.SECOND_FOLLOW_UP_ADDITIONAL:
                //附加款
                randerCommonItem(binding, model, currentContext.getString(R.string.addtional_pay_detail_tip));
                break;

            case Conts.SECOND_FOLLOW_UP_REST:
                //尾款
                randerCommonItem(binding, model, currentContext.getString(R.string.addtional_pay_detail_tip));
                break;

            case Conts.SECOND_FOLLOW_UP_MODIFY:
                //尾款修改纪录
                binding.llRow1.setVisibility(View.GONE);
                binding.llRow2.setVisibility(View.GONE);
                binding.llRow3.setVisibility(View.VISIBLE);
                binding.llRow4.setVisibility(View.VISIBLE);
                binding.ngImage.setVisibility(View.GONE);

                binding.tvItemTitle.setText(currentContext.getString(R.string.tail_pay_modify_tip));

                binding.llSelect3.tvItemSelectTitle.setText(currentContext.getString(R.string.original_time));
                binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.apply_time));

                long timeO = Long.parseLong(model.getOtherItemWeikuanOldTime()) * 1000;
                binding.llSelect3.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeO), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));

                long timeN = Long.parseLong(model.getOtherItemWeikuanOldTime()) * 1000;
                binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeN), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));


                break;

        }

//        binding.tvNoteContent.setText(model.getOrderFollowDesc());
//        long timeFollowCreate = Long.parseLong(model.getOrderFollowCreateTime()) * 1000;
//        binding.tvNoteTime.setText(DateUtil.convertDateToString(new Date(timeFollowCreate), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));


        return binding.getRoot();

    }

    private void randerCommonItem(PayLogInfoBinding binding, PayRecordLogModel model, String title) {
        //中款
        binding.llRow1.setVisibility(View.GONE);
        binding.llRow2.setVisibility(View.GONE);
        binding.llRow3.setVisibility(View.VISIBLE);
        binding.llRow4.setVisibility(View.VISIBLE);
        binding.ngImage.setVisibility(View.VISIBLE);

        binding.tvItemTitle.setText(title);

        binding.llSelect3.tvItemSelectTitle.setText(currentContext.getString(R.string.middle_money));
        binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.pay_time));
        binding.ngImage.setAdapter(new NineGridViewClickAdapter(currentContext, model.getOrderSignPic()));

        binding.llSelect3.tvItemSelectContent.setText(model.getOrderMoney());
        long timeMiddle = Long.parseLong(model.getOrderTime()) * 1000;
        binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeMiddle), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));

    }

    public List<PayRecordLogModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }
}