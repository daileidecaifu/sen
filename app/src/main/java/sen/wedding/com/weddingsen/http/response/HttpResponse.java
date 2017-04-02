package sen.wedding.com.weddingsen.http.response;

import java.util.HashMap;
import java.util.Map;

import sen.wedding.com.weddingsen.http.base.Response;


public interface HttpResponse extends Response{
    
	/**
	 * HTTP status code
	 * @return
	 */
    int getStatusCode();

    /**
     * Get HTTP response headers
     * @return
     */
    Map<String, String> getHeaders();


}
