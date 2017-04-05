package sen.wedding.com.weddingsen.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import sen.wedding.com.weddingsen.utils.model.BaseTypeModel;

/**
 * Created by lorin on 17/3/24.
 */

public class Conts {

    public enum GuestInfoType {
        WeddingBanquet_1, WeddingBanquet_2, WeddingBanquet_3
    }

    //客资类型
    public static BaseTypeModel WEDDING_BANQUET_1 = new BaseTypeModel("WeddingBanquet_1", "婚宴");
    public static BaseTypeModel WEDDING_BANQUET_2 = new BaseTypeModel("WeddingBanquet_2", "会务");
    public static BaseTypeModel WEDDING_BANQUET_3 = new BaseTypeModel("WeddingBanquet_3", "生日宴、团宴、宝宝宴");
    public static List<BaseTypeModel> typeModels;
    //指定类型
    public static BaseTypeModel SPECIFY_1 = new BaseTypeModel("specify_1", "指定区域");
    public static BaseTypeModel SPECIFY_2 = new BaseTypeModel("specify_2", "指定酒店");
    public static List<BaseTypeModel> districtModels;

//    //酒店类型
//    public static BaseTypeModel HOTEL_1 = new BaseTypeModel("hotel_1", "酒店1");
//    public static BaseTypeModel HOTEL_2 = new BaseTypeModel("hotel_2", "酒店2");
//    public static BaseTypeModel HOTEL_3 = new BaseTypeModel("hotel_3", "酒店3");
//    public static List<BaseTypeModel> hotelModels;

    /**
     * 获取客资信息类型
     */
    public static List<BaseTypeModel> getGuestInfoArray() {

        if (typeModels == null) {
            typeModels = new ArrayList<>();
            typeModels.add(WEDDING_BANQUET_1);
            typeModels.add(WEDDING_BANQUET_2);
            typeModels.add(WEDDING_BANQUET_3);
        }
        return typeModels;
    }

    /**
     * 获取区域类型
     */
    public static List<BaseTypeModel> getSpecifyTypeArray() {
        if (districtModels == null) {
            districtModels = new ArrayList<>();
            districtModels.add(SPECIFY_1);
            districtModels.add(SPECIFY_2);
        }
        return districtModels;
    }

    /**
     * 获取酒店类型
     */
//    public static List<BaseTypeModel> getHotelInfoArray() {
//        if (hotelModels == null) {
//            hotelModels = new ArrayList<>();
//            hotelModels.add(HOTEL_1);
//            hotelModels.add(HOTEL_2);
//            hotelModels.add(HOTEL_3);
//        }
//        return hotelModels;
//    }

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

    //单选多选
    public final static int OPTION_SINGLE_SELECT = 0;
    public final static int OPTION_MULTI_SELECT = 1;
    //
    public final static int SELECT_OPTION_REQUEST_CODE = 1;

}