package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.WarningDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;
import com.bluewhite.onlineretailers.inventory.entity.Warning;
@Service
public class CommodityServiceImpl  extends BaseServiceImpl<Commodity, Long> implements  CommodityService{
	
	@Autowired
	private CommodityDao dao;
	@Autowired
	private WarningDao warningDao;
	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private BaseDataService service;

	@Override
	public PageResult<Commodity> findPage(Commodity param, PageParameter page) {
		 Page<Commodity> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	
	        	//按编号过滤
	        	if (!StringUtils.isEmpty(param.getSkuCode())) {
					predicate.add(cb.equal(root.get("skuCode").as(String.class),param.getSkuCode()));
				}
	        	
	        	//按产品名称过滤
	        	if (!StringUtils.isEmpty(param.getName())) {
					predicate.add(cb.like(root.get("name").as(String.class),"%"+StringUtil.specialStrKeyword(param.getName())+"%"));
				}
	        	
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);

	        PageResult<Commodity> result = new PageResult<>(pages,page);
	        return result;
	    }

	@Override
	public int deleteCommodity(String ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			String[] pers = ids.split(",");
			if(pers.length>0){
				for(String idString : pers){
					dao.delete(Long.valueOf(idString));
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> checkWarning() {
		//获取所有的仓库
		List<BaseData> baseDataList  = service.getBaseDataListByType("inventory");
		for(BaseData pWarehouse : baseDataList){
			//1.库存下限预警
			Warning warning = warningDao.findByTypeAndWarehouseId(1, pWarehouse.getId());
			//2.库存上限预警
			Warning warning1 = warningDao.findByTypeAndWarehouseId(2, pWarehouse.getId());
			//3.库存时间过长预警
			Warning warning2 = warningDao.findByTypeAndWarehouseId(3, pWarehouse.getId());
			
			if(warning.getType()==1){
				//获取天数 
				Integer time = warning.getTime();
				//获取离当前日期加上预警天数之间的出库单
				//获取开始时间
				Date beginTime = DatesUtil.getDaySum(new Date(), -time);
				List<Procurement> procurementList = procurementService.findByTypeAndCreatedAt(3,beginTime,new Date());
				if(procurementList.size()>0){
					List<ProcurementChild> procurementChildList = new ArrayList<>();
					//将当前时间段所有的出库商品过滤出，同时 
					procurementList.stream().forEach(pt->{
						procurementChildList.addAll(pt.getProcurementChilds());
					});
					Map<Long, List<ProcurementChild>> mapProcurementChildList = procurementChildList.stream().collect(Collectors.groupingBy(ProcurementChild::getCommodityId,Collectors.toList()));
					//按产品id分组
					for(Long ps : mapProcurementChildList.keySet()){ 
						List<ProcurementChild> psList= mapProcurementChildList.get(ps);
						Map<Long, List<ProcurementChild>> mapWarehouse = psList.stream().collect(Collectors.groupingBy(ProcurementChild::getWarehouseId,Collectors.toList()));
						//按库存类型分类
						for(Long pWarehouseId : mapWarehouse.keySet()){
							List<ProcurementChild> psWarehouse = mapProcurementChildList.get(pWarehouseId);
							IntSummaryStatistics stats = psWarehouse.stream().mapToInt((x) -> x.getNumber()).summaryStatistics();
							//获取每个产品在时间段内的出库数量
							int count = (int) stats.getSum();
							
							if(count<warning.getNumber()){
								
								
								
							}
						}
						
					}

					
				}
				
				
			}
		}
		
		
		return null;
		
		
	}

	@Override
	public Warning saveWarning(Warning warning) {
		if(warning.getId()==null){
			Warning wn = warningDao.findByTypeAndWarehouseId(warning.getType(), warning.getWarehouseId());
			if(wn!=null){
				throw new ServiceException("已有该预警类型和仓库类型的库存预警，不可再次添加");
			}
		}
		return warningDao.save(warning);
	}
}
