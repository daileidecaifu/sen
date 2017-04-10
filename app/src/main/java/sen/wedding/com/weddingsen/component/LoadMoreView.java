package sen.wedding.com.weddingsen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;

/**
 * Created by sunyun on 16/9/12.
 */
public class LoadMoreView extends LinearLayout {

    private LinearLayout llContainer;
    private ProgressBar pbLoading;
    private ImageView ivFailed;
    private TextView tvLoadMore;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isScrollActive = true;
    private boolean canEffect = true;


    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.new_listview_footer_loading, this, true);
        setOrientation(VERTICAL);

        llContainer = (LinearLayout) findViewById(R.id.ll_container);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        ivFailed = (ImageView) findViewById(R.id.iv_failed);
        tvLoadMore = (TextView) findViewById(R.id.tv_load_more);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        onLoadMoreListener = listener;
    }

    public void attach(ListView lv) {
        lv.addFooterView(this);
        lv.setOnScrollListener(new ListViewOnScrollListener());
        lv.setOnItemSelectedListener(new ListViewOnItemSelectedListener());
    }

    public void dismissLoading() {
        canEffect = true;
        isScrollActive = false;
        llContainer.setVisibility(GONE);
        setOnClickListener(null);
    }

    public void showLoading() {
        canEffect = false;
        isScrollActive = true;
        llContainer.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        ivFailed.setVisibility(View.GONE);
        tvLoadMore.setText(getResources().getString(R.string.schedule_loading));

        setOnClickListener(null);
    }

    public void showFailed() {
        canEffect = false;
        isScrollActive = false;
        llContainer.setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        ivFailed.setVisibility(VISIBLE);
        tvLoadMore.setText(getResources().getString(R.string.loading_failed));
        setOnClickListener(onClickLoadMoreListener);

    }

    public void showNoMore() {

        canEffect = false;
        isScrollActive = false;
        llContainer.setVisibility(VISIBLE);
        pbLoading.setVisibility(GONE);
        ivFailed.setVisibility(GONE);
        tvLoadMore.setText(getResources().getString(R.string.no_more_data));
        setOnClickListener(null);
    }

    protected View.OnClickListener onClickLoadMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
            }
        }
    };

    private class ListViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
            if (listView.getLastVisiblePosition() + 1 >= listView.getCount() - 1) {// 如果滚动到最后一行
                if (isScrollActive && onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * 滚动到底部自动加载更多数据
     */
    private class ListViewOnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && listView.getLastVisiblePosition() >= listView.getCount() - 1) {// 如果滚动到最后一行
                if (isScrollActive && onLoadMoreListener != null && canEffect) {
                    showLoading();
                    listView.setSelection(listView.getBottom());
                    onLoadMoreListener.onLoadMore();

                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


}
