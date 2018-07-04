package com.bluewhite.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.entity.Product;
import com.bluewhite.product.service.ProductService;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;
import com.bluewhite.system.sys.entity.SysLog;

@Controller
public class ProductAction {
	
	private static final Log log = Log.getLog(ProductAction.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProcedureService  procedureService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Product.class,"id","number","name","departmentPrice","hairPrice","deedlePrice");
	}
	
	/**
	 * 分页查看所有的产品
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/productPages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse productPages(HttpServletRequest request,PageParameter page,Product product) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(productService.findPages(product,page))
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 添加产品
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@ResponseBody
	@SysLogAspectAnnotation(description = "产品新增操作", module = "产品管理", operateType = "增加", logType = SysLog.ADMIN_LOG_TYPE)
	public CommonResponse addProduct(HttpServletRequest request,Product product) {
		CommonResponse cr = new CommonResponse();
		if(!StringUtils.isEmpty(product.getNumber()) || !StringUtils.isEmpty(product.getName())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品编号和产品名都不能为空");
		}else{
			productService.save(product);
				cr.setMessage("添加成功");
		}
		return cr;
	}
	
	/**
	 * 修改产品
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateProduct(HttpServletRequest request,Product product) {
		CommonResponse cr = new CommonResponse();
		if(product.getId()!=null){
			Product oldProduct = productService.findOne(product.getId());
			BeanCopyUtils.copyNullProperties(oldProduct,product);
			product.setCreatedAt(oldProduct.getCreatedAt());
			productService.update(product);
			//根据不同部门，计算不同的外发价格
			if(product.getType()==3){
				List<Procedure>	procedureList = procedureService.findByProductIdAndType(product.getId(),product.getType(),0);
				if(procedureList.size()==0){
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("请先填写工序");
					return cr;
				}
				for(Procedure pro : procedureList){
					pro.setHairPrice(product.getHairPrice());
				}
				procedureService.saveList(procedureList);
			}
			cr.setMessage("修改成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品id不能为空");
		}
		return cr;
	}
	
	
}
