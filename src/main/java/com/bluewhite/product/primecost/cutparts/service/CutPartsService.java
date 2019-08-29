package com.bluewhite.product.primecost.cutparts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

@Service
public interface CutPartsService extends BaseCRUDService<CutParts,Long>{
	
	/**
	 * 新增cc裁片
	 * @param cutParts
	 * @return
	 */
	public CutParts saveCutParts(CutParts cutParts);
	
	/**
	 * 按条件查询cc裁片
	 * @param cutParts
	 * @param page
	 * @return
	 */
	public PageResult<CutParts>  findPages(CutParts cutParts, PageParameter page);
	
	/**
	 * 删除裁片
	 * @param id
	 */
	public void deleteCutParts(Long id );
	
	/**
	 * 根据产品id查询
	 * @param productId
	 * @return
	 */
	public List<CutParts> findByProductId(Long productId);

	/**
	 * 根据产品id查询和压货类型id
	 * @param productId
	 * @return
	 */
	public  List<CutParts> findByProductIdAndOverstockId(Long productId, Long id);

}
