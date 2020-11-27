package com.bluewhite.personnel.officeshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.officeshare.dao.OfficeSuppliesDao;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;

import cn.hutool.core.date.DateUtil;

@Service
public class OfficeSuppliesServiceImpl extends BaseServiceImpl<OfficeSupplies, Long> implements OfficeSuppliesService {

    @Autowired
    private OfficeSuppliesDao dao;

    @Override
    public PageResult<OfficeSupplies> findPages(OfficeSupplies param, PageParameter page) {
        Page<OfficeSupplies> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按名称过滤
            if (!StringUtils.isEmpty(param.getName())) {
                predicate.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
            }
            // 按类型
            if (param.getType() != null) {
                predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
            }
            // 按数量
            if (param.getInventoryNumber() != null) {
                predicate.add(cb.equal(root.get("inventoryNumber").as(Double.class), param.getInventoryNumber()));
            }
            // 按仓位过滤
            if (!StringUtils.isEmpty(param.getLocation())) {
                predicate.add(cb.like(root.get("location").as(String.class), "%" + param.getLocation() + "%"));
            }
            if (!StringUtils.isEmpty(param.getCreatedAt())) {
                if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                    predicate.add(cb.between(root.get("createdAt").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
                }
            }
            // 编码
            if(param.getQcCode()!=null && !param.getQcCode().isEmpty()) {
            	predicate.add(cb.equal(root.get("qcCode").as(String.class), param.getQcCode()));
            }
            // 食材材料
            if(param.getSingleMealConsumptionId() != null ) {
            	predicate.add(cb.equal(root.get("singleMealConsumptionId").as(Long.class), param.getSingleMealConsumptionId()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResultStat<OfficeSupplies> result = new PageResultStat<>(pages, page);
        // result.getRows().forEach(o->{
        // List<InventoryDetail> inventoryDetailList = inventoryDetailDao.findByOfficeSuppliesId(o.getId());
        // //入库记录
        // List<InventoryDetail> inList =
        // inventoryDetailList.stream().filter(InventoryDetail->InventoryDetail.getFlag().equals(0)).collect(Collectors.toList());
        // double inNumber = inList.stream().mapToDouble(InventoryDetail::getNumber).sum();
        // //出库记录
        // List<InventoryDetail> outList =
        // inventoryDetailList.stream().filter(InventoryDetail->InventoryDetail.getFlag().equals(1)).collect(Collectors.toList());
        // double outNumber = outList.stream().mapToDouble(InventoryDetail::getNumber).sum();
        // o.setInventoryNumber(NumUtils.sub(inNumber,outNumber));
        // });
        return result;
    }

    @Override
    @Transactional
    public void addOfficeSupplies(OfficeSupplies officeSupplies) {
        if (officeSupplies.getId() != null) {
            OfficeSupplies ot = dao.findOne(officeSupplies.getId());
            BeanCopyUtils.copyNotEmpty(officeSupplies, ot, "");
            ot.setLibraryValue(
                NumUtils.mul(ot.getInventoryNumber(), NumUtils.setzro(ot.getPrice())));
            save(ot);
        } else {
        	//新增
        	if(officeSupplies.getQcCode()==null || officeSupplies.getQcCode().isEmpty()) {
        		officeSupplies.setQcCode(String.valueOf(DateUtil.current(true)));
        	}
            officeSupplies.setInventoryNumber(0.0);
            officeSupplies.setLibraryValue(
                NumUtils.mul(officeSupplies.getInventoryNumber(), NumUtils.setzro(officeSupplies.getPrice())));
            save(officeSupplies);
        }
    }

    @Override
    @Transactional
    public int deleteOfficeSupplies(String ids) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    dao.delete(id);
                    count++;
                }
            }
        }
        return count;
    }

}
