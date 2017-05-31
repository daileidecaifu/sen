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

    public static void clearAll() {
        saveToken("");
        saveUserName("");
        saveUserType("");
        saveUserType("");
        saveAlipayAccount("");
        saveHotelId("");
        saveHotelName("");
    }


}
