package com.bluewhite.production.finance.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.PayB;

public interface PayBDao extends BaseRepository<PayB, Long> {
	/**
	 * 根据任务id查询工资
	 * 
	 * @return
	 */
	List<PayB> findByTaskId(Long id);

	/**
	 * 根据任务id和员工id查询工资
	 * 
	 * @param id
	 * @param userid
	 * @return
	 */
	PayB findByTaskIdAndUserId(Long id, Long userid);

	/**
	 * 根据条件查询b
	 * 
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<PayB> findByUserIdAndTypeAndAllotTimeBetween(Long userId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);

	/**
	 * 根据条件查询b
	 * 
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<PayB> findByTypeAndAllotTimeBetween(Integer type, Date orderTimeBegin, Date orderTimeEnd);
	
	/**
	 * 根据人员id查询b
	 * 
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<PayB> findByUserIdInAndAllotTimeBetween(List<Long> userIds, Date orderTimeBegin, Date orderTimeEnd);
	

	/**
	 * 根据条件查询b返回参数减少
	 * 
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT pay_number, group_id ,allot_time, user_id FROM pro_payb WHERE type = ?1 AND allot_time BETWEEN ?2 AND ?3")
	List<Object> findByTypeAndAllotTimeBetween1(Integer type, Date orderTimeBegin, Date orderTimeEnd);

	/**
	 * 根据条件查询b工资
	 * 
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT pay_number,performance_pay_number FROM pro_payb WHERE type = ?1 AND allot_time BETWEEN ?2 AND ?3 "
			+ "and if(?4 !='',user_name like CONCAT('%',?4,'%'),1=1) and if(?5 !='',bacth = ?5,1=1) and if(?6 !='',product_name like CONCAT('%',?6,'%'),1=1)")
	List<Object> findPayNumber(Integer type, Date orderTimeBegin, Date orderTimeEnd, String userName, String bacth,
			String productName);

	/**
	 * 根据时间查询
	 * 
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<PayB> findByAllotTimeBetween(Date orderTimeBegin, Date orderTimeEnd);

}
