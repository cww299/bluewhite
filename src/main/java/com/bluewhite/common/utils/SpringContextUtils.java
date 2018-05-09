package com.bluewhite.common.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 考虑到有些类或工具bean不受spring控制
 * 提供手动获取bean实例的方法
 * @author TSOSilence
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SpringContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		applicationContext = ac;
	}

    /**
     * 获取applicationContext对象
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
     
    /**
     * 根据bean的id来查找对象
     * @param id beanid
     * @return object
     */
    public static Object getBeanById(String id){
        return applicationContext.getBean(id);
    }
     
    /**
     * 根据bean的class来查找对象
     * @param c class对象
     * @return obect
     */
    
	public static Object getBeanByClass(Class c){
        return applicationContext.getBean(c);
    }
     
    /**
     * 根据bean的class来查找所有的对象(包括子类)
     * @param c class对象
     * @return map
     */
	public static Map getBeansByClass(Class c){
        return applicationContext.getBeansOfType(c);
    }
}
