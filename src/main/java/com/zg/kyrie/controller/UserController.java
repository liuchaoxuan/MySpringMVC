/**
 * 
 */
package com.zg.kyrie.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zg.kyrie.annotation.Controller;
import com.zg.kyrie.annotation.Qualifier;
import com.zg.kyrie.annotation.RequestMapping;
import com.zg.kyrie.service.UserService;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 9:43:20 PM
 * @version 1.0
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController{
	@Qualifier("userService")
	private UserService userService;
	
	@RequestMapping("/insert")
	public void insert(HttpServletRequest request, HttpServletResponse response, String param) {
		String result = userService.insert(param);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, String param) {
		String result = userService.delete(param);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/update")
	public void update(HttpServletRequest request, HttpServletResponse response, String param) {
		String result = userService.update(param);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response, String param) {
		String result = userService.query(param);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
