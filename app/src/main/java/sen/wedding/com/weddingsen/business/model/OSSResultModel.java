package sen.wedding.com.weddingsen.business.model;

import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import java.util.List;

/**
 * Created by lorin on 17/6/12.
 */

public class OSSResultModel {
    private List<OSSImageInfoModel> successlist;

    private List<OSSImageInfoModel> faillist;

    List<PutObjectRequest> puts;
    private boolean isSuccess;

    public List<OSSImageInfoModel> getSuccesslist() {
        return successlist;
    }

    public void setSuccesslist(List<OSSImageInfoModel> successlist) {
        this.successlist = successlist;
    }

    public List<OSSImageInfoModel> getFaillist() {
        return faillist;
    }

    public void setFaillist(List<OSSImageInfoModel> faillist) {
        this.faillist = faillist;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<PutObjectRequest> getPuts() {
        return puts;
    }

    public void setPuts(List<PutObjectRequest> puts) {
        this.puts = puts;
    }
}
