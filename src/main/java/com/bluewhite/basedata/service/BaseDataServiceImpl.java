package com.bluewhite.basedata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 基础数据业务处理实现类
 * 
 * <p>1.查询基础数据类型列表
 * <p>2.查询某类型下基础数据列表
 * <p>3.查询基础数据树形数据
 * <p>4.查询某基础数据的子数据列表
 * @author TSOSilence
 *
 */
@Service
public class BaseDataServiceImpl  extends BaseServiceImpl<BaseData, Long> implements BaseDataService{
	
	@Autowired
	BaseDataDao baseDataDao;

	@Override
	public List<BaseData> getBaseDataListByType(String type) {
		Sort sort = new Sort(Direction.ASC, "ord");
		List<BaseData> baseDatas = baseDataDao.findAll((root, query, cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("type").as(String.class), type));
			predicates.add(cb.equal(root.get("flag").as(Integer.class), 1));//启用状态
			Predicate[] preArr = new Predicate[predicates.size()];
			predicates.toArray(preArr);
			query.where(preArr);
			return null;
		}, sort);
		return baseDatas;
	}

	@Override
	public List<BaseData> getBaseDataTreeByType(String type) {
		Sort sort = new Sort(Direction.ASC, "ord");
		List<BaseData> baseDatas = baseDataDao.findAll((root, query, cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("type").as(String.class), type));
			predicates.add(cb.equal(root.get("flag").as(Integer.class), 1));//启用状态
			Predicate[] preArr = new Predicate[predicates.size()];
			predicates.toArray(preArr);
			query.where(preArr);
			return null;
		}, sort);
		return baseDatas;
	}

	@Override
	public List<BaseData> getBaseDataChildrenById(Long dataId) {
		Sort sort = new Sort(Direction.ASC, "ord");
		List<BaseData> baseDatas = baseDataDao.findAll((root, query, cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("parentId").as(String.class), dataId));
			predicates.add(cb.equal(root.get("flag").as(Integer.class), 1));//启用状态
			Predicate[] preArr = new Predicate[predicates.size()];
			predicates.toArray(preArr);
			query.where(preArr);
			return null;
		}, sort);
		return baseDatas;
	}

	@Override
	public List<Map<String, String>> getBaseDataTypes() {
		List<Map<String, String>> types = new ArrayList<Map<String, String>>();
		Sort sort = new Sort(Direction.ASC, "ord");
		List<BaseData> baseDatas = baseDataDao.findAll((root, query, cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("flag").as(Integer.class), 1));//启用状态
			Predicate[] preArr = new Predicate[predicates.size()];
			predicates.toArray(preArr);
			query.where(preArr);
			query.groupBy(root.get("type"));
			return null;
		}, sort);
		
		for(BaseData bd: baseDatas){
			Map<String, String> nameValue = new HashMap<String, String>();
			String type = bd.getType();
			String remark = bd.getRemark();
			if(StringUtils.isNotBlank(type)){
				if(StringUtils.isBlank(remark)){
					nameValue.put("name", type);
				}else{
					nameValue.put("name", remark);
				}
				nameValue.put("value", type);
			}
			types.add(nameValue);
		}
		return types;
	}

	@Override
	public List<Map<String, String>> getDynamicBaseDataTypes() {
		List<Map<String, String>> types = new ArrayList<Map<String, String>>();
		Sort sort = new Sort(Direction.ASC, "ord");
		List<BaseData> baseDatas = baseDataDao.findAll((root, query, cb)->{
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("flag").as(Integer.class), 1));//启用状态
			predicates.add(cb.equal(root.get("type").as(String.class), BaseData.BASE_DATA_TYPE_DYNAMICFORM));//查询动态表单数据源类型
			Predicate[] preArr = new Predicate[predicates.size()];
			predicates.toArray(preArr);
			query.where(preArr);
			return null;
		}, sort);
		
		for(BaseData bd: baseDatas){
			Map<String, String> nameValue = new HashMap<String, String>();
			String name = bd.getName();
			String remark = bd.getRemark();
			if(StringUtils.isNotBlank(name)){
				if(StringUtils.isBlank(remark)){
					nameValue.put("name", name);
				}else{
					nameValue.put("name", remark);
				}
				nameValue.put("value", name);
			}
			types.add(nameValue);
		}
		
		return types;
	}

	@Override
	public void insertBaseDataType(BaseData baseData) {
		baseDataDao.save(baseData);
	}

	@Override
	public void updateBaseDataType(BaseData baseData) {
		baseDataDao.save(baseData);
	}

	@Override
	public void deleteBaseDataType(Long id) {
		baseDataDao.delete(id);
	}

}
