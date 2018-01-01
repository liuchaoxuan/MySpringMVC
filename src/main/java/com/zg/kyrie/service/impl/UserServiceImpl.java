/**
 * 
 */
package com.zg.kyrie.service.impl;

import com.zg.kyrie.annotation.Qualifier;
import com.zg.kyrie.annotation.RequestMapping;
import com.zg.kyrie.annotation.Service;
import com.zg.kyrie.dao.UserDao;
import com.zg.kyrie.service.UserService;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:20:00 PM
 * @version 1.0
 */
@Service("userService")
@RequestMapping("/user")
public class UserServiceImpl implements UserService {

	@Qualifier("userDao")
	private UserDao userDao;
	public String insert(String param) {
		// TODO Auto-generated method stub
		return userDao.insert(param);
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.service.UserService#delete(java.lang.String)
	 */
	public String delete(String param) {
		// TODO Auto-generated method stub
		return userDao.delete(param);
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.service.UserService#update(java.lang.String)
	 */
	public String update(String param) {
		// TODO Auto-generated method stub
		return userDao.update(param);
	}

	/* (non-Javadoc)
	 * @see com.zg.kyrie.service.UserService#query(java.lang.String)
	 */
	public String query(String param) {
		// TODO Auto-generated method stub
		return userDao.query(param);
	}

}
