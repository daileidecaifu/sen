package sen.wedding.com.weddingsen.base.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/8/14.
 */

public class CheckVersionResModel {

    @SerializedName("update_status")
    private UpdateStatusModel updateStatus;

    private String url;

    public UpdateStatusModel getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(UpdateStatusModel updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
