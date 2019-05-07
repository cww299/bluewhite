package com.bluewhite.system.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.system.user.dao.UserContractDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private UserContractDao userContractDao;

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
	public PageResult<User> getPagedUser(PageParameter page, User user) {
		CurrentUser cu = SessionManager.getUserSession();
		if(user.getTemporarily()==null){
			//质检
			if(cu.getRole().contains(Constants.PRODUCT_FRIST_QUALITY)){
				user.setQuit(0);
				user.setOrgNameIds(Constants.QUALITY_ORGNAME);
			}
			//包装
			if(cu.getRole().contains(Constants.PRODUCT_FRIST_PACK)){
				user.setQuit(0);
				user.setOrgNameIds(Constants.PACK_ORGNAME);
			}
			//针工
			if(cu.getRole().contains(Constants.PRODUCT_TWO_DEEDLE)){
				user.setQuit(0);
				user.setOrgNameIds(Constants.DEEDLE_ORGNAME);
			}
			//机工
			if(cu.getRole().contains(Constants.PRODUCT_TWO_MACHINIST)){
				user.setQuit(0);
				user.setOrgNameIds(Constants.MACHINIST_ORGNAME);
			}
			//裁剪
			if(cu.getRole().contains(Constants.PRODUCT_RIGHT_TAILOR)){
				user.setQuit(0);
				user.setOrgNameIds(Constants.TAILOR_ORGNAME);
			}
		}
		page.setSort(new Sort(Direction.DESC, "lotionNumber"));
		Page<User> pageUser = userDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按id查找
			if (user.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),user.getId()));
			}
			//按分组查找
			if (user.getGroupId() != null) {
				predicate.add(cb.equal(root.get("groupId").as(Long.class),user.getGroupId()));
			}
			
			//忽略管理员
			if (user.getRole().size() == 0) {
			predicate.add(cb.equal(root.get("isAdmin").as(Boolean.class),false));
			}
			//是否外调
			if (user.getForeigns() != null) {
				predicate.add(cb.equal(root.get("foreigns").as(Integer.class),user.getForeigns()));
			}
			
			//归属车间
			if (user.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class),user.getType()));
			}
			
			//是否离职
			if (user.getQuit() != null) {
				predicate.add(cb.equal(root.get("quit").as(Integer.class),user.getQuit()));
			}
			
			//按手机号查找
			if (!StringUtils.isEmpty(user.getPhone())) {
				predicate.add(cb.like(root.get("phone").as(String.class),"%" + user.getPhone() + "%"));
			}
			
			//按姓名查找
			if (!StringUtils.isEmpty(user.getUserName())) {
				predicate.add(cb.like(root.get("userName").as(String.class),"%" + user.getUserName() + "%"));
			}
			
			//按学历查找
			if (!StringUtils.isEmpty(user.getEducation())) {
				predicate.add(cb.like(root.get("education").as(String.class),"%" + user.getEducation() + "%"));
			}
			
			//外调中按姓名精确查找
			if (!StringUtils.isEmpty(user.getTemporarilyName())) {
				predicate.add(cb.equal(root.get("userName").as(String.class),user.getTemporarilyName()));
			}
			
			//按归属银行查找
			if (!StringUtils.isEmpty(user.getAscriptionBank1())) {
				predicate.add(cb.like(root.get("ascriptionBank1").as(String.class),"%" + user.getAscriptionBank1() + "%"));
			}
			
			//按员工编号
			if (!StringUtils.isEmpty(user.getNumber())) {
				predicate.add(cb.like(root.get("number").as(String.class),"%" + user.getNumber() + "%"));
			}
			
			//按位置编号
			if (!StringUtils.isEmpty(user.getLotionNumber())) {
				predicate.add(cb.like(root.get("userContract").get("number").as(String.class),"%" + user.getLotionNumber() + "%"));
			}
			
			//是否签订合同
			if (!StringUtils.isEmpty(user.getCommitment())) {
				predicate.add(cb.equal(root.get("commitment").as(Integer.class), user.getCommitment() ));
			}
			//是否签订承诺书
			if (!StringUtils.isEmpty(user.getPromise())) {
				predicate.add(cb.equal(root.get("promise").as(Integer.class), user.getPromise() ));
			}
			
			//根据签订协议查找
			if (!StringUtils.isEmpty(user.getAgreementId())) {
				predicate.add(cb.like(root.get("agreementId").as(String.class), "%" + user.getAgreementId() + "%" ));
			}
			
			//是否保险
			if (!StringUtils.isEmpty(user.getSafe())) {
				predicate.add(cb.equal(root.get("safe").as(Integer.class), user.getSafe() ));
			}
			
			//男女
			if (!StringUtils.isEmpty(user.getGender())) {
				predicate.add(cb.equal(root.get("gender").as(Integer.class), user.getGender() ));
			}
			
			//是否销售
			if (!StringUtils.isEmpty(user.getSale())) {
				predicate.add(cb.equal(root.get("sale").as(Integer.class), user.getSale()) );
			}
			
			// 角色不为null
			if (user.getRole().size()>0) {
				SetJoin<User,Role> join = root.join(root.getModel().getSet("roles", Role.class),JoinType.LEFT);
				predicate.add(cb.isNotNull(join.get("id").as(Long.class)));
			}
			
			//退休返聘（男age>60，女age>55,还在正常工作）
			if (!StringUtils.isEmpty(user.getRetire())) {
				if(!StringUtils.isEmpty(user.getGender())){
					if (user.getGender()==1) {
						predicate.add(cb.greaterThan(root.get("age").as(Integer.class), 55 ));
					}else{
						predicate.add(cb.greaterThan(root.get("age").as(Integer.class), 60 ));
					}
				}
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
			
			
			//按时间过滤
    		if (!StringUtils.isEmpty(user.getOrderTimeBegin()) &&  !StringUtils.isEmpty(user.getOrderTimeEnd()) ) {
    			//按入职时间过滤
    			if(!StringUtils.isEmpty(user.getEntry())){
    				predicate.add(cb.between(root.get("entry").as(Date.class),
    						user.getOrderTimeBegin(),
    						user.getOrderTimeEnd()));
    			}
    			//按实际转正时间过滤
    			if(!StringUtils.isEmpty(user.getEstimate())){
    				predicate.add(cb.between(root.get("estimate").as(Date.class),
    						user.getOrderTimeBegin(),
    						user.getOrderTimeEnd()));
    			}
    			//按转正时间过滤
    			if(!StringUtils.isEmpty(user.getActua())){
    				predicate.add(cb.between(root.get("actua").as(Date.class),
    						user.getOrderTimeBegin(),
    						user.getOrderTimeEnd()));
    			}
    			
    		}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			query.distinct(true);
			return null;
		}, page);
		PageResult<User> result = new PageResult<>(pageUser,page);
		return result;
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
	public User findByNumber(String number) {
		return userDao.findByNumber(number);
	}
	
	@Override
	public List<User> findByNumberNotNull() {
		return userDao.findByNumberNotNull();
	}

	@Override
	public List<User> findByOrgNameId(Long orgNameId) {
		return dao.findByOrgNameIdAndQuit(orgNameId,0);
	}

	@Override
	public List<User> findByForeigns() {
		return  dao.findByForeignsAndIsAdminAndQuit(0,false,0);
	}

	@Override
	public int deleteUser(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					dao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public User addUser(User user) {
		if(!StringUtils.isEmpty(user.getPhone())){
			User u = findByPhone(user.getPhone());
			if(u != null){
				throw  new ServiceException("该用户手机号已存在");
			}else{
				user.setPassword( new SimpleHash("md5", "123456").toHex());
				user.setForeigns(0);
				UserContract userContract = new UserContract();
				userContractDao.save(userContract);
				user.setUserContract(userContract);
			}
		}else{
			throw  new ServiceException("手机号不能为空");
		}
		return save(user);
	}

	@Override
	public List<User> findUserList(User user) {
		List<User> result = userDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按id查找
			if (user.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),user.getId()));
			}
			//管理员
			if (user.getIsAdmin()!=null) {
				predicate.add(cb.equal(root.get("isAdmin").as(Boolean.class),user.getIsAdmin()));
			}
			
			//是否外调
			if (user.getForeigns() != null) {
				predicate.add(cb.equal(root.get("foreigns").as(Integer.class),user.getForeigns()));
			}
			
			//是否离职
			if (user.getQuit() != null) {
				predicate.add(cb.equal(root.get("quit").as(Integer.class),user.getQuit()));
			}
			
			//按手机号查找
			if (!StringUtils.isEmpty(user.getPhone())) {
				predicate.add(cb.like(root.get("phone").as(String.class),"%" + user.getPhone() + "%"));
			}
			
			//按姓名查找
			if (!StringUtils.isEmpty(user.getUserName())) {
				predicate.add(cb.like(root.get("userName").as(String.class),"%" + user.getUserName() + "%"));
			}
			
			//按学历查找
			if (!StringUtils.isEmpty(user.getEducation())) {
				predicate.add(cb.like(root.get("education").as(String.class),"%" + user.getEducation() + "%"));
			}
			
			//按员工编号
			if (!StringUtils.isEmpty(user.getNumber())) {
				predicate.add(cb.like(root.get("number").as(String.class),"%" + user.getNumber() + "%"));
			}
			
			//按位置编号
			if (!StringUtils.isEmpty(user.getLotionNumber())) {
				predicate.add(cb.like(root.get("userContract").get("number").as(String.class),"%" + user.getLotionNumber() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public boolean checkPassword(String password) {
		CurrentUser cu = SessionManager.getUserSession();
		String newPassword = new SimpleHash("md5", password).toHex();
		User user = dao.findOne(cu.getId());
		return user.getPassword().equals(newPassword);
	}

}
