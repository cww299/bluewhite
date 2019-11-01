package com.bluewhite.personnel.roomboard.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.coefficient;

public interface coeffcientDao extends BaseRepository<coefficient, Long>{
	
	/*根据 部门 职位查询 */
	public coefficient findByOrgNameIdAndPositionId(Long positionId,Long orgNameId);
}
