package com.bluewhite.production.task.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.UnUtil;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.dao.TaskDao;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.dao.TemporaryUserDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, Long> implements TaskService {

	@Autowired
	private TaskDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private TemporaryUserDao temporaryUserDao;
	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private BacthDao bacthDao;
	@Autowired
	private PayBDao payBDao;
	@Autowired
	private PayBService payBService;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private AttendancePayDao attendancePayDao;

	private final static String QUALITY_STRING = "贴破洞";

	@Override
	@Transactional
	public Task addTask(Task task, HttpServletRequest request) {
		CurrentUser cu = SessionManager.getUserSession();
		task.setUserId(cu.getId());
		List<Long> idsList = new ArrayList<>();
		List<Long> temporaryIdList = new ArrayList<>();
		List<Long> userIdsList = new ArrayList<>();
		List<Long> temporaryUserIdList = new ArrayList<>();
		Date orderTimeBegin = DatesUtil.getfristDayOftime(task.getAllotTime());
		Date orderTimeEnd = DatesUtil.getLastDayOftime(task.getAllotTime());
		// 将用户变成string类型储存
		if (!StringUtils.isEmpty(task.getUserIds())) {
			task.setUsersIds(task.getUserIds().split(","));
			String[] idStrings = task.getIds().split(",");
			// 正式员工出勤记录ids
			idsList = Arrays.asList(idStrings).stream().map(a -> Long.parseLong(a)).collect(Collectors.toList());
			// 正式员工ids
			userIdsList = Arrays.asList(task.getUsersIds()).stream().map(a -> Long.parseLong(a))
					.collect(Collectors.toList());
		}

		if (!StringUtils.isEmpty(task.getTemporaryUserIds())) {
			task.setTemporaryUsersIds(task.getTemporaryUserIds().split(","));
			String[] temporaryIds = task.getTemporaryIds().split(",");
			// 临时员工出勤记录ids
			temporaryIdList = Arrays.asList(temporaryIds).stream().map(a -> Long.parseLong(a))
					.collect(Collectors.toList());
			temporaryUserIdList =  Arrays.asList(task.getTemporaryUsersIds()).stream().map(a -> Long.parseLong(a))
					.collect(Collectors.toList());

		}
		// 总考勤时间
		double sumTime = 0;
		List<AttendancePay> attendancePayList = attendancePayDao.findByIdInAndTypeAndAllotTimeBetween(idsList,
				task.getType(), orderTimeBegin, orderTimeEnd);
		List<Temporarily> temporarilyList = temporarilyDao.findByIdInAndTemporarilyDateAndType(temporaryIdList,
				orderTimeBegin, task.getType());
		if (!UnUtil.isFromMobile(request) && task.getType() == 2) {
			// 正式员工的出勤时长
			// 获取正式员工的出勤记录
			if (idsList.size() > 0) {
				for (int i = 0; i < idsList.size(); i++) {
					Long id = idsList.get(i);
					Long usersId = userIdsList.get(i);
					List<AttendancePay> attendancePayListOne = attendancePayList.stream()
							.filter(AttendancePay -> AttendancePay.getId().equals(id)
									&& AttendancePay.getUserId().equals(usersId))
							.collect(Collectors.toList());
					Temporarily temporarily = null;
					if (attendancePayListOne.size() == 0) {
						temporarily = temporarilyDao.findByIdAndUserId(idsList.get(i), userIdsList.get(i));
					}
					sumTime += attendancePayListOne.size() == 0 ? temporarily.getWorkTime()
							: attendancePayListOne.get(0).getWorkTime();
				}
			}
			// 临时员工
			if (task.getTemporaryUsersIds() != null && task.getTemporaryUsersIds().length > 0) {
				for (Long id : temporaryIdList) {
					List<Temporarily> temporarilyListOne = temporarilyList.stream()
							.filter(Temporarily -> Temporarily.getId().equals(id)).collect(Collectors.toList());
					sumTime += temporarilyListOne.get(0).getWorkTime();
				}
			}
		}

		Double sumTaskPrice = 0.0;
		// 将工序ids分成多个任务
		if (task.getProcedureIds().length > 0) {
			Task newTask = null;
			for (int i = 0; i < task.getProcedureIds().length; i++) {
				newTask = new Task();
				BeanCopyUtils.copyNullProperties(task, newTask);
				Long id = Long.parseLong(task.getProcedureIds()[i]);
				Procedure procedure = procedureDao.findOne(id);
				if (procedure.getName().equals(QUALITY_STRING)) {
					if (task.getHoleNumber() != 0) {
						newTask.setNumber(task.getHoleNumber());
					} else {
						newTask.setNumber(task.getNumber());
					}
				}
				newTask.setProcedureId(id);
				newTask.setProcedureName(procedure.getName());
				// 二楼特殊业务，当存在实际时间不为null的时候，先 计算出任务数量
				if (task.getTaskTime() != null && task.getType() == 3) {
					newTask.setNumber(NumUtils.roundTwo(ProTypeUtils.getTaskNumber(newTask.getTaskTime(),
							newTask.getType(), procedure.getWorkingTime())));
				}

				// 预计完成时间（1.工序类型不是返工，预计时间利用公式计算的得出。2.工序类型是返工，手填预计完成时间）
				// 当前台传值得预计时间不为null，说明该任务类型是返工类型
				newTask.setFlag(procedure.getFlag());
				if (task.getExpectTime() == null) {
					newTask.setExpectTime(NumUtils.round(ProTypeUtils.sumExpectTime(procedure, procedure.getType(), newTask.getNumber()), 5));
				}
				// 实际完成时间（1.工序类型不是返工，预计时间等于实际时间，2工序类型是返工，实际完成时间根据公式的出）
				if (task.getExpectTime() == null) {
					newTask.setTaskTime(newTask.getExpectTime());
				} else {
					newTask.setTaskTime(NumUtils.round(ProTypeUtils.sumTaskTime(procedure.getWorkingTime(),
							procedure.getType(), newTask.getNumber()), 5));
				}
				// 预计任务价值（通过预计完成时间得出）（1.工序类型不是返工，预计任务价值通过计算得出
				// 2.工序类型是返工,没有预计任务价值）
				if (task.getExpectTime() == null) {
					newTask.setExpectTaskPrice(NumUtils.round(
							ProTypeUtils.sumTaskPrice(newTask.getExpectTime(), procedure.getType(), 0, null), 5));
				} else {
					newTask.setExpectTaskPrice(null);
				}
				// 实际任务价值（通过实际完成时间得出）
				newTask.setTaskPrice(NumUtils.round(
						ProTypeUtils.sumTaskPrice(newTask.getTaskTime(), procedure.getType(), newTask.getFlag(), null),
						5));
				// B工资净值
				newTask.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(newTask.getTaskPrice(), procedure.getType()), 5));
				dao.save(newTask);

				// 查出该任务的所有b工资
				List<PayB> payBList = new ArrayList<>();
				int userInt = newTask.getUsersIds() != null && newTask.getUsersIds().length > 0 ? newTask.getUsersIds().length
						: 0;
				int temporaryUsersInt = newTask.getTemporaryUsersIds() != null && newTask.getTemporaryUsersIds().length > 0
						? newTask.getTemporaryUsersIds().length : 0;
				if (newTask.getId() != null) {
					payBList = payBDao.findByTaskId(task.getId());
				}
				/// 员工和任务形成多对多关系
				if (userIdsList.size() > 0) {
					for (int j = 0; j < userIdsList.size(); j++) {
						// 获取正式员工的出勤记录
						Temporarily temporarily = null;
						AttendancePay attendancePay = null;
						// 任务人员出勤记录id
						Long idP = idsList.get(j);
						// 任务人员id
						Long userIdP = userIdsList.get(j);
						if (!UnUtil.isFromMobile(request)) {
							List<AttendancePay> attendancePayListOne = attendancePayList.stream()
									.filter(AttendancePay -> AttendancePay.getId().equals(idP)
											&& AttendancePay.getUserId().equals(userIdP))
									.collect(Collectors.toList());
							if (attendancePayListOne.size() == 0) {
								temporarily = temporarilyDao.findByIdAndUserId(idP, userIdP);
							} else {
								attendancePay = attendancePayListOne.get(0);
							}
						}
						PayB payB = null;
						if (task.getId() != null) {
							// 给予每个员工b工资
							List<PayB> payBOneList = payBList.stream().filter(PayB ->PayB.getUserId()!=null && PayB.getUserId().equals(userIdP))
									.collect(Collectors.toList());
							if (payBOneList.size() > 0) {
								payB = payBOneList.get(0);
							}
						}
						if (payB == null) {
							payB = new PayB();
							payB.setUserId(userIdP);
							if (!UnUtil.isFromMobile(request)) {
								payB.setGroupId(
										attendancePay != null ? attendancePay.getGroupId() : temporarily.getGroupId());
								payB.setUserName(attendancePay != null ? attendancePay.getUserName()
										: temporarily.getUser().getUserName());
							} else {
								User user = userDao.findOne(userIdP);
								payB.setGroupId(user.getGroupId());
								payB.setUserName(user.getUserName());
							}
							payB.setBacth(newTask.getBacthNumber());
							payB.setBacthId(newTask.getBacthId());
							payB.setProductId(newTask.getProductId());
							payB.setProductName(newTask.getProductName());
							payB.setTaskId(newTask.getId());
							payB.setType(newTask.getType());
							payB.setAllotTime(newTask.getAllotTime());
							payB.setFlag(newTask.getFlag());
						} else {
							String performance = payB.getPerformance();
							if (!StringUtils.isEmpty(performance)) {
								long count = payBList.stream().filter(PayB -> PayB.getPerformance() != null
										&& PayB.getPerformance().equals(performance)).count();
								payB.setPerformancePayNumber(NumUtils.div(newTask.getPerformancePrice(), count, 3));
							}
						}
						// 计算B工资数值
						if (!UnUtil.isFromMobile(request) && newTask.getType() == 2) {
							// 包装分配任务，员工b工资根据考情占比分配，其他部门是均分
							// 该员工实际工作时长
							Double workTime = attendancePay != null ? attendancePay.getWorkTime()
									: temporarily.getWorkTime();
							// 按考情时间占比分配B工资
							if (workTime != null) {
								payB.setPayNumber(NumUtils.div(NumUtils.mul(newTask.getPayB(), workTime),
										NumUtils.round(sumTime, 2), 5));
							}
						} else {
							payB.setPayNumber(NumUtils.div(newTask.getPayB(), (userInt + temporaryUsersInt), 5));
						}
						payBList.add(payB);
					}
				}

				// 临时员工
				if (temporaryUserIdList.size() > 0) {
					for (int j = 0; j < temporaryUserIdList.size(); j++) {
						// 临时员工出勤记录id
						Long idLong = temporaryIdList.get(j);
						// 临时员工id
						Long userIdLong = temporaryUserIdList.get(j);
						// 获取临时员工出勤记录
						List<Temporarily> temporarilyListOne = temporarilyList.stream()
								.filter(Temporarily -> Temporarily.getId().equals(idLong)).collect(Collectors.toList());
						PayB payB = null;
						// 给予每个员工b工资
						if (newTask.getId() != null) {
							List<PayB> payBOneList = payBList.stream().filter(PayB -> PayB.getTemporaryUserId()!=null && PayB.getTemporaryUserId().equals(temporarilyListOne.get(0).getTemporaryUserId()))
									.collect(Collectors.toList());
							if (payBOneList.size() > 0) {
								payB = payBOneList.get(0);
							}
						}
						if (payB == null) {
							payB = new PayB();
							payB.setTemporaryUserId(temporarilyListOne.get(0).getTemporaryUserId());
							payB.setGroupId(temporarilyListOne.get(0).getGroupId());
							payB.setUserName(temporarilyListOne.get(0).getTemporaryUser().getUserName());
							payB.setBacth(newTask.getBacthNumber());
							payB.setBacthId(newTask.getBacthId());
							payB.setProductId(newTask.getProductId());
							payB.setProductName(newTask.getProductName());
							payB.setTaskId(newTask.getId());
							payB.setType(newTask.getType());
							payB.setAllotTime(newTask.getAllotTime());
							payB.setFlag(newTask.getFlag());
						} else {
							String performance = payB.getPerformance();
							if (!StringUtils.isEmpty(performance)) {
								long count = payBList.stream().filter(PayB -> PayB.getPerformance() != null
										&& PayB.getPerformance().equals(performance)).count();
								payB.setPerformancePayNumber(NumUtils.div(newTask.getPerformancePrice(), count, 3));
							}
						}
						// 计算B工资数值
						if (!UnUtil.isFromMobile(request) && newTask.getType() == 2) {
							// 包装分配任务，员工b工资根据考情占比分配，其他部门是均分
							// 该员工实际工作时长
							Double workTime = temporarilyListOne.get(0).getWorkTime();
							// 按考情时间占比分配B工资
							if (workTime != null) {
								payB.setPayNumber(NumUtils.div(NumUtils.mul(newTask.getPayB(), workTime),
										NumUtils.round(sumTime, 2), 5));
							}
						} else {
							payB.setPayNumber(NumUtils.div(newTask.getPayB(), (userInt + temporaryUsersInt), 5));
						}
						payBList.add(payB);
					}
				}
				payBService.batchSave(payBList);
			}
		}

		// 查出该批次的所有任务
		Bacth bacth = bacthDao.findOne(task.getBacthId());
		// 计算出该批次下所有人的实际成本总和
		int count = 0;
		double bacthDepartmentPrice = 0;
		if (bacth.getTasks().size() > 0) {
			List<Double> listDouble = new ArrayList<>();
			bacth.getTasks().stream().filter(StringUtil.distinctByKey(Task::getProcedureId)).forEach(a -> {
				if(a.getProcedureId()!=null ){
					Procedure procedure  = procedureDao.findOne(a.getProcedureId());
					listDouble.add(procedure.getWorkingTime());
				}
			});
			if(listDouble.size()>0){
				bacthDepartmentPrice = ProTypeUtils.sumProTypePrice(NumUtils.sum(listDouble), bacth.getType());
			}
		}
		for (Task ta : bacth.getTasks()) {
			sumTaskPrice += ta.getTaskPrice();
			if (task.getType() == 2) {
				if (ta.getProcedureName().indexOf(Constants.BAGABOARD) != -1) {
					count += ta.getNumber();
				}
			}
		};
		if (bacth.getNumber() == count) {
			bacth.setStatus(1);
			bacth.setStatusTime(task.getAllotTime());
		}
		bacth.setSumTaskPrice(NumUtils.round(sumTaskPrice, 5));
		bacth.setBacthDepartmentPrice(bacthDepartmentPrice);
		// 计算出该批次的地区差价
		if (bacth.getFlag() == 0) {
			bacth.setRegionalPrice(NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth, bacth.getType()), 5));
		}
		bacthDao.save(bacth);
		return task;
	}

	@Override
	public PageResult<Task> findPages(Task param, PageParameter page) {
		Page<Task> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按分配人过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按批次id过滤
			if (param.getBacthId() != null) {
				predicate.add(cb.equal(root.get("bacthId").as(Long.class), param.getBacthId()));
			}
			// 按批次号
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("productName").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按工序名称
			if (!StringUtils.isEmpty(param.getProcedureName())) {
				predicate
						.add(cb.like(root.get("procedureName").as(String.class), "%" + param.getProcedureName() + "%"));
			}
			// 按工序类型
			if (!StringUtils.isEmpty(param.getProcedureTypeId())) {
				predicate.add(cb.equal(root.get("procedure").get("procedureTypeId").as(Long.class),
						param.getProcedureTypeId()));
			}

			// 按工序id
			if (!StringUtils.isEmpty(param.getProcedureId())) {
				predicate.add(cb.equal(root.get("procedureId").as(Long.class), param.getProcedureId()));
			}

			// 按类型
			if (!StringUtils.isEmpty(param.getType())) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}

			// 按返工类型
			if (!StringUtils.isEmpty(param.getFlag())) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 机工楼层区分
			if (!StringUtils.isEmpty(param.getMachinist())) {
				predicate.add(cb.equal(root.get("bacth").get("machinist").as(Integer.class), param.getMachinist()));
			}

			// 按批次是否完成
			if (!StringUtils.isEmpty(param.getStatus())) {
				predicate.add(cb.equal(root.get("bacth").get("status").as(Integer.class), param.getStatus()));
			}

			// 按时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("allotTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Task> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void deleteTask(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					// 同时删除B工资
					List<PayB> payB = payBDao.findByTaskId(id);
					if (payB.size() > 0) {
						payBDao.deleteInBatch(payB);
					}
					// 更新该批次的数值(sumTaskPrice(总任务价值),regionalPrice（地区差价）)
					Task task = dao.findOne(id);
					Bacth bacth = task.getBacth();
					Double sumTaskPrice = 0.0;
					// 计算出该批次下所有人的实际成本总和
					CopyOnWriteArraySet<Task> taskset = new CopyOnWriteArraySet<Task>(bacth.getTasks());
					for (Task ta : taskset) {
						// 排除要删除的任务id
						if (!ta.getId().equals(id)) {
							sumTaskPrice += ta.getTaskPrice();
						} else {
							dao.delete(ta);
							bacth.getTasks().remove(ta);
						}
					}
					;
					bacth.setSumTaskPrice(sumTaskPrice);
					if (bacth.getFlag() == 0) {
						// 计算出该批次的地区差价
						bacth.setRegionalPrice(
								NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth, bacth.getType()), 4));
					}
					// 更新批次
					bacthDao.save(bacth);
				}
				;
			}
		}
	}

	@Override
	public List<Task> assembleTask(Task task) {

		Integer number = task.getNumber();
		List<Task> taskList = new ArrayList<Task>();

		// 总时间
		double sumTime = 0;
		// 将时间段转变成数组,计算出总时间
		if (!StringUtils.isEmpty(task.getTimes())) {
			String[] timeArr = task.getTimes().split(",");
			if (timeArr.length > 0) {
				for (int i = 0; i < timeArr.length; i++) {
					if (!timeArr[i].equals("delete")) {
						double time = Double.valueOf(timeArr[i]);
						sumTime += time;
					}
				}
			}
		}

		int sumNumber = 0;
		// 将时间段转变成数组
		if (!StringUtils.isEmpty(task.getTimes())) {
			String[] timeArr = task.getTimes().split(",");
			if (timeArr.length > 0) {
				for (int i = 0; i < timeArr.length; i++) {
					Task tasks = new Task();
					double time = 0;
					if (!timeArr[i].equals("delete")) {
						time = Double.valueOf(timeArr[i]);
						tasks.setBacthId(task.getBacthId());
						tasks.setExpectTime(task.getExpectTime());
						tasks.setAllotTime(task.getAllotTime());
						tasks.setType(task.getType());
						tasks.setBacthNumber(task.getBacthNumber());
						tasks.setProductName(task.getProductName());
						tasks.setProcedureIds(task.getProcedureIds());
						tasks.setPerformance(task.getPerformance());
						tasks.setPerformanceNumber(task.getPerformanceNumber());
						tasks.setNumber(NumUtils.roundInt(time * number / sumTime));
						tasks.setHoleNumber(NumUtils.roundInt(time * number / sumTime));
						sumNumber += tasks.getNumber();
						if (i == timeArr.length - 1) {
							tasks.setNumber(tasks.getNumber() + (number - sumNumber));
						}
						// 将用户段转换成数组,将同顺序的用户添加到任务中
						if (!StringUtils.isEmpty(task.getUsers())) {
							String[] userArr = task.getUsers().split("\\.");
							if (userArr.length > 0) {
								if (!timeArr[i].equals("delete")) {
									tasks.setUserIds(userArr[i]);
								}
							}
						}
						taskList.add(tasks);
					}
				}
			}
		}
		return taskList;

	}

	@Override
	@Transactional
	public Task addReTask(Task task) {
		CurrentUser cu = SessionManager.getUserSession();
		task.setUserId(cu.getId());
		// 将用户变成string类型储存
		if (!StringUtils.isEmpty(task.getUserIds())) {
			String[] idArr = task.getUserIds().split(",");
			task.setUsersIds(idArr);
		}
		// 当数量不为null，计算出实际完成时间
		if (task.getNumber() != null) {
			task.setTaskTime(NumUtils.round(
					ProTypeUtils.sumFarragoTaskTime(task.getTaskTime(), task.getType(), task.getNumber()), null));
		}
		// 返工任务价值
		task.setTaskPrice(
				NumUtils.round(ProTypeUtils.sumTaskPrice(task.getTaskTime(), task.getType(), 0, task.getAC5()), null));

		// B工资净值
		task.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(task.getTaskPrice(), task.getType()), null));

		task = dao.save(task);
		// 将返工工资统计成流水
		if (task.getUsersIds().length > 0) {
			for (int j = 0; j < task.getUsersIds().length; j++) {
				Long userid = Long.parseLong(task.getUsersIds()[j]);
				User user = userDao.findOne(userid);
				// 给予每个员工b工资
				PayB payB = new PayB();
				payB.setUserId(userid);
				payB.setUserName(user.getUserName());
				payB.setBacth(task.getBacthNumber());
				payB.setBacthId(task.getBacthId());
				payB.setProductId(task.getProductId());
				payB.setProductName(task.getProductName());
				payB.setTaskId(task.getId());
				payB.setType(task.getType());
				payB.setAllotTime(task.getAllotTime());
				payB.setFlag(task.getFlag());
				// 计算B工资数值
				payB.setPayNumber(task.getPayB() / task.getUsersIds().length);
				payBDao.save(payB);
			}
		}
		return dao.save(task);
	}

	@Override
	@Transactional
	public void deleteReTask(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					// 同时删除B工资
					List<PayB> payB = payBDao.findByTaskId(id);
					if (payB.size() > 0) {
						payBDao.deleteInBatch(payB);
					}
					dao.delete(id);
				}
			}
		}
	}

	@Override
	public void giveTaskPerformance(String[] taskIds, String[] ids, String[] performance, Double[] performanceNumber,
			Integer update) {
		if (!StringUtils.isEmpty(taskIds)) {
			if (taskIds.length > 0) {
				for (int i = 0; i < taskIds.length; i++) {
					Long id = Long.parseLong(taskIds[i]);
					Task task = dao.findOne(id);
					if (!StringUtils.isEmpty(ids) && !StringUtils.isEmpty(performance)
							&& !StringUtils.isEmpty(performanceNumber)) {
						task.setPerformance(performance[i]);
						task.setPerformanceNumber(performanceNumber[i]);
						// 任务加绩具体数值
						double performancePrice = NumUtils.round(ProTypeUtils.sumtaskPerformancePrice(task), null);
						task.setPerformancePrice(performancePrice);
						// 修改加绩时，先清空b工资，在赋值
						if (update == 1) {
							List<PayB> payBListO = payBDao.findByTaskId(id);
							payBListO.stream().filter(PayB -> PayB.getPerformancePayNumber() != null).forEach(pl -> {
								pl.setPerformance(null);
								pl.setPerformancePayNumber(0.0);
								pl.setPerformanceNumber(0.0);
							});
							payBDao.save(payBListO);
						}
						// 人员具体数量
						if (ids.length > 0) {
							for (int j = 0; j < ids.length; j++) {
								Long userid = Long.parseLong(ids[j]);
								PayB payB = payBDao.findByTaskIdAndUserId(task.getId(), userid);
								if (payB == null) {
									payB = payBDao.findByTaskIdAndTemporaryUserId(task.getId(), userid);
								}
								payB.setPerformance(task.getPerformance());
								payB.setPerformanceNumber(task.getPerformanceNumber());
								payB.setPerformancePayNumber(NumUtils.div(performancePrice,ids.length, 3));
								payBDao.save(payB);
							}
						}
					} else {
						throw new ServiceException("加绩不能为空");
					}
					dao.save(task);
				}
			}
		}

	}

	@Override
	public List<Task> findByUserIdAndAllotTime(String userid, Date beginTime, Date endTime) {
		return dao.findByUserIdAndAllotTime(userid, beginTime, endTime);
	}

	@Override
	public List<Task> findByTypeAndAllotTimeBetween(Integer type, Date startTime, Date endTime) {
		return dao.findByTypeAndAllotTimeBetween(type, startTime, endTime);
	}

	@Override
	public List<Task> findInSetIds(String ids, Date beginTime, Date endTime) {
		return dao.findInSetIds(ids, beginTime, endTime);
	}

	@Override
	public List<Task> findInSetTemporaryIds(String id, Date startTime, Date endTime) {
		return dao.findInSetTemporaryIds(id, startTime, endTime);
	}

}
