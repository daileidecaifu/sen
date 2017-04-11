package sen.wedding.com.weddingsen.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dinuscxj.refresh.RecyclerRefreshLayout;

import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.activity.GuestInfoDetailActivity;
import sen.wedding.com.weddingsen.component.LoadMoreView;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.ListViewAdapter;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

public class GuestInfoFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse>,
        AdapterView.OnItemClickListener, RecyclerRefreshLayout.OnRefreshListener,
        LoadMoreView.OnLoadMoreListener {

    ListView listView;
    ListViewAdapter listViewAdapter;
    private ApiRequest getListRequest,loadMoreRequest;

    private GuestInfosResModel model;
    private int currentStatus;
    private int currentPage;

    /**
     * 刷新加载更多逻辑
     */
    RecyclerRefreshLayout recyclerRefreshLayout;
    private boolean mIsLoading;
    private LoadMoreView loadMoreView;

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

        View view = inflater.inflate(R.layout.fragment_order_list, null);
        initViews(view);
        return view;

    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        listViewAdapter = new ListViewAdapter(getActivity());
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(this);
//        listView.setOnScrollListener(this);

        loadMoreView = new LoadMoreView(getActivity());
        loadMoreView.setOnLoadMoreListener(this);
        loadMoreView.attach(listView);

        recyclerRefreshLayout = (RecyclerRefreshLayout) view.findViewById(R.id.refresh_layout);
        recyclerRefreshLayout.setOnRefreshListener(this);
        recyclerRefreshLayout.setBackgroundColor(getResources().getColor(R.color.common_background));
        getGuestInfoList();
    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//            // 判断是否滚动到底部
//            if (view.getLastVisiblePosition() == view.getCount() - 1) {
//                showToast("load more");
//            }
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//    }

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
            currentPage = 1;
            getListRequest = new ApiRequest(URLCollection.URL_GET_GUEST_INFO_LIST, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("order_status", currentStatus + "");
            param.put("order_page", currentPage + "");

            getListRequest.setParams(param);
            getApiService().exec(getListRequest, this);

    }

    private void loadMoreInfoList() {

            currentPage = currentPage + 1;
            loadMoreRequest = new ApiRequest(URLCollection.URL_GET_GUEST_INFO_LIST, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("order_status", currentStatus + "");
            param.put("order_page", currentPage + "");

            loadMoreRequest.setParams(param);
            getApiService().exec(loadMoreRequest, this);

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
            requestComplete();
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    listViewAdapter.notifyDataChanged(model.getOrderList());
                }
            } else {
                showToast(resultModel.message);
            }
        }else if(req == loadMoreRequest)
        {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    listViewAdapter.notifyMoreDataChanged(model.getOrderList());
                    loadMoreComplete();
                }else
                {
                    loadMoreView.showNoMore();
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
            intent.putExtra("order_id", listViewAdapter.getList().get(position).getId());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        mIsLoading = true;
        loadMoreView.setLoading(mIsLoading);
        getGuestInfoList();
    }

    protected void requestComplete() {
        mIsLoading = false;
        loadMoreView.setLoading(mIsLoading);

        if (recyclerRefreshLayout != null) {
            recyclerRefreshLayout.setRefreshing(false);
        }

    }

    protected void loadMoreComplete() {

        if (loadMoreView != null) {
            loadMoreView.dismissLoading();
        }

    }

    @Override
    public void onLoadMore() {
            showToast("load more");
            loadMoreInfoList();

    }
}
