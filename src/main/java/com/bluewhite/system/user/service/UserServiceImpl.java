package com.bluewhite.system.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.entity.User;
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	@Autowired
	private UserDao dao;
	
	@Autowired
	private PermissionService permissionService;
	
	
	private final static Log log = Log.getLog(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	
	@Override
	public User findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public User loginByUsernameAndPassword(String username, String password) {
//		  	String newPassword = new SimpleHash("md5", password).toHex();
	        User user = dao.findByUserNameAndPassword(username, password);
	        if (user != null) {
	            user.setPermissions(findStringPermissions(user));
	        }
	        return user;
	}
	
	@Override
	public Set<String> findStringPermissions(User user) {
        if (user == null || user.getId() == null) {
            return new HashSet<String>();
        }
        Set<String> result = new HashSet<String>();
        User u = findOne(user.getId());
        Set<Role> roles = u.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
            	// 查找所有角色关联的菜单权限
                List<RoleMenuPermission> roleMenuPermissions = role.getResourcePermission();
                if (!CollectionUtils.isEmpty(roleMenuPermissions)) {
                    for (RoleMenuPermission rmp : roleMenuPermissions) {
                        Set<String> actualPermissions = permissionService.getActualPermissionStr(rmp.getMenuId(),rmp.getPermissionIds());
                        result.addAll(actualPermissions);
                    }
                }
            }
        }
        return result;
    }

	@Override
	public User findByloginName(String userName) {
		return dao.findByLoginName(userName);
	}
	
	@Override
	public PageResult<User> getPagedUser(PageParameter page, User user) {
		CurrentUser cu = SessionManager.getUserSession();
		//质检
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_QUALITY)){
			 user.setOrgNameIds(Constants.QUALITY_ORGNAME);
		}
		//包装
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_PACK)){
			 user.setOrgNameIds(Constants.PACK_ORGNAME);
		}
		//针工
		if(cu.getRole().contains(Constants.PRODUCT_TWO_DEEDLE)){
			 user.setOrgNameIds(Constants.DEEDLE_ORGNAME);
		}
		//机工
		if(cu.getRole().contains(Constants.PRODUCT_TWO_MACHINIST)){
			 user.setOrgNameIds(Constants.MACHINIST_ORGNAME);
		}
		//裁剪
		if(cu.getRole().contains(Constants.PRODUCT_RIGHT_TAILOR)){
			 user.setOrgNameIds(Constants.TAILOR_ORGNAME);
		}
		
		Page<User> pageUser = userDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按id查找
			if (user.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),
						user.getId()));
			}
			//按分组查找
			if (user.getGroupId() != null) {
				predicate.add(cb.equal(root.get("groupId").as(Long.class),
						user.getGroupId()));
			}
			
			//按姓名查找
			if (!StringUtils.isEmpty(user.getUserName())) {
				predicate.add(cb.like(root.get("userName").as(String.class),
						"%" + user.getUserName() + "%"));
			}
			//按员工编号
			if (!StringUtils.isEmpty(user.getNumber())) {
				predicate.add(cb.like(root.get("number").as(String.class),
						"%" + user.getNumber() + "%"));
			}
			
			//部门,多个
			if (!StringUtils.isEmpty(user.getOrgNameIds())) {
				List<Long>  orgNameIdList = new ArrayList<Long>();
					String[] idArr = user.getOrgNameIds().split(",");
					for (String idStr : idArr) {
						Long id = Long.parseLong(idStr);
						orgNameIdList.add(id);
					}
				predicate.add(cb.and(root.get("orgNameId").as(Long.class).in(orgNameIdList)));
			}
			
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<User> result = new PageResult<>(pageUser,page);
		return result;
	}
	

	
	@Override
	public List<User> getPagedUserForCount(PageParameter page, User user) {
		Page<User> pagedArticles = userDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		List<User> users = pagedArticles.getContent();
		return users;
	}

	@Override
	public User findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}
	

	@Override
	public boolean deleteUsers(String ids) {
		String[] arrIds = ids.split(",");
		for(int i=0 ;i<arrIds.length;i++){
			userDao.delete(Long.valueOf(arrIds[i]));
		}
		return true;
	}



	@Override
	public boolean resetPwdByDefault(Long userId) {
		User user = userDao.findOne(userId);
		if(user == null){
			log.error("用户id对应的用户不存在。");
			return false;
		}
		String username = user.getUserName();
		//初始化密码为123456并加密存储
		user.setPassword(user.getPassword());
		userDao.save(user);
		return true;
	}






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
