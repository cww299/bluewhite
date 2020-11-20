package com.bluewhite.production.work.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.production.work.common.TaskConstant;
import com.bluewhite.production.work.dao.TaskDeliverDao;
import com.bluewhite.production.work.dao.TaskWorkDao;
import com.bluewhite.production.work.entity.TaskWork;
import com.bluewhite.production.work.service.TaskWorkService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public class TaskWorkServiceImpl  extends BaseServiceImpl<TaskWork, Long> implements TaskWorkService {

	@Autowired
	private TaskWorkDao dao;
	@Autowired
	private TaskDeliverDao deliverDao;
	
	@Override
	public PageResult<TaskWork> findPages(TaskWork task, PageParameter page) {
		Page<TaskWork> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (task.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), task.getId()));
            }
            // 任务编号
            if (StrUtil.isNotBlank(task.getTaskNumber())) {
            	predicate.add(cb.equal(root.get("taskNumber").as(String.class), task.getTaskNumber()));
            }
            // 任务类型
            if (task.getType() != null) {
            	predicate.add(cb.equal(root.get("type").as(Integer.class), task.getType()));
            }
            // 产品信息
            if (task.getProductId() != null) {
            	predicate.add(cb.equal(root.get("productId").as(Long.class), task.getProductId()));
            }
            if (StrUtil.isNotBlank(task.getProductName())) {
            	predicate.add(cb.like(root.get("productName").as(String.class), "%" + task.getProductName() + "%"));
            }
            // 工序信息
            if (task.getProcessesId() != null) {
            	predicate.add(cb.equal(root.get("processesId").as(Long.class), task.getProcessesId()));
            }
            if (StrUtil.isNotBlank(task.getProcessesName())) {
            	predicate.add(cb.like(root.get("processesName").as(String.class), "%" + task.getProcessesName() + "%"));
            }
            // 任务状态
            if (task.getStatus() != null) {
            	predicate.add(cb.equal(root.get("status").as(Integer.class), task.getStatus()));
            }
            // 创建时间
            if (task.getOrderTimeBegin() != null && task.getOrderTimeEnd() != null) {
                predicate.add(cb.between(root.get("createdAt").as(Date.class), task.getOrderTimeBegin(),
                		task.getOrderTimeEnd()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
		}, page);
	    PageResultStat<TaskWork> result = new PageResultStat<>(pages, page);
		return result;
	}
	
	@Override
	@Transactional
	public int deleteTask(String ids) {
		List<TaskWork> list = new ArrayList<>();
		List<Long> taskIds = new ArrayList<>();
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.parseLong(arrIds[i]);
			TaskWork t = findOne(id);
			if(t.getStatus() != TaskConstant.TASK_UNALLOCATION) {
				throw new ServiceException("任务：" + t.getTaskNumber() + " 不是未分配状态，无法删除任务！");
			}
			list.add(t);
			taskIds.add(t.getId());
		}
		baseRepository.deleteInBatch(list);
		deliverDao.deleteByTaskIdIn(taskIds);
		return list.size();
	}
	
	@Override
	public void saveTask(TaskWork task) {
		// 修改
		if(task.getId() != null) {
			// 如果是修改, 只能修改：备注、任务数量。且任务数量不能小于已分配数量
			TaskWork t = findOne(task.getId());
			if (t.getStatus() == TaskConstant.TASK_END) {
				throw new ServiceException("任务已完成，无法修改！");
			}
			if(t.getAllocationNumber() > task.getNumber()) {
				throw new ServiceException("任务数量不能小于已分配数量！");
			}
			t.setNumber(task.getNumber());
			t.setTimeSecond(task.getTimeSecond());
			t.setRemark(task.getRemark());
			dao.save(t);
			return;
		}
		// 新增
		String taskNumber = getNumber();
		if(!StringUtils.isEmpty(task.getProcessesIds())) {
			// 按工序新增
			String[] processesIds = task.getProcessesIds().split(",");
			String[] processesNames = task.getProcessesNames().split(",");
			String[] timeSeconds = task.getTimeSeconds().split(",");
			List<TaskWork> list = new ArrayList<>();
			for (int i = 0; i < processesIds.length; i++) {
				TaskWork t = new TaskWork();
				BeanUtil.copyProperties(task, t);
				t.setTaskNumber(taskNumber);
				t.setProcessesId(Long.parseLong(processesIds[i]));
				t.setProcessesName(processesNames[i]);
				t.setTimeSecond(Double.parseDouble(timeSeconds[i]));
				t.setStatus(TaskConstant.TASK_UNALLOCATION);
				list.add(t);
			}
			dao.save(list);
		} else {
			task.setTaskNumber(taskNumber);
			task.setStatus(TaskConstant.TASK_UNALLOCATION);
			dao.save(task);
		}
	}
	
	/**
	 * 获取任务编号
	 * @return
	 */
	public String getNumber() {
		Date time = new Date();
		int size = getTaskCount();
		size++;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return "T" + sdf.format(time) + StringUtil.get0LeftString(size, 5);
	}

	/**
	 * 获取某一个日期任务数量
	 * 因为存在任务删除的情况，因此不能通过计算任务数量生成任务订单号，获取任务表最后一条记录，反解析任务编号获取排序
	 */
	public int getTaskCount() {
		PageParameter page = new PageParameter();
		page.setSize(1);
		PageResult<TaskWork> pages = findPages(new TaskWork(), page);
		List<TaskWork> list = pages.getRows();
		int size = 0;
		if (CollectionUtil.isNotEmpty(list)) {
			TaskWork task = CollectionUtil.getFirst(list);
			// 如果最后一条任务单是当天的
			if (DateUtil.compare(task.getCreatedAt(), DateUtil.beginOfDay(new Date())) >= 0) {
				// 任务编号最后5位
				String taskNumber = task.getTaskNumber();
				size = Integer.parseInt(taskNumber.substring(taskNumber.length() - 5));
			}
		}
		return size;
	}

}
