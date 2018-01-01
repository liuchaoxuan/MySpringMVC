/**
 * 
 */
package com.zg.kyrie.utills;

import java.io.File;
import java.net.URL;

/**
 * @author kyrie liu
 * @date Dec 28, 2017
 * @time 11:05:27 PM
 * @version 1.0
 */
public class TestUtill {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url = TestUtill.class.getClassLoader().getResource("com/zg/kyrie");
		System.out.println(url);
		String path = url.getPath();
		System.out.println(path);
		File file = new File(path);
		System.out.println(file);
		File[] childFiles = file.listFiles();
		for (File childFile:childFiles) {
			if (childFile.isDirectory()) {
				System.out.println(childFile);
				continue;
			} else {
				System.out.println(childFile);
			}
			
		}
	}

}
