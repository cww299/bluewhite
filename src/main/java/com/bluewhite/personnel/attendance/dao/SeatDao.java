package com.bluewhite.personnel.attendance.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Seat;

public interface SeatDao extends BaseRepository<Seat, Long>{
	/*
	 * 根据岗位ID 年龄范围 手速去查询奖励
	 */
	public List<Seat> findByPositionIdAndAgeAndSpeed(Long positionId,Integer age,Integer speed);

}
