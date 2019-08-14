package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.ledger.dao.SaleDao;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Mixed;
import com.bluewhite.ledger.entity.ReceivedMoney;
import com.bluewhite.ledger.entity.Sale;
@Service
public class SaleServiceImpl extends BaseServiceImpl<Sale, Long> implements SaleService  {
	
	@Autowired
	private SaleDao dao;
	@Autowired
	private MixedService mixedService;
	@Autowired
	private ReceivedMoneyService receivedMoneyService;
	
	@Override
	public PageResult<Sale> findSalePage(Sale param, PageParameter page) {
		Page<Sale> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}

			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}

			// 是否转批次
			if (param.getNewBacth() != null) {
				predicate.add(cb.equal(root.get("newBacth").as(Integer.class), param.getNewBacth()));
			}

			// 是否有版权
			if (param.getCopyright() != null) {
				predicate.add(cb.equal(root.get("copyright").as(Integer.class), param.getCopyright()));
			}
			
			// 是否业务员确认
			if (param.getDeliveryStatus() != null) {
				predicate.add(cb.equal(root.get("deliveryStatus").as(Integer.class), param.getDeliveryStatus()));
			}

			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.equal(root.get("product").get("name").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Sale> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public void updateFinanceSale(Sale sale) {
		if (sale.getId() != null) {
			Sale oldSale = dao.findOne(sale.getId());
			if (oldSale.getAudit() == 1) {
				throw new ServiceException("销售单已审核，无法修改");
			}
			// 计算总价
			BeanCopyUtils.copyNotEmpty(sale, oldSale, "");
			oldSale.setSumPrice(NumUtils.mul(oldSale.getCount(), oldSale.getPrice()));
			dao.save(oldSale);
		}
	}

	@Override
	public void updateUserSale(Sale sale) {
		if (sale.getId() != null) {
			Sale oldSale = dao.findOne(sale.getId());
			if (oldSale.getDeliveryStatus() == 1) {
				throw new ServiceException("销售单已确认，无法修改");
			}
			if (oldSale.getAudit() == 1) {
				throw new ServiceException("销售单已审核，无法修改");
			}
			// 根据收货数量确认状态
			if (sale.getDeliveryNumber() != null) {
				if (oldSale.getCount() == sale.getDeliveryNumber()) {
					sale.setDelivery(3);
				} else {
					sale.setDelivery(2);
				}
			}
			BeanCopyUtils.copyNotEmpty(sale, oldSale, "");
			dao.save(oldSale);
		}
	}

	@Override
	public int auditSale(String ids, Integer audit) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Sale sale = dao.findOne(id);
					if (sale.getDeliveryStatus() == 0) {
						throw new ServiceException("业务员未确认到货数量，无法审核");
					}
					if (audit == 1 && sale.getAudit() == 1) {
						throw new ServiceException("发货单已审核，请勿多次审核");
					}
					if (audit == 0 && sale.getAudit() == 0) {
						throw new ServiceException("发货单未审核，无需取消审核");
					}
					// 审核成功后,生成账单
					if (audit == 1) {
						// 货款总值
						sale.setOffshorePay(NumUtils.mul(sale.getCount(), sale.getPrice()));
						// 客户认可货款
						sale.setAcceptPay(NumUtils.mul(sale.getDeliveryNumber(), sale.getPrice()));
						// 争议货款
						sale.setDisputePay(
								NumUtils.sub(sale.getOffshorePay(), sale.getAcceptPay()));
					}
					sale.setAudit(audit);
					dao.save(sale);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Bill> collectBill(Bill bill) {
		bill.setFlag(1);
		bill.setAudit(1);
		List<Bill> billList = new ArrayList<>();
		List<Sale> pList = findSaleList(bill);
		List<Mixed> mixedList = mixedService.findList(bill);
		List<ReceivedMoney> receivedMoneyList = receivedMoneyService.receivedMoneyList(bill);
		Map<Long, List<Sale>> mapPList = pList.stream()
				.collect(Collectors.groupingBy(Sale::getCustomerId, Collectors.toList()));
		Map<Long, List<Mixed>> mapMixedList = mixedList.stream()
				.collect(Collectors.groupingBy(Mixed::getCustomerId, Collectors.toList()));
		Map<Long, List<ReceivedMoney>> receivedMoneyMap = receivedMoneyList.stream()
				.collect(Collectors.groupingBy(ReceivedMoney::getCustomerId, Collectors.toList()));
		for (Long ps : mapPList.keySet()) {
			List<Sale> psList = mapPList.get(ps);
			List<Mixed> mixeds = mapMixedList.get(ps);
			List<ReceivedMoney> receivedMoneys = receivedMoneyMap.get(ps);
			Bill bl = new Bill();
			NumUtils.setzro(bl);
			bl.setBillDate(bill.getOrderTimeBegin());
			bl.setCustomerName(psList.get(0).getCustomer().getName());
			bl.setCustomerId(psList.get(0).getCustomerId());
			if (psList != null && psList.size() > 0) {
				// 货款总值
				bl.setOffshorePay(NumUtils.round(psList.stream().mapToDouble(Sale::getOffshorePay).sum(), 2));
				// 确认货款
				bl.setAcceptPay(NumUtils.round(psList.stream().mapToDouble(Sale::getAcceptPay).sum(), 2));
				// 争议货款
				bl.setDisputePay(NumUtils.round(psList.stream().mapToDouble(Sale::getDisputePay).sum(), 2));
			}
			if (mixeds != null && mixeds.size() > 0) {
				// 杂支
				bl.setAcceptPayable(NumUtils.round(mixeds.stream().mapToDouble(Mixed::getMixPrice).sum(), 2));
			}
			if (receivedMoneys != null && receivedMoneys.size() > 0) {
				// 已到货款
				bl.setArrivalPay(
						NumUtils.round(receivedMoneys.stream().mapToDouble(ReceivedMoney::getReceivedMoney).sum(), 2));
			}
			// 未到货款
			bl.setNonArrivalPay(
					NumUtils.sub(NumUtils.sum(bl.getAcceptPay(), bl.getAcceptPayable()), bl.getArrivalPay()));
			// 客户多付货款
			bl.setOverpaymentPay(bl.getArrivalPay() < 0 ? bl.getArrivalPay() : 0);
			billList.add(bl);
		}
		return billList;
	}


	@Override
	public int auditUserSale(String ids, Integer deliveryStatus) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Sale sale = dao.findOne(id);
					if (sale.getAudit() == 0) {
						if (deliveryStatus == 1 && sale.getDeliveryStatus() == 1) {
							throw new ServiceException("销售单已被确认，请勿多次确认");
						}
						if (deliveryStatus == 0 && sale.getDeliveryStatus() == 0) {
							throw new ServiceException("销售单未确认，无需取消");
						}
					}
					if (sale.getAudit() == 1) {
						throw new ServiceException("发货单已审核，无法操作");
					}
					sale.setDeliveryStatus(deliveryStatus);
					dao.save(sale);
					count++;
				}
			}
		}
		return count;
	}

	

	@Override
	public List<Sale> findSaleList(Bill param) {
		List<Sale> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public List<Sale> getSalePrice(Sale sale) {
		return dao.findByProductIdAndCustomerIdAndAudit(sale.getProductId(),
				sale.getCustomerId(), 1);
	}
}
