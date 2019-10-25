package com.bluewhite.production.bacth.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.bacth.entity.Bacth;
@Service
public interface BacthService extends BaseCRUDService<Bacth,Long>{
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageResult<Bacth>  findPages(Bacth param,PageParameter page);
	
	/**
	 * 批次删除
	 * @param id
	 */
	public int deleteBacth(String ids);
	/**
	 * 一键完成批次，改变status，会转入包装列表
	 * @param ids
	 * @return
	 */
	public int statusBacth(String ids,Date time,HttpServletRequest request);
	
	/**
	 *  一键接收批次，改变批次的数量，重新变成批次到包装列表，type=2
	 * @param ids
	 * @param numbers
	 * @return
	 */
	public int receiveBacth(String[] ids, String[] numbers) throws Exception;
	
	/**
	 * 新增批次
	 * @param bacth
	 */
	public Bacth saveBacth(Bacth bacth);
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Bacth> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);

}
