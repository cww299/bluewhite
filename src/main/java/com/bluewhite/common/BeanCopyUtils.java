package com.bluewhite.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 为简化开发，特提供bean拷贝类，将a bean中不为null的元素拷贝到b中。
 * <br/>可以增加过滤字段，为目标bean数据安全，优先不过滤原则。
 * @author ZhouYong
 *
 */
public class BeanCopyUtils {

	/**
	 * 格式化非空属性复制给target
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyNotEmpty(Object source, Object target, String...ignoreProperties){
		BeanWrapper src = new BeanWrapperImpl(source);
		List<String> ignoreList = null;
		if(ignoreProperties != null){
			ignoreList = Arrays.asList(ignoreProperties);
	    }
		List<String> ignoreListRe = ignoreList;
	    String[] arr = Arrays.stream(
	    		 src
	    	    .getPropertyDescriptors())
	    		.filter(pd -> {
	    			if(ignoreListRe != null && ignoreListRe.contains(pd.getName())) return true;
	    			Object value = src.getPropertyValue(pd.getName());
	    			return value == null || (value instanceof Collection && ((Collection<?>)value).size() == 0);
	    		 })
	    		.flatMap(pd -> Arrays.asList(pd.getName()).stream())
	    		.collect(Collectors.toList())
	    		.toArray(new String[]{});
	    
	    BeanUtils.copyProperties(source, target, arr);
	}
	
}
