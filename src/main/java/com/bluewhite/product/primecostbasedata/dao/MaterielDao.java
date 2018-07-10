package com.bluewhite.product.primecostbasedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.production.group.entity.Group;

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

}
