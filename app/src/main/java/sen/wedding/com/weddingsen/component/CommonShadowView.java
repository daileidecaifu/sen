package sen.wedding.com.weddingsen.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import sen.wedding.com.weddingsen.R;

/**
 * Created by zenglinggui on 16/4/8.
 * 该View自带 marginBottom 10dip
 * 使用时如有间隔不对请自行处理
 */
public class CommonShadowView extends LinearLayout {


    public CommonShadowView(Context context) {
        this(context, null);
    }

    public CommonShadowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.common_shadow_view, this);
    }
}
