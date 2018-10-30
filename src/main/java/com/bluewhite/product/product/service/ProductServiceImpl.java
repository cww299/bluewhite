package com.bluewhite.product.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
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
		  Page<Product> pages = productDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (product.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),product.getId()));
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
	public PrimeCost getPrimeCost(PrimeCost primeCost) {
		PrimeCost oldPrimeCost = primeCostDao.findOne(primeCost.getId());
		BeanCopyUtils.copyNullProperties(oldPrimeCost,primeCost);
		primeCost.setCreatedAt(oldPrimeCost.getCreatedAt());
		
		//面料价格(含复合物料和加工费)
		List<CutParts> cutPartsList = cutPartsDao.findByProductId(primeCost.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		primeCost.setCutPartsPrice((batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice)/primeCost.getNumber());
		//单只
		primeCost.setOneCutPartsPrice(primeCost.getCutPartsPrice()/primeCost.getNumber());
		
		//除面料以外的其他物料价格
		List<ProductMaterials> productMaterialsList = productMaterialsDao.findByProductId(primeCost.getProductId());
		double batchMaterialPrice1 = productMaterialsList.stream().mapToDouble(ProductMaterials::getBatchMaterialPrice).sum();
		primeCost.setOtherCutPartsPrice(batchMaterialPrice1);
		primeCost.setOneOtherCutPartsPrice((batchMaterialPrice1)/primeCost.getNumber());
		// 裁剪
		List<Tailor> tailorList = tailorDao.findByProductId(primeCost.getProductId());
		double allCostPriceTailor = tailorList.stream().mapToDouble(Tailor::getAllCostPrice).sum();
		primeCost.setCutPrice(allCostPriceTailor);
		primeCost.setOneCutPrice((allCostPriceTailor)/primeCost.getNumber());
		// 机工
		List<Machinist> machinistList = machinistDao.findByProductId(primeCost.getProductId());
		double allCostPriceMachinist = machinistList.stream().mapToDouble(Machinist::getAllCostPrice).sum();
		primeCost.setMachinistPrice(allCostPriceMachinist);
		primeCost.setOneMachinistPrice((allCostPriceMachinist)/primeCost.getNumber());
		// 绣花
		List<Embroidery> embroideryList = embroideryDao.findByProductId(primeCost.getProductId());
		double allCostPriceEmbroidery = embroideryList.stream().mapToDouble(Embroidery::getAllCostPrice).sum();
		primeCost.setEmbroiderPrice(allCostPriceEmbroidery);
		primeCost.setOneEmbroiderPrice((allCostPriceEmbroidery)/primeCost.getNumber());
		// 针工
		List<Needlework> needleworkList = needleworkDao.findByProductId(primeCost.getProductId());
		double allCostPriceNeedlework = needleworkList.stream().mapToDouble(Needlework::getAllCostPrice).sum();
		primeCost.setNeedleworkPrice(allCostPriceNeedlework);
		primeCost.setOneNeedleworkPrice((allCostPriceNeedlework)/primeCost.getNumber());
		// 包装
		List<Pack> packList = packDao.findByProductId(primeCost.getProductId());
		double allCostPricePack = packList.stream().mapToDouble(Pack::getAllCostPrice).sum();
		primeCost.setPackPrice(allCostPricePack);
		primeCost.setOnePackPrice((allCostPricePack)/primeCost.getNumber());
		//批成本
		primeCost.setBacthPrimeCost(primeCost.getCutPartsPrice()+primeCost.getOtherCutPartsPrice()+
				primeCost.getCutPrice()+primeCost.getMachinistPrice()+primeCost.getEmbroiderPrice()
				+primeCost.getNeedleworkPrice()+primeCost.getPackPrice());
		//单只成本
		primeCost.setOnePrimeCost(primeCost.getBacthPrimeCost()/primeCost.getNumber());
		
		//预计运费价格
		primeCost.setFreightPrice(primeCost.getAdjustNumber()*primeCost.getOneFreightPrice());
		
		//付上游开票点

		
		
		
		
		
		
		return primeCost;
	}


}
