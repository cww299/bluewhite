package com.bluewhite.production.temporarypack;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import java.util.Date;
import com.bluewhite.production.temporarypack.Quantitative;

public interface QuantitativeDao  extends BaseRepository<Quantitative, Long>{

	/**
	 * 查询下货单已发货数量
	 */
	@Query("SELECT distinct qc.id FROM Quantitative q,QuantitativeChild qc,UnderGoods u WHERE qc.underGoodsId = u.id AND q.flag = 1 and u.id = (?1)")
	List<Long> findSendNumber(Long id);
	
	/**
	 * 查询下货单已贴包数量
	 */
	@Query("SELECT distinct qc.id FROM Quantitative q,QuantitativeChild qc,UnderGoods u WHERE qc.underGoodsId = u.id and u.id = (?1)")
	List<Long> findStickNumber(Long id);
	
	/**
	 * 根据贴包日期
	 * @param time
	 * @return
	 */
	List<Quantitative> findByTimeBetween(Date startTime ,Date endTime);
}

