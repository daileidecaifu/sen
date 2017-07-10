package sen.wedding.com.weddingsen.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.http.service.HttpService;

/**
 * Created by lorin on 17/3/20.
 */

public class BaseActivity extends FragmentActivity {

    TitleBar titleBar;
    private ProgressDialog progressDialog;
    private HttpService httpService;
    private ApiService apiService;


    protected void jumpToOtherActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void initTitleBar(View titleView, TitleBar.Type type) {
        titleBar = new TitleBar(titleView, type);
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialog(boolean isScreenLock) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, null, getString(R.string.progress_wait), true, false);
            progressDialog.setCanceledOnTouchOutside(false);// dialog外点击取消dialog
            progressDialog.setCancelable(!isScreenLock);// 返回键有效
        } else {
            progressDialog.show();
        }
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public HttpService getHttpService() {
        if (httpService == null) {
            httpService = SenApplication.getInstance().getHttpService();
        }
        return httpService;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = SenApplication.getInstance().getApiService();
        }
        return apiService;
    }

    protected void hideSoftKeyBoard()
    {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SenApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SenApplication.getInstance().removeActivity(this);
        titleBar = null;
        progressDialog = null;

    }

    protected void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
