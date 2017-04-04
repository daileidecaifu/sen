package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.business.adapter.ReviewInfoAdapter;
import sen.wedding.com.weddingsen.business.adapter.SelectOptionAdapter;
import sen.wedding.com.weddingsen.business.model.SelectOptionModel;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.databinding.SelectOptionBinding;

/**
 * Created by lorin on 17/4/5.
 */

public class SelectOptionActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    SelectOptionBinding binding;
    private SelectOptionAdapter adapter;
    private ArrayList<SelectOptionModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_option);
        binding.setClickListener(this);

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
                finish();
            }
        });

        initListView();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getAdapter().getItem(position) instanceof SelectOptionModel) {
            SelectOptionModel model = (SelectOptionModel) parent.getAdapter().getItem(position);
            if (model.isSelect()) {
                list.get(position).setSelect(false);
            } else {
                list.get(position).setSelect(true);
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

}
