package sen.wedding.com.weddingsen.business.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/9.
 */

public class OrderItemModel {

    private int id;

    @SerializedName("create_time")
    private String createTime;

    @SerializedName("order_status")
    private int orderStatus;

    @SerializedName("order_phone")
    private String orderPhone;

    @SerializedName("watch_user")
    private String watchUser;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("order_type")
    private int orderType;

    @SerializedName("order_area_hotel_type")
    private int orderAreaHotelType;

    @SerializedName("order_area_hotel_id")
    private int orderAreaHotelId;

    @SerializedName("desk_count")
    private String destCount;

    @SerializedName("order_money")
    private String orderMoney;

    @SerializedName("use_date")
    private String useDate;

    @SerializedName("order_desc")
    private String orderDesc;

    @SerializedName("order_area_hotel_name")
    private String orderAreaHotelName;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderAreaHotelType() {
        return orderAreaHotelType;
    }

    public void setOrderAreaHotelType(int orderAreaHotelType) {
        this.orderAreaHotelType = orderAreaHotelType;
    }

    public int getOrderAreaHotelId() {
        return orderAreaHotelId;
    }

    public void setOrderAreaHotelId(int orderAreaHotelId) {
        this.orderAreaHotelId = orderAreaHotelId;
    }

    public String getDestCount() {
        return destCount;
    }

    public void setDestCount(String destCount) {
        this.destCount = destCount;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderAreaHotelName() {
        return orderAreaHotelName;
    }

    public void setOrderAreaHotelName(String orderAreaHotelName) {
        this.orderAreaHotelName = orderAreaHotelName;
    }
}
