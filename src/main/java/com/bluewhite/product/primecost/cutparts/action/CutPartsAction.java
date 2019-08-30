package com.bluewhite.product.primecost.cutparts.action;

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
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecost.primecost.dao.PrimeCostDao;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.product.service.ProductService;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;

@Controller
public class CutPartsAction {

	private final static Log log = Log.getLog(CutPartsAction.class);

	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PrimeCostDao primeCostDao;
	

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON
				.get().addRetainTerm(CutParts.class, "id", "productId", "number", "tailorId", "overstock", 
						"materiel", "cutPartsName","loss","cutPartsNumber","perimeter","allPerimeter","oneMaterial"
						,"unit","scaleMaterial","addMaterial","manualLoss","composite","doubleComposite","complexMateriel"
						,"compositeManualLoss","batchMaterial","batchMaterialPrice","complexBatchMaterial","batchComplexMaterialPrice"
						,"batchComplexAddPrice","oneCutPartsPrice")
				.addRetainTerm(BaseOne.class, "name","type")
				.addRetainTerm(Materiel.class,"id","number","name","price","unit");
	}

	/**
	 * cc裁片填写
	 * 
	 */
	@RequestMapping(value = "/product/addCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCutParts( CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(cutParts.getProductId())) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		} else {
			cutPartsService.saveCutParts(cutParts);
			PrimeCost primeCost = primeCostDao.findByProductId(cutParts.getProductId());
			if(primeCost == null){
				 primeCost = new PrimeCost();
				 primeCost.setProductId(cutParts.getProductId());
			}
			productService.getPrimeCost(primeCost);
			cutParts.setOneCutPartsPrice(primeCost.getOneCutPartsPrice());
			cr.setMessage("添加成功");
		}
		return cr;
	}

	/**
	 * cc裁片修改
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updateCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateCutParts(HttpServletRequest request, CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(cutParts.getId())) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁片不能为空");
		} else {
			CutParts oldCutParts = cutPartsService.findOne(cutParts.getId());
			BeanCopyUtils.copyNotEmpty(cutParts, oldCutParts, "");
			cutPartsService.saveCutParts(oldCutParts);
			cr.setMessage("修改成功");
		}
		return cr;
	}

	/**
	 * 分页查看cc裁片
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getCutParts(PageParameter page, CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		cr.setData(cutPartsService.findPages(cutParts, page));
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除cc裁片
	 * 
	 */
	@RequestMapping(value = "/product/deleteCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCutParts(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					cutPartsService.deleteCutParts(id);
				}
			}
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁片id不能为空");
		}
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
