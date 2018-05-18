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

}
