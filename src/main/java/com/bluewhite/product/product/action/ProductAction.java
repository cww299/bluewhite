package com.bluewhite.product.product.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.metadata.IIOMetadataFormat;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Constants;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.embroidery.service.EmbroideryService;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.machinist.service.MachinistService;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.needlework.service.NeedleworkService;
import com.bluewhite.product.primecost.pack.entity.Pack;
import com.bluewhite.product.primecost.pack.service.PackService;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.OrdinaryLaserService;
import com.bluewhite.product.primecost.tailor.service.TailorService;
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
	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductMaterialsService productMaterialsService;
	@Autowired
	protected EntityManager entityManager = null;
	@Autowired
	private EmbroideryService embroideryService;
	@Autowired
	private MachinistService machinistService;
	@Autowired
	private NeedleworkService needleworkService;
	@Autowired
	private PackService packService;
	@Autowired
	private TailorService tailorService;
	@Autowired
	private OrdinaryLaserService  ordinaryLaserService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Product.class,"id","number","departmentNumber","originDepartment","name","departmentPrice","hairPrice","deedlePrice","puncherHairPrice","puncherDepartmentPrice");
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
			Product productOne = productService.findOne(product.getId());
			session.setAttribute("productId", product.getId());
			session.setAttribute("number", productOne.getPrimeCost()!=null ? productOne.getPrimeCost().getNumber():null);
			session.setAttribute("productName", productOne.getName());
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
		CurrentUser cu = SessionManager.getUserSession();
		
		//质检
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_QUALITY)){
			product.setOriginDepartment(Constants.PRODUCT_FRIST_QUALITY);
		}
		//包装
		if(cu.getRole().contains(Constants.PRODUCT_FRIST_PACK)){
			product.setOriginDepartment(Constants.PRODUCT_FRIST_PACK);
		}
		//针工
		if(cu.getRole().contains(Constants.PRODUCT_TWO_DEEDLE)){
			product.setOriginDepartment(Constants.PRODUCT_TWO_DEEDLE);
		}
		//机工
		if(cu.getRole().contains(Constants.PRODUCT_TWO_MACHINIST)){
			product.setOriginDepartment(Constants.PRODUCT_TWO_MACHINIST);
		}
		//裁剪
		if(cu.getRole().contains(Constants.PRODUCT_RIGHT_TAILOR)){
			product.setOriginDepartment(Constants.PRODUCT_RIGHT_TAILOR);
			
		}
		
		if(StringUtils.isEmpty(product.getDepartmentNumber()) && StringUtils.isEmpty(product.getName())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品编号和产品名都不能为空");
			return cr;
		}
		
		//通过传入的部门产品编号查询是否存在编号为此的产品。如果存在则不能添加。在这里控制产品共同编号的唯一性
		Product products = dao.findByNumber(product.getDepartmentNumber());
		Product products1 = dao.findByDepartmentNumber(product.getDepartmentNumber());
		
		//如果后期出现需求，一个产品在各个地方所用的所有费用，这个时候无法用产品编号进行查询
		//产品名称在包装环境中不会变动
		//所以可以通过产品名称来查询。同时保证在拥有产品编号的产品中 保证产品名称的唯一性
//			Product ps = dao.findByNumberNotNullAndName(product.getName());
//			if(ps!=null){
//				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
//				cr.setMessage("已有该产品，请检查后再次添加");
//				return cr;
//			}
		
		//下面存入各部门产品编号
		if(products!=null && products1!=null){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("已有该产品编号的产品，请检查后再次添加");
		}else{
			//同时判断各部门的编号是否和最终的产品编号位数相同，如果相同，则存入产品编号中，同时清空来源部门，让产品变成共同的集合
			if(product.getDepartmentNumber().length()==7){
				product.setNumber(product.getDepartmentNumber());
				product.setOriginDepartment(null);
			};
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
			//这里控制共同产品无法被修改，只能修改自己部门的产品名称和产品编号
			//判断是否来源于部门
			if(StringUtils.isEmpty(oldProduct.getOriginDepartment())){
				//需要修改产品编号和产品名称时
				if(product.getName()!=null || product.getDepartmentNumber()!=null){
					if(!product.getName().equals(oldProduct.getName()) || !product.getDepartmentNumber().equals(oldProduct.getNumber())){
						cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
						cr.setMessage("该产品名和编号无权限修改");
						return cr;
					}
				}
			}
			BeanCopyUtils.copyNullProperties(oldProduct,product);
			product.setCreatedAt(oldProduct.getCreatedAt());
			productService.save(product);
			
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
	 * 删除产品
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteProduct(Long id) {
		CommonResponse cr = new CommonResponse();
		productService.delete(id);
		cr.setMessage("删除成功");
		return cr;
	}
	
	
	
	/**
	 * (成本价格表中) 复制成本价格表中的所有
	 * 
	 */
	@RequestMapping(value = "product/copyProduct", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse copyProduct(HttpServletRequest request,Long id, Long oldId) {
		CommonResponse cr = new CommonResponse();
		HibernateEntityManager hEntityManager = (HibernateEntityManager)entityManager;
        Session session = hEntityManager.getSession();
        Product oldIdProduct =  productService.findOne(oldId);
        Product product =  productService.findOne(id);
        PrimeCost  primeCost = new PrimeCost();
        PrimeCost  oldprimeCost =  oldIdProduct.getPrimeCost();
    	BeanCopyUtils.copyNotEmpty(oldprimeCost,primeCost,"");
    	primeCost.setId(null);
    	primeCost.setProductId(id);
        product.setPrimeCost(primeCost);
        session.clear();//清除缓存	
        productService.save(product);
        
		//裁片
		List<CutParts> cutPartsList = cutPartsService.findByProductId(oldId);
		cutPartsList.stream().forEach(CutParts->CutParts.setId(null));
		cutPartsList.stream().forEach(CutParts->CutParts.setProductId(id));
		
		//绣花
		List<Embroidery>  embroideryList = embroideryService.findByProductId(oldId);
		embroideryList.stream().forEach(Embroidery->Embroidery.setId(null));
		embroideryList.stream().forEach(Embroidery->Embroidery.setProductId(id));
		
		//除裁片
		List<ProductMaterials>  productMaterialsList = productMaterialsService.findByProductId(oldId);
		productMaterialsList.stream().forEach(ProductMaterials->ProductMaterials.setId(null));
		productMaterialsList.stream().forEach(ProductMaterials->ProductMaterials.setProductId(id));
		
		//机工
		List<Machinist>  machinistList = machinistService.findByProductId(oldId);
		machinistList.stream().forEach(Machinist->Machinist.setId(null));
		machinistList.stream().forEach(Machinist->Machinist.setProductId(id));
		
		//针工
		List<Needlework>  needleworkList = needleworkService.findByProductId(oldId);
		needleworkList.stream().forEach(Needlework->Needlework.setId(null));
		needleworkList.stream().forEach(Needlework->Needlework.setProductId(id));
		
		//包装
		List<Pack>  packList = packService.findByProductId(oldId);
		packList.stream().forEach(Pack->Pack.setId(null));
		packList.stream().forEach(Pack->Pack.setProductId(id));
		
		session.clear();//清除缓存	
		
		//保存新实体
		cutPartsService.save(cutPartsList);
		embroideryService.save(embroideryList);
		productMaterialsService.save(productMaterialsList);
		machinistService.save(machinistList);
		needleworkService.save(needleworkList);
		packService.save(packList);
		
	    //1.将新的裁片存起，这时，裁剪id还存在，同时根据裁剪id 查出所有，然后将裁剪页面的裁片id换成新的裁片id，同时制空裁剪id，组成新的裁剪实体,保存。在将裁片页面的裁剪id替换成新的  
		List<Tailor>  tailorList =  new ArrayList<Tailor>();
		for(CutParts cp : cutPartsList){
			Tailor tailor = tailorService.findOne(cp.getTailorId());
			tailor.setId(null);
			tailor.setProductId(id);
			tailor.setCutPartsId(cp.getId());
			//同时绣花页面和裁剪实体存在关联，将新的绣花页面的id同时赋值给新的裁剪
			for(Embroidery ed : embroideryList){
				//当新的绣花实体中的裁剪id等同于老的裁剪id时
				if(ed.getTailorId()==cp.getTailorId()){
					tailor.setEmbroideryId(ed.getId());
					break;
				}
			}
			tailorList.add(tailor);
		}
		session.clear();//清除缓存	
		tailorService.save(tailorList);
	
		
		for(Tailor tr : tailorList){
			CutParts cutParts = cutPartsService.findOne(tr.getCutPartsId());
			Embroidery  embroidery = embroideryService.findOne(tr.getEmbroideryId());
			OrdinaryLaser ordinaryLaser = ordinaryLaserService.findOne(tr.getOrdinaryLaserId());
			ordinaryLaser.setId(null);
			ordinaryLaser.setProductId(id);
			ordinaryLaser.setTailorId(tr.getId());
			cutParts.setTailorId(tr.getId());
			embroidery.setTailorId(tr.getId());
			session.clear();//清除缓存	
			cutPartsService.save(cutParts);
			embroideryService.save(embroidery);
			ordinaryLaserService.save(ordinaryLaser);
		}
		cr.setMessage("复制成功");
		return cr;
	}
	
	
	
	
	
	/**
	 * (成本价格表中) 根据不同菜单跳转不同的jsp（同时将产品id放入session中，
	 * 通过引入通用的<%@include file="../../decorator/leftbar.jsp"%> 获取到
	 */
	@RequestMapping(value = "product/menusToUrl", method = RequestMethod.GET)
	public String menusToJsp(HttpServletRequest request,String url, String paramNum) {
		HttpSession session = request.getSession();
		Product productOne = productService.findOne(Long.valueOf(paramNum));
		session.setAttribute("number", productOne.getPrimeCost()!=null ? productOne.getPrimeCost().getNumber():null);
		session.setAttribute("productName", productOne.getName());
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
