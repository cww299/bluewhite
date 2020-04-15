package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;

public interface QuantitativeDao  extends BaseRepository<Quantitative, Long>{

	/**
	 * 查询下货单已发货数量
	 */
	@Query(nativeQuery = true ,value = "SELECT distinct qc.id FROM pro_quantitative q,pro_quantitative_child qc,pro_under_goods u WHERE q.id = qc.quantitative_id and  qc.underGoods_id = u.id AND q.flag = 1 and u.id = (?1)")
	List<Object> findSendNumber(Long id);
	
	/**
	 * 根据贴包日期
	 * @param time
	 * @return
	 */
	List<Quantitative> findByTimeBetweenOrderByIdDesc(Date startTime ,Date endTime);
	
	/**
     * 根据发货单id 查找 贴包单
     */
	List<Quantitative> findBySendOrderId(Long id);
}

