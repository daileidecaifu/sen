package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/5/7.
 */

public class LogInfoModel {

    @SerializedName("order_follow_time")
    private String orderFollowTime;

    @SerializedName("order_follow_desc")
    private String orderFollowDesc;

    @SerializedName("order_follow_create_time")
    private String orderFollowCreateTime;

    @SerializedName("user_order_status")
    private String userOrderStatus;

    public String getOrderFollowTime() {
        return orderFollowTime;
    }

    public void setOrderFollowTime(String orderFollowTime) {
        this.orderFollowTime = orderFollowTime;
    }

    public String getOrderFollowDesc() {
        return orderFollowDesc;
    }

    public void setOrderFollowDesc(String orderFollowDesc) {
        this.orderFollowDesc = orderFollowDesc;
    }

    public String getOrderFollowCreateTime() {
        return orderFollowCreateTime;
    }

    public void setOrderFollowCreateTime(String orderFollowCreateTime) {
        this.orderFollowCreateTime = orderFollowCreateTime;
    }

    public String getUserOrderStatus() {
        return userOrderStatus;
    }

    public void setUserOrderStatus(String userOrderStatus) {
        this.userOrderStatus = userOrderStatus;
    }
}
