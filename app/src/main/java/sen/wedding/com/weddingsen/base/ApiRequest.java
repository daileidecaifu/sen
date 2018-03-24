package sen.wedding.com.weddingsen.base;

import java.util.Iterator;
import java.util.Map;

import sen.wedding.com.weddingsen.http.base.CacheStrategy;
import sen.wedding.com.weddingsen.http.request.BasicHttpRequest;
import sen.wedding.com.weddingsen.http.request.HttpMethod;
import sen.wedding.com.weddingsen.http.request.RequestBody;
import sen.wedding.com.weddingsen.utils.SystemUtil;

/**
 * Created by lorin on 17/4/2.
 */

public class ApiRequest extends BasicHttpRequest {

//    Map<String, String> params;
//    Object data;

    public ApiRequest(String url) {
        this(url, HttpMethod.POST, DEFAULT_TIMEOUT);
    }

    public ApiRequest(String url, String method) {
        this(url, method, DEFAULT_TIMEOUT);
    }


    public ApiRequest(String url, String method, int timeout) {
        super(url, method, timeout);
    }

    public Map<String, String> getParams() {
        return ((RequestBody.FormBody) getBody()).getParams();
    }


    public void setParams(Map<String, String> params) {
//        this.params = params;
        RequestBody.FormBody formBody = new RequestBody.FormBody(params);
        setBody(formBody);
    }

//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }

    public void formatRequestParams() {

        addHeader("Accept", "application/json");
        String UID = SystemUtil.getUniqueIdentification();
        addHeader("APP-DEVICE", UID);//设备唯一识别码
        long currentTime = System.currentTimeMillis() / 1000;
        addHeader("APP-TIME", currentTime + "");
        addHeader("APP-SIGN", SystemUtil.getMD5(Conts.MD5_MARK + UID + currentTime));

//        if (params != null) {
//            setBody(new RequestBody.JsonBody(params));
//        } else {
//            setBody(new RequestBody.JsonBody(data));
//        }
    }

}
