package com.bluewhite.finance.consumption.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.dao.CustomDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.ConsumptionPoi;
import com.bluewhite.finance.consumption.entity.Custom;
import com.bluewhite.finance.ledger.dao.ContactDao;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class ConsumptionServiceImpl extends BaseServiceImpl<Consumption, Long> implements ConsumptionService {

	@Autowired
	private ConsumptionDao dao;

	@Autowired
	private CustomDao customDao;
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public PageResult<Consumption> findPages(Consumption param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		if(cu!=null && !cu.getIsAdmin() && cu.getOrgNameId() != 6 ){
			param.setOrgNameId(cu.getOrgNameId());
		}
		Page<Consumption> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按人员过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按部门id过滤
			if (param.getOrgNameId() != null) {
				//当部门id不为null，过滤掉存在父id的数据
				if(param.getParentId() == null){
					predicate.add(cb.isNull(root.get("parentId").as(Long.class)));
				}
				predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			
			// 按父类id过滤
			if (param.getParentId() != null) {
				predicate.add(cb.equal(root.get("parentId").as(Long.class), param.getParentId()));
			}
			// 按消费类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}

			// 按是否已付款报销过滤
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 按是否已付款报销过滤
			if (param.getBudget() != null) {
				predicate.add(cb.equal(root.get("budget").as(Integer.class), param.getBudget()));
			}
			// 按报销人姓名查找
			if (!StringUtils.isEmpty(param.getUsername())) {
				predicate.add(
						cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUsername() + "%"));
			}
			// 按客户姓名查找
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(
						cb.like(root.get("custom").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按报销內容查找
			if (!StringUtils.isEmpty(param.getContent())) {
				predicate.add(cb.like(root.get("content").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
			}

			// 按报销金额查找
			if (!StringUtils.isEmpty(param.getMoney())) {
				predicate.add(cb.equal(root.get("money").as(Double.class), param.getMoney() ));
			}
			
			// 按客户查找
			if (!StringUtils.isEmpty(param.getCustomId())) {
				predicate.add(cb.equal(root.get("customId").as(String.class), param.getCustomId()));
			}

			if (!StringUtils.isEmpty(param.getExpenseDate())) {
				// 按申请日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("expenseDate").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			if (!StringUtils.isEmpty(param.getPaymentDate())) {
				// 按财务付款日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("paymentDate").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			
			if (!StringUtils.isEmpty(param.getLogisticsDate())) {
				// 按其他日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("logisticsDate").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, SalesUtils.getQueryNoPageParameter());
		PageResultStat<Consumption> result = new PageResultStat<>(pages, page);
		result.setAutoStateField(null, "money");
		result.count();
		return result;
	}

	@Override
	@Transactional
	public Consumption addConsumption(Consumption consumption) {
		CurrentUser cu = SessionManager.getUserSession();
		Consumption ot = null;
		Double money = null;
		if (consumption.getId() != null) {
			ot = dao.findOne(consumption.getId());
			money = ot.getMoney();
			if (ot.getFlag() == 1) {
				throw new ServiceException("已放款，无法修改");
			}
			BeanCopyUtils.copyNotEmpty(consumption,ot,"");
			consumption = ot;
		} 
		
		boolean flag = true;
		switch (consumption.getType()) {
		case 1:
			flag = false;
			//修改子类报销单1.改变当前子类报销金额 2改变父类预算的报销金额
			if(consumption.getParentId()!=null){
				//获取报销单的父id实体
				Consumption parentConsumption = dao.findOne(consumption.getParentId());
				//表示为修改
				if(consumption.getId() != null){
					parentConsumption.setMoney(NumUtils.sum(parentConsumption.getMoney(),NumUtils.sub(money,consumption.getMoney())));
				}else{
					parentConsumption.setMoney(NumUtils.sub(parentConsumption.getMoney(), consumption.getMoney()));
				}
				dao.save(parentConsumption);
			}
			
			//修改父类报销单
			if(consumption.getId() != null  && consumption.getBudget()==1){
				//获取父类报销单的全部子类
				List<Consumption> consumptionList = dao.findByParentId(consumption.getId());
				if(consumptionList.size()>0){
					 List<Double> listDouble = new ArrayList<>();
					    consumptionList.stream().forEach(c->{
					    	listDouble.add(c.getMoney());
					    });
					consumption.setMoney(NumUtils.sub(consumption.getMoney(),NumUtils.sum(listDouble)));
				}
			}
			break;
		case 2:
			if(consumption.getId() == null && consumption.getCustomId() == null){
				if(!StringUtils.isEmpty(consumption.getCustomerName())){
					Custom custom =  customDao.findByTypeAndName(consumption.getType(), consumption.getCustomerName());
					if(custom!=null){
						consumption.setCustomId(custom.getId());
					}
				}
			}
			if(consumption.getId() == null && consumption.getUserId() == null){
				if(!StringUtils.isEmpty(consumption.getUsername())){
					User user = userDao.findByUserName(consumption.getUsername());
					if(user!=null){
						consumption.setUserId(user.getId());
					}
				}
			}
			break;
		case 3:
			flag = false;
			break;
		case 4:
			break;
		case 5:
			if (consumption.getContactId() == null) {
				Contact contact = new Contact();
				contact.setConPartyNames(consumption.getContactName());
				contactDao.save(contact);
				consumption.setContactId(contact.getId());
			}
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			flag = false;
			break;
		}
		if (flag && (consumption.getCustomId() == null || consumption.getCustomId() == 0)) {
			Custom custom = new Custom();
			custom.setName(consumption.getCustomerName());
			custom.setType(consumption.getType());
			customDao.save(custom);
			consumption.setCustomId(custom.getId());
		}
		
		if(consumption.getExpenseDate()==null){
			throw new ServiceException("申请时间不能为空");
		}
		if(consumption.getMoney()==null){
			throw new ServiceException("申请金额不能为空");
		}
		if (consumption.getId() == null) {
			consumption.setOrgNameId(cu.getOrgNameId());
		}
		consumption.setFlag(0);
		
		return dao.save(consumption);
	}

	@Override
	@Transactional
	public int deleteConsumption(String ids) {
		CurrentUser cu = SessionManager.getUserSession();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Consumption consumption = dao.findOne(id);
					if(cu.getOrgNameId() != consumption.getOrgNameId()){
						throw new ServiceException("无权限删除");
					}
					if (consumption.getFlag() == 0) {
						//获取当前采购单，判断是否为预算
						if(consumption.getBudget()!=null && consumption.getBudget()==1){
							//获取所有的子报销单,删除子报销单时，同步更新父报销单的费用
							List<Consumption> consumptionList= dao.findByParentId(id);
							if(consumptionList.size()>0){
								consumptionList.stream().forEach(co->co.setParentId(null));
							}
							dao.save(consumptionList);
						}else{//不为预算单时，当拥有父id，属于子报销单，删除同时更新父预算报销单的金额
							if(consumption.getParentId()!=null){
								Consumption pConsumption = dao.findOne(consumption.getParentId());
								pConsumption.setMoney(NumUtils.sum(pConsumption.getMoney(), consumption.getMoney()));
								dao.save(pConsumption);
							}
						}
						dao.delete(id);
						count++;
					} else {
						throw new ServiceException(consumption.getContent() + "已经审核放款无法删除");
					}
				}
			}
		}
		return count;
	}

	@Override
	public int auditConsumption(String ids, Integer flag) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Consumption consumption = dao.findOne(id);
					if (consumption.getPaymentDate() == null && consumption.getPaymentMoney() == null) {
						throw new ServiceException("返款金额或放款时间不能为空");
					}
					consumption.setFlag(flag);
					dao.save(consumption);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public Map<String, Object> countConsumptionMoney() {
		Map<String, Object>  map = new HashMap<>();
		CurrentUser cu = SessionManager.getUserSession();
	    List<Consumption> consumptionList =  dao.findByBudgetAndOrgNameId(0,cu.getOrgNameId());
	    List<Consumption> consumptionList1 =  dao.findByBudgetAndOrgNameId(1,cu.getOrgNameId());
	    List<Double> listDouble = new ArrayList<>();
	    Double budget = 0.0;
	    Double nonBudget =0.0;
	    if(consumptionList.size()>0){
	    	consumptionList.stream().forEach(c->{
	    		listDouble.add(c.getMoney());
	    	});
	    	budget = NumUtils.sum(listDouble);
	    }
	    List<Double> listDouble1 = new ArrayList<>();
	    if(consumptionList1.size()>0){
	    	consumptionList1.stream().forEach(c->{
	    		listDouble1.add(c.getMoney());
	    	});
	    	nonBudget = NumUtils.sum(listDouble1);
	    }
	    map.put("budget", budget);
	    map.put("nonBudget", nonBudget);
	    map.put("sumBudget", NumUtils.sum(budget, nonBudget));
		return map;
	}

	@Override
	public int excelAddConsumption(ExcelListener excelListener) {
		int count = 0;
		//获取导入的订单
		List<Object> excelListenerList = excelListener.getData();
		for(Object object : excelListenerList){
			ConsumptionPoi cPoi = (ConsumptionPoi)object;
			Consumption consumption = new Consumption();
			consumption.setBatchNumber(cPoi.getBatchNumber());
			consumption.setContent(cPoi.getContent());
			consumption.setCustomerName(cPoi.getCustomerName());
			consumption.setUsername(cPoi.getUsername());
			consumption.setMoney(cPoi.getMoney());
			consumption.setExpenseDate(cPoi.getExpenseDate());
			consumption.setLogisticsDate(cPoi.getLogisticsDate());
			consumption.setType(2);
			addConsumption(consumption);
			count++;
		}
		return count;
	}

}
