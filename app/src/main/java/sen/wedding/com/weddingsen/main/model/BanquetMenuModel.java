package sen.wedding.com.weddingsen.main.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lorin on 17/5/26.
 */

public class BanquetMenuModel {

    @SerializedName("menu_name")
    private String menuName;

    @SerializedName("menu_money")
    private String menuMoney;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuMoney() {
        return menuMoney;
    }

    public void setMenuMoney(String menuMoney) {
        this.menuMoney = menuMoney;
    }
}
