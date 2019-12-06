package com.bluewhite.production.temporarypack;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.action.TaskAction;
import com.bluewhite.production.task.entity.Task;

@Controller
public class TemporaryPackAction {
	
	@Autowired
	private UnderGoodsService underGoodsService;
	@Autowired
	private QuantitativeService quantitativeService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(UnderGoods.class, "id", "remarks","product","number","bacthNumber","status","allotTime")
				.addRetainTerm(Product.class, "id", "name");
	}
	private ClearCascadeJSON clearCascadeJSONQuantitative;
	{
		clearCascadeJSONQuantitative = ClearCascadeJSON.get()
				.addRetainTerm(Quantitative.class, "id", "underGoods","sumPackageNumber","singleNumber","time")
				.addRetainTerm(UnderGoods.class, "id", "remarks","product","number","bacthNumber","status","allotTime")
				.addRetainTerm(Product.class, "id", "name");
	}
	
	
	/**
	 * 新增下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/saveUnderGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveUnderGoods(UnderGoods underGoods) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.saveUnderGoods(underGoods);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 查询下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesUnderGoods( UnderGoods underGoods ,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(underGoodsService.findPages(underGoods, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 删除下货单
	 */
	@RequestMapping(value = "/temporaryPack/deleteUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteUnderGoods(String ids) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.delete(ids);
		cr.setMessage("删除成功");
		return cr;
	}
	
	/**
	 * 新增量化单
	 */
	@RequestMapping(value = "/temporaryPack/saveQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveQuantitative(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.saveQuantitative(quantitative);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 新增量化单包装物
	 */
	@RequestMapping(value = "/temporaryPack/saveQuantitativeMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveQuantitativeMaterials(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.saveQuantitativeMaterials(quantitative);
		cr.setMessage("新增成功");
		return cr;
	}
	
	/**
	 * 审核打印 量化单
	 */
	@RequestMapping(value = "/temporaryPack/auditQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditQuantitative(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		Quantitative ot = quantitativeService.findOne(quantitative.getId());
		quantitativeService.update(quantitative, ot, "");
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 查询量化单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesQuantitative(Quantitative quantitative ,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONQuantitative.format(quantitativeService.findPages(quantitative, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 删除量化单
	 */
	@RequestMapping(value = "/temporaryPack/deleteQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.delete(ids);
		cr.setMessage("删除成功");
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	

}
