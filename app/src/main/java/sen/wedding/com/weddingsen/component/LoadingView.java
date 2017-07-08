package sen.wedding.com.weddingsen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;


/**
 * Created by chen on 15/4/24.
 */
public class LoadingView extends FrameLayout implements View.OnClickListener {

    public static final int TYPE_EMPTY_COST = 0;
    public static final int TYPE_EMPTY_SCHEDULE = 1;

    public static final int STATUS_IDLE = 0;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_FAILED = 3;

    private OnLoadingViewClickListener listener;
    private OnLoadingDismissClickListener dismissClickListener;
    private int currentStatus;

    public interface OnLoadingViewClickListener {
        public void OnLoadingFailedClick(View view);

        public void OnLoadingEmptyClick(View view);
    }

    public interface OnLoadingDismissClickListener {
        public void OnLoadingDismissClick(View view);
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentStatus = STATUS_IDLE;
        setBackgroundColor(getResources().getColor(R.color.white));
        setVisibility(GONE);
    }

    public void setLoadingViewClickListener(OnLoadingViewClickListener listener) {
        this.listener = listener;
    }

    public void setLoadingDismissClickListener(OnLoadingDismissClickListener dismissClickListener) {
        this.dismissClickListener = dismissClickListener;
    }

    public void showLoading() {
        currentStatus = STATUS_LOADING;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.loading_kezi_refresh_layout, this, true);
        setVisibility(View.VISIBLE);
    }

//    public void showLoadingFailed() {
//        showLoadingFailed(1024, null);
//        setVisibility(VISIBLE);
//    }

    public void showGuestInfoLoadingFailed() {
        currentStatus = STATUS_FAILED;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.loading_kezi_unnormal_layout, this, true);

        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView click = ((TextView) findViewById(R.id.tv_action));

        click.setVisibility(GONE);
        title.setText(getContext().getString(R.string.request_error));

        title.setOnClickListener(this);


//        //断网
//        if (resultCode == 1025) {
//            failedIcon.setImageResource(R.drawable.network_off);
//            failedTitle.setText(getContext().getString(R.string.network_anomaly_prompt));
//            failedBtn.setVisibility(View.GONE);
//        } else {
//            //网络异常
//            failedIcon.setImageResource(R.drawable.network_abnormal);
//            failedTitle.setText(getContext().getString(R.string.abnormal));
//            failedBtn.setVisibility(View.VISIBLE);
//        }

        setVisibility(View.VISIBLE);

    }


    public void showGuestInfoLoadingEmpty() {
        setVisibility(VISIBLE);
        currentStatus = STATUS_EMPTY;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.loading_kezi_unnormal_layout, this, true);

        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView click = ((TextView) findViewById(R.id.tv_action));

        title.setText(getContext().getString(R.string.list_no_data_tip));
        click.setText(getContext().getString(R.string.add_guest_info));

        click.setOnClickListener(this);

        setVisibility(View.VISIBLE);
    }

    public void showLoadingEmpty() {
        setVisibility(VISIBLE);
        currentStatus = STATUS_EMPTY;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.loading_kezi_unnormal_layout, this, true);

        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView click = ((TextView) findViewById(R.id.tv_action));

        click.setVisibility(GONE);
        title.setText(getContext().getString(R.string.no_data));
        title.setOnClickListener(this);

        setVisibility(View.VISIBLE);
    }

    public void showEmptyWithNoAction(String str) {
        setVisibility(VISIBLE);
        currentStatus = STATUS_EMPTY;
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.loading_kezi_unnormal_layout, this, true);

        TextView title = (TextView) findViewById(R.id.tv_title);
        TextView click = ((TextView) findViewById(R.id.tv_action));

        click.setVisibility(GONE);
        title.setText(str);

        setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        if (dismissClickListener != null) {
            dismissClickListener.OnLoadingDismissClick(this);
        }
        currentStatus = STATUS_IDLE;
        removeAllViews();
        setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            switch (currentStatus) {
                case STATUS_EMPTY:
                    listener.OnLoadingEmptyClick(v);
                    break;
                case STATUS_FAILED:
                    listener.OnLoadingFailedClick(v);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
