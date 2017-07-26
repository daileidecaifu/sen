package sen.wedding.com.weddingsen.main.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseFragment;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.component.LoadingView;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.main.activity.HotelDetailActivity;
import sen.wedding.com.weddingsen.main.activity.HotelDistinctActivity;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;
import sen.wedding.com.weddingsen.main.activity.InfoProvideActivity;
import sen.wedding.com.weddingsen.main.activity.SearchHotelActivity;
import sen.wedding.com.weddingsen.main.adapter.HotelDistinctAdapter;
import sen.wedding.com.weddingsen.main.adapter.HotelTypeAdapter;
import sen.wedding.com.weddingsen.main.adapter.HotelsAdapter;
import sen.wedding.com.weddingsen.main.adapter.SearchHistoryAdapter;
import sen.wedding.com.weddingsen.main.model.HotelDistinctModel;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.model.EventIntent;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener, View.OnClickListener {

    ListView listView;
    TextView tvRecommend;
    LoadingView loadingView;
    RelativeLayout rlSelectShow;
    RecyclerView rvDistinct;
    LinearLayout llDistinct;
    ListView lvType;
    LinearLayout llType;

    HotelsAdapter hotelsAdapter;
    private HotelDistinctAdapter hotelDistinctAdapter;
    HotelTypeAdapter hotelTypeAdapter;

    String[] items;
    String[] types;
    List<String> hotelTypes = new ArrayList<>();
    ArrayList<HotelShowModel> hotelShowModels = new ArrayList<>();
    private ArrayList<HotelDistinctModel> selectedDistincts = new ArrayList<>();

    private ApiRequest getHotelListRequest, getDistinctRequest;
    TextView textViewLeft;
    int yourChoice = 0;
    String selectDistinctTitle;
    String selectDistinctId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String jsonResult = msg.obj.toString();
            HotelDistinctModel hotelDistinctModel = GsonConverter.fromJson(jsonResult, HotelDistinctModel.class);
            if (hotelDistinctModel != null) {

                selectDistinctId = hotelDistinctModel.getDistinctId();
                selectDistinctTitle = hotelDistinctModel.getTitle();
                if (!TextUtils.isEmpty(selectDistinctId)) {
                    rlSelectShow.setVisibility(View.GONE);
                    loadingView.showLoading();
                    getHotelList(selectDistinctId);
                }

            }

        }
    };

    public static HotelShowFragment newInstance() {

        Bundle args = new Bundle();
        HotelShowFragment fragment = new HotelShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hotel_show, null);
        listView = (ListView) view.findViewById(R.id.lv_hotels);
        tvRecommend = (TextView) view.findViewById(R.id.tv_recommend);
        loadingView = (LoadingView) view.findViewById(R.id.loading_view);
        rlSelectShow = (RelativeLayout) view.findViewById(R.id.rl_select_show);
        rvDistinct = (RecyclerView) view.findViewById(R.id.rv_dictinct_show);
        llDistinct = (LinearLayout) view.findViewById(R.id.ll_distinct);
        llType = (LinearLayout) view.findViewById(R.id.ll_type);
        lvType = (ListView) view.findViewById(R.id.lv_type);

        llDistinct.setOnClickListener(this);
        llType.setOnClickListener(this);
        rlSelectShow.setOnClickListener(this);

        loadingView.setLoadingViewClickListener(new LoadingView.OnLoadingViewClickListener() {
            @Override
            public void OnLoadingFailedClick(View view) {
                loadingView.showLoading();
                getHotelList("");
            }

            @Override
            public void OnLoadingEmptyClick(View view) {
                loadingView.showLoading();
                getHotelList("");
            }
        });
        initTitle(view);
        initComponents();
        initListView();
        initData();
        loadingView.showLoading();
        getHotelList("");
        getDistincts();
        return view;

    }

    private void initComponents() {
        if (BasePreference.getUserType().equals(Conts.LOGIN_MODEL_FIRST_SALE)
                || BasePreference.getUserType().equals(Conts.LOGIN_MODEL_SECOND_SALE)) {
            tvRecommend.setVisibility(View.GONE);
        } else {
            tvRecommend.setOnClickListener(this);
        }

        hotelDistinctAdapter = new HotelDistinctAdapter(getContext(), selectedDistincts, handler);
        rvDistinct.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rvDistinct.setAdapter(hotelDistinctAdapter);

        hotelTypeAdapter = new HotelTypeAdapter(getContext());
        types = getResources().getStringArray(R.array.hotel_type);

        lvType.setAdapter(hotelTypeAdapter);
        for (String str : types) {
            hotelTypes.add(str);
        }
        hotelTypeAdapter.notifyDataChanged(hotelTypes);
    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);
        LinearLayout layoutRight = (LinearLayout) linearLayoutTitle.findViewById(R.id.ll_right);
        LinearLayout layoutLeft = (LinearLayout) linearLayoutTitle.findViewById(R.id.ll_left);
        initLeftTopIcon();
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.to_select));

        textViewTitle.setText(getString(R.string.sen));
        layoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showHotelSort();

                jumpToOtherActivity(SearchHotelActivity.class);
            }
        });

        layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(BasePreference.getToken())) {

                    jumpToOtherActivity(LoginActivity.class);
                } else {
                    ((HotelShowActivity) getActivity()).openMenu();
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainReceiver(EventIntent eventIntent) {
        if (eventIntent.getActionId() == Conts.EVENT_INIT_MAIN_SLIDE) {
            initLeftTopIcon();
        }
    }

    private void initLeftTopIcon() {
        switch (BasePreference.getUserType()) {
            case Conts.LOGIN_MODEL_PHONE:
                textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_w_tg));
                break;
            case Conts.LOGIN_MODEL_FIRST_SALE:
                textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_w_sx));
                break;
            case Conts.LOGIN_MODEL_SECOND_SALE:
                textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_w_ex));
                break;
            case Conts.LOGIN_MODEL_ACCOUNT:
                textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_w_gz));
                break;
            default:
                textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
                break;
        }
    }

    private void initListView() {
        hotelsAdapter = new HotelsAdapter(getActivity());
        listView.setAdapter(hotelsAdapter);
//        hotelsAdapter.notifyDataChanged(getFakeData());
        listView.setOnItemClickListener(this);
    }

    private void initData() {
        items = getResources().getStringArray(R.array.hotel_sort_type);

    }

    private void showHotelSort() {


        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setSingleChoiceItems(items, yourChoice,
                null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                yourChoice = which;

                switch (which) {
                    case 0:
                        loadingView.showLoading();
                        getHotelList("");
                        break;

                    case 1:
                        Intent intent = new Intent(getActivity(), HotelDistinctActivity.class);
                        startActivityForResult(intent, 10000);
//                        jumpToOtherActivity(HotelDistinctActivity.class);
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

    private void getHotelList(String shId) {
        getHotelListRequest = new ApiRequest(URLCollection.URL_GET_HOTEL_LIST, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();
        if (!TextUtils.isEmpty(shId)) {
            param.put("list_type", "2");
            param.put("area_sh_id", shId);
        } else {
            param.put("list_type", "1");
        }

        getHotelListRequest.setParams(param);
        getApiService().exec(getHotelListRequest, this);

    }

    private void getDistincts() {
        getDistinctRequest = new ApiRequest(URLCollection.URL_DISTINCTS, HttpMethod.POST);
        HashMap<String, String> param = new HashMap<>();

        getDistinctRequest.setParams(param);
        getApiService().exec(getDistinctRequest, this);

    }

//    private ArrayList<HotelShowModel> getFakeData() {
//
//        ArrayList<HotelShowModel> fakeList = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            HotelShowModel hotelShowModel = new HotelShowModel();
//            hotelShowModel.setAddressDescription("1231234");
//            hotelShowModel.setHotelName("afadsfaf");
//            hotelShowModel.setPhoneNumber("14543333333");
//            hotelShowModel.setTableCount("36桌");
//            hotelShowModel.setUnitPrice("8800元/桌");
//            fakeList.add(hotelShowModel);
//        }
//        return fakeList;
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), HotelDetailActivity.class);
        intent.putExtra("hotel_id", hotelShowModels.get(position).getHotelId());
//        jumpToOtherActivity(HotelDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recommend:
                switch (BasePreference.getUserType()) {
                    case Conts.LOGIN_MODEL_PHONE:
                    case Conts.LOGIN_MODEL_ACCOUNT:
                        jumpToOtherActivity(InfoProvideActivity.class);
                        break;
                    case "":
                        jumpToOtherActivity(LoginActivity.class);
                        break;
                }
                break;
            case R.id.ll_distinct:
                rlSelectShow.setVisibility(View.VISIBLE);
                rvDistinct.setVisibility(View.VISIBLE);
                lvType.setVisibility(View.GONE);
                break;

            case R.id.ll_type:
                rlSelectShow.setVisibility(View.VISIBLE);
                rvDistinct.setVisibility(View.GONE);
                lvType.setVisibility(View.VISIBLE);
                break;

            case R.id.rl_select_show:
                rlSelectShow.setVisibility(View.GONE);

                break;
        }
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

        if (req == getHotelListRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();

                if (resultModel.data != null) {
                    loadingView.dismiss();
                    hotelShowModels = GsonConverter.fromJson(resultModel.data.toString(),
                            new TypeToken<List<HotelShowModel>>() {
                            }.getType());

                    if (hotelShowModels != null && hotelShowModels.size() > 0) {
                        hotelsAdapter.notifyDataChanged(hotelShowModels);
                    } else {
                        loadingView.showEmptyWithNoAction(selectDistinctTitle + getString(R.string.distinct_no_hotel));
                    }

                } else {
                    loadingView.showEmptyWithNoAction(selectDistinctTitle + getString(R.string.distinct_no_hotel));
                }

            } else {
                showToast(resultModel.message);
            }
        } else if (req == getDistinctRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                //testFake
//                model = getFakeData();

                if (resultModel.data != null) {
                    loadingView.dismiss();
                    Map<String, String> logMap = GsonConverter.fromJson(resultModel.data.toString(),
                            new TypeToken<Map<String, String>>() {
                            }.getType());
                    selectedDistincts.clear();
                    for (Map.Entry<String, String> entry : logMap.entrySet()) {
                        HotelDistinctModel model = new HotelDistinctModel();
                        model.setDistinctId(entry.getKey());
                        model.setTitle(entry.getValue());
                        selectedDistincts.add(model);
                    }
                    if (selectedDistincts != null && selectedDistincts.size() > 0) {
                        hotelDistinctAdapter.notifyDataSetChanged();
                    } else {
                        loadingView.showLoadingEmpty();
                    }

                } else {
                    loadingView.showLoadingEmpty();
                }

            } else {
                showToast(resultModel.message);
                loadingView.showGuestInfoLoadingFailed();

            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        if (req == getHotelListRequest) {
            loadingView.showGuestInfoLoadingFailed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (data != null) {
//            selectDistinctId = data.getStringExtra("select_id");
//            selectDistinctTitle = data.getStringExtra("select_title");
//
//            if (!TextUtils.isEmpty(selectDistinctId)) {
//                loadingView.showLoading();
//                getHotelList(selectDistinctId);
//            }
//        }
    }
}
