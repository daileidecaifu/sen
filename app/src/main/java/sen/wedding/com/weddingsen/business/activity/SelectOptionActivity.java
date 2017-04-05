package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.adapter.SelectOptionAdapter;
import sen.wedding.com.weddingsen.business.model.SelectOptionModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.SelectOptionBinding;
import sen.wedding.com.weddingsen.utils.Conts;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectOptionActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    SelectOptionBinding binding;
    private SelectOptionAdapter adapter;
    private ArrayList<SelectOptionModel> list = new ArrayList<>();
    private ArrayList<SelectOptionModel> resultList = new ArrayList<>();

    private int type;
    private int totalSelect;
    private int multiCount = 0;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_option);
        binding.setClickListener(this);

        gson = new Gson();

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.select));
        getTitleBar().setCommonRightText(getString(R.string.confirm));
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResultData();
                if(resultList.size()==0)
                {
                    showToast("No Selected");
                }else {

                    String resultStr = gson.toJson(resultList);

                    Intent mIntent = new Intent();
                    mIntent.putExtra("result", resultStr);
                    // 设置结果，并进行传送
                    setResult(RESULT_OK, mIntent);
                    finish();
                }


            }
        });

        initListView();
        initComponents();
    }

    private void getParams() {
        type = getIntent().getIntExtra("select_type", 0);

    }

    private void initComponents() {
        getParams();
        if (type == Conts.OPTION_SINGLE_SELECT) {
            binding.llMultiTitle.setVisibility(View.GONE);
            totalSelect = 1;//单选
        } else {
            binding.llMultiTitle.setVisibility(View.VISIBLE);
            totalSelect = 3;//多选最多为3
            binding.tvSelectCount.setText("0");
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getAdapter().getItem(position) instanceof SelectOptionModel) {


            switch (type) {
                case Conts.OPTION_SINGLE_SELECT:
                    clearList();
                    SelectOptionModel modelSingle = (SelectOptionModel) parent.getAdapter().getItem(position);
                    list.get(position).setSelect(true);
                    break;

                case Conts.OPTION_MULTI_SELECT:
                    SelectOptionModel modelMulti = (SelectOptionModel) parent.getAdapter().getItem(position);
                    if (modelMulti.isSelect()) {
                        list.get(position).setSelect(false);
                        multiCount = multiCount - 1;

                    } else {
                        if (multiCount == 3) {
                            showToast("No More Than 3");
                        } else {
                            multiCount = multiCount + 1;
                            list.get(position).setSelect(true);

                        }
                    }
                    binding.tvSelectCount.setText(""+multiCount);

                    break;
            }


            adapter.notifyDataChanged(list);
        }

    }

    private void initListView() {

        getFakeData();

        adapter = new SelectOptionAdapter(this);
        binding.lvOptions.setAdapter(adapter);
        binding.lvOptions.setOnItemClickListener(this);
        adapter.notifyDataChanged(list);
    }

    private void getFakeData() {

        for (int i = 0; i < 5; i++) {
            SelectOptionModel reviewInfoModel = new SelectOptionModel();
            reviewInfoModel.setContent("Option" + i);
            list.add(reviewInfoModel);
        }
    }

    private void clearList() {
        for (SelectOptionModel model : list) {
            model.setSelect(false);
        }
    }

    private void getResultData()
    {
        for (SelectOptionModel model : list) {

            if(model.isSelect())
            {
                resultList.add(model);
            }
        }
    }

}
