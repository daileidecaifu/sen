package sen.wedding.com.weddingsen.business.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.adapter.SelectHotelAdapter;
import sen.wedding.com.weddingsen.business.model.AreaModel;
import sen.wedding.com.weddingsen.business.model.HotelModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.EditGuestInfoBinding;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/25.
 */

public class EditGuestInfoActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditGuestInfoBinding binding;

    private ReviewInfoAdapter adapter;
    private List<BaseTypeModel> models;

    private List<BaseTypeModel> specifyModels;
    private BaseTypeModel selectTypeModel;

    StringBuffer sbType;
    StringBuffer sbItemHotel;
    StringBuffer sbItemDistrict;

    List<AreaModel> selectAreaList;
    List<HotelModel> selectHotelList;


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        models = Conts.getGuestInfoArray();
        specifyModels = Conts.getSpecifyTypeArray();
        selectTypeModel = specifyModels.get(0);

        sbType = new StringBuffer();
        sbType.append(StringUtil.createHtml(getString(R.string.specify_position), "#313133"));
        sbType.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemHotel = new StringBuffer();
        sbItemHotel.append(StringUtil.createHtml(getString(R.string.hotel), "#313133"));
        sbItemHotel.append(StringUtil.createHtml("*", "#fa4b4b"));

        sbItemDistrict = new StringBuffer();
        sbItemDistrict.append(StringUtil.createHtml(getString(R.string.district), "#313133"));
        sbItemDistrict.append(StringUtil.createHtml("*", "#fa4b4b"));

    }

    private void initComponents() {
        //姓名
        binding.llEditName.tvItemEditTitle.setText(getString(R.string.name));
        binding.llEditName.etItemEditInput.setHint(getString(R.string.edit_name_hint));

        //类型
        binding.llSelectType.tvItemSelectTitle.setText(getString(R.string.type));
        binding.llSelectType.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectType.tvItemSelectContent.setText(models.get(0).getValue());

        //手机号
        binding.llEditPhoneNumber.tvItemEditTitle.setText(getString(R.string.phone_number));
        binding.llEditPhoneNumber.etItemEditInput.setText(getString(R.string.phone_number_hint));
        binding.llEditPhoneNumber.etItemEditInput.setInputType(InputType.TYPE_CLASS_PHONE);
        binding.llEditPhoneNumber.etItemEditInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});

        //指定类型

        binding.llSelectSpecifyType.tvItemSelectTitle.setText(Html.fromHtml(sbType.toString()));
        binding.llSelectSpecifyType.tvItemSelectContent.setText(specifyModels.get(0).getValue());
        binding.llSelectSpecifyType.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDistrict(specifyModels);
            }
        });

        //指定item

        binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
//        binding.llSelectSpecifyItem.tvItemSelectContent.setText(hotelModels.get(0).getValue());
        binding.llSelectSpecifyItem.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (selectTypeModel.getType()) {
                    case Conts.OPTION_DISTRICT_SELECT:
                        Intent intent = new Intent(EditGuestInfoActivity.this, SelectAreaOptionActivity.class);
                        startActivityForResult(intent, Conts.SELECT_OPTION_REQUEST_CODE);
                        break;

                    case Conts.OPTION_HOTEL_SELECT:
                        Intent intent2 = new Intent(EditGuestInfoActivity.this, SelectHotelOptionActivity.class);
                        startActivityForResult(intent2, Conts.SELECT_OPTION_REQUEST_CODE);
                        break;
                }

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
        binding.llSelectTime.tvItemSelectTitle.setText(getString(R.string.time));
        binding.llSelectTime.tvItemSelectContent.setHint(getString(R.string.time_hint));
        binding.llSelectTime.tvItemSelectIcon.setVisibility(View.INVISIBLE);
        binding.llSelectTime.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
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
                binding.llSelectSpecifyType.tvItemSelectContent.setText(items[which]);
                BaseTypeModel tempTypeModel = specifyModels.get(which);
                //必须将选项和districts array一一按照顺序匹配
                if (tempTypeModel.getType() != selectTypeModel.getType()) {
                    binding.llSelectSpecifyItem.tvItemSelectContent.setText("");
                }
                selectTypeModel = tempTypeModel;
                switch (selectTypeModel.getType()) {
                    case Conts.OPTION_DISTRICT_SELECT:
                        binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemDistrict.toString()));
                        break;

                    case Conts.OPTION_HOTEL_SELECT:
                        binding.llSelectSpecifyItem.tvItemSelectTitle.setText(Html.fromHtml(sbItemHotel.toString()));
                        break;
                }

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

    private void showSelectDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                EditGuestInfoActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        binding.llSelectTime.tvItemSelectContent.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Conts.SELECT_OPTION_REQUEST_CODE:

                if (resultCode == RESULT_OK) {
                    String resultJsonStr = data.getStringExtra("result");
                    receiveDataAndProcess(resultJsonStr);
                }

                break;

            default:
                break;
        }
    }

    private void receiveDataAndProcess(String str) {
        switch (selectTypeModel.getType()) {
            case Conts.OPTION_DISTRICT_SELECT:
                showAreaContent(str);
                break;

            case Conts.OPTION_HOTEL_SELECT:
                showHotelContent(str);
                break;
        }
    }

    private void showAreaContent(String str) {
        selectAreaList = GsonConverter.fromJson(str,
                new TypeToken<List<AreaModel>>() {
                }.getType());

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < selectAreaList.size(); i++) {
            sb.append(selectAreaList.get(i).getAreaName());
            if (i != (selectAreaList.size() - 1)) {
                sb.append(",");
            }
        }

        binding.llSelectSpecifyItem.tvItemSelectContent.setText(sb.toString());
    }

    private void showHotelContent(String str) {
        selectHotelList = GsonConverter.fromJson(str,
                new TypeToken<List<HotelModel>>() {
                }.getType());

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < selectHotelList.size(); i++) {
            sb.append(selectHotelList.get(i).getHotelName());
            if (i != (selectHotelList.size() - 1)) {
                sb.append(",");
            }
        }

        binding.llSelectSpecifyItem.tvItemSelectContent.setText(sb.toString());
    }

}