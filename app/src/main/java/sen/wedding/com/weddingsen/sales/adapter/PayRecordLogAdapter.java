package sen.wedding.com.weddingsen.sales.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.component.album.ImageAlbumActivity;
import sen.wedding.com.weddingsen.databinding.PayLogInfoBinding;
import sen.wedding.com.weddingsen.sales.model.PayRecordLogModel;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.ScreenUtil;

/**
 * Created by lorin on 17/5/7.
 */

public class PayRecordLogAdapter extends BaseAdapter {

    List<PayRecordLogModel> list;
    private int screenWidth;
    private int imageWidth;

    private Context currentContext;

    public PayRecordLogAdapter(Context context) {
        this.currentContext = context;
        this.list = new ArrayList<>();
        this.screenWidth = ScreenUtil.getScreenWidthPixels(currentContext);
        this.imageWidth = (screenWidth - ScreenUtil.dip2px(currentContext, 25)) / 4;
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
        List<ImageView> imgList = new ArrayList<>();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.ivItem1.getLayoutParams();
        //获取当前控件的布局对象
        params.height = imageWidth;//设置当前控件布局的高度

        binding.ivItem1.setLayoutParams(params);
        binding.ivItem2.setLayoutParams(params);
        binding.ivItem3.setLayoutParams(params);
        binding.ivItem4.setLayoutParams(params);
        binding.ivItem5.setLayoutParams(params);
        binding.ivItem6.setLayoutParams(params);
        binding.ivItem7.setLayoutParams(params);
        binding.ivItem8.setLayoutParams(params);

        imgList.add(binding.ivItem1);
        imgList.add(binding.ivItem2);
        imgList.add(binding.ivItem3);
        imgList.add(binding.ivItem4);
        imgList.add(binding.ivItem5);
        imgList.add(binding.ivItem6);
        imgList.add(binding.ivItem7);
        imgList.add(binding.ivItem8);

        binding.llSelect1.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect2.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect3.tvItemSelectIcon.setVisibility(View.GONE);
        binding.llSelect4.tvItemSelectIcon.setVisibility(View.GONE);

        if (model.getOrderSignPic() == null || model.getOrderSignPic().size() == 0) {
            binding.llImageShow.setVisibility(View.GONE);
            binding.llImageShowSecond.setVisibility(View.GONE);
        } else if (model.getOrderSignPic().size() < 5) {
            binding.llImageShow.setVisibility(View.VISIBLE);
            binding.llImageShowSecond.setVisibility(View.GONE);
        } else {
            binding.llImageShow.setVisibility(View.VISIBLE);
            binding.llImageShowSecond.setVisibility(View.VISIBLE);
        }

        switch (Integer.parseInt(model.getSignType())) {
            case Conts.SECOND_FOLLOW_UP_FIRST_SALE:
                //首款
                binding.llRow1.setVisibility(View.VISIBLE);
                binding.llRow2.setVisibility(View.VISIBLE);
                binding.llRow3.setVisibility(View.VISIBLE);
                binding.llRow4.setVisibility(View.VISIBLE);

                binding.llImageShow.setVisibility(View.VISIBLE);

                binding.llSelect1.tvItemSelectTitle.setText(currentContext.getString(R.string.contract_money));
                binding.llSelect2.tvItemSelectTitle.setText(currentContext.getString(R.string.held_time));
                binding.llSelect3.tvItemSelectTitle.setText(currentContext.getString(R.string.first_sale_amount));
                binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.first_sale_time));

                binding.tvItemTitle.setText(currentContext.getString(R.string.first_pay_detail_tip));

                binding.llSelect3.tvItemSelectContent.setText(model.getOrderMoney());
                long time = Long.parseLong(model.getFirstOrderUsingTime()) * 1000;
                binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time), DateUtil.FORMAT_COMMON_Y_M_D));

                initImages(model.getOrderSignPic(), imgList);

                binding.llSelect1.tvItemSelectContent.setText(model.getFirstOrderMoney());
                if (!TextUtils.isEmpty(model.getFirstOrderUsingTime())) {
                    long time1 = Long.parseLong(model.getOtherItemWeikuanOldTime()) * 1000;
                    binding.llSelect2.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(time1), DateUtil.FORMAT_COMMON_Y_M_D));
                }
                break;

            case Conts.SECOND_FOLLOW_UP_MIDDLE:
                //中款
                randerCommonItem(binding, model, currentContext.getString(R.string.middle_pay_detail_tip), currentContext.getString(R.string.middle_money), imgList);
                break;

            case Conts.SECOND_FOLLOW_UP_ADDITIONAL:
                //附加款
                randerCommonItem(binding, model, currentContext.getString(R.string.addtional_pay_detail_tip), currentContext.getString(R.string.additional_money), imgList);
                break;

            case Conts.SECOND_FOLLOW_UP_REST:
                //尾款
                randerCommonItem(binding, model, currentContext.getString(R.string.tail_pay_detail_tip), currentContext.getString(R.string.tail_money), imgList);
                break;

            case Conts.SECOND_FOLLOW_UP_MODIFY:
                //尾款修改纪录
                binding.llRow1.setVisibility(View.GONE);
                binding.llRow2.setVisibility(View.GONE);
                binding.llRow3.setVisibility(View.VISIBLE);
                binding.llRow4.setVisibility(View.VISIBLE);
                binding.llImageShow.setVisibility(View.GONE);

                binding.tvItemTitle.setText(currentContext.getString(R.string.held_change_tip));

                binding.llSelect3.tvItemSelectTitle.setText(currentContext.getString(R.string.original_time));
                binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.apply_time));

                long timeO = Long.parseLong(model.getOtherItemWeikuanOldTime()) * 1000;
                binding.llSelect3.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeO), DateUtil.FORMAT_COMMON_Y_M_D));

                long timeN = Long.parseLong(model.getOtherItemWeikuanNewTime()) * 1000;
                binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeN), DateUtil.FORMAT_COMMON_Y_M_D));


                break;

        }

        return binding.getRoot();

    }

    private void randerCommonItem(PayLogInfoBinding binding, PayRecordLogModel model, String title, String moneyTitle, List<ImageView> imgList) {
        //中款
        binding.llRow1.setVisibility(View.GONE);
        binding.llRow2.setVisibility(View.GONE);
        binding.llRow3.setVisibility(View.VISIBLE);
        binding.llRow4.setVisibility(View.VISIBLE);
        binding.llImageShow.setVisibility(View.VISIBLE);

        binding.tvItemTitle.setText(title);

        binding.llSelect3.tvItemSelectTitle.setText(moneyTitle);
        binding.llSelect4.tvItemSelectTitle.setText(currentContext.getString(R.string.pay_time));
        initImages(model.getOrderSignPic(), imgList);

        binding.llSelect3.tvItemSelectContent.setText(model.getOrderMoney());
        long timeMiddle = Long.parseLong(model.getOrderTime()) * 1000;
        binding.llSelect4.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(timeMiddle), DateUtil.FORMAT_COMMON_Y_M_D));

    }

    public List<PayRecordLogModel> getList() {
        return list;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    private void initImages(final List<String> urls, List<ImageView> views) {


        if (urls != null || urls.size() > 0) {

            for (int i = 0; i < urls.size(); i++) {
                if (i >= 8) {
                    continue;
                }
                setImageClick(views.get(i), i, urls);
                views.get(i).setVisibility(View.VISIBLE);

                Glide.with(currentContext)
                        .load(urls.get(i))
                        .centerCrop()
                        .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.__picker_ic_broken_image_black_48dp)
                        .crossFade()
                        .into(views.get(i));
            }


        }
    }

    private void setImageClick(ImageView iv, final int position, final List<String> urls) {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentContext, ImageAlbumActivity.class);
                intent.putStringArrayListExtra("info_list", (ArrayList<String>) urls);
                intent.putExtra("index", position);
                currentContext.startActivity(intent);
            }
        });
    }
}