package sen.wedding.com.weddingsen.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import sen.wedding.com.weddingsen.R;

/**
 * Created by lorin on 17/4/9.
 */

public class BaseFragment extends Fragment{

    private ProgressDialog progressDialog;
    private ApiService apiService;


    public void showProgressDialog(boolean isScreenLock) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(getActivity(), null, getActivity().getString(R.string.progress_wait), true, false);
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

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = SenApplication.getInstance().getApiService();
        }
        return apiService;
    }

    public void showToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }

    protected void jumpToOtherActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivity(intent);
    }

}
