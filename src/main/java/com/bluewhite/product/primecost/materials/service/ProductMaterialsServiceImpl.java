package com.bluewhite.product.primecost.materials.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.primecost.materials.dao.ProductMaterialsDao;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.entity.poi.ProductMaterialsPoi;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public class ProductMaterialsServiceImpl extends BaseServiceImpl<ProductMaterials, Long>
    implements ProductMaterialsService {

    @Autowired
    private ProductMaterialsDao dao;
    @Autowired
    private MaterielDao materielDao;
    @Autowired
	private BaseOneDao baseOneDao;

    @Override
    @Transactional
    public ProductMaterials saveProductMaterials(ProductMaterials productMaterials) {
        NumUtils.setzro(productMaterials);
        countComposite(productMaterials);
        Materiel materiel = materielDao.findOne(productMaterials.getMaterielId());
        productMaterials
        .setBatchMaterialPrice(NumUtils.mul(productMaterials.getBatchMaterial(),NumUtils.setzro(materiel.getPrice())));
        /*
        if (productMaterials.getConvertUnit() == 0) {
        } else {
            productMaterials
                .setBatchMaterialPrice(NumUtils.mul(productMaterials.getBatchMaterial(), materiel.getConvertPrice()));
        }
        */
        return dao.save(productMaterials);
    }

    @Override
    public ProductMaterials countComposite(ProductMaterials productMaterials) {
        productMaterials.setBatchMaterial(NumUtils.mul(
        		NumUtils.sum(productMaterials.getManualLoss(), productMaterials.getOneMaterial()), 
        		(double)productMaterials.getNumber()));
        return productMaterials;
    }

    @Override
    public PageResult<ProductMaterials> findPages(ProductMaterials param, PageParameter page) {
        Page<ProductMaterials> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按产品id过滤
            if (param.getProductId() != null) {
                predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
            }
            // 按压货环节id
            if (param.getOverstockId() != null) {
                predicate.add(cb.equal(root.get("overstockId").as(Long.class), param.getOverstockId()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<ProductMaterials> result = new PageResult<ProductMaterials>(pages, page);
        return result;
    }

    @Override
    @Transactional
    public int deleteProductMaterials(String ids) {
        return delete(ids);
    }

    @Override
    public List<ProductMaterials> findByProductIdAndOverstockId(Long productId, Long id) {
        return dao.findByProductIdAndOverstockId(productId, id);
    }

    @Override
    public List<ProductMaterials> findByProductId(Long productId) {
        return dao.findByProductId(productId);
    }

	@Override
	@Transactional
	public int uploadProductMateruals(ExcelListener excelListener, Long productId) {
		int count = 0;
        // 获取导入的裁片
        List<Object> excelListenerList = excelListener.getData();
        List<BaseOne> unitList = baseOneDao.findByType("unit");
        for (Object object : excelListenerList) {
        	ProductMaterialsPoi poi = (ProductMaterialsPoi) object;
        	if( poi.getMaterialNumber() == null || poi.getMaterialNumber().isEmpty() ||
			    poi.getUnitName() == null || poi.getUnitName().isEmpty() ||
			    poi.getOneMaterial() == null ||  poi.getManualLoss() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行存在空数据，请检查");
        	}
        	ProductMaterials productMate = new ProductMaterials();
        	// 根据面料编号查找面类，321 为面料类型id
        	Materiel mate = materielDao.findByNumber(poi.getMaterialNumber());
        	if(mate == null || mate.getId() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行找不到面料：" + poi.getMaterialNumber());
        	}
        	// 查找单位
        	unitList.forEach(unit -> {
        		if(unit.getName().equals(poi.getUnitName())) {
        			// 单位id
        			productMate.setUnitId(unit.getId());
        		}
        	});
        	if(productMate.getUnitId() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行找不到单位：" + poi.getUnitName());
        	}
        	// 产品id
        	productMate.setProductId(productId);
        	// 物料id
        	productMate.setMaterielId(mate.getId());
        	productMate.setOneMaterial(poi.getOneMaterial());
        	productMate.setManualLoss(poi.getManualLoss());
        	productMate.setOverstockId(poi.getOverstockId());
        	saveProductMaterials(productMate);
            count++;
        }
        return count;
	}

}
