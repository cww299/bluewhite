package com.bluewhite.product.primecostbasedata.action;

import java.text.SimpleDateFormat;

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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.BaseOneTimeDao;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;

@Controller
public class BaseOneAction {
	
	private final static Log log = Log.getLog(BaseOneAction.class);
	

	
	@Autowired
	private BaseThreeDao baseThreeDao;
	
	@Autowired
	private BaseOneTimeDao baseOneTimeDao;
	
	@Autowired
	private MaterielService materielService;
	
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	
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
				.addRetainTerm(BaseOne.class, "id","name","textualTime","time")
				.format(materielService.findPagesBaseOne(baseOne)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 产品基础数据详细获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseOneTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseOneTime(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(BaseOneTime.class,"textualTime","time","categorySetting")
				.format(baseOneTimeDao.findByBaseOneId(id)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 产品基础数据3获取
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
	 * 物料产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMateriel", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMateriel(HttpServletRequest request,Materiel materiel) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Materiel.class,"id","number","name","price","type","unit","changePrice","count","convertUnit","convertPrice")
				.format(materielService.findPages(materiel)).toJSON());
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
			//每CM 用时/秒
			oldPrimeCoefficient.setTime(1/primeCoefficient.getPeripheralLaser());
			//被/数
			oldPrimeCoefficient.setQuilt(primeCoefficient.getQuilt());
			//每秒设备折旧费用
			oldPrimeCoefficient.setDepreciation(primeCoefficient.getWorth()/primeCoefficient.getShareDay()
					/primeCoefficient.getWorkTime()/TIME/TIME);
			//每秒激光管费用
			oldPrimeCoefficient.setLaserTubePriceSecond(primeCoefficient.getLaserTubePrice()/primeCoefficient.getShareTime()/TIME/TIME);
			//每秒维护费用
			oldPrimeCoefficient.setMaintenanceChargeSecond(primeCoefficient.getMaintenanceCharge()/primeCoefficient.getShareTimeTwo()/TIME/TIME);
			//每秒耗3费
			oldPrimeCoefficient.setPerSecondPrice((primeCoefficient.getOmnHorElectric()+primeCoefficient.getOmnHorWater()+primeCoefficient.getOmnHorHouse())/TIME/TIME);
			//每秒工价
			oldPrimeCoefficient.setPerSecondMachinist(primeCoefficient.getOmnHorMachinist()/TIME/TIME);
			//每秒管理费用
			oldPrimeCoefficient.setPerSecondManage(primeCoefficient.getManagePrice()/primeCoefficient.getManageEquipmentNumber()/25/8/TIME/TIME);
			primeCoefficientDao.save(oldPrimeCoefficient);
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
	public CommonResponse getPrimeCoefficient(HttpServletRequest request,String type) {
		CommonResponse cr = new CommonResponse();
		cr.setData(primeCoefficientDao.findByType(type));
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
