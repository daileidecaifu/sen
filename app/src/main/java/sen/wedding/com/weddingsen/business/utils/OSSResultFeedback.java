package sen.wedding.com.weddingsen.business.utils;

import sen.wedding.com.weddingsen.business.model.OSSResultModel;
import sen.wedding.com.weddingsen.business.model.OSSUploadModel;

/**
 * Created by lorin on 17/6/12.
 */

public interface OSSResultFeedback {


    /**
     * 实现此接口,能拿到服务器的响应结果
     * @return
     */

    public void onComplete(OSSResultModel result);


}
