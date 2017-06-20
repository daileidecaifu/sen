package sen.wedding.com.weddingsen.component;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sen.wedding.com.weddingsen.R;

/**
 * Created by lorin on 17/3/24.
 */

public class TitleBar {

    public enum Type {
        COMMON, CUSTOM_1
    }

    private Type currentType;

    private RelativeLayout mainLayout;
    private LinearLayout layoutLeft;
    private LinearLayout layoutRight;
    private TextView textViewTitle;
    private TextView textViewRight;

    public TitleBar(View titleView, Type type) {
        currentType = type;
        mainLayout = (RelativeLayout)  titleView.findViewById(R.id.title_bar);
        layoutLeft = (LinearLayout) titleView.findViewById(R.id.ll_left);
        layoutRight = (LinearLayout) titleView.findViewById(R.id.ll_right);
        textViewTitle = (TextView) titleView.findViewById(R.id.tv_title_title);
        textViewRight = (TextView) titleView.findViewById(R.id.tv_right);

    }

    public void setLeftVisibility(int visibility)
    {
        layoutLeft.setVisibility(visibility);
    }

    public void setRightVisibility(int visibility)
    {
        textViewRight.setVisibility(visibility);
    }

    public void setCommonRightText(String content) {
        textViewRight.setText(content);
    }

    public void setTitle(String content)
    {
        textViewTitle.setText(content);
    }

    public void setLeftClickEvent(View.OnClickListener listener)
    {
        layoutLeft.setOnClickListener(listener);
    }

    public void setRightClickEvent(View.OnClickListener listener)
    {
        layoutRight.setOnClickListener(listener);
    }

    public void setBackground(int color)
    {
        mainLayout.setBackgroundColor(color);
    }

}
