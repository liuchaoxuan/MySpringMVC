/**
 * 
 */
package com.zg.kyrie.service;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:19:28 PM
 * @version 1.0
 */
public interface UserService {
	/**
	 * do some operations
	 * @param param
	 * @return
	 */
	
	String insert(String param);
	
	String delete(String param);
	
	String update(String param);
	
	String query(String param);
}
