package sen.wedding.com.weddingsen.business.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.business.model.ReviewInfoModel;
import sen.wedding.com.weddingsen.business.model.SelectOptionModel;
import sen.wedding.com.weddingsen.databinding.ItemOptionBinding;
import sen.wedding.com.weddingsen.databinding.ReviewInfoBinding;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectOptionAdapter extends BaseAdapter {

    Context context;
    ArrayList<SelectOptionModel> list;

    double ratio = 0;

    public SelectOptionAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<SelectOptionModel> dataList) {

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
        SelectOptionModel model = list.get(position);

        binding.tvOptionContent.setText(model.getContent());
        if (model.isSelect()) {
            binding.tvIconSelect.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.icon_check));

        } else {
            binding.tvIconSelect.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        }

        return binding.getRoot();
    }
}
