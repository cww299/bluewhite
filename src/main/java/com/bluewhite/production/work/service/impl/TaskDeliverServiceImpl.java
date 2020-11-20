package com.bluewhite.production.work.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.production.work.common.TaskConstant;
import com.bluewhite.production.work.dao.TaskDeliverDao;
import com.bluewhite.production.work.entity.TaskDeliver;
import com.bluewhite.production.work.entity.TaskWork;
import com.bluewhite.production.work.service.TaskDeliverService;
import com.bluewhite.production.work.service.TaskWorkService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

@Service
public class TaskDeliverServiceImpl extends BaseServiceImpl<TaskDeliver, Long> implements TaskDeliverService{

	@Autowired
	private TaskDeliverDao dao;
	@Autowired
	private TaskWorkService taskService;
	
	@Override
	public PageResult<TaskDeliver> findPages(TaskDeliver deliver, PageParameter page) {
		Page<TaskDeliver> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (deliver.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), deliver.getId()));
            }
            // 任务id
            if (deliver.getTaskId() != null) {
                predicate.add(cb.equal(root.get("taskId").as(Long.class), deliver.getTaskId()));
            }
            // 分配人员
            if (StrUtil.isNotBlank(deliver.getProductName())) {
                predicate.add(cb.like(root.get("productName").as(String.class), "%" + deliver.getProductName() + "%"));
            }
            // 任务编号
            if (StrUtil.isNotBlank(deliver.getTaskNumber())) {
            	predicate.add(cb.equal(root.get("taskNumber").as(String.class), deliver.getTaskNumber()));
            }
            // 预警日期
            if (deliver.getOrderTimeBegin() != null && deliver.getOrderTimeEnd() != null) {
                predicate.add(cb.between(root.get("deliverDate").as(Date.class), deliver.getOrderTimeBegin(),
                		deliver.getOrderTimeEnd()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
		}, page);
	    PageResultStat<TaskDeliver> result = new PageResultStat<>(pages, page);
		return result;
	}
	
	@Override
	public PageResult<TaskDeliver> warnList(TaskDeliver deliver, PageParameter page) {
		if (page == null) {
			page = new PageParameter();
		}
		if (deliver == null) {
			deliver = new TaskDeliver();
		}
		Props props = new Props("additional.properties");
		Integer day = Integer.parseInt(props.getProperty("pro.task.warnDay"));
		Date begin = DateUtil.beginOfDay(new Date()).toJdkDate();
		Date end = DateUtil.endOfDay(DateUtil.offsetDay(begin, day)).toJdkDate();
		deliver.setOrderTimeBegin(begin);
		deliver.setOrderTimeEnd(end);
		return findPages(deliver, page);
	}

	@Override
	public void saveDeliver(TaskDeliver deliver) {
		if (deliver.getId() != null) {
			TaskDeliver oldDeliver = findOne(deliver.getId());
			BeanCopyUtils.copyNotEmpty(deliver, oldDeliver);
			deliver = oldDeliver;
		} else {
			TaskWork task = taskService.findOne(deliver.getTaskId());
			deliver.setTaskNumber(task.getTaskNumber());
			String name = task.getProductName();
			if (task.getProcessesName() != null) {
				name += (TaskConstant.NAME_SPLIT + task.getProcessesName());
			}
			deliver.setProductName(name);
		}
		save(deliver);
	}

}
