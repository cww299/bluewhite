package com.bluewhite.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;

/**
 * 权限工具表
 * 
 * @author zhangliang
 *
 */
@Component
public class RoleUtil {
	
	@Autowired
	private BaseDataService baseDataService;

	private static RoleUtil roleUtil;
	
	@PostConstruct
	public void init() {
		roleUtil = this;
		roleUtil.baseDataService = this.baseDataService; // 初使化时将已静态化的Service实例化
	}

	/**
	 * 根据权限获取仓库种类
	 * @param role
	 * @return
	 */
	public static Long getWarehouseTypeDelivery(Set<String> role) {
		Integer id = null;
		if(role==null && role.size()==0){
			throw new ServiceException("无权限，无法操作");
		}
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
		if (role.contains(Constants.COTWAREHOUSE)) {
			id = 379;
		}
		return id == null ? null : Long.valueOf(id);
	}
	
	
	
	/**
	 * 根据仓库类型获取仓库种类的所有id
	 * @param role
	 * @return
	 */
	public static String getWarehouseTypeIds(Integer warehouse) {
		List<BaseData> baseDataList = new ArrayList<>();
		if(warehouse==1){
			baseDataList = RoleUtil.roleUtil.baseDataService.getBaseDataChildrenById((long)157);
		}
		if(warehouse==2){
			baseDataList =  RoleUtil.roleUtil.baseDataService.getBaseDataChildrenById((long)160);
		}
		return baseDataList.stream().map(baseData->String.valueOf(baseData.getId())).collect(Collectors.joining(","));
	}
	
	
}
