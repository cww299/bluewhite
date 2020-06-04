package com.bluewhite.personnel.officeshare.action;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.personnel.officeshare.entity.InventoryDetail;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;
import com.bluewhite.personnel.officeshare.service.InventoryDetailService;
import com.bluewhite.personnel.officeshare.service.OfficeSuppliesService;
import com.bluewhite.system.user.entity.User;

@Controller
public class OfficeSuppliesAction {
	
	@Autowired
	private OfficeSuppliesService officeSuppliesService;
	@Autowired
	private InventoryDetailService inventoryDetailService;
	
	
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(OfficeSupplies.class,"id","number", "name", "price", "unit", "qcCode"
						, "inventoryNumber","location","libraryValue","createdAt","customer","singleMealConsumption")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(BaseData.class, "id", "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONInventoryDetail;
	{
		clearCascadeJSONInventoryDetail = ClearCascadeJSON.get()
				.addRetainTerm(InventoryDetail.class,"id","officeSupplies", "flag", "orgName", "user"
						, "time","number","remark","outboundCost","mealType","status", "qcCode")
				.addRetainTerm(OfficeSupplies.class, "id", "name","price","singleMealConsumption")
				.addRetainTerm(BaseData.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}
	
	
	/**
	 * 办公用品列表
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getOfficeSupplies", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOfficeSupplies(OfficeSupplies officeSupplies,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(officeSuppliesService.findPages(officeSupplies, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 新增修改办公用品
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/personnel/addOfficeSupplies", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOfficeSupplies(OfficeSupplies officeSupplies) {
		CommonResponse cr = new CommonResponse();
		if(officeSupplies.getId()!=null){
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("新增成功");
		}
		officeSuppliesService.addOfficeSupplies(officeSupplies);
		return cr;
	}
	
	
	/**
	 * 删除办公用品
	 * 
	 */
	@RequestMapping(value = "/product/deleteOfficeSupplies", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOfficeSupplies(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = officeSuppliesService.deleteOfficeSupplies(ids);
		cr.setMessage("成功删除"+count+"件物品");
		return cr;
	}
	
	/**
	 * 出入库明细列表
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getInventoryDetail", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getInventoryDetail(InventoryDetail inventoryDetail,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONInventoryDetail.format(inventoryDetailService.findPages(inventoryDetail, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 新增出入库记录
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/personnel/addInventoryDetail", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addInventoryDetail(InventoryDetail onventoryDetail) {
		CommonResponse cr = new CommonResponse();
		inventoryDetailService.addInventoryDetail(onventoryDetail);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 批量新增出库记录（扫码出库）
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/personnel/addInventoryDetailMores", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addInventoryDetailMores(Long userId,Long orgId,String outList,String remark,String operator) {
		CommonResponse cr = new CommonResponse();
		if(outList==null || outList.isEmpty()) {
			cr.setCode(1500);
			cr.setMessage("出库信息不能为空");
			return cr;
		}
		inventoryDetailService.addInventoryDetailMores(userId,orgId,outList,remark,operator);
		cr.setMessage("出库成功");
		return cr;
	}
	
	/**
	 * 批量新增入库记录（扫码入库）
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/personnel/addInventoryDetailMoresIn", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addInventoryDetailMoresIn(String inList,String operator) {
		CommonResponse cr = new CommonResponse();
		if(inList==null || inList.isEmpty()) {
			cr.setCode(1500);
			cr.setMessage("入库信息不能为空");
			return cr;
		}
		inventoryDetailService.addInventoryDetailMoresIn(inList,operator);
		cr.setMessage("入库成功");
		return cr;
	}
	
	/**
	 * 删除出入库记录
	 * 
	 */
	@RequestMapping(value = "/personnel/deleteInventoryDetail", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteInventoryDetail(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = inventoryDetailService.deleteInventoryDetail(ids);
		cr.setMessage("成功删除"+count+"条出入库记录");
		return cr;
	}
	
	/**
	 * 统计部门分摊费用
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/personnel/statisticalInventoryDetail", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse statisticalInventoryDetail(InventoryDetail onventoryDetail) {
		CommonResponse cr = new CommonResponse();
		cr.setData(inventoryDetailService.statisticalInventoryDetail(onventoryDetail));
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 食材出库记录统计
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/personnel/ingredientsStatisticalInventoryDetail", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse ingredientsStatisticalInventoryDetail(InventoryDetail onventoryDetail) {
		CommonResponse cr = new CommonResponse();
		cr.setData(inventoryDetailService.ingredientsStatisticalInventoryDetail(onventoryDetail));
		cr.setMessage("成功");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


}
