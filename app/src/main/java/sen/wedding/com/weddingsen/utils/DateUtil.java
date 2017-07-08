package sen.wedding.com.weddingsen.utils;



import android.content.res.Resources;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final String FORMAT_COMMON_Y_M_D_H_M_S = "yyyy-MM-dd hh:mm:ss";
    public static final String FORMAT_COMMON_Y_M_D = "yyyy-MM-dd";


    public static Date convertStringToDate(String date, String formatStr) {
        if (TextUtils.isEmpty(date)) {
            return new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        Date resultDate = null;
        try {
            resultDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    public static String convertDateToString(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);

        return simpleDateFormat.format(date);
    }

    public static String formatValue(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return value + "";
        }
    }


}
