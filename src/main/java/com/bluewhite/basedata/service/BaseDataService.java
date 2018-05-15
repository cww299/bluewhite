package com.bluewhite.basedata.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.basedata.entity.BaseData;
/**
 * 基础数据业务处理接口类
 * 
 * <p>1.查询基础数据类型列表
 * <p>2.查询某类型下基础数据列表
 * <p>3.查询基础数据树形数据
 * <p>4.查询某基础数据的子数据列表
 * <p>5.查询动态表单类型列表
 * <p>6.新增基础数据
 * <p>7.修改基础数据
 * <p>8.删除基础数据
 * 
 * @author TSOSilence
 *
 */
@Service
public interface BaseDataService extends BaseCRUDService<BaseData, Long> {
	
	/**
	 * 获取基础数据类型分类
	 * @return list
	 */
	public List<Map<String, String>> getBaseDataTypes();
	
	/**
	 * 查询<code>type</code>类型的数据列表
	 * @param type 类型
	 * @return list
	 */
	public List<BaseData> getBaseDataListByType(String type);
	/**
	 * 查询<code>type</code>类型的树形数据
	 * @param type 类型
	 * @return list
	 */
	public List<BaseData> getBaseDataTreeByType(String type);
	/**
	 * 查询某数据的子类型的数据列表
	 * @param dataId 数据id
	 * @return list
	 */
	public List<BaseData> getBaseDataChildrenById(Long dataId);

	/**
	 * 获取基础数据动态表单数据源列表
	 * @return list
	 */
	public List<Map<String, String>> getDynamicBaseDataTypes();
	
	/**
	 * 新增基础数据
	 * @param baseData
	 */
	public void insertBaseDataType(BaseData baseData);
	
	/**
	 * 修改基础数据
	 * @param baseData
	 */
	public void updateBaseDataType(BaseData baseData);
	
	/**
	 * 删除基础数据
	 * @param baseData
	 */
	public void deleteBaseDataType(Long id);
	

}
