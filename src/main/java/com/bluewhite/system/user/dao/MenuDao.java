package com.bluewhite.system.user.dao;




import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.Menu;

/**
 *  MenuDao
 * @author zhangliang
 **/
public interface MenuDao extends BaseRepository<Menu, Long> {

	/**
	 * 取出parentid《1的一级菜单，并排序
	 * 
	 * @param ids menu ids
	 * @param parentId
	 * @return list
	 */
	public List<Menu> findByIdInAndParentIdLessThanOrderByOrderNo(
            Set<Long> ids, Long parentId);

	/**
	 * 取出parentid《1的一级菜单，并排序
	 *
	 * @param parentId 父级id
	 * @return list
	 */
	public List<Menu> findByParentIdOrderByOrderNo(Long parentId);

	/**
	 * 取出ids菜单
	 * @param ids menuids
	 * @param isshow true 未删除，false已删除
	 * @return list
	 */
	public List<Menu> findByIdInAndIsShowOrderByOrderNo(Set<Long> ids, Boolean isshow);
	
	/**
	 * 通过name查找
	 * @param name 菜单名字
	 * @return menu
	 */
	public Menu findByName(String name);
	
	/**
	 * 通过身份查找
	 * @param name 菜单名字
	 * @return menu
	 */
	public Optional<Menu> findByIdentity(String identity);
}
