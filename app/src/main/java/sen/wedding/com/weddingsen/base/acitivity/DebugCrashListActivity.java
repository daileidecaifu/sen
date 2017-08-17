package sen.wedding.com.weddingsen.base.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.LinkedList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.utils.crash.CrashInfoModel;
import sen.wedding.com.weddingsen.utils.crash.CrashManager;


/**
 * 崩溃日志列表界面
 * Created by sunyun on 16/1/26.
 */
public class DebugCrashListActivity extends BaseActivity {

    private ListView crashInfoLv;

    private CrashListItemAdapter adapter;
    private LinkedList<CrashInfoModel> dataList = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info_list);

        initView();
        initListView();
    }


    private void initView() {
        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.title_bar);
        initTitleBar(titleLayout, TitleBar.Type.COMMON);

        getTitleBar().setTitle("崩溃列表");
        getTitleBar().setCommonRightText("清空历史");
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getTitleBar().setRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashManager.clearAllCrashInfos();
                dataList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        crashInfoLv = (ListView) findViewById(R.id.crash_info_list_view);
        adapter = new CrashListItemAdapter();
        crashInfoLv.setDivider(null);
        crashInfoLv.setAdapter(adapter);
        crashInfoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(DebugCrashListActivity.this, DebugCrashDetailActivity.class);
                intent.putExtra("crashInfo", dataList.get(position).getExp());
                startActivity(intent);

            }
        });
    }

    private void initListView() {
        List<CrashInfoModel> infos = CrashManager.getAllCrashInfos();

        dataList.addAll(infos);
        adapter.notifyDataSetChanged();
    }

    class CrashListItemAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup arg2) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = DebugCrashListActivity.this.getLayoutInflater().inflate(R.layout.item_crash_info, null);
                viewHolder = new ViewHolder();
                viewHolder.crashInfoTv = (TextView) convertView.findViewById(R.id.crash_info_item_tv);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if(dataList.get(position).getHasSent()==0)
            {
                viewHolder.crashInfoTv.setBackgroundColor(Color.parseColor("#178ef6"));
            }else if(dataList.get(position).getHasSent()==1)
            {
                viewHolder.crashInfoTv.setBackgroundColor(Color.parseColor("#FF4081"));
            }
            viewHolder.crashInfoTv.setText(dataList.get(position).getDate());

            return convertView;
        }

    }

    class ViewHolder {
        public TextView crashInfoTv;

    }
}
