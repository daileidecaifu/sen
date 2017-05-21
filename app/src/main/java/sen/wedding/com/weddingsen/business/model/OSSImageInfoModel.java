package sen.wedding.com.weddingsen.business.model;

/**
 * Created by lorin on 17/5/21.
 */

public class OSSImageInfoModel {

    private String remoteUrl;
    private String originalPath;
    private String status;

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
