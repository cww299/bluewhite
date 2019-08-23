package com.bluewhite.reportexport.service;

import java.text.SimpleDateFormat;
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
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.BaseOneTimeDao;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.reportexport.entity.EightTailorPoi;
import com.bluewhite.reportexport.entity.MachinistProcedurePoi;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.system.user.dao.UserContractDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

@Service
public class ReportExportServiceImpl implements ReportExportService {

	@Autowired
	private BaseDataDao baseDataDao;

	@Autowired
	private CustomerDao contactDao;
	
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

	@Autowired
	private MaterielDao materielDao;

	@Autowired
	private BaseOneDao baseOneDao;

	@Autowired
	private BaseThreeDao baseThreeDao;

	@Autowired
	private BaseOneTimeDao baseOneTimeDao;

	@Autowired
	private UserContractDao userContractDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	public int importProductExcel(List<ProductPoi> excelProduct) {
		int count = 0;
		if (excelProduct.size() > 0) {
			List<Product> productList = new ArrayList<Product>();
			for (ProductPoi proPoi : excelProduct) {
				Product products = productDao.findByNumber(proPoi.getNumber());
				Product products1 = productDao.findByDepartmentNumber(proPoi.getNumber());
				// 下面存入各部门产品编号
				if (products != null || products1 != null) {
					throw new ServiceException("已有产品编号为" + proPoi.getNumber() + "的产品，请检查后再次添加");
				} else {
					// 同时判断各部门的编号是否和最终的产品编号位数相同，如果相同，则存入产品编号中，同时清空来源部门，让产品变成共同的集合
					if (proPoi.getNumber().length() == 7) {
						Product pro = new Product();
						pro.setNumber(proPoi.getNumber());
						pro.setName(proPoi.getName());
						pro.setDepartmentNumber(proPoi.getNumber());
						productList.add(pro);
						count++;
					} else {
						throw new ServiceException(proPoi.getNumber() + "的编号不符合规范");
					}
					;
				}
			}
			this.saveAllProduct(productList);
		}
		return count;
	}

	/**
	 * 产品导入批处理
	 * 
	 * @param productList
	 */
	private void saveAllProduct(List<Product> productList) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		for (int i = 0; i < productList.size(); i++) {
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
		if (excelUser.size() > 0) {
			List<User> userList = new ArrayList<User>();
			for (UserPoi proPoi : excelUser) {
		
				count++;
			}
			userDao.save(userList);
		}
		return count;
	}

	@Override
	public int importImportUserContract(List<UserContract> excelUser) {
		int count = 0;
		return count;
	}

	@Override
	@Transactional
	public int importProcedureExcel(List<ProcedurePoi> excelProcedure, Long productId, Integer type, Integer flag) {
		int count = 0;
		if (excelProcedure.size() == 0) {
			throw new ServiceException("excel无数据");
		}
		List<Procedure> procedureList = new ArrayList<Procedure>();
		if (type == null) {
			type = ProTypeUtils.roleGetProType();
		}
		List<BaseData> baseDataList = null;
		if (type == 1) {
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_FRIST_QUALITY);
			for (ProcedurePoi procedurePoi : excelProcedure) {
				Procedure procedure = new Procedure();
				procedure.setFlag(flag);
				procedure.setProductId(productId);
				procedure.setName(procedurePoi.getName());
				procedure.setWorkingTime(NumUtils.round(procedurePoi.getWorkingTime(), 4));
				procedure.setType(type);
				procedure.setProcedureTypeId(baseDataList.get(0).getId());
				procedureList.add(procedure);
				count++;
			}
		}
		if (type == 2) {
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_FRIST_PACK);
			for (ProcedurePoi procedurePoi : excelProcedure) {
				Procedure procedure = new Procedure();
				procedure.setFlag(flag);
				procedure.setProductId(productId);
				procedure.setName(procedurePoi.getName());
				procedure.setWorkingTime(NumUtils.round(procedurePoi.getWorkingTime(), 4));
				procedure.setType(type);
				procedure.setProcedureTypeId(baseDataList.get(0).getId());
				procedureList.add(procedure);
				count++;
			}

		}
		if (type == 3) {
			baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_TWO_DEEDLE);
			for (ProcedurePoi procedurePoi : excelProcedure) {
				Procedure procedure = new Procedure();
				procedure.setFlag(flag);
				procedure.setProductId(productId);
				procedure.setName(procedurePoi.getName());
				procedure.setWorkingTime(NumUtils.round(procedurePoi.getWorkingTime() * 60, 2));
				procedure.setType(type);
				if (procedure.getFlag() == 0) {
					procedure.setProcedureTypeId(baseDataList.get(0).getId());
				} else {
					procedure.setProcedureTypeId((long) 100);
				}
				procedureList.add(procedure);
				count++;
			}
			Procedure procedure = new Procedure();
			procedure.setProductId(productId);
			procedure.setFlag(flag);
			procedure.setName("下货点到包装");
			procedure.setWorkingTime(0.5);
			procedure.setType(type);
			procedure.setProcedureTypeId(baseDataList.get(0).getId());
			procedureList.add(procedure);
		}
		procedureDao.save(procedureList);
		procedureService.countPrice(procedureList.get(0));
		return count;
	}

	@Override
	public int importMaterielExcel(List<Materiel> excelMateriel) {
		int count = 0;
		for (Materiel materiel : excelMateriel) {
			Materiel mt = materielDao.findByNumber(materiel.getNumber());
			if (mt != null) {
				mt.setName(materiel.getName());
				mt.setPrice(materiel.getPrice());
				materielDao.save(mt);
			} else {
				materiel.setType("accessories");
				materielDao.save(materiel);
				count++;
			}
		}
		return count;
	}

	@Override
	public int importexcelBaseOneExcel(List<BaseOne> excelBaseOne) {
		for (BaseOne baseOne : excelBaseOne) {
			baseOne.setType("unit");
		}
		baseOneDao.save(excelBaseOne);
		return excelBaseOne.size();
	}

	@Override
	public int importexcelBaseOneTimeExcel(List<BaseOneTime> excelBaseOneTime) {
		int count = 0;
		BaseOne baseOne = new BaseOne();
		baseOne.setName("2楼送棉及叉车卸货");
		baseOne.setType("endocyst");
		baseOne = baseOneDao.save(baseOne);
		for (BaseOneTime bot : excelBaseOneTime) {
			bot.setBaseOneId(baseOne.getId());
			count++;
		}
		baseOneTimeDao.save(excelBaseOneTime);
		return count;
	}

	@Override
	public int importMachinistProcedureExcel(List<MachinistProcedurePoi> excelProcedure, Long productId, Integer type,
			Integer flag) {
		int count = 0;
		if (excelProcedure.size() == 0) {
			throw new ServiceException("excel无数据");
		}
		List<Procedure> procedureList = new ArrayList<Procedure>();
		List<BaseData> baseDataList = baseDataService.getBaseDataListByType(Constants.PRODUCT_TWO_MACHINIST);
		for (MachinistProcedurePoi machinistProcedurePoi : excelProcedure) {
			Procedure procedure = new Procedure();
			procedure.setFlag(flag);
			procedure.setProductId(productId);
			procedure.setName(machinistProcedurePoi.getName());
			procedure.setWorkingTime(
					NumUtils.sum(machinistProcedurePoi.getOneTime(), machinistProcedurePoi.getScissorsTime() == null
							? 0.0 : machinistProcedurePoi.getScissorsTime() / 12 * 1.08 * 1.25));
			procedure.setType(type);
			if (flag == 0) {
				procedure.setProcedureTypeId(baseDataList.get(0).getId());
			} else {
				procedure.setProcedureTypeId((long) 139);
			}
			procedureList.add(procedure);
			count++;
		}
		procedureDao.save(procedureList);
		procedureService.countPrice(procedureList.get(0));
		return count;
	}

	@Override
	public int importEightTailorProcedure(List<EightTailorPoi> excelProcedure, Long productId, Integer type,
			Integer sign) {
		int count = 0;
		if (excelProcedure.size() == 0) {
			throw new ServiceException("excel无数据");
		}
		double sumPrice = 0;
		List<Procedure> procedureList = new ArrayList<Procedure>();
		for (EightTailorPoi et : excelProcedure) {
			Procedure procedure = new Procedure();
			procedure.setFlag(0);
			procedure.setProductId(productId);
			procedure.setName(et.getName());
			procedure.setType(type);
			procedure.setSign(sign);
			if (sign == 0) {
				procedure.setWorkingTime(
						NumUtils.mul(NumUtils.sum(et.getClothTime(), et.getLaserTime()), et.getNumber()));
				procedure.setProcedureTypeId((long) 140);
				sumPrice += et.getNumber() * et.getPerimeter() * 0.005 / 10;
			} else {

				procedure.setWorkingTime(
						NumUtils.mul(et.getNumber(), NumUtils.sum(et.getOverlay(), et.getStamping(), 1.0)));
				procedure.setProcedureTypeId((long) 141);
				sumPrice += et.getNumber() * 0.012;
			}
			procedureList.add(procedure);
			count++;
		}
		for (Procedure pro : procedureList) {
			pro.setHairPrice(NumUtils.round(sumPrice, 5));
		}
		procedureDao.save(procedureList);
		procedureService.countPrice(procedureList.get(0));
		return count;
	}

	@Override
	public int importexcelBaseThreeExcel(List<BaseThree> excelBaseThree) {
		return baseThreeDao.save(excelBaseThree).size();
	}

	/**
	 * 财务订单批处理
	 * 
	 * @param orderList
	 */
	private void saveAllOrder(List<Order> orderList) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		for (int i = 0; i < orderList.size(); i++) {
			Order courtsResident = orderList.get(i);
			entityManager.merge(courtsResident);
			if (i % 1000 == 0 && i > 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.close();
	}

}
