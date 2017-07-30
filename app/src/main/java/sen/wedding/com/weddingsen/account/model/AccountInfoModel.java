package sen.wedding.com.weddingsen.account.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/8.
 */

public class AccountInfoModel {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("alipay_account")
    private String alipayAccount;

    @SerializedName("nike_name")
    private String nikeName;

    @SerializedName("user_type")
    private String userType;

    @SerializedName("hotel_id")
    private String hotelId;

    @SerializedName("hotel_name")
    private String hotelName;

    @SerializedName("bank_account")
    private String bankAccount;

    @SerializedName("hotel_area")
    private String hotelArea;

    @SerializedName("area_id")
    private String areaId;

    @SerializedName("auto_type")
    private String autoType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getHotelArea() {
        return hotelArea;
    }

    public void setHotelArea(String hotelArea) {
        this.hotelArea = hotelArea;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }
}
