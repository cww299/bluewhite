package com.bluewhite.finance.attendance.service;

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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
@Service
public class AttendancePayServiceImpl extends BaseServiceImpl<AttendancePay, Long> implements AttendancePayService{
	
	@Autowired
	private AttendancePayDao dao;
	@Autowired
	BaseDataService service;

	@Override
	public PageResult<AttendancePay> findPages(AttendancePay param, PageParameter page) {
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
	        }, page);
			 if(param.getSign()!=null){
				 this.countMaxPay(pages.getContent(),param);
			 }
	        PageResult<AttendancePay> result = new PageResult<AttendancePay>(pages,page);
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
	public void addAttendancePay(AttendancePay attendancePay) {
		attendancePay.setPayNumber(NumUtils.round(attendancePay.getWorkPrice()*attendancePay.getWorkTime(),2));
		dao.save(attendancePay);
	}

	@Override
	public AttendancePay findByUserIdAndAllotTime(AttendancePay attendancePay) {
		return dao.findByUserIdAndAllotTime(attendancePay.getUserId(),attendancePay.getAllotTime());
	}

}

