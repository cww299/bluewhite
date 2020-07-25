package com.bluewhite.finance.consumption.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.ConsumptionPoi;
import com.bluewhite.production.temporarypack.SendOrder;
import com.bluewhite.production.temporarypack.SendOrderService;

@Service
public class ConsumptionServiceImpl extends BaseServiceImpl<Consumption, Long> implements ConsumptionService {

    @Autowired
    private ConsumptionDao dao;
    @Autowired
    SendOrderService sendOrderService;
    @Autowired
    BaseDataDao basedataDao;

    @Override
    public PageResult<Consumption> findPages(Consumption param, PageParameter page) {
        CurrentUser cu = SessionManager.getUserSession();
        if (param.getType() == 1 && cu != null && !cu.getIsAdmin() && cu.getOrgNameId() != 6) {
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
                // 当部门id不为null，过滤掉存在父id的数据
                if (param.getParentId() == null) {
                    predicate.add(cb.isNull(root.get("parentId").as(Long.class)));
                }
                predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
            }
            // 生产单编号搜索
            if (!StringUtils.isEmpty(param.getOrderNumber())) {
                predicate
                    .add(cb.like(root.get("orderOutSource").get("materialRequisition").get("order").get("orderNumber")
                        .as(String.class), "%" + StringUtil.specialStrKeyword(param.getOrderNumber()) + "%"));
            }
            // 按父类id过滤
            if (param.getParentId() != null) {
                predicate.add(cb.equal(root.get("parentId").as(Long.class), param.getParentId()));
            }
            if (param.getMode() != null) {
                if (param.getMode() == 2) {
                    predicate.add(cb.equal(root.get("parentId").as(Long.class), 0));
                } else {
                    predicate.add(cb.notEqual(root.get("parentId").as(Long.class), 0));
                }
            }
            // 按消费类型过滤
            if (param.getType() != null) {
                predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
            }

            // 按报销类型过滤
            if (param.getDeleteFlag() != null) {
                predicate.add(cb.equal(root.get("deleteFlag").as(Integer.class), param.getDeleteFlag()));
            }

            // 按是否已付款报销过滤
            if (!StringUtils.isEmpty(param.getFlags())) {
                String[] falg = param.getFlags().split(",");
                List<String> list = Arrays.asList(falg);
                if (list != null && list.size() > 0) {
                    In<Object> in = cb.in(root.get("flag"));
                    for (String id : list) {
                        in.value(Integer.parseInt(id));
                    }
                    predicate.add(in);
                }
            }

            // 按是否預算
            if (param.getBudget() != null) {
                predicate.add(cb.equal(root.get("budget").as(Integer.class), param.getBudget()));
            }
            // 按报销人姓名查找
            if (!StringUtils.isEmpty(param.getUsername())) {
                predicate
                    .add(cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUsername() + "%"));
            }
            // 按客户姓名查找
            if (!StringUtils.isEmpty(param.getCustomerName())) {
                predicate.add(
                    cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
            }
            // 按物流点查找
            if (!StringUtils.isEmpty(param.getLogisticsName())) {
                predicate.add(
                    cb.like(root.get("logistics").get("name").as(String.class), "%" + param.getLogisticsName() + "%"));
            }
            // 按报销內容查找
            if (!StringUtils.isEmpty(param.getContent())) {
                predicate.add(cb.like(root.get("content").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
            }
            // 物流编号搜索
            if (!StringUtils.isEmpty(param.getLogisticsNumber())) {
                predicate.add(cb.like(root.get("logisticsNumber").as(String.class),
                    "%" + param.getLogisticsNumber() + "%"));
            }
            // 按报销金额查找
            if (!StringUtils.isEmpty(param.getMoney())) {
                predicate.add(cb.equal(root.get("money").as(Double.class), param.getMoney()));
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

            if (!StringUtils.isEmpty(param.getExpectDate())) {
                // 按实际付款日期
                if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                    predicate.add(cb.between(root.get("expectDate").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
                }
            }

            if (!StringUtils.isEmpty(param.getRealityDate())) {
                // 按实际付款日期
                if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                    predicate.add(cb.between(root.get("realityDate").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
                }
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<Consumption> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    @Transactional
    public Consumption addConsumption(Consumption consumption) {
        Double originalMoney = consumption.getMoney();
        CurrentUser cu = SessionManager.getUserSession();
        Consumption ot = null;
        Double money = null;
        if (consumption.getId() != null) {
            ot = dao.findOne(consumption.getId());
            money = ot.getMoney();
            if (ot.getFlag() == 1) {
                throw new ServiceException("已放款，无法修改");
            }
            BeanCopyUtils.copyNotEmpty(consumption, ot);
            consumption = ot;
        }
        switch (consumption.getType()) {
            // 报销
            case 1:
                // 表示不是修改金额时自动跳过
                if (consumption.getId() != null && originalMoney == null) {
                    break;
                }
                // 修改子类报销单1.改变当前子类报销金额 2改变父类预算的报销金额
                if (consumption.getParentId() != null && consumption.getBudget() == 0) {
                    // 获取报销单的父id实体
                    Consumption parentConsumption = dao.findOne(consumption.getParentId());
                    // 表示为修改
                    if (consumption.getId() != null) {
                        parentConsumption.setMoney(
                            NumUtils.sum(parentConsumption.getMoney(), NumUtils.sub(money, consumption.getMoney())));
                    } else {
                        parentConsumption.setMoney(NumUtils.sub(parentConsumption.getMoney(), consumption.getMoney()));
                    }
                    dao.save(parentConsumption);
                }

                // 修改父类报销单
                if (consumption.getId() != null && consumption.getParentId() == null && consumption.getBudget() == 1) {
                    // 获取父类报销单的全部子类
                    List<Consumption> consumptionList = dao.findByParentId(consumption.getId());
                    if (consumptionList.size() > 0) {
                        List<Double> listDouble = new ArrayList<>();
                        consumptionList.stream().forEach(c -> {
                            listDouble.add(c.getMoney());
                        });
                        consumption.setMoney(NumUtils.sub(consumption.getMoney(), NumUtils.sum(listDouble)));
                    }

                }
                if (consumption.getPaymentMoney() != null && consumption.getPaymentMoney() > consumption.getMoney()) {
                    throw new ServiceException("放款金额不能大于申请金额");
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
            	if(originalMoney != null) {
            		Consumption parent = dao.findOne(consumption.getParentId());
            		parent.setMoney(NumUtils.sum(parent.getMoney(), -money, originalMoney));
        		    dao.save(parent);
            	}
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
        }

        if (consumption.getExpenseDate() == null) {
            throw new ServiceException("申请时间不能为空");
        }
        if (consumption.getMoney() == null) {
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
                    if (consumption.getType() != 5 && consumption.getOrgNameId() != null
                        && cu.getOrgNameId() != consumption.getOrgNameId()) {
                        throw new ServiceException("无权限删除");
                    }
                    if (consumption.getFlag() == 0) {
                        // 获取当前采购单，判断是否为预算
                        if (consumption.getBudget() != null && consumption.getBudget() == 1) {
                            // 获取所有的子报销单,删除子报销单时，同步更新父报销单的费用
                            List<Consumption> consumptionList = dao.findByParentId(id);
                            if (consumptionList.size() > 0) {
                                consumptionList.stream().forEach(co -> co.setParentId(null));
                            }
                            dao.save(consumptionList);
                        } else {// 不为预算单时，当拥有父id，属于子报销单，删除同时更新父预算报销单的金额
                            if (consumption.getType() == 1 && consumption.getParentId() != null) {
                                Consumption pConsumption = dao.findOne(consumption.getParentId());
                                pConsumption.setMoney(NumUtils.sum(pConsumption.getMoney(), consumption.getMoney()));
                                dao.save(pConsumption);
                            }
                        }

                        // 删除物流申请
                        if (consumption.getType() == 5) {
                            if (consumption.getSendOrderId() != null) {
                                SendOrder sendOrder = sendOrderService.findOne(consumption.getSendOrderId());
                                sendOrder.setAudit(0);
                                sendOrderService.save(sendOrder);
                            }
                            if (consumption.getParentId() != null && consumption.getParentId() != 0) {
                                Consumption consumptionPrent = dao.findOne(consumption.getParentId());
                                consumptionPrent
                                    .setMoney(NumUtils.sub(consumptionPrent.getMoney(), consumption.getMoney()));
                                if (null != consumptionPrent && consumptionPrent.getMoney() == 0) {
                                    dao.delete(consumptionPrent);
                                } else {
                                    dao.save(consumptionPrent);
                                }
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
                    if (consumption.getPaymentDate() == null
                        && (consumption.getPaymentMoney() == null || consumption.getPaymentMoney() == 0)) {
                        throw new ServiceException("放款金额或放款时间不能为空或者为0");
                    }
                    if (flag == 1) {
                        if ((consumption.getType() == 1 || consumption.getType() == 4 || consumption.getType() == 5)
                            && (consumption.getPaymentMoney() < consumption.getMoney())) {
                            flag = 2;
                        }
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
    public Map<String, Object> countConsumptionMoney(Consumption consumption) {
        CurrentUser cu = SessionManager.getUserSession();
        Map<String, Object> map = new HashMap<>();
        consumption.setOrgNameId(cu.getOrgNameId());
        List<Consumption> cpList = findList(consumption);
        List<Consumption> consumptionList =
            cpList.stream().filter(Consumption -> Consumption.getBudget() == 0).collect(Collectors.toList());
        List<Consumption> consumptionList1 =
            cpList.stream().filter(Consumption -> Consumption.getBudget() == 1).collect(Collectors.toList());
        List<Double> listDouble = new ArrayList<>();
        Double budget = 0.0;
        Double nonBudget = 0.0;
        if (consumptionList.size() > 0) {
            consumptionList.stream().forEach(c -> {
                listDouble.add(c.getMoney());
            });
            budget = NumUtils.sum(listDouble);
        }
        List<Double> listDouble1 = new ArrayList<>();
        if (consumptionList1.size() > 0) {
            consumptionList1.stream().forEach(c -> {
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
    public int excelAddConsumption(ExcelListener excelListener, Integer type) {
        int count = 0;
        // 获取导入的订单
        List<Object> excelListenerList = excelListener.getData();
        for (Object object : excelListenerList) {
            ConsumptionPoi cPoi = (ConsumptionPoi)object;
            Consumption consumption = new Consumption();
            consumption.setContent(cPoi.getContent());
            consumption.setCustomerName(cPoi.getCustomerName());
            consumption.setMoney(cPoi.getMoney());
            consumption.setExpenseDate(cPoi.getExpenseDate());
            consumption.setType(type);
            addConsumption(consumption);
            count++;
        }
        return count;
    }

    @Override
    public double totalAmount(Consumption consumption) {
        List<Consumption> consumptionList = findList(consumption);
        double amount = 0;
        List<Double> listDouble = new ArrayList<>();
        if (consumptionList.size() > 0) {
            consumptionList.stream().forEach(c -> {
                if (c.getType() == 5) {
                    if (c.getParentId() != null && c.getParentId() == 0) {
                        double money = 0;
                        if (c.getFlag() == 0) {
                            money = c.getMoney();
                        }
                        if (c.getFlag() == 1) {
                            money = 0;
                        }
                        if (c.getFlag() == 2) {
                            money = NumUtils.sub(c.getMoney(), c.getPaymentMoney());
                        }
                        listDouble.add(money);
                    }
                } else {
                    double money = 0;
                    if (c.getFlag() == 0) {
                        money = c.getMoney();
                    }
                    if (c.getFlag() == 1) {
                        money = 0;
                    }
                    if (c.getFlag() == 2) {
                        money = NumUtils.sub(c.getMoney(), c.getPaymentMoney());
                    }
                    listDouble.add(money);
                }
            });
            amount = NumUtils.sum(listDouble);
        }
        return NumUtils.round(amount, 2);
    }

    @Override
    public List<Consumption> findList(Consumption param) {
        List<Consumption> result = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按人员过滤
            if (param.getUserId() != null) {
                predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
            }

            // 按消费类型过滤
            if (param.getType() != null) {
                predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
            }

            // 生产单编号搜索
            if (!StringUtils.isEmpty(param.getOrderNumber())) {
                predicate
                    .add(cb.like(root.get("orderOutSource").get("materialRequisition").get("order").get("orderNumber")
                        .as(String.class), "%" + StringUtil.specialStrKeyword(param.getOrderNumber()) + "%"));
            }

            // 按是否已付款报销过滤
            if (!StringUtils.isEmpty(param.getFlags())) {
                String[] falg = param.getFlags().split(",");
                List<String> list = Arrays.asList(falg);
                if (list != null && list.size() > 0) {
                    In<Object> in = cb.in(root.get("flag"));
                    for (String id : list) {
                        in.value(Integer.parseInt(id));
                    }
                    predicate.add(in);
                }
            }
            // 按部门id过滤
            if (param.getOrgNameId() != null) {
                predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
            }

            // 按是否預算
            if (param.getBudget() != null) {
                predicate.add(cb.equal(root.get("budget").as(Integer.class), param.getBudget()));
            }
            // 按报销人姓名查找
            if (!StringUtils.isEmpty(param.getUsername())) {
                predicate
                    .add(cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUsername() + "%"));
            }

            // 按客户姓名查找
            if (!StringUtils.isEmpty(param.getCustomerName())) {
                predicate.add(
                    cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
            }
            // 按业务员姓名查找
            if (!StringUtils.isEmpty(param.getSaleUserName())) {
                predicate.add(cb.like(root.get("customer").get("user").get("userName").as(String.class),
                    "%" + param.getSaleUserName() + "%"));
            }
            // 按物流点查找
            if (!StringUtils.isEmpty(param.getLogisticsName())) {
                predicate.add(
                    cb.like(root.get("logistics").get("name").as(String.class), "%" + param.getLogisticsName() + "%"));
            }
            // 按报销內容查找
            if (!StringUtils.isEmpty(param.getContent())) {
                predicate.add(cb.like(root.get("content").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
            }
            // 按报销金额查找
            if (!StringUtils.isEmpty(param.getMoney())) {
                predicate.add(cb.equal(root.get("money").as(Double.class), param.getMoney()));
            }
            if (!StringUtils.isEmpty(param.getExpenseDate())) {
                // 按申请日期
                if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                    predicate.add(cb.between(root.get("expenseDate").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
                }
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        });
        return result;
    }

    @Override
    public Consumption findByTypeAndLogisticsIdAndExpenseDateBetween(Integer type, Long id, Date beginTime,
        Date endTime) {
        return dao.findByTypeAndLogisticsIdAndParentIdAndExpenseDateBetween(type, id, (long)0, beginTime, endTime);
    }

    @Override
    public Consumption findBySendOrderId(Long id) {
        return dao.findBySendOrderId(id);
    }

    @Override
    public List<Map<String, Object>> logisticsConsumption(Consumption consumption) {
        List<Map<String, Object>> listmap = new ArrayList<>();
        consumption.setType(5);
        List<Consumption> list = findList(consumption);
        list = list.stream().filter(Consumption -> Consumption.getParentId() != null && Consumption.getParentId() != 0)
            .collect(Collectors.toList());
        Map<Long, List<Consumption>> mapGourp =
            list.stream().collect(Collectors.groupingBy(Consumption::getCustomerId));
        for (Map.Entry<Long, List<Consumption>> entry : mapGourp.entrySet()) {
            Map<String, Object> map = new HashMap<String, Object>();
            double pay = entry.getValue().stream().mapToDouble(Consumption::getMoney).sum();
            map.put("pay", pay);
            map.put("name", entry.getValue().get(0).getCustomer().getName());
            map.put("username", entry.getValue().get(0).getCustomer().getUser().getUserName());
            BaseData orgName = basedataDao.findOne(entry.getValue().get(0).getOrgNameId());
            map.put("orgName", orgName.getName());
            listmap.add(map);
        }
        return listmap;
    }
}
