package com.bluewhite.personnel.contract.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		if(contract.getId()==null){
			contract.setFlag(1);
		}
		save(contract);
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
				predicate.add(cb.between(root.get("endTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
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
	public List<Map<String, Object>> remindContract() {
		List<Map<String, Object>> contractEndList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Contract> contractList = dao.findByFlag(1);
		for (Contract contract : contractList) {
			Map<String, Object> us = new HashMap<String, Object>();
			long co = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
					DatesUtil.getfristDayOftime(contract.getEndTime()));
			if (co <= 45) {
				us.put("id", contract.getId());
				us.put("content", contract.getContent());
				us.put("kind", contract.getContractKind().getName());
				us.put("type", contract.getContractType().getName());
				us.put("time", sdf.format(contract.getEndTime()));
				contractEndList.add(us);
			}
		}
		return contractEndList;
	}

}
