package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

import cn.hutool.core.util.StrUtil;

@Service
public class UnderGoodsServiceImpl extends BaseServiceImpl<UnderGoods, Long> implements UnderGoodsService {

    @Autowired
    private UnderGoodsDao dao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private QuantitativeChildDao quantitativeChildDao;
    @Autowired
    private QuantitativeDao quantitativeDao;
    @Autowired
    private MantissaLiquidationDao mantissaLiquidationDao;

    private static final String END = "end";

    @Override
    public PageResult<UnderGoods> findPages(UnderGoods param, PageParameter page) {
        CurrentUser cu = SessionManager.getUserSession();
        //蓝白仓库
        if(cu.getRole().contains("stickBagAccount") || cu.getRole().contains("stickBagStick") ) {
            param.setWarehouseTypeId((long)274);
        }
        //11号仓库
        if(cu.getRole().contains("packScene") || cu.getRole().contains("elevenSend")) {
            param.setWarehouseTypeId((long)275);
        }
        Page<UnderGoods> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按产品id
            if (param.getProductId() != null) {
                predicate.add(cb.equal(root.get("productId").as(Long.class), param.getId()));
            }
            // 按库区
            if (param.getWarehouseTypeId() !=null) {
                predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
            }
            // 是否天猫
            if (param.getInternal() != null) {
                predicate.add(cb.equal(root.get("internal").as(Integer.class), param.getInternal()));
            }
            // 按产品名称
            if (!StringUtils.isEmpty(param.getProductName())) {
                predicate.add(cb.like(root.get("product").get("name").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
            }
            // 按产品编号
            if (!StringUtils.isEmpty(param.getProductNumber())) {
                predicate.add(
                    cb.like(root.get("product").get("number").as(String.class), "%" + param.getProductNumber() + "%"));
            }
            // 按批次
            if (!StringUtils.isEmpty(param.getBacthNumber())) {
                predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
            }
            // 按状态
            if (!StringUtils.isEmpty(param.getStatus())) {
                predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
            }
            // 按备注
            if (!StringUtils.isEmpty(param.getRemarks())) {
                predicate.add(cb.like(root.get("remarks").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getRemarks()) + "%"));
            }
            // 按生成时间过滤
            if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("allotTime").as(Date.class), param.getOrderTimeBegin(),
                    param.getOrderTimeEnd()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<UnderGoods> result = new PageResult<>(pages, page);
        // 发货剩余数量
        // 贴包剩余数量
        result.getRows().forEach(r -> {
            // 贴包数量
            List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(r.getId());
            int numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
            r.setSurplusStickNumber(r.getNumber() - numberStickSum);
            // 发货数量
            List<Object> quantitativeListId = quantitativeDao.findSendNumber(r.getId());
            List<Long> quantitativeListIdLong =  quantitativeListId.stream().map(x -> Long.valueOf(x.toString())).collect(Collectors.toList());
            List<QuantitativeChild> quantitativeList = quantitativeChildDao.findByIdIn(quantitativeListIdLong);
            
            int numberSendSum = quantitativeList.stream().mapToInt(QuantitativeChild::getActualSingleNumber).sum();
            r.setSurplusSendNumber(r.getNumber() - numberSendSum);
            // 尾数清算数量
            List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
            int numberMantissaLiquidationSum =
                mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
            r.setSurplusStickNumber(r.getSurplusStickNumber() - numberMantissaLiquidationSum);
            r.setSurplusSendNumber(r.getSurplusSendNumber() - numberMantissaLiquidationSum);
        });
        return result;
    }

    @Override
    public PageResult<UnderGoods> findList(UnderGoods param) {
        CurrentUser cu = SessionManager.getUserSession();
        //蓝白仓库
        if(cu.getRole().contains("stickBagAccount") || cu.getRole().contains("stickBagStick") ) {
            param.setWarehouseTypeId((long)274);
        }
        //11号仓库
        if(cu.getRole().contains("packScene") || cu.getRole().contains("elevenSend")) {
            param.setWarehouseTypeId((long)275);
        }
        PageParameter page = new PageParameter(0, 50);
        Page<UnderGoods> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按产品名称
            if (!StringUtils.isEmpty(param.getProductName())) {
                predicate.add(cb.like(root.get("product").get("name").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
            }
            // 按库区
            if (param.getWarehouseTypeId() !=null) {
                predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
            }
            // 默认过滤未完成的数据
            predicate.add(cb.equal(root.get("status").as(Integer.class), 0));
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<UnderGoods> result = new PageResult<>(pages, page);
        result.getRows().forEach(r -> {
            r.setProductName(r.getProduct().getName());
            // 贴包数量
            List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(r.getId());
            int numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
            r.setSurplusStickNumber(r.getNumber() - numberStickSum);
            // 尾数清算数量
            List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
            int numberMantissaLiquidationSum =
                mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
            r.setSurplusStickNumber(r.getSurplusStickNumber() - numberMantissaLiquidationSum);
        });
        return result;
    }

    @Override
    public void saveUnderGoods(UnderGoods underGoods) {
        underGoods.setStatus(0);
        CurrentUser cu = SessionManager.getUserSession();
        //蓝白仓库
        if(cu.getRole().contains("stickBagAccount")) {
            underGoods.setWarehouseTypeId((long)274);
        }
        //11号仓库
        if(cu.getRole().contains("packScene")) {
            underGoods.setWarehouseTypeId((long)275);
        }
        save(underGoods);
    }

    @Override
    public int excelUnderGoods(ExcelListener excelListener) {
        List<Object> excelListenerList = excelListener.getData();
        List<UnderGoods> underGoodsList = new ArrayList<>();
        for (int i = 0; i < excelListenerList.size(); i++) {
            UnderGoods underGoods = new UnderGoods();
            UnderGoodsPoi cPoi = (UnderGoodsPoi)excelListenerList.get(i);
            if (cPoi.getName().equals(END)) {
                break;
            }
            List<Product> productList = productDao.findByName(cPoi.getName());
            if (productList.size() > 0) {
                productList.forEach(p -> {
                    if (StrUtil.isNotBlank(p.getNumber()) || (StrUtil.isNotBlank(p.getOriginDepartment())
                        && p.getOriginDepartment().equals(Constants.PRODUCT_FRIST_PACK))) {
                        underGoods.setProductId(p.getId());
                    }
                });
            } else {
                throw new ServiceException("当前导入excel第" + (i + 1) + "条商品不存在，请先添加");
            }
            if (cPoi.getNumber() == null) {
                throw new ServiceException("当前导入excel第" + (i + 1) + "条商品的数量不存在，请先添加");
            }
            underGoods.setNumber(cPoi.getNumber());
            underGoods.setBacthNumber(cPoi.getBacthNumber());
            underGoods.setAllotTime(cPoi.getAllotTime());
            underGoodsList.add(underGoods);
        }
        dao.save(underGoodsList);
        return underGoodsList.size();
    }

    @Override
    public List<UnderGoods> getAll() {
        List<UnderGoods> result = dao.findAll();
        List<Long> longList = result.stream().map(UnderGoods::getId).collect(Collectors.toList());
        // 贴包数量
        List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsIdIn(longList);
        // 贴包剩余数量
        result.forEach(r -> {
            int numberStickSum = stickListList.stream()
                .filter(QuantitativeChild -> r.getId().equals(QuantitativeChild.getUnderGoodsId()))
                .mapToInt(QuantitativeChild::getSingleNumber).sum();
            r.setSurplusStickNumber(r.getNumber() - numberStickSum);
            // 尾数清算数量
            List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
            int numberMantissaLiquidationSum =
                mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
            r.setSurplusStickNumber(r.getSurplusStickNumber() - numberMantissaLiquidationSum);
        });
        return result;
    }

    @Override
    public void updateUnderGoods(UnderGoods underGoods) {
        CurrentUser cu = SessionManager.getUserSession();
        //蓝白仓库
        if(cu.getRole().contains("stickBagAccount")) {
            underGoods.setWarehouseTypeId((long)274);
        }
        //11号仓库
        if(cu.getRole().contains("packScene")) {
            underGoods.setWarehouseTypeId((long)275);
        }
        if (underGoods.getId() != null) {
            // 贴包数量
            List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(underGoods.getId());
            int numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
            underGoods.setSurplusStickNumber(underGoods.getNumber() - numberStickSum);
            // 尾数清算数量
            List<MantissaLiquidation> mantissaLiquidationList =
                mantissaLiquidationDao.findByUnderGoodsId(underGoods.getId());
            int numberMantissaLiquidationSum =
                mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
            underGoods.setSurplusStickNumber(underGoods.getSurplusStickNumber() - numberMantissaLiquidationSum);
            // 当使用新的数量对贴包数量进行更新，>0则将下货单更新为活动状态
            if (underGoods.getSurplusStickNumber() > 0) {
                underGoods.setStatus(0);
            }
            if (underGoods.getSurplusStickNumber() == 0) {
                underGoods.setStatus(1);
            }
            save(underGoods);
        }
    }
}
