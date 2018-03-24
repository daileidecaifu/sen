package sen.wedding.com.weddingsen.base;

/**
 * Created by lorin on 17/4/8.
 */

public class URLCollection {

    public static String URL_DOMAIN = "http://dev.51isen.com";

    //正式
    public final static String URL_DOMAIN_APP = "http://app.51isen.com";
    //dev
    public final static String URL_DOMAIN_DEV = "http://dev.51isen.com";

    //正式
    public final static String URL_LOGIN = "/index.php?m=app&c=user&f=login";

    public final static String URL_ACCOUNT_LOGIN = "/index.php?m=app&c=user&f=loginByUser";

    public final static String URL_GET_CODE = "/index.php?m=app&c=user&f=getPhoneCode";
    //
    public final static String URL_BIND_ALIPAY = "/index.php?m=app&c=user&f=alipayBind";
    //
    public final static String URL_VERIFY_GUEST_PHONE = "/index.php?m=app&c=order&f=validatePhoneOrderType";
//
    public final static String URL_GET_HOTEL_OR_AREAS = "/index.php?m=app&c=order&f=orderHotelArea";
//
    public final static String URL_FEEDBACK = "/index.php?m=app&c=feedback&f=create";
//
    public final static String URL_CREATE_GUEST_INFO = "/index.php?m=app&c=order&f=createKeZi";
//
    public final static String URL_GET_GUEST_INFO_LIST = "/index.php?m=app&c=order&f=orderKeZiList";
//
    public final static String URL_SHOW_GUEST_INFO_DETAIL = "/index.php?m=app&c=order&f=orderKeZiDetail";
//
    public final static String URL_FOLLOW_HANDLER_LIST = "/index.php?m=app&c=order&f=orderHandleKeZiList";
//
    public final static String URL_FOLLOW_LOG_LIST = "/index.php?m=app&c=order&f=keziOrderFollowList";
//
    public final static String URL_ORDER_FOLLOW = "/index.php?m=app&c=order&f=keziOrderFollow";
//
    public final static String URL_ORDER_SIGN = "/index.php?m=app&c=order&f=keziOrderSign";
//
    public final static String URL_SHOW_ORDER_SIGN_DETAIL = "/index.php?m=app&c=order&f=keziOrderSignDetail";
//
    public final static String URL_VERIFY_BUILD_PHONE = "/index.php?m=app&c=order&f=validatePhoneDaJianOrderType";
//
    public final static String URL_CREATE_BUILD_INFO = "/index.php?m=app&c=order&f=createDaJian";
//
    public final static String URL_GET_BUILD_INFO_LIST = "/index.php?m=app&c=order&f=orderDaJianList";
//
    public final static String URL_SHOW_BUILD_INFO_DETAIL = "/index.php?m=app&c=order&f=orderDaJianDetail";
//
    public final static String URL_GET_HOTEL_LIST = "/index.php?m=app&c=user&f=mainList";
//
    public final static String URL_DISTINCTS = "/index.php?m=app&c=user&f=getShArea";
//
    public final static String URL_HOTEL_DETAIL = "/index.php?m=app&c=user&f=mainHotelDetail";
//
    public final static String URL_RESET_PASSWORD = "/index.php?m=app&c=feedback&f=accountEdit";
//
    public final static String URL_PERSON_DETAIL = "/index.php?m=app&c=feedback&f=wallet";
//
    public final static String URL_BUILD_LOG_LIST = "/index.php?m=app&c=order&f=dajianOrderFollowList";
//
    public final static String URL_FIRST_SALE_FOLLOW = "/index.php?m=app&c=order&f=dajianOrderFollow";
//
    public final static String URL_FIRST_SALE_SIGN = "/index.php?m=app&c=order&f=dajianOrderSign";
//
    public final static String URL_FIRST_SALE_SIGN_DETAIL = "/index.php?m=app&c=order&f=dajianOrderSignDetail";
//
    public final static String URL_SECOND_SALE_SIGN = "/index.php?m=app&c=order&f=dajianOrderSignOther";
//
    public final static String URL_BUILD_PAY_LIST = "/index.php?m=app&c=order&f=dajianOrderSignOtherList";
//
    public final static String URL_SHOW_OTHER_ORDER_SIGN_DETAIL = "/index.php?m=app&c=order&f=dajianOrderOtherSignDetail";
//
    public final static String URL_SYNCHRONIZE = "/index.php?m=app&c=feedback&f=autoType";

    public final static String URL_UPDATE_DATA = "/index.php?m=app&c=sysData&f=updateData";

    public final static String URL_LOG_UPLOAD = "/index.php?m=app&c=sysData&f=log";

}