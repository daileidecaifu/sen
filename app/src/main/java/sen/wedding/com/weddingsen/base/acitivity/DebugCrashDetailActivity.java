package sen.wedding.com.weddingsen.base.acitivity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.component.TitleBar;

/**
 * 崩溃日志详情界面
 * Created by lorin on 16/2/14.
 */
public class DebugCrashDetailActivity extends BaseActivity{


    TextView crashInfoDetailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_info_detail);
        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.title_bar);
        initTitleBar(titleLayout, TitleBar.Type.COMMON);

        getTitleBar().setTitle("崩溃日志详情");

        crashInfoDetailTv = (TextView) findViewById(R.id.crash_info_detail_tv);
        crashInfoDetailTv.setText(getIntent().getStringExtra("crashInfo"));

    }



}
