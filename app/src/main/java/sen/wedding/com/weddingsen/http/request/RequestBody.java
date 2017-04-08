package sen.wedding.com.weddingsen.http.request;

import android.graphics.Bitmap;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import sen.wedding.com.weddingsen.utils.GsonConverter;

/**
 * Created by sunyun on 16/4/22.
 */
public abstract class RequestBody {

    private static final String CONTENT_TYPE_URL_ENCODED_DESCRIPTION = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final String CONTENT_TYPE_JSON_DESCRIPTION = "application/json; charset=UTF-8";
    private static final String CONTENT_TYPE_FILE_DESCRIPTION = "multipart/form-data; boundary=";

    public abstract String contentType();

    public abstract void writeTo(OutputStream out) throws IOException;

    public static RequestBody create(final String contentType, final String content) {
        return new RequestBody() {
            @Override public String contentType() {
                return contentType;
            }

            @Override public void writeTo(OutputStream out) throws IOException{
                out.write(content.getBytes("UTF-8"));
            }
        };
    }

    public static final class FormBody extends RequestBody {

        private Map<String, String> params = new HashMap<>();

        public FormBody(Map<String, String> params) {
            if(params != null) {
                this.params = params;
            }
        }

        public Map<String, String> getParams() {
            return params;
        }

        @Override
        public String contentType() {
            return CONTENT_TYPE_URL_ENCODED_DESCRIPTION;
        }

        public String encodeParams() throws IOException {
            StringBuilder encodedParams = new StringBuilder();
            Iterator uee = params.entrySet().iterator();
            while (uee.hasNext()) {
                Map.Entry entry = (Map.Entry) uee.next();
                encodedParams.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
                encodedParams.append('&');
            }

            return encodedParams.toString();
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(encodeParams().getBytes("UTF-8"));
        }
    }


    public static final class JsonBody extends RequestBody {

        private Object data;

        public JsonBody(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public String getJson() {
            return GsonConverter.toJson(data);
        }

        @Override
        public String contentType() {
            return CONTENT_TYPE_JSON_DESCRIPTION;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            if(data != null) {
                out.write(getJson().getBytes("UTF-8"));
            }
        }
    }


}
