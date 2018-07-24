package com.bluewhite.product.primecost.cutparts.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.common.entity.CommonData;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

@Service
public interface CutPartsService extends BaseCRUDService<CutParts,Long>{
	
	/**
	 * 新增cc裁片
	 * @param cutParts
	 * @return
	 * @throws Exception
	 */
	public CutParts saveCutParts(CutParts cutParts,CommonData commonData) throws Exception;
	
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
	public void deleteCutParts(CutParts cutParts);

}
