package sen.wedding.com.weddingsen.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.main.activity.HotelDetailActivity;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;
import sen.wedding.com.weddingsen.main.adapter.HotelsAdapter;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    ListView listView;
    HotelsAdapter hotelsAdapter;

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
        initTitle(view);
        initListView();
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

            }
        });

        textViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HotelShowActivity) getActivity()).openMenu();
            }
        });

    }

    private void initListView()
    {
        hotelsAdapter = new HotelsAdapter(getActivity());
        listView.setAdapter(hotelsAdapter);
        hotelsAdapter.notifyDataChanged(getFakeData());
        listView.setOnItemClickListener(this);
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
}
