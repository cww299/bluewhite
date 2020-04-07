package com.bluewhite.production.procedure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.dao.TaskDao;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;

@Service
public class ProcedureServiceImpl extends BaseServiceImpl<Procedure, Long> implements ProcedureService {

	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private BacthDao bacthDao;
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskService taskService;

	@Override
	public List<Procedure> findByProductIdAndType(Long productId, Integer type, Integer flag) {
		return procedureDao.findByProductIdAndTypeAndFlag(productId, type, flag);
	}

	@Override
	@Transactional
	public void countPrice(Procedure procedure) {
		List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(procedure.getProductId(),
				procedure.getType(), 0);
		//裁剪
		if (procedure.getType() != null && procedure.getType() == 5) {
			procedureList = procedureList.stream().filter(Procedure -> Procedure.getSign() == procedure.getSign())
					.collect(Collectors.toList());
		}
		if(procedureList.size()>0){
			// 计算部门生产总价
			double sumTime = procedureList.stream().mapToDouble(Procedure::getWorkingTime).sum();
			double sumPrice = ProTypeUtils.sumProTypePrice(sumTime, procedure.getType());
			double sumDeeblePrice = ProTypeUtils.getDEEDLE(sumTime, procedure.getType());
			// 计算外发价格
			double price = ProTypeUtils.sumProTypeHairPrice(procedureList, procedure.getType());
			procedureList.stream().forEach(p->{
				p.setDepartmentPrice(NumUtils.round(sumPrice, 4));
				p.setDeedlePrice(NumUtils.round(sumDeeblePrice, 4));
				p.setHairPrice(NumUtils.round(price, 4));
			});
		}
		procedureDao.save(procedureList);
	}

	@Override
	public List<Procedure> findByProductIdAndProcedureTypeIdAndType(Long productId, Long procedureTypeId,
			Integer type) {
		return procedureDao.findByProductIdAndProcedureTypeIdAndType(productId, procedureTypeId, type);
	}

	@Override
	public List<Procedure> findList(Procedure param) {
		List<Procedure> list = procedureDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按工序类型
			if (param.getProcedureTypeId() != null) {
				predicate.add(cb.equal(root.get("procedureTypeId").as(Long.class), param.getProcedureTypeId()));
			}
			// 按产品id
			if (!StringUtils.isEmpty(param.getProductId())) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			// 按类型
			if (!StringUtils.isEmpty(param.getType())) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			
			// 裁剪特殊业务
			if (!StringUtils.isEmpty(param.getSign())) {
				predicate.add(cb.equal(root.get("sign").as(Integer.class), param.getSign()));
			}
			// 是否返工
			if (!StringUtils.isEmpty(param.getFlag())) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		if (param.getBacthId() != null) {
			// 查出所分配的批次
			Bacth bacth = bacthDao.findOne(param.getBacthId());
			// 循环出所有任务，当所任务的工序和当前工序想匹配时，记录其数值
			for (Procedure pro : list) {
				// 任务总数
				int number = bacth.getNumber();
				if (bacth.getTasks().size() > 0) {
					for (Task task : bacth.getTasks()) {
						if (task.getProcedureId().equals(pro.getId())) {
							number = number - task.getNumber();
						}
					}
				}
				pro.setResidualNumber(number);
			}
		}
		return list;
	}

	@Override
	public List<Procedure> saveList(List<Procedure> procedureList) {
		return procedureDao.save(procedureList);
	}
	

	@Override
	public void deleteProcedure(Long id) {
		List<Task> taskList = taskDao.findByProcedureId(id);
		String ids = taskList.stream().map(task -> String.valueOf(task.getId())).collect(Collectors.joining(","));
		taskService.deleteTask(ids);
		Procedure procedure = procedureDao.findOne(id);
		procedureDao.delete(id);
		this.countPrice(procedure);
	}

}
