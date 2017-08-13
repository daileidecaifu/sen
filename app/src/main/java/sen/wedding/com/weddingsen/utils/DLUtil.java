package sen.wedding.com.weddingsen.utils;

import android.os.Build;

import java.util.List;


/**
 * Created by lorin on 17/8/6.
 */

public class DLUtil {

    public static <T> boolean  isArrayEffective(List<T> list) {

        if(list!=null&&list.size()>0)
        {
            return true;
        }
        return false;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }
}
