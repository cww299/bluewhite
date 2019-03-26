package com.bluewhite.personnel.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public class ApplicationLeaveServiceImpl extends BaseServiceImpl<ApplicationLeave, Long> implements ApplicationLeaveService {

	@Autowired
	private ApplicationLeaveDao dao;
	@Override
	public ApplicationLeave updateApplicationLeave(ApplicationLeave applicationLeave) {
		ApplicationLeave oldApplicationLeave = dao.findOne(applicationLeave.getId());
		BeanCopyUtils.copyNotEmpty(applicationLeave, oldApplicationLeave, "");
		return dao.save(oldApplicationLeave);
	}
	
	@Override
	public int deleteApplicationLeave(String ids) {
		int count = 0;
		String[] arrIds = ids.split(",");
		for(int i=0 ;i<arrIds.length;i++){
			Long id = Long.getLong(arrIds[i]);
			dao.delete(id);
			count++;
		}
		return count;
	}

}
