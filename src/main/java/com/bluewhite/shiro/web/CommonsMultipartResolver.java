package com.bluewhite.shiro.web;

import javax.servlet.http.HttpServletRequest;

public class CommonsMultipartResolver extends org.springframework.web.multipart.commons.CommonsMultipartResolver {
	private String[] includeUrlArray;

	public void setIncludeUrlArray(String[] includeUrlArray) {
		this.includeUrlArray = includeUrlArray;
	}

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		if (includeUrlArray != null) {
			for (String url : includeUrlArray) {
				if (request.getRequestURI().contains(url)) {
					return super.isMultipart(request);
				}
			}
		}
		return false;
	}
}
