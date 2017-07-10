package sen.wedding.com.weddingsen.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.lzy.ninegrid.NineGridView;

import org.greenrobot.eventbus.EventBus;

import java.util.Stack;

import sen.wedding.com.weddingsen.MyEventBusIndex;
import sen.wedding.com.weddingsen.account.activity.LoginActivity;
import sen.wedding.com.weddingsen.http.service.HttpService;
import sen.wedding.com.weddingsen.utils.NineGlideLoader;
import sen.wedding.com.weddingsen.utils.crash.CrashManager;

/**
 * Created by lorin on 17/4/2.
 */

public class SenApplication extends Application {

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
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
