package com.bluewhite.ledger.service;

import java.util.ArrayList;
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
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;

@Service
public class OrderMaterialServiceImpl extends BaseServiceImpl<OrderMaterial, Long> implements OrderMaterialService {

	@Autowired
	private OrderMaterialDao dao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductMaterialsService productMaterialsService;
	@Autowired
	private TailorService tailorService;
	@Autowired
	private MaterielDao materielDao;

	@Override
	public PageResult<OrderMaterial> findPages(OrderMaterial param, PageParameter page) {
		Page<OrderMaterial> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按订单
			if (param.getOrderId() != null) {
				predicate.add(cb.equal(root.get("orderId").as(Long.class), param.getOrderId()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("order").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OrderMaterial> result = new PageResult<>(pages, page);
		result.getRows().stream().forEach(ot -> {
			// 审核时获取采购单的库存的剩余，进行库存状态的判断，以及有库存的直接生成库存分散单
			double number = ot.getMateriel().getInventoryNumber();
			// 库存充足
			if (number > ot.getDosage()) {
				ot.setState(1);
			}
			// 库存不足
			if (number > 1) {
				ot.setState(2);
			}
			// 量不足
			if (number < ot.getDosage() && number > 1) {
				ot.setState(3);
			}
		});
		return result;
	}

	@Override
	public int confirmOrderMaterial(String ids) {
		List<OrderMaterial> orderMaterialList = new ArrayList<>();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order = orderDao.findOne(id);
					if (order.getOrderMaterials().size() > 0) {
						throw new ServiceException("第" + (i + 1) + "条下单合同，已生成耗料用料，请勿多次生成");
					}
					if (order.getProductId() != null) {
						// 将裁片的耗料添加
						List<CutParts> cutPartsList = cutPartsService.findByProductId(order.getProductId());
						if (cutPartsList.size() > 0) {
							cutPartsList.stream().forEach(c -> {
								c.setNumber(order.getNumber());
								cutPartsService.countComposite(c);
								OrderMaterial orderMaterial = new OrderMaterial();
								orderMaterial.setOrderId(id);
								orderMaterial.setAudit(0);
								orderMaterial.setMaterielId(c.getMaterielId());
								orderMaterial.setUnitId(c.getUnitId());
								orderMaterial.setDosage(c.getBatchMaterial());
								Tailor tailor = tailorService.findOne(c.getTailorId());
								orderMaterial.setReceiveModeId(tailor.getTailorTypeId());
								orderMaterialList.add(orderMaterial);
								if (c.getComposite() == 1) {
									OrderMaterial orderMaterialComposite = new OrderMaterial();
									orderMaterial.setOrderId(id);
									orderMaterial.setMaterielId(c.getComplexMaterielId());
									orderMaterial.setUnitId(c.getUnitId());
									orderMaterial.setAudit(0);
									orderMaterial.setDosage(c.getComplexBatchMaterial());
									orderMaterial.setReceiveModeId(tailor.getTailorTypeId());
									orderMaterialList.add(orderMaterialComposite);
								}
							});
						} else {
							throw new ServiceException("当前产品暂无面料，请联系相关人员添加");
						}
						// 将辅料的耗料添加
						List<ProductMaterials> productMaterialsList = productMaterialsService
								.findByProductId(order.getProductId());
						if (productMaterialsList.size() > 0) {
							productMaterialsList.stream().forEach(m -> {
								m.setNumber(order.getNumber());
								productMaterialsService.countComposite(m);
								OrderMaterial orderMaterial = new OrderMaterial();
								orderMaterial.setAudit(0);
								orderMaterial.setOrderId(id);
								orderMaterial.setMaterielId(m.getMaterielId());
								orderMaterial.setUnitId(m.getUnitId());
								orderMaterial.setDosage(m.getBatchMaterial());
								// 当为辅料时，领取模式变成压货环节
								orderMaterial.setReceiveModeId(m.getOverstockId());
								orderMaterialList.add(orderMaterial);
							});
						}
					}
					count++;
				}
			}
		}
		dao.save(orderMaterialList);
		return count;
	}

	@Override
	public void updateOrderMaterial(OrderMaterial orderMaterial) {
		OrderMaterial ot = findOne(orderMaterial.getId());
		update(orderMaterial, ot);
	}

	@Override
	public int deleteOrderMaterial(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderMaterial ot = findOne(id);
					if (ot.getAudit() == 1) {
						throw new ServiceException("第" + (i + 1) + "条耗料已审核，无法删除");
					}
					delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int auditOrderMaterial(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderMaterial ot = findOne(id);
					ot.setAudit(1);
					save(ot);
					count++;
				}
			}
		}
		return count;
	}
}
