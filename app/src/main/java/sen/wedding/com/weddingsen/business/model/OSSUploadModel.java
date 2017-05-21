package sen.wedding.com.weddingsen.business.model;

import java.util.List;

/**
 * Created by lorin on 17/5/21.
 */

public class OSSUploadModel {

    private List<OSSImageInfoModel> list;

    private boolean isSuccess;

    public List<OSSImageInfoModel> getList() {
        return list;
    }

    public void setList(List<OSSImageInfoModel> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
