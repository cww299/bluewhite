package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Seat;

public interface SeatService  extends BaseCRUDService<Seat,Long>{
	
	/**
	 * 查看 已工招工的奖励数据
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Seat> findPage(Seat seat, PageParameter page);
	
	/**
	 * 新增
	 * @param onlineOrder
	 */
	public Seat addSeat(Seat seat);
	
}
