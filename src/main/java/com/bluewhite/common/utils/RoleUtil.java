package com.bluewhite.common.utils;

import java.util.Set;

import com.bluewhite.common.Constants;

/**
 * 权限工具表
 * 
 * @author zhangliang
 *
 */
public class RoleUtil {

	/**
	 * 根据权限获取仓库种类
	 * @param role
	 * @return
	 */
	public static Long getWarehouseTypeDelivery(Set<String> role) {
		Integer id = null;
		if (role.contains(Constants.ONLINEWAREHOUSE)) {
			id = 230;
		}
		if (role.contains(Constants.FINISHEDWAREHOUSE)) {
			id = 274;
		}
		if (role.contains(Constants.SCENEWAREHOUSE)) {
			id = 273;
		}
		if (role.contains(Constants.EIGHTFINISHEDWAREHOUSE)) {
			id = 275;
		}
		return Long.valueOf(id);
	}

}
