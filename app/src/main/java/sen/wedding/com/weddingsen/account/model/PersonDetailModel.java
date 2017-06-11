package sen.wedding.com.weddingsen.account.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/4/8.
 */

public class PersonDetailModel {

    @SerializedName("my_account")
    private PersonInfoModel myAccount;

    @SerializedName("my_money")
    private CommissionModel myMoney;

    public PersonInfoModel getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(PersonInfoModel myAccount) {
        this.myAccount = myAccount;
    }

    public CommissionModel getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(CommissionModel myMoney) {
        this.myMoney = myMoney;
    }
}
