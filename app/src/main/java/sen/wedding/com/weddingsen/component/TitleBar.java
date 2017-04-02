package sen.wedding.com.weddingsen.component;

import android.view.View;
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

    private TextView textViewLeft;
    private TextView textViewRight;
    private TextView textViewTitle;

    public TitleBar(View titleView, Type type) {
        currentType = type;
        textViewLeft = (TextView) titleView.findViewById(R.id.tv_left);
        textViewRight = (TextView) titleView.findViewById(R.id.tv_right);
        textViewTitle = (TextView) titleView.findViewById(R.id.tv_title_title);
//        switch (type)
//        {
//            case COMMON:
//                break;
//
//            case CUSTOM_1:
//                break;
//        }

    }

    public void setLeftVisibility(int visibility)
    {
        textViewLeft.setVisibility(visibility);
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
        textViewLeft.setOnClickListener(listener);
    }

    public void setRightClickEvent(View.OnClickListener listener)
    {
        textViewRight.setOnClickListener(listener);
    }

}
