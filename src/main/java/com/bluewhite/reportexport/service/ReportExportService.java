package com.bluewhite.reportexport.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	int importProcedureExcel(List<ProcedurePoi> excelProcedure,Long productId);

}
