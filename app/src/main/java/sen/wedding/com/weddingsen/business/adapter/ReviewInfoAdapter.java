package sen.wedding.com.weddingsen.business.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.business.model.ReviewInfoModel;
import sen.wedding.com.weddingsen.databinding.ReviewInfoBinding;

/**
 * Created by zenglinggui on 16/8/18.
 */
public class ReviewInfoAdapter extends BaseAdapter{

    Context context;
    ArrayList<ReviewInfoModel> list;

    double ratio = 0;

    public ReviewInfoAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void notifyDataChanged(ArrayList<ReviewInfoModel> dataList) {

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
        ReviewInfoBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_review_info, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        ReviewInfoModel model = list.get(position);

        binding.tvReviewTime.setText(context.getString(R.string.follow_time_colon)+model.getFollowTime());
        binding.tvReviewStatus.setText(context.getString(R.string.status_colon)+model.getStatus());
        binding.tvReviewNote.setText(model.getNote());
        binding.tvReviewHint.setText(model.getHint());

        return binding.getRoot();
    }
}
