package com.bluewhite.product.primecost.embroidery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.embroidery.dao.EmbroideryDao;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public class EmbroideryServiceImpl  extends BaseServiceImpl<Embroidery, Long> implements EmbroideryService{

	@Autowired
	private EmbroideryDao dao;
	
	@Autowired
	private CutPartsDao cutPartsDao;
	
	@Autowired
	private TailorDao tailorDao;
	
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	
	@Override
	public Embroidery saveEmbroidery(Embroidery embroidery) {
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("embroidery");
		//单片机走时间
		embroidery.setSinglechipApplique(NumUtils.round((primeCoefficient.getEmbroideryTwo()/1000)*embroidery.getNeedleNumber()/embroidery.getFew(), 1));
		//铺布或裁片秀贴布和上绷子时间
		embroidery.setTime(embroidery.getCloth()==null ?primeCoefficient.getEmbroideryFour() : primeCoefficient.getEmbroideryThree()/embroidery.getEmbroiderySlice());
		//贴片时间
//		embroidery.setPasterTime(pasterTime);
		//站机含快手时间
		embroidery.setVentilatorStationTime(NumUtils.round((embroidery.getSinglechipApplique()+embroidery.getTime()+embroidery.getPasterTime())*1.08*primeCoefficient.getQuickWorker(),2));
		//剪线时间含快手
		embroidery.setCuttingLineTime(embroidery.getEmbroideryColorNumber()*primeCoefficient.getEmbroiderySix()*1.08*primeCoefficient.getQuickWorker());
		//绣花线用量/米
		embroidery.setLineDosage(NumUtils.round(( embroidery.getNeedleNumber()*primeCoefficient.getEmbroideryEleven()),2));
		//垫纸用量
		embroidery.setPackingPaperDosage(embroidery.getAffirmSize()+embroidery.getAffirmSizeTwo());
		//薄膜用量/平方米
		embroidery.setMembraneDosage((embroidery.getAffirmSize()+embroidery.getAffirmSizeTwo())*embroidery.getMembrane());
		//绣花物料线价格
		embroidery.setEmbroideryPrice(embroidery.getLineDosage()*primeCoefficient.getEmbroideryNine());
		//绣花物料垫纸价格（通过前台计算得到）（用）
		embroidery.setPackingPaperPrice(embroidery.getPaperPrice()*embroidery.getPackingPaperDosage());
		//绣花物料薄膜价格
		embroidery.setMembranePrice(embroidery.getMembraneDosage()*primeCoefficient.getEmbroideryOne());
		//电脑推算站机工价（含快手)
		embroidery.setReckoningPrice(embroidery.getVentilatorStationTime()*primeCoefficient.getPerSecondMachinist());
		//剪线工价
		embroidery.setCutLinePrice(embroidery.getCuttingLineTime()*primeCoefficient.getPerSecondMachinistTwo());
		//设备折旧和房水电费
		embroidery.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
				primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*(embroidery.getVentilatorStationTime()+embroidery.getCuttingLineTime()));
		//管理人员费用
		embroidery.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*(embroidery.getVentilatorStationTime()+embroidery.getCuttingLineTime()));
		//电脑推算绣花价格
		embroidery.setReckoningEmbroideryPrice(NumUtils.round(embroidery.getReckoningPrice()+embroidery.getCutLinePrice()+embroidery.getEquipmentPrice()+embroidery.getAdministrativeAtaff(),3));
		//目前市场价格
		embroidery.setReckoningSewingPrice(
		((NumUtils.round((double)embroidery.getNeedleNumber()/1000,0)*1000)<embroidery.getNeedleNumber() 
			? (NumUtils.round((double)embroidery.getNeedleNumber()/1000,0)*1000+1000) 
					: (NumUtils.round((double)embroidery.getNeedleNumber()/1000,0)*1000))
					/1000*primeCoefficient.getEmbroideryTwelve()+(embroidery.getApplique()*primeCoefficient.getEmbroideryThirteen())
					+(embroidery.getEmbroideryColorNumber()-1)*primeCoefficient.getEmbroideryFourteen()
				);
		//入成本价格
		embroidery.setAllCostPrice(embroidery.getNumber()*NumUtils.setzro(embroidery.getCostPrice()));
		//各单道比全套工价
		List<Embroidery> embroideryList = dao.findByProductId(embroidery.getProductId());
		embroideryList.add(embroidery);
		double scaleMaterial = embroidery.getAllCostPrice()/(embroideryList.stream().filter(Machinist->Machinist.getAllCostPrice()!=null).mapToDouble(Embroidery::getAllCostPrice).sum());
		embroidery.setScaleMaterial(scaleMaterial);
		//物料和上道压（裁剪）价(裁片绣上道压价 = 裁剪页面的不含绣花环节的为机工压价 ， 整布绣上道压价 = cc裁片页面 当批各单片价格)
		
		List<Tailor> tailorList = tailorDao.findByProductId(embroidery.getProductId());
		
//		double caipian = ;
				
		List<CutParts>  cutPartsList = cutPartsDao.findByProductId(embroidery.getProductId());
		
//		double zhengbu = 	;	
				
//		embroidery.setPriceDown();
//		
//		embroidery.setPriceDownRemark();
		
		return embroidery;
	}

}
