package com.bluewhite.system.user.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.dao.UserContractDao;
import com.bluewhite.system.user.entity.UserContract;

@Service
public class UserContractServiceImpl extends BaseServiceImpl<UserContract, Long> implements UserContractService  {
	
	@Autowired
	private UserContractDao dao;
	
	/*
	 *分页查询
	 */
	@Override
	public PageResult<UserContract> findPage(Map<String, Object> params,PageParameter page) {
//		Page<UserContract> pages = dao.findAll((root, query, cb) -> {
//			List<Predicate> predicate = new ArrayList<>();
//			if (userContract.getUserId() != null) {
//				predicate.add(cb.equal(root.get("userId").as(Long.class), userContract.getUserId()));
//			}
//			Predicate[] pre = new Predicate[predicate.size()];
//			query.where(predicate.toArray(pre));
//			return null;
//		}, page);
//		PageResult<UserContract> result = new PageResult<>(pages, page);
		return findAll(page, params);
	}

	@Override
	public UserContract addUserContract(UserContract userContract) {
		return dao.save(userContract);
	}

	@Override
	public int deletes(String[] ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				dao.delete(id); 
				count++;
			}
		}
		return count;
	}
	

}
