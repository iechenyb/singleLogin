package com.kiiik.sso;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {
	private static Properties p = null;
	static Log log = LogFactory.getLog(PropertyUtil.class);

	public synchronized static void init(String name) throws Exception {
		InputStream inputstream = null;
		try {
			if (p == null) {
				p = new Properties();
				String filePath = SSOContants.WEBPATH + "WEB-INF" + File.separator
						+ "classes" + File.separator + name + ".properties";
				log.info("初始化Memcache属性文件:" + filePath);
				inputstream = new FileInputStream(filePath);
				p.load(inputstream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputstream != null) {
				inputstream.close();
				inputstream = null;
			}
		}
	}

	public static String get(String key) {
		String result = "";
		try {
			init("Memcache");
			result = (String) p.get(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}