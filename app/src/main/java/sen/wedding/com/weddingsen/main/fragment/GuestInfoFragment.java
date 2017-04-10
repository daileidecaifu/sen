package sen.wedding.com.weddingsen.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.activity.GuestInfoDetailActivity;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.ListViewAdapter;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

public class GuestInfoFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener {

    ListView listView;
    ListViewAdapter listViewAdapter;
    private ApiRequest getListRequest;

    private GuestInfosResModel model;
    private int currentStatus;

    public static GuestInfoFragment newInstance(int orderStatus) {

        Bundle args = new Bundle();
        args.putInt("order_status", orderStatus);
        GuestInfoFragment fragment = new GuestInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentStatus = getArguments().getInt("order_status");
    }

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
        listView.setOnItemClickListener(this);

        getGuestInfoList();
//        listViewAdapter.notifyDataChanged(getFakeData());
    }

//    private ArrayList<OrderInfoModel> getFakeData() {
//        ArrayList<OrderInfoModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 8; i++) {
//            OrderInfoModel orderInfoModel = new OrderInfoModel();
//            orderInfoModel.setTime("2017-02-0" + i);
//            orderInfoModel.setStatus("状态" + i);
//            orderInfoModel.setContactPersonPhone("1580000000" + i);
//            orderInfoModel.setFollowerFaction("It's a hotel" + i);
//            fakeList.add(orderInfoModel);
//        }
//        return fakeList;
//    }

    private void getGuestInfoList() {

        getListRequest = new ApiRequest(URLCollection.URL_GET_GUEST_INFO_LIST, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_status", currentStatus + "");

        getListRequest.setParams(param);
        getApiService().exec(getListRequest, this);
    }

    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {
        ResultModel resultModel = resp.getResultModel();

        if (req == getListRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    listViewAdapter.notifyDataChanged(model.getOrderList());
                }
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        showToast(getString(R.string.request_error_tip));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getItem(position) instanceof OrderInfoModel) {
            Intent intent = new Intent(getActivity(), GuestInfoDetailActivity.class);
            intent.putExtra("order_id",model.getOrderList().get(position).getId());
            getActivity().startActivity(intent);
        }
    }
}
