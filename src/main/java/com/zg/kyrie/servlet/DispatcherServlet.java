package com.zg.kyrie.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zg.kyrie.annotation.Controller;
import com.zg.kyrie.annotation.Qualifier;
import com.zg.kyrie.annotation.Repository;
import com.zg.kyrie.annotation.RequestMapping;
import com.zg.kyrie.annotation.Service;

/**
 * Servlet implementation class DispatcherServlet
 * 整体思路：
 * 1. 根据给定的基础包，扫描出包下所有类的全路径（而不是绝对路径），以便反射创建实例用；
 * 2. 循环遍历类，并判断时候是否有controller/service/repository注解，如果有，获取注解的value，也就是实例名，并实例化，然后放入一个instanceMap中；
 * 3. 循环遍历map中的实例，如果属性中IOC的注解，根据注解值，从map中取出相应的实例，初始化属性；
 * 4. 遍历map实例，如果是带有controller的实例，根据类和方法中RequestMapping的值组成url，与方法名一起组成urlMethodMap;method和instance组成methodInstanceMap；
 * 5. doGet/doPost中获取请求url，并从urlMethodMap中找到相关处理方法和实例，处理客户端请求。
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<String> classNames = new ArrayList<String>();
	HashMap<String, Object> instanceMap = new HashMap<String, Object>();
	HashMap<String,Method> urlMethodMap = new HashMap<String,Method>();
	HashMap<Method,Object> methodClassMap = new HashMap<Method,Object>();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DispatcherServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//1. scan package;
		scanPackage("com.zg.kyrie");
		if (classNames.size() > 0) {
			for (String name : classNames) {
				System.out.println(name);
			}
		}
		// 2. instance controller, service and repository class;
		if (classNames.size() > 0) {
			try {
				instance(classNames);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 3. IOC，qualifier

		ioc(instanceMap);

		// 4. handle url mapping
		handleUrlMap();
	}

	/**
	 * 
	 */
	private void handleUrlMap() {
		// TODO Auto-generated method stub
		if (instanceMap.size() > 0) {
			for (Object object : instanceMap.values()) {
				if (object.getClass().isAnnotationPresent(Controller.class)) {
					RequestMapping urlMapping = object.getClass().getAnnotation(RequestMapping.class);
					String baseUrl = urlMapping.value();
					Method[] methods = object.getClass().getDeclaredMethods();
					for (Method method : methods) {
						if (method.isAnnotationPresent(RequestMapping.class)) {
							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							String url = baseUrl + requestMapping.value();
							urlMethodMap.put(url, method);
							methodClassMap.put(method, object);
						}
					}
				}
			}
		}
	}

	/**
	 * @param instanceMap2
	 */
	private void ioc(HashMap<String, Object> instanceMap2) {
		// TODO Auto-generated method stub
		if (instanceMap.size() > 0) {
			for (Object object : instanceMap.values()) {
				Field[] fields = object.getClass().getDeclaredFields();
				for (Field field : fields) {
					Qualifier qualifier = field.getAnnotation(Qualifier.class);
					if (qualifier != null) {
						String value = qualifier.value();
						Object instance = instanceMap.get(value);
						System.out.println(instance.toString());
						field.setAccessible(true);
						try {
							field.set(object,instance);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * @param className
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void instance(List<String> classNames) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		for (String className : classNames) {
			Class c = Class.forName(className);
			Controller controller = (Controller) c.getAnnotation(Controller.class);
			if (controller != null) {
				String instanceName = controller.value();
				instanceMap.put(instanceName, c.newInstance());
				System.out.println(instanceName);
				continue;
			}
			Service service = Class.forName(className).getAnnotation(Service.class);
			if (service != null) {
				String instanceName = service.value();
				instanceMap.put(instanceName, c.newInstance());
				System.out.println(instanceName);
				continue;
			}
			Repository repository = Class.forName(className).getAnnotation(Repository.class);
			if (repository != null) {
				String instanceName = repository.value();
				instanceMap.put(instanceName, c.newInstance());
				System.out.println(instanceName);
			}
		}
	}
	/**
	 * @param string
	 */
	private void scanPackage(String basePath) {
		String realPath = basePath.replaceAll("\\.", "/");
		System.out.println(realPath);
		URL url = this.getClass().getClassLoader().getResource(realPath);
		System.out.println(url);
		// 扫描当前路径下的类，把所有的类名放入一个list中，以便后边反射创建对象用。
		File basePackageFile = new File(url.getPath());
		File[] childFiles = basePackageFile.listFiles();
		for (File file : childFiles) {
			if (file.isDirectory()) {
				scanPackage(basePath + "." + file.getName());
			} else {
				classNames.add(basePath + "." + file.getName().split("\\.")[0]);
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()).append("\r\n");
		String param = request.getParameter("param1");
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String requestUrl = uri.replaceAll(contextPath, "");
		if (urlMethodMap.containsKey(requestUrl)) {
			Method method = urlMethodMap.get(requestUrl);
			Object instance = methodClassMap.get(method);
			try {
				method.invoke(instance, request,response,param);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
