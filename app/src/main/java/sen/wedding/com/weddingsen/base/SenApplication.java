package sen.wedding.com.weddingsen.base;

import android.app.Application;

import sen.wedding.com.weddingsen.http.service.HttpService;

/**
 * Created by lorin on 17/4/2.
 */

public class SenApplication extends Application {

    private static SenApplication instance;
    private HttpService httpService;
    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
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
}
