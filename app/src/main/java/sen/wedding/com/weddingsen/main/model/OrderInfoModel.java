package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

import sen.wedding.com.weddingsen.component.refresh.CursorModel;

/**
 * Created by lorin on 17/3/30.
 */

public class OrderInfoModel implements CursorModel{

    private int id;

    @SerializedName("create_time")
    private String createTime;

    @SerializedName("order_status")
    private int orderStatus;

    @SerializedName("erxiao_sign_type")
    private int erxiaoSignType;

    @SerializedName("order_phone")
    private String orderPhone;

    @SerializedName("watch_user")
    private String watchUser;

    @SerializedName("order_from")
    private String orderFrom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getWatchUser() {
        return watchUser;
    }

    public void setWatchUser(String watchUser) {
        this.watchUser = watchUser;
    }

    public int getErxiaoSignType() {
        return erxiaoSignType;
    }

    public void setErxiaoSignType(int erxiaoSignType) {
        this.erxiaoSignType = erxiaoSignType;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    @Override
    public boolean hasMore() {
        return true;
    }
}
