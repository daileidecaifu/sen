package com.lzy.ninegrid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.ninegrid.album.ImageAlbumActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NineGridViewClickAdapter extends NineGridViewAdapter {

    private int statusHeight;

    public NineGridViewClickAdapter(Context context, List<String> imageInfo) {
        super(context, imageInfo);
        statusHeight = getStatusHeight(context);
    }

    @Override
    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<String> imageInfo) {
//        Toast.makeText(context,"Click"+index,Toast.LENGTH_LONG).show();
//        Log.e("NG_CLICK","XXX"+nineGridView.getId());
        Intent intent = new Intent(context, ImageAlbumActivity.class);
        intent.putStringArrayListExtra("info_list",(ArrayList<String>) imageInfo);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
