package com.bluewhite.production.farragotask.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.UnUtil;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.farragotask.dao.FarragoTaskDao;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.finance.dao.FarragoTaskPayDao;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class FarragoTaskServiceImpl extends BaseServiceImpl<FarragoTask, Long> implements FarragoTaskService {

	@Autowired
	private FarragoTaskDao dao;
	@Autowired
	private FarragoTaskPayDao farragoTaskPayDao;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private AttendancePayDao attendancePayDao;
	@Autowired
	private UserDao userDao;

	@Override
	public PageResult<FarragoTask> findPages(FarragoTask param, PageParameter page) {
		Page<FarragoTask> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按分配人过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按类型
			if (!StringUtils.isEmpty(param.getType())) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			// 任务名
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
			}
			// 按批次名
			if (!StringUtils.isEmpty(param.getBacth())) {
				predicate.add(cb.like(root.get("bacth").as(String.class), "%" + param.getBacth() + "%"));
			}
			// 按时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("allotTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, SalesUtils.getQueryNoPageParameter());
		PageResultStat<FarragoTask> result = new PageResultStat<>(pages, page);
		result.setAutoStateField("payB", "price");
		result.count();
		return result;
	}

	@Override
	public FarragoTask addFarragoTask(FarragoTask farragoTask, HttpServletRequest request) {
		CurrentUser cu = SessionManager.getUserSession();
		farragoTask.setUserId(cu.getId());
		// 领取记录
		String[] ids = null;
		if (!StringUtils.isEmpty(farragoTask.getIds())) {
			ids = farragoTask.getIds().split(",");
		}
		String[] temporaryIds = null;
		if (!StringUtils.isEmpty(farragoTask.getTemporaryIds())) {
			temporaryIds = farragoTask.getTemporaryIds().split(",");
		}
		// 将用户变成string类型储存
		if (!StringUtils.isEmpty(farragoTask.getUserIds())) {
			farragoTask.setUsersIds(farragoTask.getUserIds().split(","));
		}
		if (!StringUtils.isEmpty(farragoTask.getTemporaryUserIds())) {
			farragoTask.setTemporaryUsersIds(farragoTask.getTemporaryUserIds().split(","));
		}
		// 当数量和单只完成时间不为null，计算出实际完成时间
		if (farragoTask.getNumber() != null && farragoTask.getProcedureTime() != null) {
			farragoTask.setTime(NumUtils.round(ProTypeUtils.sumFarragoTaskTime(farragoTask.getProcedureTime(),
					farragoTask.getType(), farragoTask.getNumber()), null));
		}
		// 杂工任务价值
		if (farragoTask.getTime() != null) {
			farragoTask.setPrice(NumUtils.round(
					ProTypeUtils.sumTaskPrice(farragoTask.getTime(), farragoTask.getType(), 0, farragoTask.getAC5()),
					null));
		}
		// B工资净值
		farragoTask.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(farragoTask.getPrice(), farragoTask.getType()), null));
		dao.save(farragoTask);

		// 将杂工工资统计成流水
		int userSize = ids != null ? ids.length : 0;
		int temporaryUserSize = temporaryIds != null ? temporaryIds.length : 0;
		List<FarragoTaskPay> farragoTaskPayList = new ArrayList<>();
		if (farragoTask.getId() != null) {
			farragoTaskPayList = farragoTaskPayDao.findByTaskId(farragoTask.getId());
		}
		if (farragoTask.getUsersIds() != null && farragoTask.getUsersIds().length > 0) {
			for (int j = 0; j < farragoTask.getUsersIds().length; j++) {
				// 任务人员出勤记录id
				Long id = Long.parseLong(ids[j]);
				// 任务人员id
				Long userId = Long.parseLong(farragoTask.getUsersIds()[j]);
				AttendancePay attendancePay = null;
				Temporarily temporarily = null;
				User user = null;
				if (!UnUtil.isFromMobile(request)) {
					attendancePay = attendancePayDao.findByIdAndUserId(id, userId);
					if (attendancePay == null) {
						temporarily = temporarilyDao.findByIdAndUserId(id, userId);
					}
				} else {
					user = userDao.findOne(userId);
				}
				FarragoTaskPay farragoTaskPay = null;
				if (farragoTask.getId() != null) {
					// 给予每个员工b工资
					List<FarragoTaskPay> farragoTaskPayOneList = farragoTaskPayList.stream()
							.filter(FarragoTaskPay -> FarragoTaskPay.getUserId().equals(userId))
							.collect(Collectors.toList());
					if (farragoTaskPayOneList.size() > 0) {
						farragoTaskPay = farragoTaskPayOneList.get(0);
					}
				}
				if (farragoTaskPay == null) {
					farragoTaskPay = new FarragoTaskPay();
					farragoTaskPay.setAllotTime(farragoTask.getAllotTime());
					farragoTaskPay.setType(farragoTask.getType());
					if (!UnUtil.isFromMobile(request)) {
						farragoTaskPay.setUserId(attendancePay == null ? temporarily.getUserId() : attendancePay.getUserId());
						farragoTaskPay.setGroupId(attendancePay == null ? temporarily.getGroupId() : attendancePay.getGroupId());
						farragoTaskPay.setUserName(attendancePay == null ? temporarily.getUser().getUserName(): attendancePay.getUserName());
					} else {
						farragoTaskPay.setUserId(user.getId());
						farragoTaskPay.setGroupId(user.getGroupId());
						farragoTaskPay.setUserName(user.getUserName());
					}
					farragoTaskPay.setTaskId(farragoTask.getId());
					farragoTaskPay.setTaskName(farragoTask.getName());
				} 
				// 计算杂工工资
				farragoTaskPay.setPayNumber(NumUtils.div(farragoTask.getPayB(), (userSize + temporaryUserSize), 3));
				farragoTaskPayDao.save(farragoTaskPay);
			}
		}

		// 将杂工工资统计成流水
		if (temporaryIds != null && temporaryIds.length > 0) {
			for (int j = 0; j < temporaryIds.length; j++) {
				Long id = Long.parseLong(temporaryIds[j]);
				Temporarily temporarily = temporarilyDao.findOne(id);

				FarragoTaskPay farragoTaskPay = null;
				if (farragoTask.getId() != null) {
					// 给予每个员工b工资
					List<FarragoTaskPay> farragoTaskPayOneList = farragoTaskPayList.stream()
							.filter(FarragoTaskPay -> FarragoTaskPay.getTemporaryUserId().equals(id))
							.collect(Collectors.toList());
					if (farragoTaskPayOneList.size() > 0) {
						farragoTaskPay = farragoTaskPayOneList.get(0);
					}
				}
				if (farragoTaskPay == null) {
					farragoTaskPay = new FarragoTaskPay();
					farragoTaskPay.setAllotTime(farragoTask.getAllotTime());
					farragoTaskPay.setType(farragoTask.getType());
					farragoTaskPay.setTemporaryUserId(temporarily.getTemporaryUserId());
					farragoTaskPay.setUserId(temporarily.getUserId());
					farragoTaskPay.setGroupId(temporarily.getGroupId());
					farragoTaskPay.setTaskId(farragoTask.getId());
					farragoTaskPay.setUserName(temporarily.getTemporaryUser() != null
							? temporarily.getTemporaryUser().getUserName() : temporarily.getUser().getUserName());
					farragoTaskPay.setTaskName(farragoTask.getName());
				} 
				// 计算杂工工资
				farragoTaskPay.setPayNumber(NumUtils.div(farragoTask.getPayB(), (userSize + temporaryUserSize), 3));
				farragoTaskPayDao.save(farragoTaskPay);
			}
		}
		return dao.save(farragoTask);
	}

	@Override
	public FarragoTask updateFarragoTask(FarragoTask farragoTask) {
		return null;
	}

	@Override
	@Transactional
	public void deleteFarragoTask(Long id) {
		List<FarragoTaskPay> taskList = farragoTaskPayDao.findByTaskId(id);
		if (taskList != null) {
			farragoTaskPayDao.delete(taskList);
		}
		dao.delete(id);
	}

	@Override
	public List<FarragoTask> findByTypeAndAllotTimeBetween(Integer type, Date startTime, Date endTime) {
		return dao.findByTypeAndAllotTimeBetween(type, startTime, endTime);
	}

	@Override
	public List<FarragoTask> findInSetIds(String ids, Date beginTime, Date endTime) {
		return dao.findInSetIds(ids, beginTime, endTime);
	}

	@Override
	public List<FarragoTask> findInSetTemporaryIds(String id, Date startTime, Date endTime) {
		return dao.findInSetTemporaryIds(id, startTime, endTime);
	}
}
