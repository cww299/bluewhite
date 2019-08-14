package com.bluewhite.finance.attendance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
@Service
public class AttendancePayServiceImpl extends BaseServiceImpl<AttendancePay, Long> implements AttendancePayService{
	
	@Autowired
	private AttendancePayDao dao;
	@Autowired
	private BaseDataService service;
	@Autowired
	private UserService userService;
	

	@Override
	public PageResult<AttendancePay> findPages(AttendancePay param, PageParameter page) {
		if(param.getOrgNameId()!=null){
			switch (String.valueOf(param.getOrgNameId())) {
			case Constants.QUALITY_ORGNAME:
				param.setType(1);
				break;
			case Constants.PACK_ORGNAME:
				param.setType(2);
				break;
			case Constants.DEEDLE_ORGNAME:
				param.setType(3);
				break;
			case Constants.MACHINIST_ORGNAME:
				param.setType(4);
				break;
			case Constants.TAILOR_ORGNAME:
				param.setType(5);
				break;
			default:
				break;
			}
		}
		 Page<AttendancePay> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按员工姓名
	        	if(!StringUtils.isEmpty(param.getUserName())){
	        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
	        	}
	        	
	        	//按员工id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
				}
	        	
	        	//按分组id过滤
	        	if (param.getGroupId() != null) {
					predicate.add(cb.equal(root.get("user").get("groupId").as(Long.class),param.getGroupId()));
				}
	        	
	        	//是否错误
	        	if (param.getWarning() != null) {
					predicate.add(cb.equal(root.get("warning").as(Integer.class),param.getWarning()));
				}
	        	
	        	//按工种id过滤
	        	if (param.getKindWorkId() != null) {
					predicate.add(cb.equal(root.get("user").get("group").get("kindWorkId").as(Long.class),param.getGroupId()));
				}
	        	
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	            //按时间过滤
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
					predicate.add(cb.between(root.get("allotTime").as(Date.class),
							param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, SalesUtils.getQueryNoPageParameter());
			  if(param.getSign()!=null){
			 	 this.countMaxPay(pages.getContent(),param);
			  }
			  PageResultStat<AttendancePay> result = new PageResultStat<>(pages,page);
			  result.setAutoStateField("workTime", "payNumber");
			  result.count();
	        return result;
	    }
	
	/**
	 * 计算同种工资,(指同工种)
	 * @param content
	 * @param param
	 */
	private List<AttendancePay> countMaxPay(List<AttendancePay> content, AttendancePay param) {
		PageParameter page = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		param.setOrderTimeBegin(DatesUtil.getFristDayOfLastMonth(new Date()));
		param.setOrderTimeEnd(DatesUtil.getLastDayOLastMonth(new Date()));
		List<BaseData> baseDatas = service.getBaseDataListByType("kindWork");
		for(BaseData base : baseDatas){
			param.setKindWorkId(base.getId());
			param.setSign(null);
			List<AttendancePay> attendancePay = this.findPages(param, page).getRows();
			OptionalDouble maxPay = attendancePay.stream().mapToDouble(AttendancePay::getWorkPrice).max();
			Double sumMaxPay = null;
			if(maxPay.isPresent()){
				sumMaxPay = maxPay.getAsDouble();
			}else{
				sumMaxPay = 0.0;
			}
			for(AttendancePay attendance : content){
				attendance.setMaxPay(sumMaxPay);
				attendance.setDisparity(sumMaxPay-attendance.getWorkPrice());
			}
		}
		return content;
	}

	@Override
	@Transactional
	public int addAttendancePay(AttendancePay attendancePay) {
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 新增考勤工资，一键增加考勤
		for (int i = 0; i < attendancePay.getUsersId().length; i++) {
			Long userid = Long.parseLong(attendancePay.getUsersId()[i]);
			AttendancePay attendance = new AttendancePay();
			User user = userService.findOne(userid);
			if(user.getPrice()==null){
				throw new ServiceException(user.getUserName() +"小时单价为空，无法添加考勤");
			}
			attendance.setUserId(userid);
			attendance.setGroupId(user.getGroupId());
			attendance.setAllotTime(attendancePay.getAllotTime());
			attendance.setOrderTimeBegin(DatesUtil.getfristDayOftime(attendance.getAllotTime()));
			attendance.setOrderTimeEnd(DatesUtil.getLastDayOftime(attendance.getAllotTime()));
			attendance.setType(attendancePay.getType());
			if (findAttendancePay(attendance).size() > 0) {
				throw new ServiceException(user.getUserName() + sdf.format(attendance.getAllotTime())+"日已存在考情记录，无需再次添加，请重新选择");
			}
			if(attendancePay.getTurnWorkTimes()[i] !=0 || attendancePay.getOvertimes()[i]!=0){
				// 出勤时长
				attendance.setTurnWorkTime(attendancePay.getTurnWorkTimes()[i]);
				// 加班时长
				attendance.setOverTime(attendancePay.getOvertimes()[i]);
				// 工作时长
				attendance.setWorkTime(NumUtils.sum(attendancePay.getTurnWorkTimes()[i], attendancePay.getOvertimes()[i]));
				attendance.setWorkPrice(user.getPrice());
				attendance.setUserName(user.getUserName());
				attendance.setPayNumber(NumUtils.round(attendance.getWorkPrice()*attendance.getWorkTime(),2));
				dao.save(attendance);
			}
			count++;
		}
		return count; 
	}

	@Override
	public AttendancePay findByUserIdAndAllotTime(AttendancePay attendancePay) {
		return dao.findByUserIdAndAllotTimeLike(attendancePay.getUserId(),attendancePay.getAllotTime());
	}

	@Override
	public List<AttendancePay> findAttendancePay(AttendancePay attendancePay) {
		return dao.findByUserIdAndTypeAndAllotTimeBetween(attendancePay.getUserId(),attendancePay.getType(),attendancePay.getOrderTimeBegin(),attendancePay.getOrderTimeEnd());
	}

	@Override
	public List<AttendancePay> findAttendancePayNoId(AttendancePay attendancePay) {
		return dao.findByTypeAndAllotTimeBetween(attendancePay.getType(),attendancePay.getOrderTimeBegin(),attendancePay.getOrderTimeEnd());
	}

	@Override
	public int updateAllAttendance(AttendancePay attendancePay) {
		int count = 0 ;
		// 修改
		for (int i = 0; i < attendancePay.getUsersId().length; i++) {
			Long userid = Long.parseLong(attendancePay.getUsersId()[i]);
			User user = userService.findOne(userid);
			user.setPrice(attendancePay.getWorkPrice());
			userService.save(user);
			attendancePay.setUserId(userid);
			attendancePay.setOrderTimeBegin(
					DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(attendancePay.getAllotTime())));
			attendancePay.setOrderTimeEnd(
					DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth(attendancePay.getAllotTime())));
			// 获取所有的工资流水
			List<AttendancePay> attendancePayList = findAttendancePay(attendancePay);
			for (AttendancePay pay : attendancePayList) {
				pay.setWorkPrice(attendancePay.getWorkPrice());
				pay.setPayNumber(NumUtils.round(pay.getWorkPrice()*pay.getWorkTime(),2));
				dao.save(pay);
			}
			count++;
		}
		return count;
	}

}

