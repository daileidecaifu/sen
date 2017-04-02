package sen.wedding.com.weddingsen.http.base;

/**
 * Response contains result and error info.
 * 
 * @author Chensiqi
 * 
 */
public interface Response {

	/**
	 * Get byte array as result if request is success.
	 * @return
	 */
    byte[] getResult();
	
    /**
	 * Get byte array as error info if request is failed.
	 * @return
	 */
    byte[] getError();
}
