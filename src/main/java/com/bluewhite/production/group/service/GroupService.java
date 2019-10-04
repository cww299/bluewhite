package com.bluewhite.production.group.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
@Service
public interface GroupService extends BaseCRUDService<Group,Long>{
	/**
	 * 根据类型查找分组
	 * @param type
	 * @return
	 */
	List<Group> findByType(Integer type);
	/**
	 * 按条件查询
	 * @param group
	 * @return
	 */
	List<Group> findList(Group group);
	
	/**
	 * 汇总外调人员绩效
	 * @param temporarily
	 */
	 List<Map<String, Object>> sumTemporarily(Temporarily temporarily);
	 
	 /**
	 * 按条件查询
	 * @param temporarily
	 * @return
	 */
	List<Temporarily> findTemporarilyList(Temporarily temporarily);
	

}
