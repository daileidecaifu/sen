package sen.wedding.com.weddingsen.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.refresh.DragDistanceConverterEg;
import sen.wedding.com.weddingsen.component.refresh.RecyclerFragment;
import sen.wedding.com.weddingsen.component.refresh.RecyclerListAdapter;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.model.GuestInfosResModel;
import sen.wedding.com.weddingsen.main.model.OrderInfoModel;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;

public class OpenProjectNormalFragment extends RecyclerFragment<OrderInfoModel> implements RequestHandler<ApiRequest, ApiResponse> {


    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final List<OrderInfoModel> mItemList = new ArrayList<>();

    private ApiRequest getListRequest, loadMoreRequest;

    private GuestInfosResModel model;
    private int currentStatus;
    private int currentPage;

    private InteractionListener itemInteractionListener;

    public static OpenProjectNormalFragment newInstance(int orderStatus) {

        Bundle args = new Bundle();
        args.putInt("order_status", orderStatus);
        OpenProjectNormalFragment fragment = new OpenProjectNormalFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        currentStatus = getArguments().getInt("order_status");

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getOriginAdapter().setItemList(mItemList);
        getHeaderAdapter().notifyDataSetChanged();
        getRecyclerRefreshLayout().setDragDistanceConverter(new DragDistanceConverterEg());

    }

    @Override
    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @NonNull
    @Override
    public RecyclerListAdapter createAdapter() {
        return new RecyclerListAdapter() {
            {
                addViewType(OrderInfoModel.class, new ViewHolderFactory<ViewHolder>() {
                    @Override
                    public ViewHolder onCreateViewHolder(ViewGroup parent) {
                        return new ItemViewHolder(parent);
                    }
                });
            }
        };
    }

    @Override
    protected InteractionListener createInteraction() {

        if (itemInteractionListener == null) {
            itemInteractionListener = new ItemInteractionListener();
        }
        return itemInteractionListener;
    }

//    private void simulateNetworkRequest(final RequestListener listener) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(REQUEST_DURATION);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if (isAdded()) {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mSimulateStatus == SIMULATE_FRESH_FAILURE) {
//                                listener.onFailed();
//                            } else if (mSimulateStatus == SIMULATE_FRESH_NO_DATA) {
//                                listener.onSuccess(Collections.EMPTY_LIST);
//                            } else {
//                                listener.onSuccess(getFakeData());
//                            }
//
//                            mSimulateStatus = SIMULATE_UNSPECIFIED;
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

//    private interface RequestListener {
//        void onSuccess(List<OrderInfoModel> openProjectModels);
//
//        void onFailed();
//    }

    private class ItemInteractionListener extends InteractionListener {

        @Override
        public void requestRefresh() {
            getGuestInfoList();
        }

        @Override
        public void requestMore() {
            loadMoreInfoList();
        }
    }

    private class ItemViewHolder extends RecyclerListAdapter.ViewHolder<OrderInfoModel> {
        private final TextView tvOrderTime;
        private final TextView tvOrderStatus;
        private final TextView tvContantPerson;
        private final TextView tvFollower;
        private final View vPlaceholderHead;


        public ItemViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(getActivity()).inflate(R.layout.item_main_info_recycle, parent, false));

            tvOrderTime = (TextView) itemView.findViewById(R.id.tv_order_time);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tv_order_status);
            tvContantPerson = (TextView) itemView.findViewById(R.id.tv_contant_person_phone);
            tvFollower = (TextView) itemView.findViewById(R.id.tv_follower_faction);
            vPlaceholderHead = (View) itemView.findViewById(R.id.v_review_placeholder_head);
        }

        @Override
        public void bind(final OrderInfoModel item, final int position) {
            if (position == 0) {
                vPlaceholderHead.setVisibility(View.VISIBLE);
            } else {
                vPlaceholderHead.setVisibility(View.GONE);

            }

//            long time = Long.parseLong(item.getCreateTime()) * 1000;
            tvOrderTime.setText(item.getCreateTime());
            tvOrderStatus.setText(Conts.getOrderStatusMap().get(item.getOrderStatus()));
            tvContantPerson.setText(item.getOrderPhone());
            tvFollower.setText(item.getWatchUser());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "" + position, Toast.LENGTH_LONG).show();
                }
            });
        }
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
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    mItemList.clear();
                    mItemList.addAll(model.getOrderList());
                    getHeaderAdapter().notifyDataSetChanged();
                    itemInteractionListener.requestRefreshAciton();
                } else {
                    itemInteractionListener.showEmpty();
                }
            } else {
                showToast(resultModel.message);
            }
        } else if (req == loadMoreRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                model = GsonConverter.decode(resultModel.data, GuestInfosResModel.class);
                currentPage = Integer.parseInt(((ApiRequest) req).getParams().get("order_page"));
                if (model.getOrderList() != null && model.getOrderList().size() > 0) {
                    mItemList.addAll(model.getOrderList());
                    getHeaderAdapter().notifyDataSetChanged();
                    itemInteractionListener.requestMoreAction();
                } else {
                    itemInteractionListener.showNoMore();
                    showToast("No More");

                }
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }


}
