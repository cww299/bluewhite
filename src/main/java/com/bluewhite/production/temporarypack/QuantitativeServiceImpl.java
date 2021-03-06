package com.bluewhite.production.temporarypack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.dao.LogisticsCostsDao;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.LogisticsCosts;
import com.bluewhite.ledger.entity.PackingMaterials;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class QuantitativeServiceImpl extends BaseServiceImpl<Quantitative, Long> implements QuantitativeService {

    @Autowired
    private QuantitativeDao dao;
    @Autowired
    private UnderGoodsDao underGoodsDao;
    @Autowired
    private SendOrderDao sendOrderDao;
    @Autowired
    private QuantitativeChildDao quantitativeChildDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    LogisticsCostsDao logisticsCostsDao;

    @Override
    public PageResult<Quantitative> findPages(Quantitative param, PageParameter page) {
        CurrentUser cu = SessionManager.getUserSession();
        // 蓝白仓库
        if (cu.getRole().contains("stickBagAccount") || cu.getRole().contains("stickBagStick")) {
            param.setWarehouseTypeId((long) 274);
        }
        // 11号仓库
        if (cu.getRole().contains("packScene") || cu.getRole().contains("elevenSend")) {
            param.setWarehouseTypeId((long) 275);
        }
        Page<Quantitative> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按仓库种类
            if (param.getWarehouseTypeId() != null) {
                predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
            }
            // 按客户名称
            if (!StringUtils.isEmpty(param.getCustomerName())) {
                predicate.add(
                        cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
            }
            // 是否发货
            if (param.getStatus() != null) {
                predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
            }
            // 是否打印
            if (param.getPrint() != null) {
                predicate.add(cb.equal(root.get("print").as(Integer.class), param.getPrint()));
            }
            // 是否发货
            if (param.getFlag() != null) {
                predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
            }
            // 是否内部
            if (param.getInterior() != null) {
                predicate.add(cb.equal(root.get("customer").get("interior").as(Integer.class), param.getInterior()));
            }
            // 是否审核
            if (param.getAudit() != null) {
                predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
            }
            // 是否对账
            if (param.getReconciliation() != null) {
                predicate.add(cb.equal(root.get("reconciliation").as(Integer.class), param.getReconciliation()));
            }
            // 按批次
            if (!StringUtils.isEmpty(param.getBacthNumber())) {
                Join<Quantitative, QuantitativeChild> join =
                        root.join(root.getModel().getList("quantitativeChilds", QuantitativeChild.class), JoinType.LEFT);
                predicate.add(cb.like(join.get("underGoods").get("bacthNumber").as(String.class),
                        "%" + StringUtil.specialStrKeyword(param.getBacthNumber()) + "%"));
            }
            // 按商品名称过滤
            if (!StringUtils.isEmpty(param.getProductName())) {
                Join<Quantitative, QuantitativeChild> join =
                        root.join(root.getModel().getList("quantitativeChilds", QuantitativeChild.class), JoinType.LEFT);
                predicate.add(cb.like(join.get("underGoods").get("product").get("name").as(String.class),
                        "%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
            }
            // 按产品编号过滤
            if (!StringUtils.isEmpty(param.getProductNumber())) {
                predicate
                        .add(cb.like(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
            }
            // 按库位
            if (!StringUtils.isEmpty(param.getLocation())) {
                predicate.add(cb.like(root.get("location").as(String.class), "%" + param.getLocation() + "%"));
            }

            // 按库区
            if (!StringUtils.isEmpty(param.getReservoirArea())) {
                predicate
                        .add(cb.like(root.get("reservoirArea").as(String.class), "%" + param.getReservoirArea() + "%"));
            }
            // 按上车编号过滤
            if (!StringUtils.isEmpty(param.getVehicleNumber())) {
                predicate.add(cb.like(root.get("vehicleNumber").as(String.class),
                        "%" + StringUtil.specialStrKeyword(param.getVehicleNumber()) + "%"));
            }
            // 按下单日期
            if (!StringUtils.isEmpty(param.getTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin())
                    && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(
                        cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(), param.getOrderTimeEnd()));
            }
            // 按发货日期
            if (!StringUtils.isEmpty(param.getSendTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin())
                    && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("sendTime").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
            }
            // 是否过滤掉生成销售单
            if (param.getIsSale() == 1) {
                Join<Quantitative, QuantitativeChild> join = root
                        .join(root.getModel().getList("quantitativeChilds", QuantitativeChild.class), JoinType.LEFT);
                predicate.add(cb.isNull(join.get("saleId").as(Long.class)));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            query.distinct(true);
            return null;
        }, page);
        PageResult<Quantitative> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    @Transactional
    public void saveQuantitative(Quantitative quantitative) {
        CurrentUser cu = SessionManager.getUserSession();
        if (quantitative.getId() != null) {
            Quantitative ot = dao.findOne(quantitative.getId());
            if (ot.getAudit() == 1 && cu.getRole().contains("stickBagAccount")) {
                throw new ServiceException("已审核，无法修改");
            }
            if (ot.getFlag() == 1) {
                throw new ServiceException("已发货，无法修改");
            }
            if (ot.getSendOrderId() != null) {
                SendOrder sendOrder = sendOrderDao.findOne(ot.getSendOrderId());
                if (sendOrder != null) {
                    sendOrder.setCustomerId(quantitative.getCustomerId());
                    List<LogisticsCosts> list = logisticsCostsDao.findByCustomerId(quantitative.getCustomerId());
                    if (list.size() > 0) {
                        sendOrder.setOuterPackagingId(list.get(0).getOuterPackagingId());
                        sendOrder.setLogisticsId(list.get(0).getLogisticsId());
                        sendOrder.setSingerPrice(new BigDecimal(list.get(0).getTaxIncluded()));
                    }
                    sendOrderDao.save(sendOrder);
                    quantitative.setSendOrderId(ot.getSendOrderId());
                } else {
                    ot.setSendOrderId(null);
                }
            }
            // 如果修改了包装时间，包装编号也要进行修改
            if (!DateUtil.isSameDay(quantitative.getTime(), ot.getTime())) {
                // 按最后一条数据编号进行新增
                List<Quantitative> list =
                        dao.findByTimeBetweenOrderByIdDesc(DatesUtil.getfristDayOftime(quantitative.getTime()),
                                DatesUtil.getLastDayOftime(quantitative.getTime()));
                compareQuantitativeNumber(list);
                int count = 0;
                if (list.size() > 0) {
                    String quantitativeNumber = list.get(0).getQuantitativeNumber();
                    count = Integer.valueOf(StrUtil.sub(quantitativeNumber, 12, 16));
                }
                quantitative.setQuantitativeNumber(Constants.LHTB + DateUtil.format(quantitative.getTime(), "yyyyMMdd")
                        + StringUtil.get0LeftString((count + 1), 4));
            } else {
                quantitative.setQuantitativeNumber(ot.getQuantitativeNumber());
            }
            quantitative.setAudit(ot.getAudit());
            quantitative.setPrint(ot.getPrint());
            quantitative.setFlag(ot.getFlag());
            quantitative.setReconciliation(ot.getReconciliation());
        } else {
            // 按最后一条数据编号进行新增
            List<Quantitative> list =
                    dao.findByTimeBetweenOrderByIdDesc(DatesUtil.getfristDayOftime(quantitative.getTime()),
                            DatesUtil.getLastDayOftime(quantitative.getTime()));
            int count = 0;
            if (list.size() > 0) {
                String quantitativeNumber = list.get(0).getQuantitativeNumber();
                count = Integer.valueOf(StrUtil.sub(quantitativeNumber, 12, 16));
            }
            quantitative.setQuantitativeNumber(Constants.LHTB + DateUtil.format(quantitative.getTime(), "yyyyMMdd")
                    + StringUtil.get0LeftString((count + 1), 4));
            quantitative.setAudit(0);
            quantitative.setPrint(0);
            quantitative.setFlag(0);
            quantitative.setOutPrice(0.2);
            quantitative.setStatus(0);
            quantitative.setReconciliation(0);
        }
        // 新增子单
        if (!StringUtils.isEmpty(quantitative.getChild())) {
            JSONArray jsonArray = JSON.parseArray(quantitative.getChild());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 下货单id
                Long underGoodsId = jsonObject.getLong("underGoodsId");
                UnderGoods underGoods = underGoodsDao.findOne(underGoodsId);
                if (underGoods.getNumber() == null) {
                    throw new ServiceException("贴包数量未填写，无法新增");
                }
                // 子单，通过子单id查看是新增还是修改
                Long id = jsonObject.getLong("id");
                QuantitativeChild quantitativeChild = null;
                if (id != null) {
                    quantitativeChild = quantitativeChildDao.findOne(id);
                } else {
                    // 新增初始赋值
                    quantitativeChild = new QuantitativeChild();
                    quantitativeChild.setChecks(0);
                    quantitativeChild.setUnderGoodsId(underGoodsId);
                    quantitativeChild.setSingleNumber(0);
                }
                // 获取贴包数量，用于判断是否可以新增或者修改
                List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(underGoodsId);
                int numberStickSum = 0;
                if (stickListList.size() > 0) {
                    numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
                }
                underGoods.setSurplusStickNumber(
                        underGoods.getNumber() - (numberStickSum - quantitativeChild.getSingleNumber()));
                if (underGoods.getSurplusStickNumber() <= 0) {
                    underGoods.setStatus(1);
                    underGoodsDao.save(underGoods);
                }
                if (jsonObject.getInteger("singleNumber") > underGoods.getSurplusStickNumber()) {
                    throw new ServiceException("剩余贴包数量不足，无法新增或修改");
                }
                quantitativeChild.setSingleNumber(jsonObject.getInteger("singleNumber"));
                quantitativeChild.setActualSingleNumber(
                        id == null ? quantitativeChild.getSingleNumber() : quantitativeChild.getActualSingleNumber());
                quantitative.getQuantitativeChilds().add(quantitativeChild);
                // 添加地点
                quantitative.setWarehouseTypeId(underGoods.getWarehouseTypeId());
            }
        }
        // 新增贴包物
        if (!StringUtils.isEmpty(quantitative.getPackingMaterialsJson())) {
            JSONArray jsonArrayMaterials = JSON.parseArray(quantitative.getPackingMaterialsJson());
            for (int i = 0; i < jsonArrayMaterials.size(); i++) {
                PackingMaterials packingMaterials = new PackingMaterials();
                JSONObject jsonObject = jsonArrayMaterials.getJSONObject(i);
                packingMaterials.setPackagingId(jsonObject.getLong("packagingId"));
                packingMaterials.setPackagingCount(jsonObject.getInteger("packagingCount"));
                quantitative.getPackingMaterials().add(packingMaterials);
            }
        }
        save(quantitative);
    }

    /**
     * 按编号重新排序
     *
     * @param list
     * @return
     */
    private List<Quantitative> compareQuantitativeNumber(List<Quantitative> list) {
        Collections.sort(list, new Comparator<Quantitative>() {
            @Override
            public int compare(Quantitative q1, Quantitative q2) {
                int a = Integer.valueOf(StrUtil.sub(q1.getQuantitativeNumber(), 12, 16));
                int b = Integer.valueOf(StrUtil.sub(q2.getQuantitativeNumber(), 12, 16));
                return b - a;
            }
        });
        return list;
    }

    @Override
    @Transactional
    public int auditQuantitative(String ids, Integer audit) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    if (audit == 1 && quantitative.getAudit() == 1) {
                        throw new ServiceException("已审核请勿多次审核");
                    }
                    if (audit == 0 && quantitative.getAudit() == 0) {
                        throw new ServiceException("未审核请勿取消审核");
                    }
                    if (audit == 0 && quantitative.getFlag() == 1) {
                        throw new ServiceException("已发货无法取消审核");
                    }
                    int number = quantitative.getQuantitativeChilds().stream()
                            .mapToInt(QuantitativeChild -> QuantitativeChild.getSingleNumber()).sum();
                    quantitative.setNumber(number);
                    quantitative.setAudit(audit);
                    dao.save(quantitative);
                }
            }
        }
        return count;
    }

    @Override
    @Transactional
    public int printQuantitative(String ids) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    quantitative.setPrint(1);
                    dao.save(quantitative);
                }
            }
        }
        return count;
    }

    @Override
    @Transactional
    public int deleteQuantitative(String ids) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    if (quantitative.getFlag() == 1) {
                        throw new ServiceException("已发货无法删除");
                    }
                    if (quantitative.getAudit() == 1) {
                        throw new ServiceException("已审核无法删除");
                    }
                    if (quantitative.getQuantitativeChilds() != null) {
                        quantitative.getQuantitativeChilds().forEach(qc -> {
                            UnderGoods underGoods = qc.getUnderGoods();
                            // 获取贴包数量，用于判断是否可以新增或者修改
                            List<QuantitativeChild> stickListList =
                                    quantitativeChildDao.findByUnderGoodsId(qc.getUnderGoodsId());
                            int numberStickSum = 0;
                            if (stickListList.size() > 0) {
                                numberStickSum =
                                        stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
                            }
                            underGoods.setSurplusStickNumber(
                                    underGoods.getNumber() - (numberStickSum - qc.getSingleNumber()));
                            if (underGoods.getSurplusStickNumber() == 0) {
                                underGoods.setStatus(1);
                            } else {
                                underGoods.setStatus(0);
                            }
                        });
                        save(quantitative);
                    }
                    if (quantitative.getSendOrderId() != null) {
                        SendOrder sendOrder = sendOrderDao.findOne(quantitative.getSendOrderId());
                        if (sendOrder != null) {
                            sendOrderDao.delete(sendOrder);
                        }
                    }
                    dao.delete(id);
                }
            }
        }
        return count;
    }

    @Override
    @Transactional
    public int sendQuantitative(String ids, Integer flag, String vehicleNumber, Long logisticsId,
                                Long outerPackagingId) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    //当是调拨单的情况，不计算物流费用，向调拨仓库生成量化单
//                    if(quantitative.getAllocation()==1) {
//                        if(quantitative.getAllocationWarehousId()==null) {
//                            throw new ServiceException("调拨仓库不能为null");
//                        }
//                        Quantitative quantitativeNew = new Quantitative();
//                        BeanUtil.copyProperties(quantitative, quantitativeNew);
//                        quantitativeNew.setWarehouseTypeId(quantitative.getAllocationWarehousId());
//                        quantitativeNew.setAllocation(null);
//                        quantitativeNew.setAllocationWarehousId(null);
//                        dao.save(quantitativeNew);
//                        quantitative.setFlag(flag);
//                        dao.save(quantitative);
//                        continue;
//                    }
                    if (flag == 1 && quantitative.getFlag() == 1) {
                        throw new ServiceException("已发货请勿多次发货");
                    }
                    if (flag == 0 && quantitative.getFlag() == 0) {
                        throw new ServiceException("未发货请勿取消发货");
                    }
                    if (quantitative.getCustomerId() == null) {
                        throw new ServiceException("贴包单无客户，无法发货");
                    }
                    // 发货时，创建发货单
                    Customer customer = customerDao.findOne(quantitative.getCustomerId());
                    if (flag == 1) {
                        quantitative.setSendTime(DateUtil.parse(StrUtil.sub(vehicleNumber, 0, 8)));
                        if (customer.getInterior() == 1) {
                            Quantitative qt = dao.findByVehicleNumberAndWarehouseTypeId(Constants.WLSC + vehicleNumber,
                                    quantitative.getWarehouseTypeId());
                            if (null != qt) {
                                throw new ServiceException("上车编号已存在，请更正");
                            }
                            quantitative.setVehicleNumber(Constants.WLSC + vehicleNumber);
                            // 根据物流点和上车编号查找
                            SendOrder sendOrder = sendOrderDao.findByVehicleNumberAndWarehouseTypeId(
                                    StrUtil.sub(quantitative.getVehicleNumber(), 0, 16), quantitative.getWarehouseTypeId());
                            if (sendOrder == null) {
                                sendOrder = new SendOrder();
                                int number = quantitative.getQuantitativeChilds().stream()
                                        .mapToInt(QuantitativeChild::getSingleNumber).sum();
                                if (outerPackagingId != null) {
                                    sendOrder.setOuterPackagingId(outerPackagingId);
                                }
                                sendOrder.setAudit(0);
                                sendOrder.setCustomerId(quantitative.getCustomerId());
                                sendOrder.setSendPackageNumber(1);
                                sendOrder.setLogisticsId(logisticsId);
                                sendOrder.setInterior(1);
                                sendOrder.setTax(1);
                                sendOrder.setVehicleNumber(StrUtil.sub(quantitative.getVehicleNumber(), 0, 16));
                                sendOrder.setSendTime(quantitative.getSendTime());
                                sendOrder.setWarehouseTypeId(quantitative.getWarehouseTypeId());
                                sendOrder.setNumber(NumUtils.setzro(sendOrder.getNumber()) + number);
                                sendOrderDao.save(sendOrder);
                            }
                            quantitative.setSendOrderId(sendOrder.getId());
                        } else {
                            // 根据日期和客户查询发货单,发货地点，同一天的合并发货单
                            List<SendOrder> sendOrderList =
                                    sendOrderDao.findByCustomerIdAndSendTimeBetweenAndWarehouseTypeIdAndAudit(customer.getId(),
                                            DateUtil.beginOfDay(quantitative.getSendTime()),
                                            DateUtil.endOfDay(quantitative.getSendTime()), quantitative.getWarehouseTypeId(), 0);
                            SendOrder sendOrder = null;
                            if (CollUtil.isNotEmpty(sendOrderList)) {
                                sendOrder = CollUtil.getFirst(sendOrderList);
                            }
                            int number = quantitative.getQuantitativeChilds().stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
                            if (null == sendOrder) {
                                sendOrder = new SendOrder();
                                sendOrder.setSendTime(quantitative.getSendTime());
                                sendOrder.setTax(1);
                                sendOrder.setAudit(0);
                                sendOrder.setCustomerId(quantitative.getCustomerId());
                                sendOrder.setSendPackageNumber(1);
                                sendOrder.setWarehouseTypeId(quantitative.getWarehouseTypeId());
                                sendOrder.setInterior(0);
                            } else {
                                sendOrder.setSendPackageNumber(sendOrder.getSendPackageNumber() + 1);
                            }
                            sendOrder.setNumber(NumUtils.setzro(sendOrder.getNumber()) + number);
                            // 生成物流费用
                            List<LogisticsCosts> list =
                                    logisticsCostsDao.findByCustomerId(quantitative.getCustomerId());
                            if (list.size() > 0) {
                                sendOrder.setOuterPackagingId(list.get(0).getOuterPackagingId());
                                sendOrder.setLogisticsId(list.get(0).getLogisticsId());
                                sendOrder.setSingerPrice(new BigDecimal(list.get(0).getTaxIncluded()));
                            }
                            if (sendOrder.getSendPackageNumber() != null && sendOrder.getSingerPrice() != null) {
                                sendOrder.setSendPrice(
                                        NumberUtil.mul(sendOrder.getSendPackageNumber(), sendOrder.getSingerPrice()));
                                sendOrder.setLogisticsPrice(
                                        NumberUtil.add(sendOrder.getExtraPrice(), sendOrder.getSendPrice()));
                            }
                            sendOrderDao.save(sendOrder);
                            quantitative.setSendOrderId(sendOrder.getId());
                        }
                    } else {
                        // 取消发货，删除发货单
                        if (quantitative.getSendOrderId() != null) {
                            SendOrder sendOrder = sendOrderDao.findOne(quantitative.getSendOrderId());
                            if (sendOrder != null) {
                                if (sendOrder.getAudit() != null && sendOrder.getAudit() == 1) {
                                    throw new ServiceException("财务已审核生成物流费用,无法取消发货");
                                }
                                int number = quantitative.getQuantitativeChilds().stream()
                                        .mapToInt(QuantitativeChild::getSingleNumber).sum();
                                // 取消发货，重新计算费用
                                sendOrder.setSendPackageNumber(sendOrder.getSendPackageNumber() - 1);
                                sendOrder.setNumber(sendOrder.getNumber() - number);
                                if (sendOrder.getSendPackageNumber() != null && sendOrder.getSingerPrice() != null) {
                                    sendOrder.setSendPrice(
                                            NumberUtil.mul(sendOrder.getSendPackageNumber(), sendOrder.getSingerPrice()));
                                    sendOrder.setLogisticsPrice(
                                            NumberUtil.add(sendOrder.getExtraPrice(), sendOrder.getSendPrice()));
                                }
                                if (sendOrder.getSendPackageNumber() == 0) {
                                    sendOrderDao.delete(quantitative.getSendOrderId());
                                }
                            }
                        }
                        quantitative.setSendOrderId(null);
                        quantitative.setVehicleNumber(null);
                        quantitative.setSendTime(null);
                    }

                    if (flag == 1) {
                        if (quantitative.getSendOrderId() == null) {
                            throw new ServiceException("发货失败,请重新发货");
                        }
                    }
                    quantitative.setFlag(flag);
                    dao.save(quantitative);

                }
            }
        }
        return count;
    }

    @Override
    public void setActualSingleNumber(Long id, Integer actualSingleNumber) {
        QuantitativeChild quantitativeChild = quantitativeChildDao.findOne(id);
        quantitativeChild.setActualSingleNumber(actualSingleNumber);
        if (quantitativeChild.getSingleNumber() != actualSingleNumber) {
            quantitativeChild.setChecks(1);
        } else {
            quantitativeChild.setChecks(0);
        }
        quantitativeChildDao.save(quantitativeChild);
    }

    @Override
    public void checkNumber(Long id) {
        QuantitativeChild quantitativeChild = quantitativeChildDao.findOne(id);
        quantitativeChild.setChecks(0);
        quantitativeChild.setSingleNumber(quantitativeChild.getActualSingleNumber());
        quantitativeChildDao.save(quantitativeChild);
    }

    @Override
    public void updateActualSingleNumber(QuantitativeChild quantitativeChild) {
        QuantitativeChild ot = quantitativeChildDao.findOne(quantitativeChild.getId());
        BeanCopyUtils.copyNotEmpty(quantitativeChild, ot, "");
        quantitativeChildDao.save(ot);
    }

    @Override
    public int updateQuantitativeSendTime(String ids, Date sendTime) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    if (quantitative.getFlag() == 0) {
                        throw new ServiceException("第" + (count + 1) + "条记录未发货无法修改");
                    }
                    quantitative.setSendTime(sendTime);
                    dao.save(quantitative);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    @Transactional
    public void saveUpdateQuantitative(Quantitative quantitative, String ids) {
        if (StringUtils.isEmpty(ids)) {
            // 根据总包数进行多条新增
            if (quantitative.getSumPackageNumber() > 0) {
                for (int i = 0; i < quantitative.getSumPackageNumber(); i++) {
                    Quantitative newQuantitative = new Quantitative();
                    BeanCopyUtils.copyNotEmpty(quantitative, newQuantitative, "");
                    quantitative.setId(null);
                    saveQuantitative(newQuantitative);
                }
            }
        } else {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    // 原始数据
                    Quantitative ot = findOne(id);
                    Quantitative newQuantitative = new Quantitative();
                    BeanCopyUtils.copyNotEmpty(ot, newQuantitative, "");
                    newQuantitative.setTime(quantitative.getTime());
                    newQuantitative.setCustomerId(quantitative.getCustomerId());
                    newQuantitative.setUserId(quantitative.getUserId());
                    newQuantitative.setChild(quantitative.getChild());
                    // 子单内容无法批量修改
                    if (idArr.length > 1) {
                        JSONArray jsonArray = new JSONArray();
                        if (ot.getQuantitativeChilds().size() > 0) {
                            for (QuantitativeChild quantitativeChild : ot.getQuantitativeChilds()) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("id", quantitativeChild.getId());
                                jsonObject.put("underGoodsId", quantitativeChild.getUnderGoodsId());
                                jsonObject.put("singleNumber", quantitativeChild.getSingleNumber());
                                jsonArray.add(jsonObject);
                            }
                        }
                        newQuantitative.setChild(jsonArray.toJSONString());
                    }
                    saveQuantitative(newQuantitative);
                }
            }
        }
    }

    @Override
    public PageResult<Quantitative> warehousing(int page, int size) {
        PageResult<Quantitative> pageQuantitative = new PageResult<Quantitative>();
        pageQuantitative.setRows(dao.warehousing(page, size));
        pageQuantitative.setTotal((long) dao.warehousing());
        return pageQuantitative;
    }

    @Override
    public int putWarehousing(String ids, String location, String reservoirArea) {
        int count = 0;
        if (StrUtil.isNotEmpty(ids)) {
            String[] idsArr = ids.split(",");
            for (String idString : idsArr) {
                Quantitative quantitative = findOne(Long.valueOf(idString));
                quantitative.setLocation(location);
                quantitative.setReservoirArea(reservoirArea);
                quantitative.setWarehousing(1);
                save(quantitative);
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Quantitative> findBySendTime(DateTime beginOfDay, DateTime endOfDay, long warehouseTypeId) {
        return dao.findBySendTimeBetweenAndCustomerIdAndWarehouseTypeId(beginOfDay, endOfDay, (long) 363,
                warehouseTypeId);
    }

    @Override
    @Transactional
    public int reconciliationQuantitative(String ids, Integer reconciliation) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    if (reconciliation == 1 && quantitative.getReconciliation() != null
                            && quantitative.getReconciliation() == 1) {
                        throw new ServiceException("已对账请勿多次对账");
                    }
                    if (reconciliation == 0 && quantitative.getReconciliation() != null
                            && quantitative.getReconciliation() == 0) {
                        throw new ServiceException("未对账请勿取消对账");
                    }
                    quantitative.setReconciliation(reconciliation);
                    dao.save(quantitative);
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reCreatSendOrder(String ids) {
        // 创建新的发货单
        SendOrder sendOrder = new SendOrder();
        List<Long> idlist =
                Arrays.asList(ids.split(",")).stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        List<Quantitative> quantitativeList = dao.findByIdIn(idlist);
        if (quantitativeList.size() == 1) {
            List<Quantitative> list = dao.findBySendOrderId(quantitativeList.get(0).getSendOrderId());
            if (list.size() == 1) {
                throw new ServiceException("仅剩一条，无法发货");
            }
        }
        sendOrder.setSendPackageNumber(quantitativeList.size());
        sendOrder.setSendTime(quantitativeList.get(0).getSendTime());
        sendOrder.setTax(1);
        sendOrder.setAudit(0);
        sendOrder.setCustomerId(quantitativeList.get(0).getCustomerId());
        sendOrder.setSendPackageNumber(quantitativeList.size());
        sendOrder.setWarehouseTypeId(quantitativeList.get(0).getWarehouseTypeId());
        sendOrder.setInterior(0);
        quantitativeList.forEach(q -> {
            int number = q.getQuantitativeChilds().stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
            sendOrder.setNumber(NumUtils.setzro(sendOrder.getNumber()) + number);
        });
        sendOrderDao.save(sendOrder);

        // 更新原始发货单
        if (quantitativeList.get(0).getSendOrderId() != null) {
            SendOrder ot = sendOrderDao.findOne(quantitativeList.get(0).getSendOrderId());
            ot.setSendPackageNumber(ot.getSendPackageNumber() - sendOrder.getSendPackageNumber());
            ot.setNumber(ot.getNumber() - sendOrder.getNumber());
            if (ot.getSendPackageNumber() != null && ot.getSingerPrice() != null) {
                ot.setSendPrice(NumberUtil.mul(ot.getSendPackageNumber(), ot.getSingerPrice()));
                ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSendPrice()));
            }
            sendOrderDao.save(ot);
        }

        // 更新量化单中的发货单id
        quantitativeList.forEach(q -> {
            q.setSendOrderId(sendOrder.getId());
        });
        save(quantitativeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantitativeChildRemark(String remark, String ids) {
        List<Long> idlist =
                Arrays.asList(ids.split(",")).stream().map(id -> Long.valueOf(id)).collect(Collectors.toList());
        List<Quantitative> quantitativeList = dao.findByIdIn(idlist);
        quantitativeList.forEach(quantitative -> quantitative.getQuantitativeChilds().forEach(child -> {
            child.setRemarks(remark);
        }));
        save(quantitativeList);
    }
}
