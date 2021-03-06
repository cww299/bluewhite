package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

import cn.hutool.core.date.DateTime;

public interface QuantitativeService extends BaseCRUDService<Quantitative,Long>{
	
	/**
	 * 分页
	 * @param quantitative
	 * @param page
	 * @return
	 */
	PageResult<Quantitative> findPages(Quantitative quantitative, PageParameter page);
	
	/**
	 * 新增
	 * @param quantitative
	 */
	public void saveQuantitative(Quantitative quantitative);
	
	/**
	 * 审核
	 * @param ids
	 * @return
	 */
	int auditQuantitative(String ids,Integer audit);
	
	/**
	 * 打印
	 * @param ids
	 * @return
	 */
	int printQuantitative(String ids);
	
	/**
	 * 删除
	 * @param ids
	 */
	int deleteQuantitative(String ids);
	
	/**
	 * 发货
	 * 上车编号，物流点
	 * @param ids
	 * @return
	 */
	int sendQuantitative(String ids,Integer flag,String vehicleNumber,Long logisticsId,Long outerPackagingId);
	
	/**
	 * 设置发货数量
	 * @param id
	 * @param actualSingleNumber
	 */
	public void setActualSingleNumber(Long id, Integer actualSingleNumber);
	
	/**
	 * 核对成功
	 * @param id
	 */
	void checkNumber(Long id);
	
	/**
	 * 修改子单
	 * @param quantitativeChild
	 */
	public void updateActualSingleNumber(QuantitativeChild quantitativeChild);

    /**批量修改量化单的发货时间
     * @param ids
     * @param sendTime
     */
	public int updateQuantitativeSendTime(String ids, Date sendTime);

    /**新增修改
     * @param quantitative
     */
	public void saveUpdateQuantitative(Quantitative quantitative,String ids);

    /**
     * 自动检测
     * 以量化
     * 未发货发货
     * 且时间超过三天
     */
	public PageResult<Quantitative> warehousing(int page,int size);

    /**入库
     * @param ids
     * @param location
     * @param reservoirArea
     */
    int putWarehousing(String ids, String location, String reservoirArea);

    /**通过发货时间查找
     * @param beginOfDay
     * @param endOfDay
     */
    List<Quantitative> findBySendTime(DateTime beginOfDay, DateTime endOfDay,long warehouseTypeId);

    /**
     * 对账
     * @param ids
     * @return
     */
    int reconciliationQuantitative(String ids,Integer reconciliation);

    /**从发货明细中重新生成发货单
     * @param ids
     */
    void reCreatSendOrder(String ids);

    /**
     * 批量修改子单备注
     * @param remark
     * @param ids
     * @return:
     * @author zhangliang
     * @date: 2021/2/1 17:51
     */
	void updateQuantitativeChildRemark(String remark, String ids);
}
