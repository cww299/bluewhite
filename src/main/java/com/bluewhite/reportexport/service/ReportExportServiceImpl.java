package com.bluewhite.reportexport.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.entity.Product;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.system.user.entity.User;
@Service
public class ReportExportServiceImpl implements ReportExportService{
	
	@Autowired
	private BaseDataDao baseDataDao;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional
	public int importProductExcel(List<ProductPoi> excelProduct) {
		int count = 0;
		if(excelProduct.size()>0){
			List<Product> productList = new ArrayList<Product>();
			for(ProductPoi proPoi :excelProduct){
				Product product  = new Product();
				product.setNumber(proPoi.getNumber());
				product.setName(proPoi.getName());
				productList.add(product);
				count++;
			}
			this.saveAllProduct(productList);
		}
		return count;
	}
	
	/**
	 * 产品导入批处理
	 * @param productList
	 */
	private void saveAllProduct(List<Product> productList) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		 for (int i = 0; i < productList.size(); i++){
			 Product courtsResident = productList.get(i);
			 entityManager.merge(courtsResident);
	            if (i % 1000 == 0 && i > 0) {
	            	entityManager.flush();
	            	entityManager.clear();
	            }
	        }
		 entityManager.close();
	    }

	@Override
	public int importUserExcel(List<UserPoi> excelUser) {
		int count = 0;
		if(excelUser.size()>0){
			List<User> userList = new ArrayList<User>();
			//部门
			String orgName = "";
			//职位
			String position = "";
			for(UserPoi proPoi :excelUser){
				User user  = new User();
				user.setUserName(proPoi.getUserName());
				user.setBirthDate(proPoi.getBirthday());
				user.setEntry(proPoi.getEntry());
				//导入用户同时更新基础字典表，对用户的部门和职位进行字典管理
				//部门
				if(proPoi.getOrgName()!=null && proPoi.getOrgName() != orgName ){
					orgName = proPoi.getOrgName();
				}
				BaseData baseOrgName = new BaseData();
				baseOrgName.setName(orgName);
				baseOrgName.setType("orgName");
				baseOrgName.setParentId((long)0);
				
				//职位
				if(proPoi.getPosition()!=null && proPoi.getPosition() != orgName ){
					position = proPoi.getPosition();
				}
				BaseData basePosition = new BaseData();
				basePosition.setName(position);
				basePosition.setType("position");
				basePosition.setParentId((long)0);
				
				userList.add(user);
				count++;
			}
			this.saveAllUser(userList);
		}
		return count;
	}

	private void saveAllUser(List<User> userList) {
		// TODO Auto-generated method stub
		
	}

}
