package com.bluewhite.product.primecostbasedata.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecostbasedata.dao.BaseFourDao;
import com.bluewhite.product.primecostbasedata.dao.BaseOneTimeDao;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.BaseFour;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;
import com.bluewhite.system.sys.entity.SysLog;

@Controller
public class BaseOneAction {
	
	private final static Log log = Log.getLog(BaseOneAction.class);
	
	@Autowired
	private BaseFourDao baseFourDao;
	
	@Autowired
	private BaseThreeDao baseThreeDao;
	
	@Autowired
	private BaseOneTimeDao baseOneTimeDao;
	
	@Autowired
	private MaterielService materielService;
	
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	
	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductMaterialsService productMaterialsService;
	
	/**
	 * 时间常量
	 */
	private final static Integer  TIME = 60;
	
	/**
	 * 产品基础数据获取
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseOne(HttpServletRequest request,BaseOne baseOne) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(BaseOne.class, "id","name","textualTime","time","baseOneTimes")
				.addRetainTerm(BaseOneTime.class,"textualTime","time","categorySetting","baseOneId")
				.format(materielService.findPagesBaseOne(baseOne)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 产品基础数据3获取(手选该裁片的平方M)
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseThree", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseThree(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		cr.setData(baseThreeDao.findAll());
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 产品基础数据3获取,将裁减类型和数据进行匹配
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseThreeOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseThreeOne(Long typeId, Double number) {
		CommonResponse cr = new CommonResponse();
		List<BaseThree> baseThreeList = baseThreeDao.findAll();
		BaseThree BaseThree = null;
		for(BaseThree bt : baseThreeList){
			if(bt.getOrdinaryLaser()==number){
				BaseThree = bt;
				break;
			}
		}
		
		double returnNumber = 0 ;
		switch (typeId.intValue()) {
		case 71://普通激光切割
			returnNumber = BaseThree.getTextualOrdinaryLight();
			break;
		case 72://绣花激光切割
			returnNumber = BaseThree.getTextualPositionLight();
			break;
		case 73://手工电烫
			returnNumber = BaseThree.getTextualPerm();
			break;
		case 74://设备电烫
			break;
		case 75://冲床
			returnNumber = BaseThree.getTextualPuncher();
			break;
		case 76://电推
			returnNumber = BaseThree.getTextualClippers();
			break;
		case 77://手工剪刀
			returnNumber = BaseThree.getTextualScissors();
			break;
		case 78://绣花领取
			break;
		default:
			break;
		}
		cr.setData(returnNumber);
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 物料产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMateriel", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMateriel(Materiel materiel) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Materiel.class,"id","number","name","price","materielType","unit","changePrice","count","convertUnit","convertPrice")
				.addRetainTerm(BaseOne.class,"id","name")
				.addRetainTerm(BaseData.class,"id","name")
				.format(materielService.findList(materiel)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 物料产品基础数据获取分页
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMaterielPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMaterielPage(Materiel materiel,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Materiel.class,"id","number","name","price","type","unit","changePrice","count","convertUnit","convertPrice")
				.format(materielService.findMaterielPages(materiel,page)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 新增修改物料产品基础数据
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addMateriel", method = RequestMethod.POST)
	@ResponseBody
	@SysLogAspectAnnotation(description = "基础数据新增操作", module = "新增管理", operateType = "新增", logType = SysLog.ADMIN_LOG_TYPE)
	public CommonResponse addMateriel(HttpServletRequest request,Materiel materiel) {
		CommonResponse cr = new CommonResponse();
		if(materiel.getId()!=null){
			Materiel oldMateriel = materielService.findOne(materiel.getId());
			BeanCopyUtils.copyNullProperties(oldMateriel,materiel);
			materiel.setCreatedAt(oldMateriel.getCreatedAt());
			
		}
		materielService.save(materiel);
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 修改 裁剪页面的基础系数
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updatePrimeCoefficient", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updatePrimeCoefficient(HttpServletRequest request,PrimeCoefficient primeCoefficient) {
		CommonResponse cr = new CommonResponse();
		if(primeCoefficient.getId()!=null){
			PrimeCoefficient oldPrimeCoefficient = primeCoefficientDao.findOne(primeCoefficient.getId());
			BeanCopyUtils.copyNullProperties(oldPrimeCoefficient,primeCoefficient);
			if(primeCoefficient.getExtent()!=null){
				//每CM 用时/秒
				primeCoefficient.setTime(1/primeCoefficient.getExtent());
			}
			//被/数
			primeCoefficient.setQuilt(primeCoefficient.getQuilt());
			//每秒设备折旧费用
			primeCoefficient.setDepreciation(primeCoefficient.getWorth()/primeCoefficient.getShareDay()
					/primeCoefficient.getWorkTime()/(primeCoefficient.getNeedleworkOne()!=null ? primeCoefficient.getNeedleworkOne() :1.0)/TIME/TIME);
			//每秒激光管费用
			primeCoefficient.setLaserTubePriceSecond(primeCoefficient.getLaserTubePrice()/primeCoefficient.getShareTime()/(primeCoefficient.getNeedleworkOne()!=null ? primeCoefficient.getNeedleworkOne() :1.0)/TIME/TIME);
			//每秒维护费用
			primeCoefficient.setMaintenanceChargeSecond(primeCoefficient.getMaintenanceCharge()/primeCoefficient.getShareTimeTwo()/(primeCoefficient.getNeedleworkOne()!=null ? primeCoefficient.getNeedleworkOne() :1.0)/TIME/TIME);
			//每秒耗3费
			primeCoefficient.setPerSecondPrice((primeCoefficient.getOmnHorElectric()+primeCoefficient.getOmnHorWater()+primeCoefficient.getOmnHorHouse())/(primeCoefficient.getNeedleworkOne()!=null ? primeCoefficient.getNeedleworkOne() :1.0)/TIME/TIME);
			//每秒工价
			primeCoefficient.setPerSecondMachinist(primeCoefficient.getOmnHorMachinist()/TIME/TIME);
			//每秒工价2
			if(primeCoefficient.getOmnHorAuxiliary()!=null){
				primeCoefficient.setPerSecondMachinistTwo(primeCoefficient.getOmnHorAuxiliary()/TIME/TIME);
			}
			//针工车间
			if(primeCoefficient.getNeedleworkOne()!=null){
				//每秒耗3费
				primeCoefficient.setPerSecondPriceTwo(
						(primeCoefficient.getNeedleworkTwo()+primeCoefficient.getNeedleworkThree()+primeCoefficient.getNeedleworkFour())
						/primeCoefficient.getManageEquipmentNumber()
						/TIME/TIME);
				primeCoefficient.setNeedleworkSeven(primeCoefficient.getNeedleworkFive()/TIME/TIME);
				primeCoefficient.setNeedleworkEight(primeCoefficient.getNeedleworkSix()/TIME/TIME);
			}
			
			//每秒管理费用
			primeCoefficient.setPerSecondManage(primeCoefficient.getManagePrice()/primeCoefficient.getManageEquipmentNumber()/25/8/TIME/TIME);
			cr.setData(primeCoefficientDao.save(primeCoefficient));
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	
	/**
	 * 裁剪页面的基础系数
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getPrimeCoefficient", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPrimeCoefficient(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(primeCoefficientDao.findByTailorTypeId(id));
		return cr;
	}
	
	
	/**
	 * 获取机锋时间页面布料
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseFour", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseFour(HttpServletRequest request,String sewingOrder) {
		CommonResponse cr = new CommonResponse();
		List<BaseFour> baseFour=null;
		if(!StringUtils.isEmpty(sewingOrder)){
			 baseFour = baseFourDao.findBySewingOrderLike("%"+sewingOrder+"%");
		}else{
			 baseFour = baseFourDao.findAll();
		}
		cr.setMessage("获取成功");
		cr.setData(baseFour);
		return cr;
	}
	
	/**
	 * 当物料被修改，自动更新裁片页面和dd页面相关值
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updateMaterielAndOther", method = RequestMethod.GET)
	@ResponseBody
	@SysLogAspectAnnotation(description = "物料价格变动修改操作", module = "产品价格表", operateType = "修改", logType = SysLog.ADMIN_LOG_TYPE)
	public CommonResponse updateMaterielAndOther(HttpServletRequest request,Long productId) {
		CommonResponse cr = new CommonResponse();
		//裁片
		List<CutParts> cutPartsList = cutPartsService.findByProductId(productId);
		cutPartsList = 	cutPartsList.stream().filter(CutParts->CutParts.getMaterielId()!=null).collect(Collectors.toList());
		for(CutParts cp : cutPartsList){	
			Materiel materiel = materielService.findOne(cp.getMaterielId());
			cutPartsService.saveCutParts(cp);
			}
		
		//除裁片
		List<ProductMaterials>  productMaterialsList = productMaterialsService.findByProductId(productId);
		for(ProductMaterials pm: productMaterialsList){	
			Materiel materiel = materielService.findOne(pm.getMaterielId());
			Double unitCost = null;
//			if(pm.getUnit().equals(pm.getProductUnit())){
//				unitCost=materiel.getPrice();
//			}else{
//				unitCost=materiel.getConvertPrice();
//			}
//			if(unitCost!=pm.getUnitCost()){
//				pm.setUnitCost(unitCost);
//				productMaterialsService.saveProductMaterials(pm);
//			}
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
