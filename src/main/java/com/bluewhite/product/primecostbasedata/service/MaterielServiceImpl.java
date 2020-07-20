package com.bluewhite.product.primecostbasedata.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.product.primecostbasedata.dao.BaseFourDao;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.BaseFour;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public class MaterielServiceImpl extends BaseServiceImpl<Materiel, Long> implements MaterielService {

	@Autowired
	private MaterielDao dao;
	@Autowired
	private BaseOneDao baseOneDao;
	@Autowired
	private BaseThreeDao baseThreeDao;
	@Autowired
	private BaseFourDao baseFourDao;
	@Autowired
	private MaterialPutStorageDao materialPutStorageDao;
	@Autowired
	private MaterialOutStorageDao materialOutStorageDao;



	@Override
	public List<Materiel> findList(Materiel materiel) {
		List<Materiel> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (materiel.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), materiel.getId()));
			}
			// 按类型
			if (materiel.getMaterielTypeId() != null) {
				predicate.add(cb.equal(root.get("materielType").get("id").as(Long.class), materiel.getMaterielTypeId()));
			}
			// 按物料编号过滤
			if (!StringUtils.isEmpty(materiel.getNumber())) {
				predicate.add(cb.like(root.get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(materiel.getNumber()) + "%"));
			}
			// 按产品名称过滤
			if (!StringUtils.isEmpty(materiel.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(materiel.getName()) + "%"));
			}
			// 查找多个类型
			if(!StringUtils.isEmpty(materiel.getMaterielTypeIds())) {
				List<Long> allTypeIds = new ArrayList<>();
				for(String typeId : materiel.getMaterielTypeIds().split(",")) {
					allTypeIds.add(Long.parseLong(typeId));
				}
				predicate.add(root.get("materielType").get("id").as(Long.class).in(allTypeIds));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public List<BaseOne> findPagesBaseOne(BaseOne baseOne) {
		List<BaseOne> pages = baseOneDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (baseOne.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), baseOne.getId()));
			}
			// 按类型过滤
			if (baseOne.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(String.class), baseOne.getType()));
			}
			// 按产品名称过滤
			if (baseOne.getName() != null) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(baseOne.getName()) + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return pages;
	}

	@Override
	public Double getBaseThreeOne(Long typeId, Long id) {
		BaseThree baseThree = baseThreeDao.findOne(id);
		double returnNumber = 0;
		switch (typeId.intValue()) {
		case 71:// 普通激光切割
			returnNumber = baseThree.getTextualOrdinaryLight();
			break;
		case 72:// 绣花激光切割
			returnNumber = baseThree.getTextualPositionLight();
			break;
		case 73:// 手工电烫
			returnNumber = baseThree.getTextualPerm();
			break;
		case 74:// 设备电烫
			break;
		case 75:// 冲床
			returnNumber = baseThree.getTextualPuncher();
			break;
		case 76:// 电推
			returnNumber = baseThree.getTextualClippers();
			break;
		case 77:// 手工剪刀
			returnNumber = baseThree.getTextualScissors();
			break;
		case 78:// 绣花领取
			break;
		case 79:// 考证过电烫捡片数片时间（秒）（容易）手填↓
			returnNumber = baseThree.getTextualEasyTime();
			break;
		case 80:// 考证过电烫捡片数片时间（秒）（难）手填↓
			returnNumber = baseThree.getTextualHardTime();
			break;
		case 81://
			break;
		case 82://
			break;
		default:
			break;
		}
		return returnNumber;
	}

	@Override
	public PageResult<Materiel> findMaterielPages(Materiel materiel, PageParameter page) {
		Page<Materiel> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (materiel.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), materiel.getId()));
			}
			// 按物料编号过滤
			if (!StringUtils.isEmpty(materiel.getNumber())) {
				predicate.add(cb.like(root.get("number").as(String.class), "%" + materiel.getNumber() + "%"));
			}
			// 按类型
			if (materiel.getMaterielTypeId() != null) {
				predicate.add(cb.equal(root.get("materielType").get("id").as(String.class), materiel.getMaterielTypeId()));
			}
			// 按产品名称过滤
			if (!StringUtils.isEmpty(materiel.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + materiel.getName() + "%"));
			}
			 // 查找多个类型
            if(!StringUtils.isEmpty(materiel.getMaterielTypeIds())) {
                List<Long> allTypeIds = new ArrayList<>();
                for(String typeId : materiel.getMaterielTypeIds().split(",")) {
                    allTypeIds.add(Long.parseLong(typeId));
                }
                predicate.add(root.get("materielType").get("id").as(Long.class).in(allTypeIds));
            }
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Materiel> result = new PageResult<Materiel>(pages, page);
		result.getRows().stream().forEach(m->{
			List<MaterialPutStorage> mList = materialPutStorageDao.findByMaterielId(m.getId());
			// 计算退货总数
			List<MaterialOutStorage> list = new ArrayList<>();
			mList.stream().forEach(l -> {
				MaterialOutStorage materialOutStorage = materialOutStorageDao.findOne(l.getId());
				if (materialOutStorage != null) {
					list.add(materialOutStorage);
				}
			});
			double returnNumber = list.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
			double arrivalNumber = mList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
			m.setInventoryNumber(NumUtils.sub(arrivalNumber,returnNumber));
		});
		return result;
	}

	@Override
	public Double getBaseFourDate(Long typeId, Long needleSpurId) {
		BaseFour baseFour = baseFourDao.findOne(typeId);
		Double baseFourDate = 0.0;
		switch (needleSpurId.intValue()) {
		case 204:// 每CM5-6针
			baseFourDate = baseFour.getNeedle56();
			break;
		case 205:// 每CM4-5针
			baseFourDate = baseFour.getNeedle45();
			break;
		case 206:// 每CM3-4针
			baseFourDate = baseFour.getNeedle34();
			break;
		}
		return baseFourDate;
	}

}
