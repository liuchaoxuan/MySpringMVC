/**
 * 
 */
package com.zg.kyrie.dao;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:24:49 PM
 * @version 1.0
 */
public interface UserDao {
	
	String insert(String param);

	String delete(String param);

	String update(String param);

	String query(String param);
}
