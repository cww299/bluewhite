package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;

public interface QuantitativeDao  extends BaseRepository<Quantitative, Long>{

	/**
	 * 查询下货单已发货数量
	 */
	@Query("SELECT distinct qc.id FROM Quantitative q,QuantitativeChild qc,UnderGoods u WHERE qc.underGoodsId = u.id AND q.flag = 1 and u.id = (?1)")
	List<Long> findSendNumber(Long id);
	
	/**
	 * 根据贴包日期
	 * @param time
	 * @return
	 */
	List<Quantitative> findByTimeBetweenOrderByIdDesc(Date startTime ,Date endTime);
}

