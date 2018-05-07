package com.bluewhite.common.entity;

/**
 * 系统配置加载器
 * @author longxin
 */
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ConfigManager {
	
	private static Logger logger = LoggerFactory.getLogger(ConfigManager.class);
	
	private static Properties properties = new Properties();
	
	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("resources.properties");
		} catch (IOException e) {
			logger.error("加载系统资源配置文件失败！");
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	public static void main(String[] args){
		System.out.println(properties.get("file.upload.root"));
		System.out.println(properties.get("file.upload.video"));
		System.out.println(properties.get("file.upload.doc"));
		System.out.println(properties.get("file.upload.image"));
		System.out.println(properties.get("file.upload.audio"));
		System.out.println(properties.get("file.upload.audio2"));
	}
}
