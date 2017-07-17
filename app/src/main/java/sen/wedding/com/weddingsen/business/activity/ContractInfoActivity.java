package sen.wedding.com.weddingsen.business.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

//import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import sen.wedding.com.weddingsen.R;
import sen.wedding.com.weddingsen.base.ApiRequest;
import sen.wedding.com.weddingsen.base.ApiResponse;
import sen.wedding.com.weddingsen.base.BaseActivity;
import sen.wedding.com.weddingsen.base.BasePreference;
import sen.wedding.com.weddingsen.base.Conts;
import sen.wedding.com.weddingsen.base.URLCollection;
import sen.wedding.com.weddingsen.business.adapter.PhotoAdapter;
import sen.wedding.com.weddingsen.business.model.ContractReviewModel;
import sen.wedding.com.weddingsen.business.model.DetailResModel;
import sen.wedding.com.weddingsen.business.model.OSSImageInfoModel;
import sen.wedding.com.weddingsen.business.model.OSSResultModel;
import sen.wedding.com.weddingsen.business.model.OSSUploadModel;
import sen.wedding.com.weddingsen.business.utils.OSSResultFeedback;
import sen.wedding.com.weddingsen.business.utils.OSSUploadResult;
import sen.wedding.com.weddingsen.business.utils.OSSUploadTask;
import sen.wedding.com.weddingsen.component.TitleBar;
import sen.wedding.com.weddingsen.component.compress.CompressHelper;
import sen.wedding.com.weddingsen.databinding.ContractInfoBinding;
import sen.wedding.com.weddingsen.http.base.RequestHandler;
import sen.wedding.com.weddingsen.http.model.ResultModel;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.utils.AppLog;
import sen.wedding.com.weddingsen.utils.DateUtil;
import sen.wedding.com.weddingsen.utils.FileIOUtil;
import sen.wedding.com.weddingsen.utils.GsonConverter;
import sen.wedding.com.weddingsen.utils.OSSUploader;
import sen.wedding.com.weddingsen.utils.StringUtil;

/**
 * Created by lorin on 17/5/2.
 */

public class ContractInfoActivity extends BaseActivity implements View.OnClickListener, RequestHandler<ApiRequest, ApiResponse> {

    ContractInfoBinding binding;
    private PhotoAdapter photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ContractReviewModel contractReviewModel;

    private OSS oss;
    private int orderId;
    private int type;
    private ApiRequest submitCertificateRequest, getContractReviewRequest;

    private long signTime;
    private int uploadSuccess = 10000;
    private int uploadFail = 9999;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == uploadSuccess) {
                submitertificate(msg.obj.toString());
            } else if (msg.what == uploadFail) {
                showToast("Upload Failed");
            }
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contract_info);
        binding.setClickListener(this);

        getInfo();

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(Conts.OSS_ACCESS_KEY_ID, Conts.OSS_ACCESS_KEY_SECRET);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(30 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(30 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(8); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), Conts.OSS_ENDPOINT, credentialProvider, conf);

        initTitleBar(binding.titleBar, TitleBar.Type.COMMON);
        getTitleBar().setTitle(getString(R.string.confirm_sign));
        getTitleBar().setRightVisibility(View.GONE);
//        getTitleBar().setCommonRightText("UPLOAD");
//        getTitleBar().setRightClickEvent(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                setResult(RESULT_OK);
//                finish();
//            }
//        });
        getTitleBar().setLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        binding.rvPicSelect.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        binding.rvPicSelect.setAdapter(photoAdapter);

        //合同时间
        binding.llContractMoney.tvItemEditTitle.setText(getString(R.string.contract_money));
        binding.llContractMoney.etItemEditInput.setHint(getString(R.string.contract_money_tip));
        binding.llContractMoney.etItemEditInput.setInputType(8194);

        //签单时间
        binding.llSignUpTime.tvItemSelectTitle.setText(getString(R.string.sign_up_time));
        binding.llSignUpTime.tvItemSelectIcon.setVisibility(View.GONE);

        long currentTimestamp = System.currentTimeMillis();
        signTime = currentTimestamp / 1000;
        binding.llSignUpTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(currentTimestamp), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
        FileIOUtil.deleteFile(new File(Conts.COMPRESS_IMG_PATH));

        if (type == Conts.SOURCE_MODIFY) {
            showProgressDialog(false);
            getFollowUp();
        }

    }

    private void getInfo() {
        orderId = getIntent().getIntExtra("order_id", -1);
        type = getIntent().getIntExtra("type", -1);

    }

    private void getFollowUp() {
        showProgressDialog(false);
        if (orderId != -1) {
            getContractReviewRequest = new ApiRequest(URLCollection.URL_SHOW_ORDER_SIGN_DETAIL, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");
            getContractReviewRequest.setParams(param);
            getApiService().exec(getContractReviewRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_submit_review:

                showProgressDialog(false);

                OSSUploader ossUploader = new OSSUploader(oss, prepareUploadRequests(), new OSSResultFeedback() {
                    @Override
                    public void onComplete(OSSResultModel result) {

                        closeProgressDialog();
                        if (result.getPuts().size() == result.getSuccesslist().size()) {
                            Message message = new Message();

                            StringBuffer sb = new StringBuffer();

                            for (int i = 0; i < result.getSuccesslist().size(); i++) {
                                if (i != 0) {
                                    sb.append(",");
                                }
                                sb.append(result.getSuccesslist().get(i).getRemoteUrl());

                            }
                            String ossImageUrls = sb.toString();
                            AppLog.e(ossImageUrls);

                            message.what = uploadSuccess;
                            message.obj = ossImageUrls;
                            handler.sendMessage(message);

                        } else {
                            Message message = new Message();
                            message.what = uploadFail;
                            handler.sendMessage(message);
                        }

                    }
                });
                ossUploader.toUpload();

//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        OSSUploadTask ossUploadTask = new OSSUploadTask(oss, prepareUploadRequests(), new OSSUploadResult() {
//
//                            @Override
//                            public void onComplete(OSSUploadModel result) {
//                                if (result != null && result.isSuccess()) {
////                                    closeProgressDialog();
//                                    StringBuffer sb = new StringBuffer();
//                                    ossImageUrls = "";
//
//                                    for (int i = 0; i < result.getList().size(); i++) {
//                                        if (i != 0) {
//                                            sb.append(",");
//                                        }
//                                        sb.append(result.getList().get(i).getRemoteUrl());
//
//                                    }
//                                    ossImageUrls = sb.toString();
//                                    AppLog.e(ossImageUrls);
//                                    submitertificate();
//                                } else {
//                                    closeProgressDialog();
//                                }
//                            }
//                        });
//                        ossUploadTask.execute();
//
//                    }
//                },50);


                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            List<String> photos = null;

            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    if (data != null) {
                        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    }
                    List<String> tempUrlArray = StringUtil.filterUrlImgArray(selectedPhotos);
                    selectedPhotos.clear();
                    selectedPhotos.addAll(tempUrlArray);
                    if (photos != null) {
                        selectedPhotos.addAll(photos);
                    }
                    photoAdapter.notifyDataSetChanged();
                    break;

                case PhotoPreview.REQUEST_CODE:
                    if (data != null) {
                        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    }
                    selectedPhotos.clear();

                    if (photos != null) {
                        selectedPhotos.addAll(photos);
                    }
                    photoAdapter.notifyDataSetChanged();
                    break;
            }
        }

    }


    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private List<PutObjectRequest> prepareUploadRequests() {
        List<PutObjectRequest> uploadPuts = new ArrayList<>();
        if (selectedPhotos != null && selectedPhotos.size() > 0) {
            for (String oldPath : selectedPhotos) {

                if(oldPath.startsWith("http"))
                {
                    PutObjectRequest put = new PutObjectRequest(Conts.OSS_BUCKET, Conts.OSS_UPLOAD_PREFIX + System.currentTimeMillis() + ".jpg", oldPath);
                    uploadPuts.add(put);

                }else
                {
                    File oldFile = new File(oldPath);
                    File newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(oldFile);
                    String newPath = newFile.getAbsolutePath();
                    AppLog.e(String.format("Size : %s", getReadableFileSize(newFile.length())) + "\n" + newFile.getAbsolutePath());
                    PutObjectRequest put = new PutObjectRequest(Conts.OSS_BUCKET, Conts.OSS_UPLOAD_PREFIX + System.currentTimeMillis() + ".jpg", newPath);
                    uploadPuts.add(put);
                }

            }

        }

        return uploadPuts;
    }

    private void submitertificate(String imageUrls) {
        if (orderId != -1) {
            submitCertificateRequest = new ApiRequest(URLCollection.URL_ORDER_SIGN, HttpMethod.POST);
            HashMap<String, String> param = new HashMap<>();
            param.put("access_token", BasePreference.getToken());
            param.put("user_kezi_order_id", orderId + "");
            param.put("order_money", binding.llContractMoney.etItemEditInput.getText().toString());
            param.put("sign_using_time", signTime + "");
            param.put("sign_pic", imageUrls);

            submitCertificateRequest.setParams(param);
            getApiService().exec(submitCertificateRequest, this);
        } else {
            showToast("Order ID WRONG!");
        }
    }

    private void fillData(ContractReviewModel model) {

        if (null != model.getSignUsingTime()) {
            long currentTimestamp = Long.parseLong(model.getSignUsingTime()) * 1000;
            binding.llSignUpTime.tvItemSelectContent.setText(DateUtil.convertDateToString(new Date(currentTimestamp), DateUtil.FORMAT_COMMON_Y_M_D_H_M_S));
        }
        binding.llContractMoney.etItemEditInput.setText(model.getOrderMoney());

        String[] imgs = model.getSignPic().split(",");
        for (String str : imgs) {
            selectedPhotos.add(str);
        }
        photoAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oss = null;
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
        closeProgressDialog();

        if (req == submitCertificateRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                showToast(getString(R.string.action_success));
                setResult(RESULT_OK);
                finish();
            } else {
                showToast(resultModel.message);
            }
        } else if (req == getContractReviewRequest) {
            if (resultModel.status == Conts.REQUEST_SUCCESS) {
                contractReviewModel = GsonConverter.decode(resultModel.data, ContractReviewModel.class);
                if (contractReviewModel != null) {
                    fillData(contractReviewModel);
                } else {
                    showToast(getString(R.string.data_error_tip));
                }
            } else {
                showToast(resultModel.message);
            }
        }
    }

    @Override
    public void onRequestFailed(ApiRequest req, ApiResponse resp) {
        closeProgressDialog();
        showToast(resp.getResultModel().message);
    }
}
