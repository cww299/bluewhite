package com.bluewhite.common.utils;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

public class UnUtil {

	
	/**
	 * 校验是否手机端
	 * @param request
	 * @return
	 */
	public static boolean isFromMobile(HttpServletRequest request) {
		//1. 获得请求UA
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		UserAgent ua = UserAgentUtil.parse(userAgent);
		return ua.isMobile(); 
	}
}
