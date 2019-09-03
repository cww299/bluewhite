package com.bluewhite.product.primecost.tailor.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.primecost.dao.PrimeCostDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.OrdinaryLaserService;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.product.service.ProductService;

@Controller
public class TailorAction {
	private final static Log log = Log.getLog(TailorAction.class);

	@Autowired
	private TailorService tailorService;
	@Autowired
	private OrdinaryLaserService ordinaryLaserService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PrimeCostDao primeCostDao;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Tailor.class, "id", "productId", "number", "cutPartsId", "ordinaryLaserId",
						"embroideryId", "tailorName", "tailorNumber", "bacthTailorNumber", "tailorSize", "tailorType",
						"tailorTypeId", "managePrice", "experimentPrice", "ratePrice", "costPrice","costPriceSelect", "allCostPrice",
						"scaleMaterial", "priceDown", "noeMbroiderPriceDown", "embroiderPriceDown",
						"machinistPriceDown", "oneCutPrice")
				.addRetainTerm(BaseThree.class, "id", "ordinaryLaser");
	}
	
	private ClearCascadeJSON clearCascadeJSONOrdinaryLaser;
	{
		clearCascadeJSONOrdinaryLaser = ClearCascadeJSON.get()
				.addRetainTerm(OrdinaryLaser.class, "id", "productId", "number", "tailorId",
						"tailorName", "tailorNumber", "bacthTailorNumber", "tailorSize", "tailorType",
						"tailorTypeId", "managePrice", "perimeter", "layerNumber", "typesettingNumber",
						"stallPoint","singleDouble", "time", "embroiderTime", "otherTimeOne",
						"otherTimeTwo", "otherTimeThree","rabbTime","overlappedSeconds","punchingSeconds",
						"electricSeconds","singleLaserTime","singleLaserHandTime","singleLaserHandTime","permSeconds"
						,"tearingSeconds","manualSeconds","permPrice","tearingPrice","labourCost","equipmentPrice",
						"administrativeAtaff","stallPrice","lamination","oneCutPrice")
				.addRetainTerm(BaseThree.class, "id", "ordinaryLaser");
	}

	/**
	 * 裁剪填写
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addTailor", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTailor(HttpServletRequest request, Tailor tailor) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(tailor.getId())) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁剪不能为空");
		} else {
			Tailor oldTailor = tailorService.findOne(tailor.getId());
			BeanCopyUtils.copyNotEmpty(tailor, oldTailor, "");
			tailorService.saveTailor(oldTailor);
			cr.setMessage("添加成功");
		}
		return cr;
	}

	/**
	 * 分页查看裁剪
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getTailor", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTailor(HttpServletRequest request, PageParameter page, Tailor tailor) {
		CommonResponse cr = new CommonResponse();
		PageResult<Tailor> tailorList = tailorService.findPages(tailor, page);
		cr.setData(clearCascadeJSON.format(tailorList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * (裁剪普通激光,绣花定位激光，冲床，电烫，电推，手工剪刀)填写
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addOrdinaryLaser", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrdinaryLaser(HttpServletRequest request, OrdinaryLaser ordinaryLaser) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(ordinaryLaser.getId())) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		} else {
			OrdinaryLaser oldOrdinaryLaser = ordinaryLaserService.findOne(ordinaryLaser.getId());
			BeanCopyUtils.copyNotEmpty(ordinaryLaser,oldOrdinaryLaser,"" );
			ordinaryLaserService.saveOrdinaryLaser(oldOrdinaryLaser);
			cr.setMessage("修改成功");
		}
		return cr;
	}

	/**
	 * 分页查看裁减类型实体
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getOrdinaryLaser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrdinaryLaser(HttpServletRequest request, PageParameter page,
			OrdinaryLaser ordinaryLaser) {
		CommonResponse cr = new CommonResponse();
		PageResult<OrdinaryLaser> ordinaryLaserList = ordinaryLaserService.findPages(ordinaryLaser, page);
		cr.setData(clearCascadeJSONOrdinaryLaser.format(ordinaryLaserList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}


	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
