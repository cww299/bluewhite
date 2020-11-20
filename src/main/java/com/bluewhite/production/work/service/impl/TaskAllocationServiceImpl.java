package com.bluewhite.production.work.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.production.work.common.TaskConstant;
import com.bluewhite.production.work.dao.TaskAllocationDao;
import com.bluewhite.production.work.entity.TaskAllocation;
import com.bluewhite.production.work.entity.TaskProcess;
import com.bluewhite.production.work.entity.TaskWork;
import com.bluewhite.production.work.service.TaskAllocationService;
import com.bluewhite.production.work.service.TaskProcessService;
import com.bluewhite.production.work.service.TaskWorkService;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public class TaskAllocationServiceImpl extends BaseServiceImpl<TaskAllocation, Long> implements TaskAllocationService {

	@Autowired
	private TaskAllocationDao dao;
	@Autowired
	private TaskProcessService processService;
	@Autowired
	private TaskWorkService taskService;
	
	@Override
	public PageResult<TaskAllocation> findPages(TaskAllocation allocation, PageParameter page) {
		Page<TaskAllocation> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (allocation.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), allocation.getId()));
            }
            if (allocation.getTaskId() != null) {
                predicate.add(cb.equal(root.get("taskId").as(Long.class), allocation.getTaskId()));
            }
            // 员工名称
            if (StrUtil.isNotBlank(allocation.getUserNames())) {
                predicate.add(cb.like(root.get("userNames").as(String.class), "%" + allocation.getUserNames() + "%"));
            }
            // 状态
            if (allocation.getStatus() != null) {
            	predicate.add(cb.equal(root.get("status").as(Integer.class), allocation.getStatus()));
            }
            // 产品名
            if (StrUtil.isNotBlank(allocation.getProductName())) {
            	predicate.add(cb.like(root.get("task").get("productName").as(String.class), "%" + allocation.getProductName() + "%"));
            }
        	// 工序名
            if (StrUtil.isNotBlank(allocation.getProcessesName())) {
            	predicate.add(cb.like(root.get("task").get("processesName").as(String.class), "%" + allocation.getProcessesName() + "%"));
            }
        	// 任务编号
            if (StrUtil.isNotBlank(allocation.getTaskNumber())) {
            	predicate.add(cb.equal(root.get("task").get("taskNumber").as(String.class), allocation.getTaskNumber()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
		}, page);
	    PageResultStat<TaskAllocation> result = new PageResultStat<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void saveTaskAllocation(TaskAllocation allocation) {
		if (allocation.getId() != null) {
			throw new ServiceException("任务分配后无法修改！");
		}
		// 修改任务的已分配数量
		TaskWork task = taskService.findOne(allocation.getTaskId());
		if (task.getStatus() == TaskConstant.TASK_END) {
			throw new ServiceException("当前任务已结束，无法分配");
		}
		if (task.getSurplusNumber() < allocation.getNumber()) {
			throw new ServiceException(
					StrUtil.format("剩余未分配数量为：{},无法分配：{}",task.getSurplusNumber(), allocation.getNumber()));
		}
		// 新增分配时，设置默认状态值 -> 进行中
		allocation.setStatus(TaskConstant.ALLOCATION_PROCESS);
		allocation = save(allocation);
		task.setAllocationNumber(task.getAllocationNumber() + allocation.getNumber());
		task.setStatus(TaskConstant.TASK_PROCESS);
		task = taskService.save(task);
		// 新增分配进度
		TaskProcess process = getProcess(allocation,
				StrUtil.format(TaskConstant.REMARK_ALLOCATION, allocation.getNumber()),
				TaskConstant.PROCESS_ALLOCATION);
		process.setUserNames(allocation.getUserNames());
		processService.save(process);
	}

	@Override
	@Transactional
	public int deleteTaskAllocation(String ids) {
		List<TaskAllocation> list = new ArrayList<>();
		List<TaskWork> taskList = new ArrayList<>();
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.parseLong(arrIds[i]);
			TaskAllocation allocation = findOne(id);
			if(allocation.getFinishNumber() != 0) {
				throw new ServiceException("任务：" + allocation.getTask().getTaskNumber() + " 已有完成数量，无法删除！");
			}
			list.add(allocation);
		}
		// 修改任务的分配数量 = 当前任务分配数量 + 退回剩余数量
		list.stream().forEach(allocation -> {
			TaskWork task = null;
			for (TaskWork t : taskList) {
				if (t.getId().equals(allocation.getTaskId())) {
					task = t;
					break;
				}
			}
			if (task == null) {
				task = allocation.getTask();
				taskList.add(task);
			}
			task.setAllocationNumber(task.getAllocationNumber() - allocation.getSurplusNumber());
			if (task.getAllocationNumber() == 0) {
				task.setStatus(TaskConstant.TASK_UNALLOCATION);
			}
		});
		// 删除相关分配任务的相关进度
		processService.deleteAllocation(list);
		taskService.save(taskList);
		baseRepository.deleteInBatch(list);
		return list.size();
	}

	@Override
	@Transactional
	public int startOrPause(String ids, String remark, int status) {
		CurrentUser cu = SessionManager.getUserSession();
		int processType = status == TaskConstant.ALLOCATION_PAUSE ? TaskConstant.PROCESS_PAUSE : TaskConstant.PROCESS_START;
		List<TaskAllocation> list = new ArrayList<>();
		List<TaskProcess> processList = new ArrayList<>();
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.parseLong(arrIds[i]);
			TaskAllocation allocation = findOne(id);
			if (allocation.getStatus() == TaskConstant.ALLOCATION_END) {
				throw new ServiceException("任务：" + allocation.getTask().getTaskNumber() + " 已经结束，无法开始或暂停！");
			}
			if (allocation.getStatus() == status) {
				throw new ServiceException("任务：" + allocation.getTask().getTaskNumber() + " 状态无法开始或暂停！");
			}
			allocation.setStatus(status);
			list.add(allocation);
			TaskProcess process = getProcess(allocation, remark, processType);
			// 上一节点距离此时耗时时长 ： /分钟
			int timeMin = getStartOrPauseTimeMin(allocation);
			process.setTimeMin(timeMin);
			process.setUserNames(cu.getUserName());
			processList.add(process);
		}
		save(list);
		processService.save(processList);
		return list.size();
	}
	
	@Override
	public void returns(Long allocationId, int number, String remark) {
		if (remark == null) {
			remark = StrUtil.format(TaskConstant.REMARK_RETURN, number);
		}
		if (number <= 0) {
			throw new ServiceException("退回任务数量不能小于等于0");
		}
		TaskAllocation allocation = findOne(allocationId);
		if(allocation.getSurplusNumber() < number) {
			throw new ServiceException("退回任务数量不能大于剩余数量");
		}
		allocation.setReturnNumber(allocation.getReturnNumber() + number);
		// 任务已分配数量 = 当前分配数量 - 退回数量
		TaskWork task = allocation.getTask();
		task.setAllocationNumber(task.getAllocationNumber() - number);
		// 任务进度，数量为退回数量
		TaskProcess process = getProcess(allocation, remark, TaskConstant.PROCESS_RETURN);
		process.setNumber(number);
		// 操作人员
		CurrentUser cu = SessionManager.getUserSession();
		process.setUserNames(cu.getUserName());
		isEnd(allocation);
		save(allocation);
		processService.save(process);
		taskService.save(task);
	}
	
	@Override
	public void finish(Long allocationId, int number) {
		TaskAllocation allocation = checkFinal(allocationId, number);
		if (number > allocation.getSurplusNumber()) {
			throw new ServiceException("完成数量不能大于剩余数量");
		}
		allocation.setFinishNumber(allocation.getFinishNumber() + number);
		isEnd(allocation);
		// 当前耗时
		int timeMin = getFinishTime(allocation);
		TaskWork task = allocation.getTask();
		task.setFinishNumber(task.getFinishNumber() + number);
		task.setCurrTimeMin(task.getCurrTimeMin() + timeMin);
		if (task.getSurplusNumber() == 0) {
			task.setStatus(TaskConstant.TASK_END);
		}
		// 任务进度
		TaskProcess process = getFinishProcess(allocation, number, timeMin);
		process.setUserNames(allocation.getUserNames());
		// 保存完成结果
		save(allocation);
		taskService.save(task);
		processService.save(process);
	}
	
	@Override
	@Transactional
	public int finishs(String ids) {
		String[] arrIds = ids.split(",");
		List<TaskAllocation> allocationList = new ArrayList<>();
		List<TaskWork> taskList = new ArrayList<>();
		List<TaskProcess> processList = new ArrayList<>();
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.parseLong(arrIds[i]);
			// 任务分配、当前任务
			TaskAllocation allocation = checkFinal(id, null);
			// 完成数量
			int number = allocation.getSurplusNumber();
			// 加上完成数量
			allocation.setFinishNumber(allocation.getFinishNumber() + number);
			isEnd(allocation);
			// 完成耗时
			int timeMin = getFinishTime(allocation);
			TaskWork task = allocation.getTask();
			task.setFinishNumber(task.getFinishNumber() + number);
			task.setCurrTimeMin(task.getCurrTimeMin() + timeMin);
			if (task.getSurplusNumber() == 0) {
				task.setStatus(TaskConstant.TASK_END);
			}
			// 任务进度
			TaskProcess process = getFinishProcess(allocation, number, timeMin);
			process.setUserNames(allocation.getUserNames());
			// 保存到集合
			allocationList.add(allocation);
			taskList.add(task);
			processList.add(process);
		}
		save(allocationList);
		taskService.save(taskList);
		processService.save(processList);
		return allocationList.size();
	}
	
	
	/**
	 * 获取完成的 任务进度
	 * @param allocation 分配任务
	 * @param number 完成数量
	 * @param timeMin 完成耗时
	 * @return
	 */
	public TaskProcess getFinishProcess(TaskAllocation allocation, int number, int timeMin) {
		TaskProcess process = getProcess(allocation,
				StrUtil.format(TaskConstant.REMARK_FINISH, number), TaskConstant.PROCESS_FINISH);
		process.setTimeMin(timeMin);
		process.setNumber(number);
		return process;
	}
	
	/**
	 * 检查当前分配任务是否满足完成数量
	 * @param allocationId 任务id
	 * @param number 完成数量，当为null时，表示全部完成
	 * @return
	 */
	public TaskAllocation checkFinal(Long allocationId, Integer number) {
		TaskAllocation allocation = findOne(allocationId);
		String taskNumber = allocation.getTask().getTaskNumber();
		if (number == null) {
			number = allocation.getSurplusNumber();
		}
		if (allocation.getStatus() == TaskConstant.TASK_END) {
			throw new ServiceException(StrUtil.format("任务：{} 已结束，无法完成！", taskNumber)) ;
		}
		if (number <= 0) {
			throw new ServiceException(StrUtil.format("任务：{} 完成任务数量不能小于等于0", taskNumber)) ;
		}
		if(allocation.getSurplusNumber() < number) {
			throw new ServiceException(StrUtil.format("任务：{} 完成任务数量不能大于剩余数量", taskNumber));
		}
		if (allocation.getStatus() == TaskConstant.ALLOCATION_END) {
			throw new ServiceException(StrUtil.format("任务：{} 已结束，无法完成", taskNumber));
		}
		return allocation;
	}
	
	/**
	 * 根据传入的任务分配，自动设置相应参数并返回
	 * @param allocation 分配任务
	 * @param remark 原因备注
	 * @param type 进度类型
	 * @return
	 */
	public TaskProcess getProcess(TaskAllocation allocation, String remark, int type) {
		TaskProcess process = new TaskProcess();
		process.setTaskAllocationId(allocation.getId());
		process.setTaskId(allocation.getTaskId());
		process.setNumber(allocation.getNumber());
		process.setRemark(remark);
		process.setType(type);
		return process;
	}

	/**
	 * 验证当前分配任务是否完成
	 * @param allocation 验证的分配任务
	 */
	public void isEnd(TaskAllocation allocation) {
		if (allocation != null && allocation.getSurplusNumber() == 0) {
			allocation.setStatus(TaskConstant.ALLOCATION_END);
		}
	}
	
	/**
	 * 获取开始、结束时间间隔时长 / 分钟
	 * @param allocation
	 * @return
	 */
	public int getStartOrPauseTimeMin(TaskAllocation allocation) {
		// 根据进度列表查找上次节点时间
		Date lastTime = allocation.getCreatedAt();
		// 获取该分配任务的所有进度
		List<TaskProcess> processLsit = processService.getAll(null, allocation.getId());
		for (TaskProcess process : processLsit) {
			int type = process.getType();
			if (type == TaskConstant.PROCESS_ALLOCATION || type == TaskConstant.PROCESS_FINISH
				|| type == TaskConstant.PROCESS_START || type == TaskConstant.PROCESS_PAUSE) {
				lastTime = process.getCreatedAt();
				break;
			}
		}
		return (int) DateUtil.between(lastTime, new Date(), DateUnit.MINUTE);
	}
	
	/**
	 * 完成任务时，计算当前耗时
	 * @param allocation
	 * @return
	 */
	public int getFinishTime(TaskAllocation allocation) {
		// 耗时总时长
		int allTime = 0;
		// 上一次开始时间
		List<TaskProcess> processList = processService.getAll(null, allocation.getId());
		// 本次耗时
		for (int i = 0; i < processList.size(); i++ ) {
			TaskProcess process = processList.get(i);
			int type = process.getType();
			if (type == TaskConstant.PROCESS_ALLOCATION || type == TaskConstant.PROCESS_FINISH) {
				// 上一次为开始、分配、完成，则以该次为起点，计算当前耗时
				int min = (int) DateUtil.between(process.getCreatedAt(), new Date(), DateUnit.MINUTE);
				allTime += min;
				break;
			}
			if (type == TaskConstant.PROCESS_PAUSE) {
				// 如果上一次是暂停，本次耗时为0
				break;
			}
			if (type == TaskConstant.PROCESS_START) {
				int min = (int) DateUtil.between(process.getCreatedAt(), new Date(), DateUnit.MINUTE);
				allTime += min;
				allTime += process.getTimeMin();
				// 如果上一次是开始，计算暂停时长
				for (int j = i+1; j < processList.size(); j++) {
					TaskProcess lastProcess = processList.get(j);
					int lastType = lastProcess.getType();
					if (lastType == TaskConstant.PROCESS_ALLOCATION || lastType == TaskConstant.PROCESS_FINISH) {
						// 本次开始距离上一次分配或完成
						break;
					}
					if (type == TaskConstant.PROCESS_START) {
						// 如果是开始时间，减去暂停时长
						allTime -= process.getTimeMin();
					}
					if (type == TaskConstant.PROCESS_PAUSE) {
						// 如果是暂停，加上之前的
						allTime += process.getTimeMin();
					}
				}
				break;
			}
		}
		return allTime;
	}

}
