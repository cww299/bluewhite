package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.ledger.dao.MaterialRequisitionDao;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;

@Service
public class MaterialRequisitionServiceImpl extends BaseServiceImpl<MaterialRequisition, Long>
		implements MaterialRequisitionService {

	@Autowired
	private MaterialRequisitionDao dao;
	@Autowired
	private OrderMaterialDao orderMaterialDao;
	@Autowired
	private ScatteredOutboundDao scatteredOutboundDao;
	@Autowired
	private MaterielDao materielDao;

	@Override
	public void saveMaterialRequisition(MaterialRequisition materialRequisition) {
		if (materialRequisition.getScatteredOutboundId() != null) {
			ScatteredOutbound scatteredOutbound = scatteredOutboundDao.findOne(materialRequisition.getScatteredOutboundId());
			// 查询是否已耗料出库（采购单虚拟库存）
			if (scatteredOutbound == null) {
				throw new ServiceException("还未分散出库，无法生成领料单");
			}
			materialRequisition.setDosage(
					NumUtils.div(NumUtils.mul(scatteredOutbound.getDosage(), materialRequisition.getProcessNumber()),
							scatteredOutbound.getDosageNumber(), 2));
			// 获取该耗料单所有的领取用料
			if (materialRequisition.getDosage() > scatteredOutbound.getResidueDosage()) {
				throw new ServiceException("当前领料单已超出耗料剩余数量，无法生成，请核实");
			}
			//剩余耗料
			scatteredOutbound.setResidueDosage(
					NumUtils.sub(scatteredOutbound.getResidueDosage(), materialRequisition.getDosage()));
			//剩余任务数量
			scatteredOutbound.setResidueDosageNumber(scatteredOutbound.getResidueDosageNumber()-materialRequisition.getProcessNumber());
			materialRequisition.setAudit(0);
			materialRequisition.setRequisition(0);
			scatteredOutboundDao.save(scatteredOutbound);
			save(materialRequisition);
		}
	}

	@Override
	public void updateMaterialRequisition(MaterialRequisition materialRequisition) {
		if(materialRequisition.getId()!=null){
			MaterialRequisition ot = findOne(materialRequisition.getId());
			if(ot.getAudit()==1){
				throw new ServiceException("已审核无法修改");
			}
		}
		if (materialRequisition.getScatteredOutboundId() != null) {
			ScatteredOutbound scatteredOutbound = scatteredOutboundDao.findOne(materialRequisition.getScatteredOutboundId());
			// 查询是否已耗料出库（采购单虚拟库存）
			if (scatteredOutbound == null) {
				throw new ServiceException("还未分散出库，无法生成领料单");
			}
			materialRequisition.setDosage(
					NumUtils.div(NumUtils.mul(scatteredOutbound.getDosage(), materialRequisition.getProcessNumber()),
							scatteredOutbound.getOrderMaterial().getOrder().getNumber(), 2));
			// 获取该耗料单所有的领取用料
			if (materialRequisition.getDosage() > scatteredOutbound.getResidueDosage()) {
				throw new ServiceException("当前领料单已超出耗料剩余数量，无法生成，请核实");
			}
			//剩余耗料
			scatteredOutbound.setResidueDosage(NumUtils.sub(scatteredOutbound.getResidueDosage(), materialRequisition.getDosage()));
			//剩余任务数量
			scatteredOutbound.setResidueDosageNumber(scatteredOutbound.getResidueDosageNumber()-materialRequisition.getProcessNumber());
			materialRequisition.setAudit(0);
			materialRequisition.setRequisition(0);
			scatteredOutboundDao.save(scatteredOutbound);
			save(materialRequisition);
		}
	}

	@Override
	public PageResult<MaterialRequisition> findPages(PageParameter page, MaterialRequisition param) {
		Page<MaterialRequisition> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按合同id
			if (param.getOrderId() != null) {
				predicate.add(cb.equal(root.get("orderId").as(Long.class), param.getOrderId()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按审核日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("auditTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<MaterialRequisition> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int auditMaterialRequisition(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					MaterialRequisition materialRequisition = findOne(id);
					if (materialRequisition.getAudit() == 1) {
						throw new ServiceException("第" + (i + 1) + "条领料单已审核，请勿多次审核");
					}
					materialRequisition.setAudit(1);
					save(materialRequisition);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int deleteMaterialRequisition(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					MaterialRequisition materialRequisition = findOne(id);
					if (materialRequisition.getAudit() == 1) {
						throw new ServiceException("第" + (i + 1) + "条领料单已审核，无法删除");
					}
					ScatteredOutbound scatteredOutbound = materialRequisition.getScatteredOutbound();
					scatteredOutbound.setResidueDosage(NumUtils.sum(scatteredOutbound.getResidueDosage(),materialRequisition.getDosage()));
					scatteredOutbound.setResidueDosageNumber(scatteredOutbound.getResidueDosageNumber()+materialRequisition.getProcessNumber());
					scatteredOutboundDao.save(scatteredOutbound);
					delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int outboundMaterialRequisition(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					MaterialRequisition materialRequisition = findOne(id);
					//面辅料仓库获取采购库存单，更新采购单实际库存
					OrderProcurement orderProcurement = materialRequisition.getScatteredOutbound().getOrderProcurement();
					orderProcurement.getMateriel().setInventoryNumber(NumUtils.sub(orderProcurement.getMateriel().getInventoryNumber(), materialRequisition.getDosage()));
					materielDao.save(orderProcurement.getMateriel());
					materialRequisition.setRequisition(1);
					save(materialRequisition);
					count++;
				}
			}
		}
		return count;
	}

}
