package sen.wedding.com.weddingsen.main.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import sen.wedding.com.weddingsen.main.adapter.HotelsAdapter;
import sen.wedding.com.weddingsen.main.model.HotelShowModel;
import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by lorin on 17/5/25.
 */

public class HotelShowFragment extends BaseFragment implements RequestHandler<ApiRequest, ApiResponse>, AdapterView.OnItemClickListener, View.OnClickListener {

    ListView listView;
    TextView ivRecommend;
    LoadingView loadingView;

    HotelsAdapter hotelsAdapter;
    String[] items;
    ArrayList<HotelShowModel> hotelShowModels = new ArrayList<>();

    private ApiRequest getHotelListRequest;

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
        loadingView = (LoadingView) view.findViewById(R.id.loading_view);

        ivRecommend.setOnClickListener(this);

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
        initListView();
        initData();
        loadingView.showLoading();
        getHotelList("");
        return view;

    }

    private void initTitle(View view) {

        //头部title
        RelativeLayout linearLayoutTitle = (RelativeLayout) view.findViewById(R.id.title_bar);

        TextView textViewLeft = (TextView) linearLayoutTitle.findViewById(R.id.tv_left);
        TextView textViewRight = (TextView) linearLayoutTitle.findViewById(R.id.tv_right);
        TextView textViewTitle = (TextView) linearLayoutTitle.findViewById(R.id.tv_title_title);

        textViewLeft.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon_my_center));
        textViewRight.setBackgroundDrawable(getResources().getDrawable(R.mipmap.to_select));

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
//        hotelsAdapter.notifyDataChanged(getFakeData());
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

     Intent intent = new Intent(getActivity(),HotelDetailActivity.class);
        intent.putExtra("hotel_id",hotelShowModels.get(position).getHotelId());
//        jumpToOtherActivity(HotelDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recommend:
                showToast("Z");
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
                        loadingView.showLoadingEmpty();
                    }

                } else {
                    loadingView.showLoadingEmpty();
                }

            } else {
                showToast(resultModel.message);
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
        String shId ="";

        if (data != null) {
            shId = data.getStringExtra("sh_id");
            if(!TextUtils.isEmpty(shId))
            {
                loadingView.showLoading();
                getHotelList(shId);
            }
        }
    }
}
