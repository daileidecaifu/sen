package sen.wedding.com.weddingsen.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.lzy.ninegrid.NineGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Stack;

import sen.wedding.com.weddingsen.MyEventBusIndex;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.account.model.AccountInfoModel;
import sen.wedding.com.weddingsen.base.model.CheckVersionResModel;
import sen.wedding.com.weddingsen.component.service.DownloadService;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.http.service.HttpService;
import sen.wedding.com.weddingsen.main.activity.HotelShowActivity;
import sen.wedding.com.weddingsen.utils.DLUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.NineGlideLoader;
import sen.wedding.com.weddingsen.utils.StringUtil;
import sen.wedding.com.weddingsen.utils.crash.CrashManager;

/**
 * Created by lorin on 17/4/2.
 */

public class SenApplication extends Application implements RequestHandler<ApiRequest, ApiResponse> {

    private static SenApplication instance;
    private HttpService httpService;
    private ApiService apiService;
    public Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
        NineGridView.setImageLoader(new NineGlideLoader());
        CrashManager.init();

    }

    public static SenApplication getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Application has not been created");
        }
        return instance;
    }

    public SenApplication() {
        instance = this;
    }


    public HttpService getHttpService() {

        if (httpService == null) {
            httpService = new HttpService(this);
        }
        return httpService;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = new ApiService(this);
        }
        return apiService;
    }

    /**
     * activity栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 结束所有activity
     */

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void logout() {
        BasePreference.clearAll();
        finishAllActivity();
        Intent intent = new Intent(this, HotelShowActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    public void onRequestStart(ApiRequest req) {

    }

    @Override
    public void onRequestProgress(ApiRequest req, int count, int total) {

    }

    @Override
    public void onRequestFinish(ApiRequest req, ApiResponse resp) {


    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {

    }

}
