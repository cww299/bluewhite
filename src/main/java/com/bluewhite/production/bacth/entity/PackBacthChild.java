package com.bluewhite.production.bacth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.product.product.entity.Product;

/**
 * @author ZhangLiang
 * @date 2020/01/14
 */
//@Entity
//@Table(name = "pro_pack_bacth_child")
public class PackBacthChild extends BaseEntity<Long> {
    
    /**
     * 加工单id
     * 
     */
    @Column(name = "order_outsource_id")
    private Long orderOutSourceId;

    /**
     * 加工单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_outsource_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderOutSource OrderOutSource;
    
    /**
     * 批次号
     */
    @Column(name = "bacth_number")
    private String bacthNumber;
    
    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
    
    /**
     * 单包个数
     */ 
    @Column(name = "single_number")
    private Integer singleNumber;
    
    /**
     * 单包实际发货个数
     */
    @Column(name = "actual_single_number")
    private Integer actualSingleNumber;
    
    /**
     * 是否核实
     */
    @Column(name = "checks")
    private Integer checks;
    
    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;
    
    

    public String getBacthNumber() {
        return bacthNumber;
    }

    public void setBacthNumber(String bacthNumber) {
        this.bacthNumber = bacthNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getOrderOutSourceId() {
        return orderOutSourceId;
    }

    public void setOrderOutSourceId(Long orderOutSourceId) {
        this.orderOutSourceId = orderOutSourceId;
    }

    public OrderOutSource getOrderOutSource() {
        return OrderOutSource;
    }

    public void setOrderOutSource(OrderOutSource orderOutSource) {
        OrderOutSource = orderOutSource;
    }

    public Integer getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(Integer singleNumber) {
        this.singleNumber = singleNumber;
    }

    public Integer getActualSingleNumber() {
        return actualSingleNumber;
    }

    public void setActualSingleNumber(Integer actualSingleNumber) {
        this.actualSingleNumber = actualSingleNumber;
    }

    public Integer getChecks() {
        return checks;
    }

    public void setChecks(Integer checks) {
        this.checks = checks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
