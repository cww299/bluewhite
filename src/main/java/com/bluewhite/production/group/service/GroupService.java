package com.bluewhite.production.group.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.group.entity.Group;
@Service
public interface GroupService extends BaseCRUDService<Group,Long>{
	/**
	 * 根据类型查找分组
	 * @param type
	 * @return
	 */
	List<Group> findByType(Integer type);
	/**
	 * 分页插叙
	 * @param group
	 * @return
	 */
	List<Group> findList(Group group);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteGroup(String ids);

}
