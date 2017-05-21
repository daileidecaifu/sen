package sen.wedding.com.weddingsen.business.utils;


import sen.wedding.com.weddingsen.business.model.OSSUploadModel;

/**
 * 用于返回响应结果的回调接口
 * 
 * @author Lorin
 * @version V1.0
 */
public interface OSSUploadResult {

	/**
	 * 实现此接口,能拿到服务器的响应结果
	 * @return
	 */
	
	public void onComplete(OSSUploadModel result);

}
