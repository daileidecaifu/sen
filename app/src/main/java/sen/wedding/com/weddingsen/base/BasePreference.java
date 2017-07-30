package sen.wedding.com.weddingsen.base;

import sen.wedding.com.weddingsen.utils.PreferenceUtils;

/**
 * Created by lorin on 17/4/8.
 */

public class BasePreference {

    private final static String KEY_TOKEN = "token";
    private final static String KEY_USERNAME = "username";
    private final static String KEY_ALIPAY_ACCOUNT = "alipay_account";
    private final static String KEY_USER_TYPE = "user_type";
    private final static String KEY_HOTEL_ID = "hotel_id";
    private final static String KEY_HOTEL_NAME = "hotel_name";
    private final static String KEY_BANK_ACCOUNT = "bank_account";
    private final static String KEY_AREA_ID = "area_id";
    private final static String KEY_HOTEL_AREA = "hotel_area";
    private final static String KEY_AUTO_TYPE = "auto_type";

    private final static String KEY_HOTEL_HISTORY = "hotel_history";

    public static void saveToken(String token) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_TOKEN, token);
    }

    public static String getToken() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_TOKEN, "");
    }

    public static void saveUserName(String token) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_USERNAME, token);
    }

    public static String getUserName() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_USERNAME, "");
    }

    public static void saveUserType(String type) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_USER_TYPE, type);
    }

    public static String getUserType() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_USER_TYPE, "");
    }

    public static void saveAlipayAccount(String account) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_ALIPAY_ACCOUNT, account);
    }

    public static String getAlipayAccount() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_ALIPAY_ACCOUNT, "");
    }

    public static void saveHotelId(String id) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_HOTEL_ID, id);
    }

    public static String getHotelId() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_HOTEL_ID, "");
    }

    public static void saveHotelName(String name) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_HOTEL_NAME, name);
    }

    public static String getHotelName() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_HOTEL_NAME, "");
    }

    public static void saveBankAccount(String name) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_BANK_ACCOUNT, name);
    }

    public static String getBankAccount() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_BANK_ACCOUNT, "");
    }

    public static void saveAreaId(String name) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_AREA_ID, name);
    }

    public static String getAreaId() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_AREA_ID, "");
    }

    public static void saveHotelArea(String name) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_HOTEL_AREA, name);
    }

    public static String getHotelArea() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_HOTEL_AREA, "");
    }

    public static void saveHotelSearchHistory(String history) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_HOTEL_HISTORY, history);
    }

    public static String getHotelSearchHistory() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_HOTEL_HISTORY, "");
    }

    public static void saveAutoType(String auto) {
        PreferenceUtils.setPrefString(SenApplication.getInstance(), KEY_AUTO_TYPE, auto);
    }

    public static String getAutoType() {
        return PreferenceUtils.getPrefString(SenApplication.getInstance(), KEY_AUTO_TYPE, "");
    }


    public static void clearAll() {
        saveToken("");
        saveUserName("");
        saveUserType("");
        saveAlipayAccount("");
        saveHotelId("");
        saveHotelName("");
        saveBankAccount("");
        saveAreaId("");
        saveHotelArea("");
    }


}
