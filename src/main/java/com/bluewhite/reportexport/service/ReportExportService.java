package com.bluewhite.reportexport.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.reportexport.entity.EightTailorPoi;
import com.bluewhite.reportexport.entity.MachinistProcedurePoi;
import com.bluewhite.reportexport.entity.OrderPoi;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.system.user.entity.UserContract;

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
	
	/**
	 * 导入机工工序信息
	 * @param excelUser
	 * @return
	 */
	int importMachinistProcedureExcel(List<MachinistProcedurePoi> excelProcedure, Long productId, Integer type,
			Integer flag);
	/**
	 * 导入裁剪工序信息
	 * @param excelProcedure
	 * @param productId
	 * @param type
	 * @param sign
	 * @return
	 */
	int importEightTailorProcedure(List<EightTailorPoi> excelProcedure, Long productId, Integer type, Integer sign);
	
	/**
	 * 导入基础3数据
	 * @param excelUser
	 * @return
	 */
	int importexcelBaseThreeExcel(List<BaseThree> excelBaseThree);

	int importImportUserContract(List<UserContract> excelUser);
	

}
