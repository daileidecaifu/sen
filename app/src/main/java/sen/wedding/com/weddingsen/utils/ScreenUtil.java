package sen.wedding.com.weddingsen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

/**
 * Created by lorin on 17/5/12.
 */

public class ScreenUtil {
    private static final String TAG = ScreenUtil.class.getSimpleName();
    private static int screenWidthPixels;
    private static int screenHeightPixels;

    public ScreenUtil() {
    }

    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return (int) dipValue;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5F);
        }
    }

    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return (int) pxValue;
        } else {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5F);
        }
    }

    public static float px2sp(Context context, Float pxValue) {
        if (context == null) {
            return (float) pxValue.intValue();
        } else {
            float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
            return pxValue.floatValue() / scaledDensity;
        }
    }

    public static float sp2px(Context context, float spValue) {
        if (context == null) {
            return spValue;
        } else {
            Resources r = context.getResources();
            float size = TypedValue.applyDimension(2, spValue, r.getDisplayMetrics());
            return size;
        }
    }

    public static int getScreenWidthPixels(Context context) {
        if (context == null) {
            Log.e(TAG, "Can\'t get screen size while the activity is null!");
            return 0;
        } else if (screenWidthPixels > 0) {
            return screenWidthPixels;
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(dm);
            screenWidthPixels = dm.widthPixels;
            return screenWidthPixels;
        }
    }

    public static int getScreenHeightPixels(Context context) {
        if (context == null) {
            Log.e(TAG, "Can\'t get screen size while the activity is null!");
            return 0;
        } else if (screenHeightPixels > 0) {
            return screenHeightPixels;
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(dm);
            screenHeightPixels = dm.heightPixels;
            return screenHeightPixels;
        }
    }
}
