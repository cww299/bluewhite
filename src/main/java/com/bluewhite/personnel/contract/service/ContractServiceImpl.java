package com.bluewhite.personnel.contract.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.personnel.contract.dao.ContractDao;
import com.bluewhite.personnel.contract.entity.Contract;
import com.bluewhite.system.sys.dao.FilesDao;
import com.bluewhite.system.sys.entity.Files;

import cn.hutool.core.date.DateUtil;

@Service
public class ContractServiceImpl extends BaseServiceImpl<Contract, Long> implements ContractService {

    @Autowired
    private ContractDao dao;

    @Autowired
    private FilesDao filesDao;

    @Override
    public void addContract(Contract contract) {
        if (!StringUtils.isEmpty(contract.getFileIds())) {
            String[] fileIds = contract.getFileIds().split(",");
            Set<Files> fileSet = new HashSet<>();
            for (String idString : fileIds) {
                Files files = filesDao.findOne(Long.parseLong(idString));
                fileSet.add(files);
            }
            contract.setFileSet(fileSet);
        }
        if (contract.getId() == null) {
            contract.setFlag(1);
            if(contract.getCode()==null || contract.getCode().isEmpty()) {	//没有合同编号且id为null。合同新增
            	contract.setCode(getContractCode());
            }
        } else {
            if (new Date().after(contract.getEndTime())) {
                contract.setFlag(0);
            } else {
                contract.setFlag(1);
            }
            if(contract.getIsRenew()==1) {	//如果修改操作为续签
            	renewContract(contract);
            	return;
            }
        }
        save(contract);
    }

    public void renewContract(Contract contract) {
    	Contract lastContract = dao.getOne(contract.getId());	//续签合同的上一份
    	lastContract.setIsRenew(1);		//设置合同已被续签
    	if (new Date().after(lastContract.getEndTime())) {	//判断合同是否过期
    		lastContract.setFlag(0);
        } else {
        	lastContract.setFlag(1);
        }
    	save(lastContract);	//保存续签合同
    	contract.setId(null);	
    	contract.setIsRenew(0);
    	addContract(contract);	//新增合同
    }
    
    public String getContractCode() {
    	String d = new SimpleDateFormat("yyMMdd").format(new Date());
    	while(true) {
    		int random =  new Random().nextInt(900) + 100;	//取100~999之间的随机数
    		String code = "HT"+d+random;
    		Contract contract = dao.findByCode(code);
    		if(contract==null || contract.getId()==null) {
    			return code;
    		}
    	}
    }
    
    @Override
    public PageResult<Contract> findContractPage(Contract param, PageParameter page) {
        Page<Contract> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按名称过滤
            if (!StringUtils.isEmpty(param.getContent())) {
                predicate.add(cb.like(root.get("content").as(String.class),
                    "%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
            }
            // 按合同种类过滤
            if (param.getContractKindId() != null) {
                predicate.add(cb.equal(root.get("contractKindId").as(Long.class), param.getContractKindId()));
            }
            // 按合同类型过滤
            if (param.getContractTypeId() != null) {
                predicate.add(cb.equal(root.get("contractTypeId").as(Long.class), param.getContractTypeId()));
            }
            // 是否有效
            if (param.getFlag() != null) {
                predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
            }
            // 按开始日期
            if (!StringUtils.isEmpty(param.getStartTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin())
                && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("starTime").as(Date.class), param.getOrderTimeBegin(),
                    param.getOrderTimeEnd()));
            }
            // 结束日期
            if (!StringUtils.isEmpty(param.getEndTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin())
                && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(
                    cb.between(root.get("endTime").as(Date.class), param.getOrderTimeBegin(), param.getOrderTimeEnd()));
            }
            if (param.getCode() != null && !param.getCode().isEmpty()) {
                predicate.add(cb.equal(root.get("code").as(String.class), param.getCode()));
            }
            if(param.getIsRenew()<2)	//传2的时候全查
            	predicate.add(cb.equal(root.get("isRenew").as(Integer.class), param.getIsRenew()));
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<Contract> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    public int deleteContract(String ids) {
        return delete(ids);
    }

    @Override
    public Map<String, Object> remindContract() {
        checkContract();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Contract> contractList = dao.findByFlagAndIsRenew(1,0);
        List<Map<String, Object>> contractEndList = new ArrayList<Map<String, Object>>();
        for (Contract contract : contractList) {
        	if(contract.getIsRenew()==1)	//如果已经续签
        		continue;
            Map<String, Object> us = new HashMap<String, Object>();
            long co = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
                DatesUtil.getfristDayOftime(contract.getEndTime()));
            if (co <= 45) {
            	us.put("code", contract.getCode());
                us.put("id", contract.getId());
                us.put("content", contract.getContent());
                us.put("kind", contract.getContractKind().getName());
                us.put("type", contract.getContractType().getName());
                us.put("time", DateUtil.format(contract.getEndTime(), "yyyy-MM-dd"));
                contractEndList.add(us);
            }
        }
        map.put("contractEnd", contractEndList);
        List<Map<String, Object>> contractPayList = new ArrayList<Map<String, Object>>();
        for (Contract contract : contractList) {
            Map<String, Object> us = new HashMap<String, Object>();
            if(contract.getPaymentTime()==null)
            	continue;
            long co = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
                DatesUtil.getfristDayOftime(contract.getPaymentTime()));
            if (co <= 60) {
                us.put("id", contract.getId());
                us.put("code", contract.getCode());
                us.put("content", contract.getContent());
                us.put("kind", contract.getContractKind().getName());
                us.put("type", contract.getContractType().getName());
                us.put("time", DateUtil.format(contract.getPaymentTime(), "yyyy-MM-dd"));
                contractPayList.add(us);
            }
        }
        map.put("contractPay", contractPayList);
        return map;
    }

    private void checkContract() {
        List<Contract> contractList = dao.findByFlag(1);
        contractList.forEach(c -> {
            if (new Date().after(c.getEndTime())) {
                c.setFlag(0);
            }
        });
        save(contractList);
    }

}
