package sen.wedding.com.weddingsen.utils;

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


}
