package com.bluewhite.reportexport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.Constants;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
@Service
public class ReportExportServiceImpl implements ReportExportService{
	
	@Autowired
	private BaseDataDao baseDataDao;
	
	@Autowired
	private BaseDataService baseDataService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private ProcedureService procedureService;
	
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
	@Transactional
	public int importUserExcel(List<UserPoi> excelUser) {
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(excelUser.size()>0){
			List<User> userList = new ArrayList<User>();
			for(UserPoi proPoi :excelUser){
				User user  = new User();
				user.setUserName(proPoi.getUserName());
				user.setLoginName(proPoi.getUserName());
				user.setPassword("123456");
				Date birthday = null;
				Date entry = null;
				try {
					if(proPoi.getBirthday() !=null || proPoi.getBirthday() != ""){
						birthday = sdf.parse(proPoi.getBirthday());
					}
					if(proPoi.getEntry() !=null || proPoi.getEntry() != ""){
						entry = sdf.parse(proPoi.getEntry());
						}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				user.setBirthDate(birthday);
				user.setEntry(entry);
				//导入用户同时更新基础字典表，对用户的部门和职位进行字典管理
				//部门
				BaseData baseOrgName  = baseDataDao.findByName(proPoi.getOrgName());
				if(baseOrgName == null){
					baseOrgName = new BaseData();
					baseOrgName.setName(proPoi.getOrgName());
					baseOrgName.setType("orgName");
					baseOrgName.setParentId((long)0);
					baseDataDao.save(baseOrgName);
				}
				//职位
				BaseData basePosition  = baseDataDao.findByName(proPoi.getPosition());
				if(basePosition == null){
					basePosition = new BaseData();
					basePosition.setName(proPoi.getPosition());
					basePosition.setType("position");
					basePosition.setParentId((long)0);
					baseDataDao.save(basePosition);
				}
				//更新职位和部门的id
				user.setOrgNameId(baseOrgName.getId());
				user.setPositionId(basePosition.getId());
				userList.add(user);
				count++;
			}
			userDao.save(userList);
		}
		return count;
	}

	@Override
	@Transactional
	public int importProcedureExcel(List<ProcedurePoi> excelProcedure, Long productId,Integer type) {
		int count = 0;
		if(type==null){
			type = ProTypeUtils.roleGetProType();
		}
		List<BaseData> baseDataList = null;
		if(type==1){
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_FRIST_QUALITY);
			
		}
		if(type==2){
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_FRIST_PACK);
		}
		if(type==3){
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_TOW_DEEDLE);
		}
		
		if(excelProcedure.size()>0){
			List<Procedure> procedureList =new ArrayList<Procedure>();
 			for(ProcedurePoi procedurePoi : excelProcedure){
				Procedure procedure = new Procedure();
				procedure.setProductId(productId);
				procedure.setName(procedurePoi.getName());
				procedure.setWorkingTime(NumUtils.round(procedurePoi.getWorkingTime()));
				procedure.setType(type);
				procedure.setProcedureTypeId(baseDataList.get(0).getId());
				procedureService.countPrice(procedure);
				procedureList.add(procedure);
				count++;
			}
 			procedureDao.save(procedureList);
		}
		return count;
	}


}
