package sen.wedding.com.weddingsen.main.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.main.activity.HotelDetailActivity;
import sen.wedding.com.weddingsen.main.activity.HotelDistinctActivity;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;
import sen.wedding.com.weddingsen.main.adapter.HotelsAdapter;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView listView;
    TextView ivRecommend;
    HotelsAdapter hotelsAdapter;
    String[] items;

    public static HotelShowFragment newInstance() {

        Bundle args = new Bundle();
        HotelShowFragment fragment = new HotelShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotel_show, null);
        listView = (ListView) view.findViewById(R.id.lv_hotels);
        ivRecommend = (TextView) view.findViewById(R.id.tv_recommend);
        ivRecommend.setOnClickListener(this);
        initTitle(view);
        initListView();
        initData();
        return view;

    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        textViewTitle.setText(getString(R.string.sen));
        textViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHotelSort();
            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(BasePreference.getToken())) {

                    jumpToOtherActivity(LoginActivity.class);
                    getActivity().finish();
                } else {
                    ((HotelShowActivity) getActivity()).openMenu();
                }
            }
        });

    }

    private void initListView() {
        hotelsAdapter = new HotelsAdapter(getActivity());
        listView.setAdapter(hotelsAdapter);
        hotelsAdapter.notifyDataChanged(getFakeData());
        listView.setOnItemClickListener(this);
    }

    private void initData() {
        items = getResources().getStringArray(R.array.hotel_sort_type);

    }

    private void showHotelSort() {

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle("提示"); //设置标题
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                switch (which) {
                    case 0:
                        showToast("0");
                        break;

                    case 1:
                        showToast("1");
                        jumpToOtherActivity(HotelDistinctActivity.class);
                        break;
                }

            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private ArrayList<HotelShowModel> getFakeData() {

        ArrayList<HotelShowModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            HotelShowModel hotelShowModel = new HotelShowModel();
            hotelShowModel.setAddressDescription("1231234");
            hotelShowModel.setHotelName("afadsfaf");
            hotelShowModel.setPhoneNumber("14543333333");
            hotelShowModel.setTableCount("36桌");
            hotelShowModel.setUnitPrice("8800元/桌");
            fakeList.add(hotelShowModel);
        }
        return fakeList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        jumpToOtherActivity(HotelDetailActivity.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recommend:
                showToast("Z");
                break;
        }
    }
}
