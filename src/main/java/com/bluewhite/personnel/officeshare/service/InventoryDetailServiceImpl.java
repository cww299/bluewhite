package com.bluewhite.personnel.officeshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.officeshare.dao.InventoryDetailDao;
import com.bluewhite.personnel.officeshare.dao.OfficeSuppliesDao;
import com.bluewhite.personnel.officeshare.entity.InventoryDetail;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;

@Service
public class InventoryDetailServiceImpl extends BaseServiceImpl<InventoryDetail, Long>
		implements InventoryDetailService {

	@Autowired
	private InventoryDetailDao dao;
	@Autowired
	private OfficeSuppliesDao officeSuppliesDao;

	@Override
	public PageResult<InventoryDetail> findPages(InventoryDetail param, PageParameter page) {
		Page<InventoryDetail> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按办公用品名称过滤
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(
						cb.like(root.get("OfficeSupplies").get("name").as(String.class), "%" + param.getName() + "%"));
			}
			// 按部门
			if (param.getOrgNameId() != null) {
				predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按出库入库
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 按日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<InventoryDetail> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void addInventoryDetail(InventoryDetail onventoryDetail) {
		OfficeSupplies officeSupplies = officeSuppliesDao.findOne(onventoryDetail.getOfficeSuppliesId());
		// 出库
		if (onventoryDetail.getFlag() == 0) {
			if (officeSupplies.getInventoryNumber() == 0) {
				throw new ServiceException("数量为0，无法出库");
			} else {
				officeSupplies.setInventoryNumber(officeSupplies.getInventoryNumber() - onventoryDetail.getNumber());
				onventoryDetail.setOutboundCost(NumUtils.mul(officeSupplies.getPrice(), onventoryDetail.getNumber()));
			}
		}
		// 入库
		if (onventoryDetail.getFlag() == 1) {
			officeSupplies.setInventoryNumber(officeSupplies.getInventoryNumber() + onventoryDetail.getNumber());
		}
		officeSupplies.setLibraryValue(NumUtils.mul(officeSupplies.getInventoryNumber(), officeSupplies.getPrice()));
		officeSuppliesDao.save(officeSupplies);
		dao.save(onventoryDetail);
	}

	@Override
	@Transactional
	public int deleteInventoryDetail(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					InventoryDetail onventoryDetail = dao.findOne(id);
					OfficeSupplies officeSupplies = officeSuppliesDao.findOne(onventoryDetail.getOfficeSuppliesId());
					// 出库
					if (onventoryDetail.getFlag() == 0) {
						officeSupplies
								.setInventoryNumber(officeSupplies.getInventoryNumber() + onventoryDetail.getNumber());
					}
					// 入库
					if (onventoryDetail.getFlag() == 1) {
						officeSupplies
								.setInventoryNumber(officeSupplies.getInventoryNumber() - onventoryDetail.getNumber());
					}
					officeSupplies.setLibraryValue(
							NumUtils.mul(officeSupplies.getInventoryNumber(), officeSupplies.getPrice()));
					officeSuppliesDao.save(officeSupplies);
					dao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> statisticalInventoryDetail(InventoryDetail onventoryDetail) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<InventoryDetail> onventoryDetailList = dao.findByFlagAndTimeBetween(0, onventoryDetail.getOrderTimeBegin(),
				onventoryDetail.getOrderTimeEnd());
		double sumCostList = onventoryDetailList.stream().mapToDouble(InventoryDetail::getOutboundCost).sum();
		// 按人员分组
		Map<Long, List<InventoryDetail>> mapAttendance = onventoryDetailList.stream()
				.filter(InventoryDetail -> InventoryDetail.getOrgNameId() != null)
				.collect(Collectors.groupingBy(InventoryDetail::getOrgNameId, Collectors.toList()));
		// 后勤部费用分摊到所有部门
		double logisticsCost = onventoryDetailList.stream()
				.filter(InventoryDetail -> InventoryDetail.getOutboundCost() == 60)
				.mapToDouble(InventoryDetail::getOutboundCost).sum();
		if (mapAttendance.size() > 0) {
			//均分费用
			double averageLogisticsCost = NumUtils.div(logisticsCost, mapAttendance.size(), 2);
			for (Long ps1 : mapAttendance.keySet()) {
				Map<String, Object> map = new HashMap<>();
				List<InventoryDetail> psList = mapAttendance.get(ps1);
				if (psList.get(0).getOrgName().getId() != 60) {
					double sumCost = psList.stream().mapToDouble(InventoryDetail::getOutboundCost).sum();
					sumCost = NumUtils.sum(sumCost,averageLogisticsCost);
					map.put("orgName", psList.get(0).getOrgName().getName());
					map.put("sumCost", NumUtils.round(sumCost, 2));
					map.put("accounted", NumUtils.mul(NumUtils.div(sumCost, sumCostList, 2), 100) + "%");
					mapList.add(map);
				}
			}
		}
		return mapList;
	}

}
