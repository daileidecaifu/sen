package sen.wedding.com.weddingsen.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorin on 17/4/6.
 */

public class StringUtil {

    public static String createHtml(String value, String color) {
        return "<font color=\"" + color + "\" >" + value + "</font>";
    }

    public static String selectNumber(String str) {
        String strO = str.trim();
        String strN = "";
        if (strO != null && !"".equals(strO)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    strN += str.charAt(i);
                }
            }

        }
        return strN;
    }

    public static boolean isPhoneFormat(String phone) {
        boolean isPhoneStart = phone.startsWith("1");

        if (!isPhoneStart) {
            return false;
        }

        boolean isPhoneLength = phone.length() == 11;

        if (!isPhoneLength) {
            return false;
        }

        return true;
    }

    public static boolean isURLFormat(String url) {
        boolean isPhoneStart = url.startsWith("http");

        if (!isPhoneStart) {
            return false;
        }

        boolean isPhoneLength = url.length() > 0;

        if (!isPhoneLength) {
            return false;
        }

        return true;
    }

    public static ArrayList<String> filterLocalImgArray(List<String> oArray) {
        ArrayList<String> results = new ArrayList<>();

        for (String str : oArray) {
            if (!str.startsWith("http")) {
                results.add(str);
            }
        }
        return results;
    }

    public static ArrayList<String> filterUrlImgArray(List<String> oArray) {
        ArrayList<String> results = new ArrayList<>();

        for (String str : oArray) {
            if (str.startsWith("http")) {
                results.add(str);
            }
        }
        return results;
    }

    public static int filterUrlImgSize(List<String> oArray) {
        List<String> results = new ArrayList<>();

        for (String str : oArray) {
            if (str.startsWith("http")) {
                results.add(str);
            }
        }
        return results.size();
    }
}
