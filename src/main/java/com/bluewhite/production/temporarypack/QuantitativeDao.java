package com.bluewhite.production.temporarypack;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;

public interface QuantitativeDao  extends BaseRepository<Quantitative, Long>{

	/**
	 * 查询下货单已发货数量
	 */
	@Query(nativeQuery=true,value ="SELECT distinct pro_quantitative_child FROM pro_quantitative q, pro_quantitative_child qc,pro_under_goods u WHERE qc.underGoods_id = u.id AND q.flag = 0 and u.id = ?1")
	List<QuantitativeChild> findSendNumber(Long id);
}
