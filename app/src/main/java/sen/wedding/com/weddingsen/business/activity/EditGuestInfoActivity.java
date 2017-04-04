package sen.wedding.com.weddingsen.business.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.model.ReviewInfoModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.EditGuestInfoBinding;
import sen.wedding.com.weddingsen.utils.Conts;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/25.
 */

public class EditGuestInfoActivity extends BaseActivity implements View.OnClickListener {

    private EditGuestInfoBinding binding;

    private ReviewInfoAdapter adapter;
    //    private String items[];
    private List<BaseTypeModel> typeModels;
    private final int TYPE_DISCTRICT = 0;
    private final int TYPE_HOTEL = 1;
    private int currentSelectType;
    private List<BaseTypeModel> districtModels;
    private List<BaseTypeModel> hotelModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_guest_info);
        binding.setClickListener(this);
        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.edit_guest_info));
        getTitleBar().setRightVisibility(View.GONE);
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        initListView();
        initData();
        initComponents();
    }

    private void initData() {
        typeModels = Conts.getGuestInfoArray();
        districtModels = Conts.getDistrictInfoArray();
        hotelModels = Conts.getHotelInfoArray();

//        items = Conts.getShowContentArray(modelList);
    }

    private void initComponents() {
        //姓名
        binding.llEditName.tvItemEditTitle.setText(getString(R.string.name));
        binding.llEditName.etItemEditInput.setHint(getString(R.string.edit_name_hint));

        //类型
        binding.llSelectType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llSelectType.tvItemSelectContent.setText(typeModels.get(0).getValue());

        //手机号
        binding.llEditPhoneNumber.tvItemEditTitle.setText(getString(R.string.phone_number));
        binding.llEditPhoneNumber.etItemEditInput.setText(getString(R.string.phone_number_hint));
        binding.llEditPhoneNumber.etItemEditInput.setInputType(InputType.TYPE_CLASS_PHONE);
        binding.llEditPhoneNumber.etItemEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        //区域
//        binding.llSelectDistrict.setClickListener(this);
        binding.llSelectDistrict.tvItemSelectTitle.setText(getString(R.string.district_star));
        binding.llSelectDistrict.tvItemSelectContent.setText(districtModels.get(0).getValue());
        binding.llSelectDistrict.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDistrict(districtModels);
            }
        });

        //酒店
        binding.llSelectHotel.tvItemSelectTitle.setText(getString(R.string.hotel_star));
        binding.llSelectHotel.tvItemSelectContent.setText(hotelModels.get(0).getValue());
        binding.llSelectHotel.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToOtherActivity(SelectOptionActivity.class);
            }
        });
        //桌数
        binding.llEditTableCount.tvItemEditTitle.setText(getString(R.string.table_count));
        binding.llEditTableCount.etItemEditInput.setHint(getString(R.string.table_count_hint));
        binding.llEditTableCount.etItemEditInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        //预算
        binding.llEditBudget.tvItemEditTitle.setText(getString(R.string.budget));
        binding.llEditBudget.etItemEditInput.setHint(getString(R.string.budget_hint));
        binding.llEditBudget.etItemEditInput.setInputType(InputType.TYPE_CLASS_NUMBER);

        //时间
        binding.llEditTime.tvItemEditTitle.setText(getString(R.string.time));
        binding.llEditTime.etItemEditInput.setHint(getString(R.string.time_hint));

//        //跟踪者
//        binding.llEditFollower.tvItemEditTitle.setText(getString(R.string.follower));
//        binding.llEditFollower.etItemEditInput.setHint(getString(R.string.follower_hint));
    }

//    private void initListView() {
//        adapter = new ReviewInfoAdapter(this);
//        binding.lvReviewInfo.setAdapter(adapter);
//        adapter.notifyDataChanged(getFakeData());
//    }
//
//    private ArrayList<ReviewInfoModel> getFakeData() {
//        ArrayList<ReviewInfoModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 5; i++) {
//            ReviewInfoModel reviewInfoModel = new ReviewInfoModel();
//            reviewInfoModel.setFollowTime("2017-02-0" + i);
//            reviewInfoModel.setStatus("状态" + i);
//            reviewInfoModel.setNote("Test Note " + i + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            reviewInfoModel.setHint("It's a hint " + i + "!!!!!!!!!!!!!!");
//            fakeList.add(reviewInfoModel);
//        }
//        return fakeList;
//    }

    private void showSelectDistrict(final List<BaseTypeModel> districts) {

        final String[] items = Conts.getShowContentArray(districts);
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showToast(districts.get(which).getValue());
                binding.llSelectDistrict.tvItemSelectContent.setText(items[which]);
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}