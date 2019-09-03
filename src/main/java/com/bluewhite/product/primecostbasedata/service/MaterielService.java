package com.bluewhite.product.primecostbasedata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public interface MaterielService extends BaseCRUDService<Materiel,Long>{
	
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public List<Materiel> findList(Materiel materiel);
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public  List<BaseOne> findPagesBaseOne(BaseOne baseOne);
	
	/**
	 *  产品基础数据3获取,将裁减类型和数据进行匹配
	 * @param typeId
	 * @param number
	 * @return
	 */
	public Double getBaseThreeOne(Long typeId, Long id);
	
	
	/**
	 * 分页查看
	 * @param materiel
	 * @param page
	 * @return
	 */
	public PageResult<Materiel> findMaterielPages(Materiel materiel, PageParameter page);
	
	/**
	 *  获取机锋时间页面布料对应的数据
	 * @param typeId
	 * @param id
	 * @return
	 */
	public Double getBaseFourDate(Long typeId, Long id);
	
	

}
