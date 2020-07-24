package com.bluewhite.product.primecost.materials.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;

@Service
public interface ProductMaterialsService extends BaseCRUDService<ProductMaterials, Long> {

    /**
     * 物料
     * 
     * @param productMaterials
     * @return
     * @throws Exception
     */
    public ProductMaterials saveProductMaterials(ProductMaterials productMaterials);

    /**
     * 物料分页
     * 
     * @param productMaterials
     * @param page
     * @return
     */
    public PageResult<ProductMaterials> findPages(ProductMaterials productMaterials, PageParameter page);

    /**
     * 删除物料
     * 
     * @param productMaterials
     */
    public int deleteProductMaterials(String ids);

    /**
     * 按产品和压货环节
     * 
     * @param productId
     * @param id
     * @return
     */
    public List<ProductMaterials> findByProductIdAndOverstockId(Long productId, Long id);

    /**
     * 根据产品id查询
     * 
     * @param productId
     * @return
     */
    public List<ProductMaterials> findByProductId(Long productId);

    /**
     * 计算当批耗料
     */
    public ProductMaterials countComposite(ProductMaterials productMaterials);

	public int uploadProductMateruals(ExcelListener excelListener, Long productId);

}
