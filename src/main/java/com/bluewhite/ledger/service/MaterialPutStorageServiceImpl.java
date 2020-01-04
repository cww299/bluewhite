package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.OrderProcurement;

@Service
public class MaterialPutStorageServiceImpl extends BaseServiceImpl<MaterialPutStorage, Long>
    implements MaterialPutStorageService {

    @Autowired
    private MaterialPutStorageDao dao;
    @Autowired
    private OrderProcurementDao orderProcurementDao;
    @Autowired
    private MaterialOutStorageDao materialOutStorageDao;
    @Autowired
    private MaterialPutOutStorageDao materialPutOutStorageDao;

    @Override
    public void saveMaterialPutStorage(MaterialPutStorage materialPutStorage) {
        if (materialPutStorage.getId() != null) {
            MaterialPutStorage ot = dao.findOne(materialPutStorage.getId());
            update(materialPutStorage, ot, "");
        } else {
            materialPutStorage.setSerialNumber(
                Constants.WLRK + StringUtil.getDate() + StringUtil.get0LeftString((int)(dao.count() + 1), 8));
            materialPutStorage.setInspection(0);
            dao.save(materialPutStorage);
        }
    }

    @Override
    public PageResult<MaterialPutStorage> findPages(PageParameter page, MaterialPutStorage param) {
        Page<MaterialPutStorage> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按物料编号
            if (!StringUtils.isEmpty(param.getMaterielName())) {
                predicate.add(cb.like(root.get("materiel").get("number").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getMaterielName()) + "%"));
            }
            // 按物料名称
            if (!StringUtils.isEmpty(param.getMaterielNumber())) {
                predicate.add(cb.like(root.get("materiel").get("name").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getMaterielNumber()) + "%"));
            }
            // 按库区
            if (param.getStorageAreaId() != null) {
                predicate.add(cb.equal(root.get("storageArea").get("id").as(Long.class), param.getStorageAreaId()));
            }
            // 按库位
            if (param.getStorageLocationId() != null) {
                predicate
                    .add(cb.equal(root.get("storageLocation").get("id").as(Long.class), param.getStorageLocationId()));
            }
            // 按验货
            if (param.getInspection() != null) {
                predicate.add(cb.equal(root.get("inspection").as(Long.class), param.getInspection()));
            }
            // 按到货日期
            if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
                    param.getOrderTimeEnd()));
            }
            // 按采购状态
            if (param.getInStatus() != null) {
                predicate.add(cb.equal(root.get("inStatus").as(Integer.class), param.getInStatus()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        pages.getContent().stream().forEach(m -> {
            //出库数量
            List<Long> longList = materialOutStorageDao.findMaterialPutStorageId(m.getId());
            List<MaterialOutStorage> materialOutStorageList = materialOutStorageDao.findAll(longList);
            double arrNumber = materialOutStorageList.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
            m.setSurplusNumber(NumUtils.sub(m.getArrivalNumber(), arrNumber));
        });
        PageResult<MaterialPutStorage> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    public int deleteMaterialPutStorage(String ids) {
        int i = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idStrings = ids.split(",");
            for (String idString : idStrings) {
                Long id = Long.parseLong(idString);
                MaterialPutStorage materialPutStorage = dao.findOne(id);
                List<Long> longList = materialOutStorageDao.findMaterialPutStorageId(id);
                List<MaterialOutStorage> materialOutStorageList = materialOutStorageDao.findAll(longList);
                if (materialOutStorageList.size() > 0) {
                    throw new ServiceException("第" + (i + 1) + "条入库单已有出库记录，无法删除，请先删除出库单");
                }
                if (materialPutStorage.getOrderProcurement().getArrival() == 1) {
                    throw new ServiceException("第" + (i + 1) + "条入库单的采购单已审核全部入库，无法删除");
                }
                delete(id);
                i++;
            }
        }
        return i;
    }

    @Override
    public void inspectionMaterialPutStorage(MaterialPutStorage materialPutStorage) {
        MaterialPutStorage ot = findOne(materialPutStorage.getId());
        if (ot.getInspection() == 1) {
            throw new ServiceException("当前采购入库单已检验，请勿重复检验");
        }
        materialPutStorage.setInspection(1);
        BeanCopyUtils.copyNotEmpty(materialPutStorage, ot, "");
        // 采购单
        OrderProcurement orderProcurement = orderProcurementDao.findOne(ot.getOrderProcurementId());
        // 当实际克重和约定克重不相符
        if (orderProcurement.getConventionSquareGram() != null && ot.getSquareGram() != null
            && orderProcurement.getConventionSquareGram() > ot.getSquareGram()) {
            ot.setGramPrice(NumUtils.mul(
                NumUtils.div(NumUtils.sub(orderProcurement.getConventionSquareGram(), ot.getSquareGram()),
                    orderProcurement.getConventionSquareGram(), 3),
                orderProcurement.getPrice(), ot.getArrivalNumber()));
        }
        save(ot);
    }

    @Override
    public double getArrivalNumber(Long id) {   
        // 获取到货数量
        List<MaterialPutStorage> materialPutStorageList = dao.findByOrderProcurementId(id);
        // 计算退货总数
        List<MaterialOutStorage> list = new ArrayList<>();
        materialPutStorageList.stream().forEach(m -> {
            List<Long> longList = materialOutStorageDao.findMaterialPutStorageId(m.getId());
            List<MaterialOutStorage> listMaterialOutStorage =  materialOutStorageDao.findAll(longList);
            if(listMaterialOutStorage.size()>0) {
                List<MaterialOutStorage>  mList = listMaterialOutStorage.stream().filter(MaterialOutStorage->MaterialOutStorage.getOutStatus()==4).collect(Collectors.toList());
                list.addAll(mList);
            }
        });
        double returnNumber = list.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
        double arrivalNumber = materialPutStorageList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
        return NumUtils.sub(arrivalNumber, returnNumber);
    }

    @Override
    public List<MaterialPutStorage> findByOrderProcurementId(Long id) {
        return dao.findByOrderProcurementId(id);
    }

    @Override
    public List<MaterialPutStorage> findByOrderProcurementIdAndInspection(Long id, int i) {
        return dao.findByOrderProcurementIdAndInspection(id, i);
    }

    @Override
    public List<MaterialPutStorage> detailsInventory(Long warehouseTypeId, Long materielId) {
        List<MaterialPutStorage> putStorageList = dao.findByMaterielId(materielId);
        if (warehouseTypeId != null) {
            putStorageList = dao.findByInWarehouseTypeIdAndMaterielId(warehouseTypeId, materielId);
        }
        putStorageList.forEach(m -> {
            // 入库单实际出库数量
            List<MaterialPutOutStorage> outPutStorageList = materialPutOutStorageDao.findByMaterialPutStorageId(m.getId());
            double arrNumber = outPutStorageList.stream().mapToDouble(MaterialPutOutStorage::getNumber).sum();
            // 入库单剩余数量
            m.setSurplusNumber(NumUtils.sub(m.getArrivalNumber(), arrNumber));
        });
        // 排除掉已经全部出库的入库单
        putStorageList = putStorageList.stream().filter(MaterialPutStorage -> MaterialPutStorage.getSurplusNumber() > 0)
            .sorted(Comparator.comparing(MaterialPutStorage::getArrivalTime)).collect(Collectors.toList());
        return putStorageList;
    }


   
}
