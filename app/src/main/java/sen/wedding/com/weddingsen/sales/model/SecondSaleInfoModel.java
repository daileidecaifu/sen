package sen.wedding.com.weddingsen.sales.model;

import com.google.gson.annotations.SerializedName;

import sen.wedding.com.weddingsen.component.refresh.CursorModel;

/**
 * Created by lorin on 17/3/30.
 */

public class SecondSaleInfoModel implements CursorModel{

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

    public int getErxiaoSignType() {
        return erxiaoSignType;
    }

    public void setErxiaoSignType(int erxiaoSignType) {
        this.erxiaoSignType = erxiaoSignType;
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

    @Override
    public boolean hasMore() {
        return true;
    }
}