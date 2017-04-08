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
}
