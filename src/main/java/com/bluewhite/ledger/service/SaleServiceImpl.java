package com.bluewhite.ledger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.dao.SaleDao;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.Mixed;
import com.bluewhite.ledger.entity.ReceivedMoney;
import com.bluewhite.ledger.entity.Sale;
import com.bluewhite.ledger.entity.poi.SalePoi;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.temporarypack.QuantitativeChild;
import com.bluewhite.production.temporarypack.QuantitativeChildDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
@Service
public class SaleServiceImpl extends BaseServiceImpl<Sale, Long> implements SaleService  {
	
	@Autowired
	private SaleDao dao;
	@Autowired
	private MixedService mixedService;
	@Autowired
	private ReceivedMoneyService receivedMoneyService;
	@Autowired
	private QuantitativeChildDao quantitativeChildDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private UserDao userDao;
	
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
			// 按业务员
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.like(root.get("customer").get("user").get("userName").as(String.class),
						"%" + param.getUserName() + "%"));
			}
			// 按客户类型
			if (param.getCustomerType() != null) {
				predicate.add(cb.equal(root.get("customer").get("customerTypeId").as(Long.class),
						param.getCustomerType()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
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
			// 按单价异常
			if (param.getPriceError() != null) {
				predicate.add(cb.equal(root.get("priceError").as(Integer.class), param.getPriceError()));
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
					if (audit == 1 && sale.getAudit() == 1) {
						throw new ServiceException("发货单已审核，请勿多次审核");
					}
					if (audit == 0 && sale.getAudit() == 0) {
						throw new ServiceException("发货单未审核，无需取消审核");
					}
					if (audit == 1) {
						if (sale.getPrice()==null) {
							throw new ServiceException("单只价格未填写，无法审核");
						}
					}
					// 审核成功后,生成账单
					if (audit == 1) {
						// 货款总值
						sale.setOffshorePay(NumUtils.mul(sale.getCount(), sale.getPrice()));
						// 客户认可货款
						sale.setAcceptPay(NumUtils.mul(NumUtils.setzro(sale.getDeliveryNumber()), sale.getPrice()));
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
	public int priceError(String ids, Integer priceError) {
		int count = 0;
		List<Long> allId = new ArrayList<Long>();
		if (!StringUtils.isEmpty(ids)) {
			for(String id :  ids.split(",")) {
				allId.add(Long.parseLong(id));
			}
			List<Sale> list = dao.findByIdIn(allId);
			for(Sale sale : list) {
				sale.setPriceError(priceError);
			}
			dao.save(list);
			count = list.size();
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
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 是否电商
			if (param.getCustomerType() != null) {
				predicate.add(cb.equal(root.get("customer").get("customerTypeId").as(Long.class), param.getCustomerType()));
			}
			// 按业务员
            if (param.getUserName() != null && !param.getUserName().isEmpty()) {
            	predicate.add(cb.like(root.get("customer").get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
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
		// 457=电商  459=线下
		if(sale.getCustomerType().equals(457L)) {
			return dao.findByProductIdAndBacthNumberAndAuditOrderBySendDateDesc(sale.getProductId(), 
					sale.getBacthNumber(), 1);
		}
		return dao.findByProductIdAndCustomerIdAndAudit(sale.getProductId(),
				sale.getCustomerId(), 1);
	}

	@Override
	@Transactional
	public int deleteSale(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			List<QuantitativeChild> childList = new ArrayList<QuantitativeChild>();
			List<Sale> saleList = new ArrayList<Sale>();
            String[] idStrings = ids.split(",");
            for(String id : idStrings) {
            	Sale sale = dao.findOne(Long.parseLong(id));
            	if(sale.getDeliveryStatus()!=0) {
            		throw new ServiceException("销售单："+sale.getSaleNumber() + "已确认到货无法删除，请先取消确认");
            	}
            	saleList.add(sale);
            	List<QuantitativeChild> childs = quantitativeChildDao.findBySaleId(sale.getId());
            	if(childs != null && childs.size() > 0) {
            		childs.forEach(c -> {
            			c.setSaleId(null);
            			childList.add(c);
            		});
            	}
            }
            count = saleList.size();
            dao.delete(saleList);
            if(childList.size() > 0)
            	quantitativeChildDao.save(childList);
		}
		return count;
	}

	@Override
	@Transactional
	public CommonResponse excelAddSale(ExcelListener excelListener, Long customerType) {
		// 457=电商  459=线下
		boolean isDs = customerType == 457;
		int count = 0;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 获取导入的销售单
	    List<SalePoi> errorList = new ArrayList<>();
        List<Object> excelListenerList = excelListener.getData();
        for (Object object : excelListenerList) {
        	Integer row = (excelListenerList.indexOf(object) + 1);
        	SalePoi poi = (SalePoi)object;
        	poi.setRow(row);
        	if(poi.getProductName()== null || poi.getProductName().isEmpty() || poi.getSendDate() == null ||
        	    poi.getCustomerName()== null || poi.getCustomerName().isEmpty() || poi.getBacthNumber() == null ||
        	    poi.getBacthNumber().isEmpty() || poi.getCount() == null) {
        		poi.setErrorInfo("存在空数据");
        		errorList.add(poi);
        		continue;
        	}
        	// 如果导入的是电商
        	if(isDs) {
        		if(poi.getUserName() == null || poi.getUserName().isEmpty() || poi.getSumPrice() == null || 
        			poi.getPrice() == null) {
        			poi.setErrorInfo("存在空数据");
            		errorList.add(poi);
        			continue;
        		}
        	}
        	// 查找导入的商品
        	List<Product> pList = productDao.findByName(poi.getProductName());
        	if(pList.size() == 0) {
        		poi.setErrorInfo("商品名不存在");
        		errorList.add(poi);
        		continue;
        	}
        	// 查找导入的客户，如果是电商客户不存在则新增、否则报错
        	Customer customer = customerDao.findByNameAndCustomerTypeId(poi.getCustomerName(), customerType);
        	if(customer == null || customer.getId() == null) {
        		if(isDs) {
        			customer = new Customer();
        			customer.setName(poi.getCustomerName());
        			customer.setCustomerTypeId(customerType);
        		} else {
        			poi.setErrorInfo("客户名不存在");
            		errorList.add(poi);
        			continue;
        		}
        	}
        	// 如果是电商，且客户没有业务员，则将该客户的业务员设置为当前导入业务员
        	if(isDs && customer.getUserId() == null) {
        		User user = userDao.findByUserName(poi.getUserName());
        		if(user == null || user.getId() == null) {
        			poi.setErrorInfo("业务员不存在");
            		errorList.add(poi);
        			continue;
        		}
        		customer.setUserId(user.getId());
        		customer = customerDao.save(customer);
        	}
        	Date time = poi.getSendDate();
        	int saleOrderSize = dao.findBySendDateBetween(time, DatesUtil.getLastDayOftime(time)).size() + 1;
        	Long pid = pList.get(0).getId();
        	Long cid = customer.getId();
        	Sale sale = new Sale(); 
        	/*
        	dao.findByproductIdAndCustomerIdAndSendDate(pid,cid,time);
        	if(sale != null && sale.getId() != null) {
        		poi.setErrorInfo("存在相同订单");
        		errorList.add(poi);
        		continue;
        	} else {
        		sale = new Sale();
    		}
        	*/
        	
    		// 发货日期
    		sale.setSendDate(poi.getSendDate());
    		// 实际数量
    		sale.setCount(poi.getCount());
    		// 批次号
    		sale.setBacthNumber(poi.getBacthNumber());
    		// 产品id
    		sale.setProductId(pid);
    		// 客户
    		sale.setCustomerId(cid);
    		// 生成销售编号
    		sale.setSaleNumber(Constants.XS + "-" + sdf.format(time) + "-" + StringUtil.get0LeftString(saleOrderSize++, 4));
    		// 未审核
    		sale.setAudit(0);
    		// 未拥有版权
    		sale.setCopyright(0);
    		// 未收货
    		sale.setDelivery(1);
    		// 业务员未确认数据
    		sale.setDeliveryStatus(0);
    		// 价格
    		sale.setPrice(0.0);
    		// 判定是否拥有版权
    		String name = poi.getProductName();
    		if (name.contains(Constants.LX)  || name.contains(Constants.KT)
    				|| name.contains(Constants.MW) || name.contains(Constants.BM)
    				|| name.contains(Constants.LP) || name.contains(Constants.AB)
    				|| name.contains(Constants.ZMJ) || name.contains(Constants.XXYJN)) {
    			sale.setCopyright(1);
    		}
    		sale.setPriceError(0);
    		// 如果是电商
    		if(isDs) {
    			sale.setPrice(poi.getPrice());
    			sale.setSumPrice(poi.getSumPrice());
    			// 查找以往价格、按正序排序
            	List<Sale> salePrice = dao.findByProductIdAndBacthNumberAndAuditOrderBySendDateDesc(pid,poi.getBacthNumber(),1);
            	if(salePrice != null && salePrice.size() > 0) {
            		if(salePrice.get(0).getPrice().compareTo(poi.getPrice()) != 0) {
	            		double sub = NumUtils.sub(salePrice.get(0).getPrice(),poi.getPrice());
	            		if(salePrice.get(0).getPrice().compareTo(poi.getPrice()) != 0 && 
	            				(sub > 0.005 || sub < -0.05 )) {
	            			sale.setPriceError(1);
	            		}
            		}
            	}
    		}
            dao.save(sale);
            count++;
        }
        CommonResponse cr = new CommonResponse();
        cr.setData(errorList);
        cr.setMessage("成功导入：" + count + "条数据");
        return cr;
	}

	
}
