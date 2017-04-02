package sen.wedding.com.weddingsen.http.request;

import java.util.Map;

import sen.wedding.com.weddingsen.http.base.Request;

public interface HttpRequest extends Request{
	
	/**
	 * Get http request method.
	 * @return
	 */
	String getMethod();
	
	/**
	 * Get http request headers.
	 * @return
	 */
	Map<String, String> getHeaders();
	
	/**
	 * Get request timeout in millisecond.
	 * @return
	 */
	int getTimeout();

	/**
	 * Get Request Body
	 * @return
	 */
	RequestBody getBody();

	/**
	 * Format request params
	 */
	void formatRequestParams();
}
