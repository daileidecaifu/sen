package sen.wedding.com.weddingsen.main.model;

/**
 * Created by lorin on 17/3/30.
 */

public class OrderInfoModel {

    private String time;
    private String status;
    private String contactPersonPhone;
    private String followerFaction;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getFollowerFaction() {
        return followerFaction;
    }

    public void setFollowerFaction(String followerFaction) {
        this.followerFaction = followerFaction;
    }
}
