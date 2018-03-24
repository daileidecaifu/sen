package sen.wedding.com.weddingsen.account.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/6/12.
 */

public class PersonInfoModel {

    private String alipay;

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("bank_user")
    private String bankUser;

    @SerializedName("bank_account")
    private String bankAccount;

    @SerializedName("zfb_name")
    private String alipayName;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }
}
