package sen.wedding.com.weddingsen.main.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.main.adapter.ListViewAdapter;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;

public class GuestInfoFragment extends Fragment {

    ListView listView;
    ListViewAdapter listViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, null);
        initViews(view);
        return view;

    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        listViewAdapter = new ListViewAdapter(getActivity());
        listView.setAdapter(listViewAdapter);

        listViewAdapter.notifyDataChanged(getFakeData());

    }

    private ArrayList<OrderInfoModel> getFakeData() {
        ArrayList<OrderInfoModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            OrderInfoModel orderInfoModel = new OrderInfoModel();
            orderInfoModel.setTime("2017-02-0" + i);
            orderInfoModel.setStatus("状态" + i);
            orderInfoModel.setContactPersonPhone("1580000000" + i);
            orderInfoModel.setFollowerFaction("It's a hotel" + i);
            fakeList.add(orderInfoModel);
        }
        return fakeList;
    }
}
