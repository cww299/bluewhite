package com.bluewhite.product.primecostbasedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

/**
* 
* @author zhangliang
*
*/
public interface MaterielDao extends BaseRepository<Materiel, Long>{
	
	/**
	 * 根据类型查找物料
	 * @param type
	 * @return
	 */
	List<Materiel> findByType(String type);
	/**
	 * 根据物料名查找物料
	 * @param name
	 * @return
	 */
	Materiel findByName(String name);

}
