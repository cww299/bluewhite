package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

/**
 * 关于设定人员考勤汇总时，所需要的初始化数据
 * @author zhangliang
 *
 */
public class AttendanceInit {
	
	
//	/**
//	 * 员工id
//	 */
//	@Column(name = "user_id")
//	private Long userId;
//	
//	/**
//	 * 一对一的用户
//	 */
//    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL) 
//    @JoinColumn(name="user_id",referencedColumnName="id",nullable=true)
//    private User user;
//    
//    
//    
//	/**
//	 * 约定休息方式（1.无到岗要求，2.周休一天，3.月休两天，其他周日算加班）
//	 */
//	@Column(name = "rest_type")
//	private Integer restType;
//    
//	/**
//	 * 约定休息日
//	 */
//	@Column(name = "rest_day")
//	private Date restDay;
//	
//	/**
//	 * 出勤时长
//	 */
//	@Column(name = "turnWorkTime")
//	private Double turnWorkTime;
	
    
    

}
