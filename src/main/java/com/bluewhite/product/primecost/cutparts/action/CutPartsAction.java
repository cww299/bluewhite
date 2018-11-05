package com.bluewhite.product.primecost.cutparts.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;

@Controller
public class CutPartsAction {
	
private final static Log log = Log.getLog(CutPartsAction.class);
	
	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductService productService;
	
	/**
	 * cc裁片填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCutParts(HttpServletRequest request,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(cutParts.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			cutPartsService.saveCutParts(cutParts);
			cr.setMessage("添加成功");
		}
		return cr;
	}
		
	
	/**
	 * cc裁片修改
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updateCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateCutParts(HttpServletRequest request,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(cutParts.getId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁片不能为空");
		}else{
			try {
				CutParts oldCutParts = cutPartsService.findOne(cutParts.getId());
				BeanCopyUtils.copyNullProperties(oldCutParts,cutParts);
				cutParts.setCreatedAt(oldCutParts.getCreatedAt());
				cutPartsService.saveCutParts(cutParts);
			} catch (Exception e) {
				cr.setMessage(e.getMessage());
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				return cr;
			}
			cr.setMessage("修改成功");
		}
		return cr;
	}
	
	
	
	/**
	 * 分页查看cc裁片
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getCutParts(HttpServletRequest request,PageParameter page,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		PageResult<CutParts>  cutPartsList= new PageResult<>(); 
		if(cutParts.getProductId()!=null){
			HttpSession session = request.getSession();
			Product product = productService.findOne(cutParts.getProductId());
			session.setAttribute("productId",product.getId());
			session.setAttribute("number", product.getPrimeCost()!=null ? product.getPrimeCost().getNumber():null);
			session.setAttribute("productName", product.getName());
			cutPartsList = cutPartsService.findPages(cutParts,page);
		}
		cr.setData(cutPartsList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 删除cc裁片
	 * 
	 */
	@RequestMapping(value = "/product/deleteCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCutParts(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					cutPartsService.deleteCutParts(id);
				}
			}
				cr.setMessage("删除成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("裁片id不能为空");
			}
		return cr;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	

}
