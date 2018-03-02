/**   
* @Title: asyncResult.java 
* @Package com.fixture.base 
* @Description: TODO
* @author wyh  
* @date 2017年8月24日 上午11:37:18 
* @version V1.0   
*/
package com.fixture.base;

import java.util.Map;

/** 
* @ClassName: NoticResult 
* @Description: ajax返回实体  自用
* @author wyh
* @date 2017年8月24日 上午11:37:18 
*  
*/
public class NoticResult {
	private String status;
	private String msg;
	private Map<String, Object> data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
