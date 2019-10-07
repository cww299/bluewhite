package com.bluewhite.system.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.system.user.dao.TemporaryUserDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.TemporaryUser;

@Service
public class TemporaryUserServiceImpl  extends BaseServiceImpl<TemporaryUser, Long> implements TemporaryUserService{
	
	@Autowired
	private TemporaryUserDao dao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public PageResult<TemporaryUser> getPagedUser(PageParameter page, TemporaryUser param) {
		Page<TemporaryUser> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.like(root.get("userName").as(String.class),"%"+param.getUserName()+"%"));
			}
			// 按分组id过滤
			if (param.getGroupId() != null) {
				predicate.add(cb.equal(root.get("groupId").as(Long.class), param.getGroupId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<TemporaryUser> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void addTemporaryUser(TemporaryUser temporaryUser) {
		if(temporaryUser.getId()!=null){
			TemporaryUser ot = dao.findOne(temporaryUser.getId());
			update(temporaryUser, ot);
		}else{
			//保证临时人员id和正式人员id不重复
			temporaryUser.setStatus(0);
			List<TemporaryUser> temporaryUserOld = dao.findByUserName(temporaryUser.getUserName());
			if(temporaryUserOld.size()>0){
				throw new ServiceException("该临时人员姓名已存在,请先查找，如是重名员工，请联系管理员");
			}
			temporaryUser.setId(Long.parseLong(SalesUtils.findRandomCode()));;
			dao.save(temporaryUser);
		}
	}

	@Override
	@Transactional
	public int deleteTemporaryUser(String ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			String[] idString = ids.split(",");
			for(String id : idString){
				try {
					dao.delete(Long.parseLong(id));
				} catch (Exception e) {
					throw new ServiceException("该临时人员存在任务或者b工资，无法删除，如需删除，请联系管理员");
				}
				count++;
			}
		}
		return count;
	}
}
