//package sen.wedding.com.weddingsen.component;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//
///**
// * Created by chen on 15/4/24.
// */
//public class LoadingView extends FrameLayout implements View.OnClickListener {
//
//    public static final int TYPE_EMPTY_COST = 0;
//    public static final int TYPE_EMPTY_SCHEDULE = 1;
//
//    public static final int STATUS_IDLE = 0;
//    public static final int STATUS_LOADING = 1;
//    public static final int STATUS_EMPTY = 2;
//    public static final int STATUS_FAILED = 3;
//
//    private OnLoadingViewClickListener listener;
//    private OnLoadingDismissClickListener dismissClickListener;
//    private int currentStatus;
//
//    public interface OnLoadingViewClickListener {
//        public void OnLoadingFailedClick(View view);
//
//        public void OnLoadingEmptyClick(View view);
//    }
//
//    public interface OnLoadingDismissClickListener {
//        public void OnLoadingDismissClick(View view);
//    }
//
//    public int getCurrentStatus() {
//        return currentStatus;
//    }
//
//    public LoadingView(Context context) {
//        this(context, null);
//    }
//
//    public LoadingView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        currentStatus = STATUS_IDLE;
//        setBackgroundColor(getResources().getColor(R.color.common_background_color));
//        setVisibility(GONE);
//    }
//
//    public void setLoadingViewClickListener(OnLoadingViewClickListener listener) {
//        this.listener = listener;
//    }
//
//    public void setLoadingDismissClickListener(OnLoadingDismissClickListener dismissClickListener) {
//        this.dismissClickListener = dismissClickListener;
//    }
//
//    public void showLoading() {
//        currentStatus = STATUS_LOADING;
//        removeAllViews();
//        LayoutInflater.from(getContext()).inflate(R.layout.loading_refresh_layout, this, true);
//        setVisibility(View.VISIBLE);
//    }
//
//    public void showLoadingFailed() {
//        showLoadingFailed(1024, null);
//        setVisibility(VISIBLE);
//    }
//
//    public void showLoadingFailed(int resultCode, String title) {
//        currentStatus = STATUS_FAILED;
//        removeAllViews();
//        LayoutInflater.from(getContext()).inflate(R.layout.loading_failed_layout, this, true);
//
//        ImageView failedIcon = (ImageView) findViewById(R.id.failed_icon);
//
//        TextView failedBtn = (TextView) findViewById(R.id.failed_btn);
//        failedBtn.setOnClickListener(this);
//
//        TextView failedTitle = ((TextView) findViewById(R.id.failed_title));
//
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
//        if (!TextUtils.isEmpty(title)) {
//            failedTitle.setText(title);
//        }
//        setVisibility(View.VISIBLE);
//
//    }
//
//    public void showLoadingEmpty(int type) {
//        setVisibility(VISIBLE);
//        switch (type) {
//            case TYPE_EMPTY_COST:
//                showLoadingEmpty(R.drawable.add_cost, getContext().getString(R.string.widget_not_add_expenses), "");
//                break;
//            case TYPE_EMPTY_SCHEDULE:
//                showLoadingEmpty(R.drawable.add_schedule, getContext().getString(R.string.widget_not_create_any_schedule), getContext().getString(R.string.widget_click_to_add_or_create));
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void showLoadingEmpty(int iconDrawableRes, String title, String subTitle) {
//        setVisibility(VISIBLE);
//        Drawable drawable = null;
//        if (iconDrawableRes != -1) {
//            drawable = getResources().getDrawable(iconDrawableRes);
//        }
//        showLoadingEmpty(drawable, title, subTitle);
//    }
//
//    public void showLoadingEmpty(Drawable iconDrawable, String title, String subTitle) {
//        currentStatus = STATUS_EMPTY;
//        removeAllViews();
//        LayoutInflater.from(getContext()).inflate(R.layout.loading_empty_layout, this, true);
//        if (iconDrawable != null) {
//            ((ImageView) findViewById(R.id.empty_icon)).setImageDrawable(iconDrawable);
//        }
//        findViewById(R.id.empty_icon).setOnClickListener(this);
//        if (TextUtils.isEmpty(title)) {
//            ((TextView) findViewById(R.id.empty_title)).setVisibility(View.GONE);
//        } else {
//            ((TextView) findViewById(R.id.empty_title)).setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.empty_title)).setText(title);
//        }
//        if (TextUtils.isEmpty(subTitle)) {
//            ((TextView) findViewById(R.id.empty_subtitle)).setVisibility(View.GONE);
//        } else {
//            ((TextView) findViewById(R.id.empty_subtitle)).setVisibility(View.VISIBLE);
//            ((TextView) findViewById(R.id.empty_subtitle)).setText(subTitle);
//        }
//
//        setVisibility(View.VISIBLE);
//    }
//
//    public void dismiss() {
//        if (dismissClickListener != null) {
//            dismissClickListener.OnLoadingDismissClick(this);
//        }
//        currentStatus = STATUS_IDLE;
//        removeAllViews();
//        setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (listener != null) {
//            switch (currentStatus) {
//                case STATUS_EMPTY:
//                    listener.OnLoadingEmptyClick(v);
//                    break;
//                case STATUS_FAILED:
//                    listener.OnLoadingFailedClick(v);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return true;
//    }
//}
