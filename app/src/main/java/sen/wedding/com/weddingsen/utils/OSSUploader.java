package sen.wedding.com.weddingsen.utils;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.business.model.OSSImageInfoModel;
import sen.wedding.com.weddingsen.business.model.OSSResultModel;
import sen.wedding.com.weddingsen.business.model.OSSUploadModel;
import sen.wedding.com.weddingsen.business.utils.OSSResultFeedback;
import sen.wedding.com.weddingsen.business.utils.OSSUploadResult;

/**
 * Created by lorin on 17/6/12.
 */

public class OSSUploader {

    private OSSResultFeedback ossResultFeedback = null;
    private OSS oss;
    List<PutObjectRequest> waitForUploadPuts =new ArrayList<>();
    List<PutObjectRequest> oPuts;

    List<OSSImageInfoModel> urlImageList = new ArrayList<>();
    List<OSSImageInfoModel> successList = new ArrayList<>();
    List<OSSImageInfoModel> failList = new ArrayList<>();
    private int resultCount;


    public OSSUploader(OSS client, List<PutObjectRequest> puts, OSSResultFeedback ossResultFeedback) {
        super();
        this.ossResultFeedback = ossResultFeedback;
        this.oss = client;
        this.oPuts = puts;
        for (PutObjectRequest put : oPuts) {
            if (put.getUploadFilePath().startsWith("http")) {
                OSSImageInfoModel ossImageInfoModel = new OSSImageInfoModel();
                ossImageInfoModel.setStatus("success");
                ossImageInfoModel.setRemoteUrl(put.getUploadFilePath());
                ossImageInfoModel.setOriginalPath(put.getUploadFilePath());
                urlImageList.add(ossImageInfoModel);
            } else {
                waitForUploadPuts.add(put);
            }
        }
    }

    public void toUpload() {
        resultCount = 0;
        successList.clear();
        failList.clear();
        for (final PutObjectRequest putObject : waitForUploadPuts) {

            // 异步上传时可以设置进度回调
//            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//                @Override
//                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                    Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//
//                }
//            });

            OSSAsyncTask task = oss.asyncPutObject(putObject, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    resultCount = resultCount + 1;
                    OSSImageInfoModel ossImageInfoModel = new OSSImageInfoModel();
                    ossImageInfoModel.setStatus("success");
                    ossImageInfoModel.setRemoteUrl(Conts.OSS_REMOTE_URL + putObject.getObjectKey());
                    ossImageInfoModel.setOriginalPath(putObject.getUploadFilePath());
                    successList.add(ossImageInfoModel);

                    if (resultCount == waitForUploadPuts.size()) {
                        OSSResultModel ossResultModel = new OSSResultModel();
                        ossResultModel.setFaillist(failList);
                        List<OSSImageInfoModel> allEffectivelist = new ArrayList<>();
                        allEffectivelist.addAll(urlImageList);
                        allEffectivelist.addAll(successList);
                        ossResultModel.setSuccesslist(allEffectivelist);
                        ossResultModel.setPuts(oPuts);
                        AppLog.e("result", GsonConverter.toJson(ossResultModel));
                        ossResultFeedback.onComplete(ossResultModel);

                    }

                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                    resultCount = resultCount + 1;
                    OSSImageInfoModel ossImageInfoModel = new OSSImageInfoModel();
                    ossImageInfoModel.setStatus("fail");
                    failList.add(ossImageInfoModel);

                    if (resultCount == waitForUploadPuts.size()) {
                        OSSResultModel ossResultModel = new OSSResultModel();
                        List<OSSImageInfoModel> allEffectivelist = new ArrayList<>();
                        allEffectivelist.addAll(urlImageList);
                        allEffectivelist.addAll(successList);
                        ossResultModel.setSuccesslist(allEffectivelist);
                        ossResultModel.setPuts(oPuts);
                        AppLog.e("result", GsonConverter.toJson(ossResultModel));
                        ossResultFeedback.onComplete(ossResultModel);
                    }
                }
            });
        }


    }


}
