package sen.wedding.com.weddingsen.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.activity.GuestInfoDetailActivity;
import sen.wedding.com.weddingsen.component.LoadingView;
import sen.wedding.com.weddingsen.component.LoadMoreView;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.adapter.GuestInfoAdapter;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

public class GuestInfoFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse>,
        AdapterView.OnItemClickListener, RecyclerRefreshLayout.OnRefreshListener,
        LoadMoreView.OnLoadMoreListener {

    ListView listView;
    GuestInfoAdapter guestInfoAdapter;
    private ApiRequest getListRequest, loadMoreRequest;

    private GuestInfosResModel model;
    private int currentStatus;
    private int currentPage;
    LoadingView loadingView;

    /**
     * 刷新加载更多逻辑
     */
    RecyclerRefreshLayout recyclerRefreshLayout;
    boolean isLoadMore = false;//是否请求下一页数据
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
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainReceiver(EventIntent eventIntent) {
        if (eventIntent.getActionId() == Conts.EVENT_KEZI_CREATE_LIST_REFRESH) {
            switch (currentStatus) {
                case 1:
                    loadingView.showLoading();
                    getGuestInfoList();
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_list, null);
        initViews(view);
        return view;

    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        guestInfoAdapter = new GuestInfoAdapter(getActivity(), currentStatus);
        listView.setAdapter(guestInfoAdapter);
        listView.setOnItemClickListener(this);
//        listView.setOnScrollListener(this);

        loadMoreView = new LoadMoreView(getActivity());
        loadMoreView.setOnLoadMoreListener(this);
        loadMoreView.attach(listView);

        recyclerRefreshLayout = (RecyclerRefreshLayout) view.findViewById(R.id.refresh_layout);
        recyclerRefreshLayout.setOnRefreshListener(this);
        recyclerRefreshLayout.setBackgroundColor(getResources().getColor(R.color.common_background));

        loadingView = (LoadingView) view.findViewById(R.id.loading_view);
        loadingView.setLoadingViewClickListener(new LoadingView.OnLoadingViewClickListener() {
            @Override
            public void OnLoadingFailedClick(View view) {
                loadingView.showLoading();
                getGuestInfoList();
            }

            @Override
            public void OnLoadingEmptyClick(View view) {
//                Intent intent = new Intent(getActivity(), VerifyGuestInfoActivity.class);
//                getActivity().startActivity(intent);
                loadingView.showLoading();
                getGuestInfoList();
            }
        });

        loadingView.showLoading();
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

    private GuestInfosResModel getFakeData() {

        GuestInfosResModel guestInfosResModel = new GuestInfosResModel();
        guestInfosResModel.setCount(2);
        ArrayList<OrderInfoModel> fakeList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            OrderInfoModel orderInfoModel = new OrderInfoModel();
            orderInfoModel.setCreateTime("1494748078");
            orderInfoModel.setOrderStatus(1);
            orderInfoModel.setOrderPhone("1580000000" + i);
            orderInfoModel.setWatchUser("It's a hotel" + i);
            fakeList.add(orderInfoModel);
        }

        guestInfosResModel.setOrderList(fakeList);

        return guestInfosResModel;
    }

    private void getGuestInfoList() {
        currentPage = 1;
        getListRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_GET_GUEST_INFO_LIST, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_status", currentStatus + "");
        param.put("order_page", currentPage + "");

        getListRequest.setParams(param);
        getApiService().exec(getListRequest, this);

    }

    private void loadMoreInfoList() {
        isLoadMore = true;
        int loadmorePage = currentPage + 1;
        loadMoreRequest = new ApiRequest(URLCollection.URL_DOMAIN + URLCollection.URL_GET_GUEST_INFO_LIST, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        param.put("access_token", BasePreference.getToken());
        param.put("order_status", currentStatus + "");
        param.put("order_page", loadmorePage + "");

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
                //testFake
//                model = getFakeData();
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    loadingView.dismiss();
                    ((GuestInfoListFragment) (GuestInfoFragment.this.getParentFragment())).updateTitle(model.getCount(), currentStatus);

                    guestInfoAdapter.notifyDataChanged(model.getOrderList());
                    if (model.getCount() < 10) {
                        loadMoreView.showNoMoreInFirst();
                    } else {
                        loadMoreView.showLoading();
                    }
                } else {
                    loadingView.showLoadingEmpty();
                    ((GuestInfoListFragment) (GuestInfoFragment.this.getParentFragment())).updateTitle(0, currentStatus);

                }

            } else {
                showToast(resultModel.message);
                loadingView.showGuestInfoLoadingFailed();
            }
        } else if (req == loadMoreRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                currentPage = Integer.parseInt(((ApiRequest) req).getParams().get("order_page"));
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    guestInfoAdapter.notifyMoreDataChanged(model.getOrderList());
                }

                if (model.getCount() <= currentPage * 10) {
                    loadMoreView.showNoMore();
                } else {
                    loadMoreView.showLoading();
                }
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

        if (req == getListRequest) {
            loadingView.showGuestInfoLoadingFailed();
        } else if (req == loadMoreRequest) {

        }

        if (guestInfoAdapter.isEmpty()) {
            loadMoreView.dismissLoading();
        } else {
            loadMoreView.showFailed();
            showToast(getString(R.string.abnormal));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter().getItem(position) instanceof OrderInfoModel) {
            Intent intent = new Intent(getActivity(), GuestInfoDetailActivity.class);
            intent.putExtra("order_id", guestInfoAdapter.getList().get(position).getId());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        getGuestInfoList();
    }

    protected void requestComplete() {
        if (recyclerRefreshLayout != null) {
            recyclerRefreshLayout.setRefreshing(false);
        }

    }


    @Override
    public void onLoadMore() {
        loadMoreInfoList();
    }
}
