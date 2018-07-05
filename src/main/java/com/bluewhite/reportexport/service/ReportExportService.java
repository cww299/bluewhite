package com.bluewhite.reportexport.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;

@Service
public interface ReportExportService {
	
	/**
	 * 导入基础产品信息
	 * @param excelUser
	 * @return
	 */
	int importProductExcel(List<ProductPoi> excelProduct);
	
	/**
	 * 导入基础用户信息
	 * @param excelUser
	 * @return
	 */
	int importUserExcel(List<UserPoi> excelUser);
	
	/**
	 * 导入工序信息
	 * @param excelUser
	 * @return
	 */
	int importProcedureExcel(List<ProcedurePoi> excelProcedure,Long productId,Integer type,Integer flag);
	
	/**
	 * 导入面料基础数据
	 * @param excelUser
	 * @return
	 */
	int importMaterielExcel(List<Materiel> excelMateriel);
	
	/**
	 * 导入基础1数据
	 * @param excelUser
	 * @return
	 */
	int importexcelBaseOneExcel(List<BaseOne> excelBaseOne);

	/**
	 * 导入基础1时间数据
	 * @param excelUser
	 * @return
	 */
	int importexcelBaseOneTimeExcel(List<BaseOneTime> excelBaseOneTime);

}
