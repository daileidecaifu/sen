package sen.wedding.com.weddingsen.business.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.util.ArrayList;
import java.util.List;

import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.SenApplication;
import sen.wedding.com.weddingsen.business.model.OSSImageInfoModel;
import sen.wedding.com.weddingsen.business.model.OSSUploadModel;

/**
 * 执行查询任务的异步类
 *
 * @author Lorin
 */
public class OSSUploadTask extends AsyncTask<String, Integer, String> {
    private OSSUploadResult ossUploadResult = null;
    private OSS oss;
    List<PutObjectRequest> puts;
    PutObjectRequest put;
    List<OSSImageInfoModel> infos = new ArrayList<>();

    public OSSUploadTask(OSS client, List<PutObjectRequest> puts, OSSUploadResult ossUploadResult) {
        super();
        this.ossUploadResult = ossUploadResult;
        this.oss = client;
        this.puts = puts;
    }

    public OSSUploadTask(OSS client, PutObjectRequest put, OSSUploadResult ossUploadResult) {
        super();
        this.ossUploadResult = ossUploadResult;
        this.oss = client;
        this.put = put;
    }

    @Override
    protected String doInBackground(String... params) {

        String result;

        try {
            for (PutObjectRequest putObject : puts) {
                OSSImageInfoModel ossImageInfoModel = new OSSImageInfoModel();
                ossImageInfoModel.setOriginalPath(putObject.getUploadFilePath());
                PutObjectResult putResult = oss.putObject(putObject);
                ossImageInfoModel.setRemoteUrl(Conts.OSS_REMOTE_URL + putObject.getObjectKey());
                infos.add(ossImageInfoModel);

                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", putResult.getETag());
                Log.d("RequestId", putResult.getRequestId());
            }
            result = Conts.OSS_UPLOAD_SUCCESS;
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
            result = Conts.OSS_UPLOAD_FAIL;
        } catch (ServiceException e) {
            // 服务异常
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
            result = Conts.OSS_UPLOAD_FAIL;
        }

        return result;

    }

    @Override
    protected void onPostExecute(String result) {

        if (result != null && result.equals(Conts.OSS_UPLOAD_SUCCESS)) {
            OSSUploadModel ossUploadModel = new OSSUploadModel();
            ossUploadModel.setList(infos);
            ossUploadModel.setSuccess(true);
            ossUploadResult.onComplete(ossUploadModel);
        } else {
            ossUploadResult.onComplete(null);

        }
    }

    /**
     * 判断是否连接网络
     *
     * @param context 上下文
     * @return 连接返回true, or false
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}