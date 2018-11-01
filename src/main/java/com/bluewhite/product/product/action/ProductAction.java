package com.bluewhite.product.product.action;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;
import com.bluewhite.system.sys.entity.SysLog;

@Controller
public class ProductAction {
	
	private static final Log log = Log.getLog(ProductAction.class);
	
	@Autowired
	private ProductDao dao;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProcedureService  procedureService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Product.class,"id","number","name","departmentPrice","hairPrice","deedlePrice","puncherHairPrice","puncherDepartmentPrice");
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
	 * 分页查看所有的产品的成本报价
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/getProductPages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProductPages(HttpServletRequest request,PageParameter page,Product product,Integer primeCostNumber) {
		CommonResponse cr = new CommonResponse();
		if(product.getId()!=null){
			HttpSession session = request.getSession();
			session.setAttribute("productId", product.getId());
			session.setAttribute("number", primeCostNumber);
			session.setAttribute("productName", product.getName());
		}
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Product.class,"id","primeCost","name","number")
				.addRetainTerm(PrimeCost.class,"number","cutPartsPrice","otherCutPartsPrice","cutPrice","machinistPrice","embroiderPrice"
						,"embroiderPrice","needleworkPrice","packPrice","freightPrice","freightPrice","invoice","taxIncidence"
						,"surplus","budget","budgetRate","actualCombat","actualCombatRate")
				.format(productService.findPages(product,page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 获取单个产品的成本报价
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPrimeCost", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getPrimeCost(HttpServletRequest request,PrimeCost primeCost) {
		CommonResponse cr = new CommonResponse();
		cr.setData(productService.getPrimeCost(primeCost,request));
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
		if(StringUtils.isEmpty(product.getNumber()) || StringUtils.isEmpty(product.getName())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品编号和产品名都不能为空");
		}else{
			Product products = dao.findByNumber(product.getNumber());
			if(products!=null){
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("已有该产品编号的产品，请检查后再次添加");
			}else{
				productService.save(product);
				cr.setMessage("添加成功");
			}
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
		Product products = dao.findByNumber(product.getNumber());
		if(products!=null){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("已有该产品编号的产品，请检查后再次添加");
			return cr;
		}
		
		if(product.getId()!=null){
			Product oldProduct = productService.findOne(product.getId());
			BeanCopyUtils.copyNullProperties(oldProduct,product);
			product.setCreatedAt(oldProduct.getCreatedAt());
			productService.update(product);
			
			//各部门修改产品不同处理方案
			if(product.getType()!=null){
				//根据不同部门，计算不同的外发价格
				List<Procedure>	procedureList = procedureService.findByProductIdAndType(product.getId(),product.getType(),0);
				if(procedureList.size()==0){
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("请先填写工序");
					return cr;
				}
				//针工机工修改外发价格
				if(product.getType()==3 || product.getType()==4 ){
					for(Procedure pro : procedureList){
						pro.setHairPrice(product.getHairPrice());
					}
					procedureService.saveList(procedureList);
				}
				//裁剪修改外发价格
				if(product.getType()==5){
					List<Procedure>	procedureList1 = procedureList.stream().filter(Procedure->Procedure.getSign()==0).collect(Collectors.toList());
					if(product.getHairPrice()!=null){
						for(Procedure pro : procedureList1){
							pro.setHairPrice(product.getHairPrice());
						}
						procedureService.saveList(procedureList1);
					}
					
					List<Procedure> procedureList2 = procedureList.stream().filter(Procedure->Procedure.getSign()==1).collect(Collectors.toList());
					if(product.getPuncherHairPrice()!=null){
						for(Procedure pro : procedureList2){
							pro.setHairPrice(product.getPuncherHairPrice());
						}
						procedureService.saveList(procedureList2);
					}
				}
			}
			
			cr.setMessage("修改成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品id不能为空");
		}
		return cr;
	}
	
	
	
	/**
	 * (成本价格表中) 根据不同菜单跳转不同的jsp（同时将产品id放入session中，
	 * 通过引入通用的<%@include file="../../decorator/leftbar.jsp"%> 获取到
	 */
	@RequestMapping(value = "product/menusToUrl", method = RequestMethod.GET)
	public String menusToJsp(HttpServletRequest request,String url, String paramNum) {
		HttpSession session = request.getSession();
		session.setAttribute("productId", paramNum);
		return url;
	}
	
	/**
	 * 清除当前产品session
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/cleanProduct", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse cleanProduct(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		HttpSession session = request.getSession();
		//从session中删除当前产品属性 
		session.removeAttribute("productId");
		cr.setMessage("清除成功");
		return cr;
	}
	
}
