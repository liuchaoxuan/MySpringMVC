/**
 * 
 */
package com.zg.kyrie.dao.impl;

import com.zg.kyrie.annotation.Repository;
import com.zg.kyrie.dao.UserDao;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:25:40 PM
 * @version 1.0
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	/* (non-Javadoc)
	 * @see com.zg.kyrie.dao.UserDao#insert(java.lang.String)
	 */
	public String insert(String param) {
		// TODO Auto-generated method stub
		return "insert success";
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.dao.UserDao#delete(java.lang.String)
	 */
	public String delete(String param) {
		// TODO Auto-generated method stub
		return "delete success";
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.dao.UserDao#update(java.lang.String)
	 */
	public String update(String param) {
		// TODO Auto-generated method stub
		return "update success";
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.dao.UserDao#query(java.lang.String)
	 */
	public String query(String param) {
		// TODO Auto-generated method stub
		return "query success";
	}

}
