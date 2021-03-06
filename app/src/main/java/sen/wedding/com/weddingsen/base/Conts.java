package sen.wedding.com.weddingsen.base;

import android.content.Intent;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/24.
 */

public class Conts {

    public final static String APP_VERSION = "1.3.1";

    //更新状态
    public final static String APP_UPDATE_NO_UPDATE = "1";//无更新
    public final static String APP_UPDATE_FORCED = "2";//强制
    public final static String APP_UPDATE_COMMON = "3";//普通

    public static String MD5_MARK = "51sen";


    public static String uniqueIdentification;

    public enum GuestInfoType {
        WeddingBanquet, WeddingBanquet_2, WeddingBanquet_3
    }

    //客资类型
    public static BaseTypeModel WEDDING_BANQUET = new BaseTypeModel("wedding_banquet", "婚宴", 1);
    public static BaseTypeModel BUSINESS_AFFAIRS = new BaseTypeModel("business_affairs", "会务", 2);
    public static BaseTypeModel BABY_BIRTHDAY_GROUP = new BaseTypeModel("baby_birthday_group", "生日宴、团宴、宝宝宴", 3);
    public static List<BaseTypeModel> typeModels;
    //指定类型
    public static BaseTypeModel SPECIFY_DISTRICT = new BaseTypeModel("district", "指定区域", 1);
    public static BaseTypeModel SPECIFY_HOTEL = new BaseTypeModel("hotel", "指定酒店", 2);
    public static List<BaseTypeModel> specifyModels;
    //客资类型
    public static BaseTypeModel EXHIBITION = new BaseTypeModel("exhibition", "布展", 1);
    public static List<BaseTypeModel> buildModels;

    //订单类型
    public static Map<Integer, String> orderTypeMap;
    //搭建类型
    public static Map<Integer, String> buildTypeMap;
    //订单状态
    public static Map<Integer, String> orderStatusMap;

    //跟踪状态
    public static Map<Integer, String> followStatusMap;

    //二销跟踪状态
    public static Map<Integer, String> secondSaleStatusMap;
    //跟踪操作类型
    public static Map<Integer, String> followActionStatusMap;

    public static Map<Integer, String> getOrderStatusMap() {
        if (orderStatusMap == null) {
            orderStatusMap = new HashMap<>();

            orderStatusMap.put(1, "跟踪中");
            orderStatusMap.put(2, "待结算");
            orderStatusMap.put(3, "已结算");
            orderStatusMap.put(4, "已取消");
        }
        return orderStatusMap;
    }

    public static Map<Integer, String> getFollowStatusMap() {
        if (followStatusMap == null) {
            followStatusMap = new HashMap<>();
            followStatusMap.put(1, "待处理");
            followStatusMap.put(2, "待审核");
            followStatusMap.put(3, "待结算");
            followStatusMap.put(4, "已结算");
            followStatusMap.put(5, "已驳回");
            followStatusMap.put(6, "已取消");
        }
        return followStatusMap;
    }

    public static Map<Integer, String> getSecondSaleStatusMap() {
        if (secondSaleStatusMap == null) {
            secondSaleStatusMap = new HashMap<>();
            secondSaleStatusMap.put(1, "待处理");
            secondSaleStatusMap.put(2, "待审核");
            secondSaleStatusMap.put(3, "已完结");
            secondSaleStatusMap.put(5, "已驳回");

        }
        return secondSaleStatusMap;
    }

    public static Map<Integer, String> getFollowActionStatusMap() {
        if (followActionStatusMap == null) {
            followActionStatusMap = new HashMap<>();
            followActionStatusMap.put(1, "信息有效");
            followActionStatusMap.put(2, "信息无效");
            followActionStatusMap.put(3, "确认签单");

        }
        return followActionStatusMap;
    }

    public static Map<Integer, String> getOrderTypeMap() {
        if (orderTypeMap == null) {
            orderTypeMap = new HashMap<>();
            orderTypeMap.put(1, "婚宴");
            orderTypeMap.put(2, "会务");
            orderTypeMap.put(3, "生日宴、团宴、宝宝宴");

        }
        return orderTypeMap;
    }

    public static Map<Integer, String> getBuildTypeMap() {
        if (buildTypeMap == null) {
            buildTypeMap = new HashMap<>();
            buildTypeMap.put(1, "会展");

        }
        return buildTypeMap;
    }

    /**
     * 获取客资信息类型
     */
    public static List<BaseTypeModel> getOrderTypeArray() {

        if (typeModels == null) {
            typeModels = new ArrayList<>();
            typeModels.add(WEDDING_BANQUET);
            typeModels.add(BUSINESS_AFFAIRS);
            typeModels.add(BABY_BIRTHDAY_GROUP);
        }
        return typeModels;
    }

    /**
     * 获取客资信息类型
     */
    public static List<BaseTypeModel> getBuildTypeArray() {

        if (buildModels == null) {
            buildModels = new ArrayList<>();
            buildModels.add(EXHIBITION);
        }
        return buildModels;
    }

    /**
     * 获取区域类型
     */
    public static List<BaseTypeModel> getSpecifyTypeArray() {
        if (specifyModels == null) {
            specifyModels = new ArrayList<>();
            specifyModels.add(SPECIFY_DISTRICT);
            specifyModels.add(SPECIFY_HOTEL);
        }
        return specifyModels;
    }

    public static List<BaseTypeModel> getTypeList(BaseTypeModel[] array) {
        return Arrays.asList(array);
    }

    public static String[] getShowContentArray(BaseTypeModel[] array) {

        String[] items = new String[array.length];

        for (int i = 0; i < array.length; i++) {
            items[i] = array[i].getValue();
        }

        return items;
    }

    public static String[] getShowContentArray(List<BaseTypeModel> list) {

        String[] items = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getValue();
        }

        return items;
    }

    //关联于specifyModels，用于switch和单多选时使用
    //当前和业务捆绑，OPTION_DISTRICT_SELECT即为single单选，OPTION_HOTEL_SELECT为multi多选
    public final static int OPTION_DISTRICT_SELECT = 1;
    public final static int OPTION_HOTEL_SELECT = 2;
    //
    public final static int SELECT_OPTION_REQUEST_CODE = 1;

    //返回成功状态码
    public final static int REQUEST_SUCCESS = 1000;
    public final static int REQUEST_LOGIN_INVALID = 997;

    //支付宝设置页面来源
    public final static int FROM_LOGIN = 0;//login页面
    public final static int FROM_MAIN = 1;//main页面

    //订单类型
    public final static int ORDER_TYPE_WEDDING_BANQUET = 1;
    public final static int ORDER_TYPE_BUSINESS_AFFAIRS = 2;
    public final static int ORDER_TYPE_BABY_BIRTHDAY_GROUP = 3;

    //登陆类型
    public final static String LOGIN_MODEL_ACCOUNT = "4";
    public final static String LOGIN_MODEL_PHONE = "3";
    public final static String LOGIN_MODEL_FIRST_SALE = "11";
    public final static String LOGIN_MODEL_SECOND_SALE = "12";

    //OSS Perfix
    public final static String OSS_REMOTE_URL = "http://sendevimg.oss-cn-zhangjiakou.aliyuncs.com/";
    public final static String OSS_UPLOAD_SUCCESS = "success";
    public final static String OSS_UPLOAD_FAIL = "fail";

    public static final String OSS_ENDPOINT = "http://oss-cn-zhangjiakou.aliyuncs.com";
    public static final String OSS_ACCESS_KEY_ID = "LTAIoOF3QnYG9bZm";
    public static final String OSS_ACCESS_KEY_SECRET = "b2F0dppgffD0JkAfEW2AHOC4fTF2TL";

    public static final String OSS_BUCKET = "sendevimg";
    public static final String OSS_UPLOAD_PREFIX = "upload/user_app/";

    //跟踪操作类型
    public final static int FOLLOW_UP_INFO_EFFECTIVE = 1;
    public final static int FOLLOW_UP_INFO_INVALID = 2;
    public final static int FOLLOW_UP_COMFIRM_SIGN = 3;

    //StartActivity Request Code
    public final static int TO_SUBMIT_CONTRACT_REVIEW = 0;
    public final static int TO_BIND_ACCOUNT_INFO = 1;
    public final static int TO_MODIFY_DATA = 2;

    //压缩图片目录
    public final static String COMPRESS_IMG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sen/pic/compress";

    //验证来源
    public final static int SOURCE_VERIFY_KEZI = 0;
    public final static int SOURCE_VERIFY_BUILD = 1;
    public final static int SOURCE_MODIFY = 2;

    //二销跟踪操作类型
    public final static int SECOND_FOLLOW_UP_MIDDLE = 1;
    public final static int SECOND_FOLLOW_UP_REST = 2;
    public final static int SECOND_FOLLOW_UP_ADDITIONAL = 3;
    public final static int SECOND_FOLLOW_UP_MODIFY = 4;
    public final static int SECOND_FOLLOW_UP_FIRST_SALE = 5;

    //客资详情来源分类
    public final static int KEZI_DETAIL_SOURCE_PROVIDER = 1;
    public final static int KEZI_DETAIL_SOURCE_FOLLOWER = 2;

    //EventBus的ID集合
    public final static int EVENT_KEZI_LIST_REFRESH = 1;//跟踪者跟踪
    public final static int EVENT_FIRST_SALE_LIST_REFRESH = 2;//首销跟踪
    public final static int EVENT_SECOND_SALE_LIST_REFRESH = 3;//二销跟踪
    public final static int EVENT_INIT_MAIN_SLIDE = 4;//登陆刷新
    public final static int EVENT_KEZI_LIST_UPDATE_FRESH = 5;//跟踪者重新提交
    public final static int EVENT_FIRST_SALE_UPDATE_FRESH = 6;//首销重新提交
    public final static int EVENT_SECOND_SALE_UPDATE_FRESH = 7;//二销重新提交
    public final static int EVENT_KEZI_CREATE_LIST_REFRESH = 8;//客资创建
    public final static int EVENT_BUILD_CREATE_LIST_REFRESH = 9;//搭建创建

    //同步类型
    public final static String AUTO_TYPE_SYNCHRONIZE_CLOSE = "1";
    public final static String AUTO_TYPE_SYNCHRONIZE_OPEN = "2";

    /**
     * --------------------------------------------------------------------------------
     */
    public static boolean hadVersionCheck = false;


}