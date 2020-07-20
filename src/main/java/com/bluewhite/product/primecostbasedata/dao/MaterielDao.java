package com.bluewhite.product.primecostbasedata.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

/**
* 
* @author zhangliang
*
*/
public interface MaterielDao extends BaseRepository<Materiel, Long>{
	
	/**
	 * 根据物料名查找物料
	 * @param name
	 * @return
	 */
	Materiel findByName(String name);
	
	/**
	 * 根据物料编号查找物料
	 * @param name
	 * @return
	 */
	Materiel findByNumber(String number);

}
