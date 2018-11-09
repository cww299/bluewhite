package com.bluewhite.product.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.embroidery.dao.EmbroideryDao;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.machinist.dao.MachinistDao;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.materials.dao.ProductMaterialsDao;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.needlework.dao.NeedleworkDao;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.pack.dao.PackDao;
import com.bluewhite.product.primecost.pack.entity.Pack;
import com.bluewhite.product.primecost.primecost.dao.PrimeCostDao;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, Long> implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private PrimeCostDao primeCostDao;
	@Autowired
	private CutPartsDao cutPartsDao;
	@Autowired
	private ProductMaterialsDao productMaterialsDao;
	@Autowired
	private TailorDao tailorDao;
	@Autowired
	private MachinistDao machinistDao;
	@Autowired
	private EmbroideryDao embroideryDao;
	@Autowired
	private NeedleworkDao needleworkDao;
	@Autowired
	private PackDao packDao;
	
	
	@Override
	public PageResult<Product> findPages(Product product,PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		
		//试制部
		if(cu.getRole().contains(Constants.TRIALPRODUCT)){
			product.setOriginDepartment(Constants.TRIALPRODUCT);
		}
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
		
		
		  Page<Product> pages = productDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (product.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),product.getId()));
				}
	        	//按部门展示出共同的产品 和 自身部门添加的产品
	        	//按编号过滤
	        	if (!StringUtils.isEmpty(product.getOriginDepartment())) {
	        		Predicate p1 =cb.isNotNull(root.get("number").as(String.class));
	        		Predicate p2 = cb.equal(root.get("originDepartment").as(String.class),product.getOriginDepartment());
					predicate.add(cb.or(p1,p2));
				}
	        	
	        	
	        	//按编号过滤
	        	if (!StringUtils.isEmpty(product.getNumber())) {
					predicate.add(cb.equal(root.get("number").as(String.class),product.getNumber()));
				}
	        	//按产品名称过滤
	        	if (!StringUtils.isEmpty(product.getName())) {
					predicate.add(cb.like(root.get("name").as(String.class),"%"+StringUtil.specialStrKeyword(product.getName())+"%"));
				}
	        	
	        	
	        	
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		  
		  		for(Product pro : pages.getContent()){
		  			  List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(pro.getId(), product.getType(),0);
		  				  if(procedureList!=null && procedureList.size()>0){
		  					if(product.getType()!=null && product.getType()==5){
		  						  List<Procedure> procedureList1 = procedureList.stream().filter(Procedure->Procedure.getSign()==0).collect(Collectors.toList());
		  						  if(procedureList1.size()>0){
		  							  pro.setHairPrice(procedureList1.get(0).getHairPrice());
		  							  pro.setDepartmentPrice(procedureList1.get(0).getDepartmentPrice());
		  						  }
		  						  List<Procedure> procedureList2 = procedureList.stream().filter(Procedure->Procedure.getSign()==1).collect(Collectors.toList());
		  						  if(procedureList2.size()>0){
		  							  pro.setPuncherHairPrice(procedureList2.get(0).getHairPrice());
		  							  pro.setPuncherDepartmentPrice(procedureList2.get(0).getDepartmentPrice());
		  						  }
		  					  }else{
		  						  if(procedureList.size()>0){
		  							  pro.setHairPrice(procedureList.get(0).getHairPrice());
		  							  pro.setDepartmentPrice(procedureList.get(0).getDepartmentPrice()); 
		  						  }
		  					  }
		  						  if(product.getType()==3){
		  							  pro.setDeedlePrice(procedureList.get(0).getDeedlePrice());
		  						  }
		  				  }
			  		  }
	        PageResult<Product> result = new PageResult<>(pages,page);
	        return result;
	    }

	@Override
	public PrimeCost getPrimeCost(PrimeCost primeCost,HttpServletRequest request) {
		Product product = productDao.findOne(primeCost.getProductId());
		if(product.getPrimeCost()!=null){
			PrimeCost oldPrimeCost = product.getPrimeCost();
			BeanCopyUtils.copyNullProperties(oldPrimeCost,primeCost);
			primeCost.setCreatedAt(oldPrimeCost.getCreatedAt());
		}
		//自动将类型为null的属性赋值为0
		NumUtils.setzro(primeCost);
		
		//面料价格(含复合物料和加工费)
		List<CutParts> cutPartsList = cutPartsDao.findByProductId(primeCost.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		primeCost.setCutPartsPrice(NumUtils.division((batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice)/primeCost.getNumber()));
		//单只
		primeCost.setOneCutPartsPrice(NumUtils.division(primeCost.getCutPartsPrice()/primeCost.getNumber()));
		double cutPartsPriceInvoice = primeCost.getCutPartsInvoice() == 1 ? (primeCost.getCutPartsPriceInvoice()* primeCost.getOneCutPartsPrice()) : 0;
		
		//除面料以外的其他物料价格
		List<ProductMaterials> productMaterialsList = productMaterialsDao.findByProductId(primeCost.getProductId());
		double batchMaterialPrice1 = productMaterialsList.stream().filter(ProductMaterials->ProductMaterials.getBatchMaterialPrice()!=null).mapToDouble(ProductMaterials::getBatchMaterialPrice).sum();
		primeCost.setOtherCutPartsPrice(batchMaterialPrice1);
		primeCost.setOneOtherCutPartsPrice(NumUtils.division((batchMaterialPrice1)/primeCost.getNumber()));
		double otherCutPartsPriceInvoice = primeCost.getOtherCutPartsPriceInvoice() == 1 ? (primeCost.getOtherCutPartsPriceInvoice()* primeCost.getOneOtherCutPartsPrice()) : 0;

		
		// 裁剪
		List<Tailor> tailorList = tailorDao.findByProductId(primeCost.getProductId());
		double allCostPriceTailor = tailorList.stream().filter(Tailor->Tailor.getAllCostPrice()!=null).mapToDouble(Tailor::getAllCostPrice).sum();
		primeCost.setCutPrice(allCostPriceTailor);
		primeCost.setOneCutPrice(NumUtils.division((allCostPriceTailor)/primeCost.getNumber()));
		double cutPriceInvoice = primeCost.getCutPriceInvoice() == 1 ? (primeCost.getCutPriceInvoice()* primeCost.getOneCutPrice()) : 0;

		// 机工
		List<Machinist> machinistList = machinistDao.findByProductId(primeCost.getProductId());
		double allCostPriceMachinist = machinistList.stream().filter(Machinist->Machinist.getAllCostPrice()!=null).mapToDouble(Machinist::getAllCostPrice).sum();
		primeCost.setMachinistPrice(allCostPriceMachinist);
		primeCost.setOneMachinistPrice(NumUtils.division((allCostPriceMachinist)/primeCost.getNumber()));
		double machinistPriceInvoice = primeCost.getMachinistPriceInvoice() == 1 ? (primeCost.getMachinistPriceInvoice()* primeCost.getOneMachinistPrice()) : 0;

		
		// 绣花
		List<Embroidery> embroideryList = embroideryDao.findByProductId(primeCost.getProductId());
		double allCostPriceEmbroidery = embroideryList.stream().filter(Embroidery->Embroidery.getAllCostPrice()!=null).mapToDouble(Embroidery::getAllCostPrice).sum();
		primeCost.setEmbroiderPrice(allCostPriceEmbroidery);
		primeCost.setOneEmbroiderPrice(NumUtils.division((allCostPriceEmbroidery)/primeCost.getNumber()));
		double embroiderPriceInvoice = primeCost.getEmbroiderPriceInvoice() == 1 ? (primeCost.getEmbroiderPriceInvoice()* primeCost.getOneEmbroiderPrice()) : 0;

		
		// 针工
		List<Needlework> needleworkList = needleworkDao.findByProductId(primeCost.getProductId());
		double allCostPriceNeedlework = needleworkList.stream().filter(Needlework->Needlework.getAllCostPrice()!=null).mapToDouble(Needlework::getAllCostPrice).sum();
		primeCost.setNeedleworkPrice(allCostPriceNeedlework);
		primeCost.setOneNeedleworkPrice(NumUtils.division((allCostPriceNeedlework)/primeCost.getNumber()));
		double needleworkPriceInvoice = primeCost.getNeedleworkPriceInvoice() == 1 ? (primeCost.getNeedleworkPriceInvoice()* primeCost.getOneNeedleworkPrice()) : 0;

		
		// 包装
		List<Pack> packList = packDao.findByProductId(primeCost.getProductId());
		double allCostPricePack = packList.stream().filter(Pack->Pack.getAllCostPrice()!=null).mapToDouble(Pack::getAllCostPrice).sum();
		primeCost.setPackPrice(allCostPricePack);
		primeCost.setOnePackPrice(NumUtils.division((allCostPricePack)/primeCost.getNumber()));
		double packPriceInvoice = primeCost.getPackPriceInvoice() == 1 ? (primeCost.getPackPriceInvoice()* primeCost.getOnePackPrice()) : 0;

		
		//批成本
		primeCost.setBacthPrimeCost(primeCost.getCutPartsPrice()+primeCost.getOtherCutPartsPrice()+
				primeCost.getCutPrice()+primeCost.getMachinistPrice()+primeCost.getEmbroiderPrice()
				+primeCost.getNeedleworkPrice()+primeCost.getPackPrice());
		//单只成本
		primeCost.setOnePrimeCost(NumUtils.division(primeCost.getBacthPrimeCost()/primeCost.getNumber()));
		//实战单只成本
		primeCost.setOneaAtualPrimeCost(primeCost.getCutPartsPricePricing()+primeCost.getCutPricePricing()+primeCost.getEmbroiderPricePricing()+primeCost.getMachinistPricePricing()+primeCost.getEmbroiderPricePricing()+primeCost.getPackPricePricing()+primeCost.getFreightPricePricing());
		//预计运费价
		primeCost.setFreightPrice(primeCost.getAdjustNumber()*primeCost.getOneFreightPrice());
		//付上游开票点()
		primeCost.setUpstream(cutPartsPriceInvoice+otherCutPartsPriceInvoice+
				cutPriceInvoice+machinistPriceInvoice+embroiderPriceInvoice+needleworkPriceInvoice+packPriceInvoice);
		//付国家的
		primeCost.setState(NumUtils.division(primeCost.getInvoice()==1 ? primeCost.getState()/1.17*0.17-(primeCost.getOneCutPartsPrice()+primeCost
		.getOneOtherCutPartsPrice()+primeCost.getOneCutPrice()+primeCost.getOneMachinistPrice()+
		primeCost.getOneEmbroiderPrice()+primeCost.getOnePackPrice()+primeCost.getOneNeedleworkPrice()+primeCost.getOnePrimeCost()) : 0.0));
		//预计多付国家的
		primeCost.setExpectState(primeCost.getInvoice()==1 ? primeCost.getState()-(primeCost.getSale()*primeCost.getTaxes()) : 0.0);
		//考虑多付国家的不付需要的进项票点
		primeCost.setNoState(NumUtils.division(primeCost.getInvoice()==1 ? primeCost.getState()/0.17*1.17*0.08 : 0.0));
		//付返点和版权点
		primeCost.setRecidivate((primeCost.getSale()*primeCost.getRebateRate())+(primeCost.getSale()*primeCost.getRebateRate()));
		//付运费
		primeCost.setFreight(primeCost.getFreightPrice());
		//剩余到手的
		primeCost.setSurplus(primeCost.getSale()-primeCost.getState()-primeCost.getUpstream()-primeCost.getRecidivate()-primeCost.getFreight()-primeCost.getNoState());
		//实际加价率
		primeCost.setMakeRate(NumUtils.division(primeCost.getSurplus()/primeCost.getOnePrimeCost()));
		//目前综合税负加所得税负比
		primeCost.setTaxesRate(NumUtils.division((primeCost.getState()-primeCost.getExpectState())/(primeCost.getSale()/1.17)));
		//预算成本
		primeCost.setBudget(primeCost.getOnePrimeCost()-primeCost.getFreight());
		//预算成本加价率
		primeCost.setBudgetRate(NumUtils.division(primeCost.getSurplus()/primeCost.getBudget()));
		//实战成本
		primeCost.setActualCombat(primeCost.getOneaAtualPrimeCost()-primeCost.getFreight());
		//实战成本加价率
		primeCost.setActualCombatRate(NumUtils.division(primeCost.getSurplus()/primeCost.getActualCombat()));
		product.setPrimeCost(primeCost);
		productDao.save(product);
		HttpSession session = request.getSession();
		session.setAttribute("productId", product.getId());
		session.setAttribute("number", primeCost.getNumber());
		session.setAttribute("productName", product.getName());
		return primeCost;
	}


}
