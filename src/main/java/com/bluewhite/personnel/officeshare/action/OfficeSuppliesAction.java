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
				.addRetainTerm(OfficeSupplies.class,"id","number", "name", "price", "unit"
						, "inventoryNumber","location")
				.addRetainTerm(BaseData.class, "id", "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONInventoryDetail;
	{
		clearCascadeJSONInventoryDetail = ClearCascadeJSON.get()
				.addRetainTerm(InventoryDetail.class,"id","OfficeSupplies", "flag", "orgName", "user"
						, "time","number","remark")
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
		officeSuppliesService.addOfficeSupplies(officeSupplies);
		cr.setMessage("新增成功");
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
		cr.setMessage("成功删除"+count+"件办公用品");
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
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}


}
