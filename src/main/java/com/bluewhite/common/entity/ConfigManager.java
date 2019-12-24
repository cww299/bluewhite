package com.bluewhite.common.entity;

/**
 * 系统配置加载器
 * @author zhangliang
 */
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.bluewhite.common.Log;
import com.bluewhite.common.MyExceptionHandlerExceptionResolver;

public class ConfigManager {
	
	private static final Log log = Log.getLog(MyExceptionHandlerExceptionResolver.class);
	
	private static Properties properties = new Properties();
	
	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("resources.properties");
		} catch (IOException e) {
			log.error("加载系统资源配置文件失败！");
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
}
