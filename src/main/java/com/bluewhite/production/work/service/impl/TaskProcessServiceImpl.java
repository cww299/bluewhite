package com.bluewhite.production.work.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.production.work.dao.TaskProcessDao;
import com.bluewhite.production.work.entity.TaskAllocation;
import com.bluewhite.production.work.entity.TaskProcess;
import com.bluewhite.production.work.service.TaskProcessService;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public class TaskProcessServiceImpl extends BaseServiceImpl<TaskProcess, Long> implements TaskProcessService {

	@Autowired
	private TaskProcessDao dao;
	
	@Override
	public List<TaskProcess> getAll(Long taskId, Long taskAllocationId) {
		List<Integer> types = new ArrayList<>();
		types.add(1); types.add(2); types.add(3);
		if(taskId == null) {
			types.add(4); types.add(5); types.add(6);
		}
		if(taskAllocationId == null) {
			types.add(0);
		}
		List<TaskProcess> list = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            if (taskId != null) {
                predicate.add(cb.equal(root.get("taskId").as(Long.class), taskId));
            }
            if(taskAllocationId != null) {
            	predicate.add(cb.equal(root.get("taskAllocationId").as(Long.class), taskAllocationId));
            }
            Path<Object> path = root.get("type");
			CriteriaBuilder.In<Object> in = cb.in(path);
			for(Integer type : types){
				in.value(type);
			}
			predicate.add(in);
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
		});
		// 按id降序
		return list.stream().sorted(Comparator.comparing(TaskProcess::getId).reversed()).collect(Collectors.toList());
	}

	@Override
	public int deleteAllocation(List<TaskAllocation> list) {
		List<Long> allocationIds = new ArrayList<>();
		list.stream().forEach(allocation -> {
			allocationIds.add(allocation.getId());
		});
		return dao.deleteByTaskAllocationIdIn(allocationIds);
	}

}
