package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 客户物流费用基础数据
 * 
 * @author ZhangLiang
 * @date 2020/03/18
 */
@Entity
@Table(name = "ledger_logistics_costs")
public class LogisticsCosts extends BaseEntity<Long>{

    /**
     * 客户id
     * 
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 客户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Customer customer;

    /**
     * 物流点id
     */
    @Column(name = "logistics_id")
    private Long logisticsId;

    /**
     * 物流点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistics_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BaseData logistics;

    /**
     * 外包装方式id
     */
    @Column(name = "outerPackaging_id")
    private Long outerPackagingId;

    /**
     * 外包装方式
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outerPackaging_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BaseData outerPackaging;

    /**
     * 含税价格
     */
    @Column(name = "tax_included")
    private Double taxIncluded;

    /**
     * 不含税价格
     */
    @Column(name = "excluding_tax")
    private Double excludingTax;
    
    /**结算类型
     * 1.按包装 2.按车
     */
    @Column(name = "settlement_type")
    private Integer settlement_type;
    
    /**结算方式
     * 1.单月结 2. 双月结
     */
    @Column(name = "settlement")
    private Integer settlement;
    
    /**付款方式
     * 1.到付 2. 垫付
     */
    @Column(name = "payment")
    private Integer payment;
    
    /**
     * 是否含税
     */
    @Transient
    private Integer tax;

    
    

    public Integer getSettlement_type() {
        return settlement_type;
    }

    public void setSettlement_type(Integer settlement_type) {
        this.settlement_type = settlement_type;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getSettlement() {
        return settlement;
    }

    public void setSettlement(Integer settlement) {
        this.settlement = settlement;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public BaseData getLogistics() {
        return logistics;
    }

    public void setLogistics(BaseData logistics) {
        this.logistics = logistics;
    }

    public Long getOuterPackagingId() {
        return outerPackagingId;
    }

    public void setOuterPackagingId(Long outerPackagingId) {
        this.outerPackagingId = outerPackagingId;
    }

    public BaseData getOuterPackaging() {
        return outerPackaging;
    }

    public void setOuterPackaging(BaseData outerPackaging) {
        this.outerPackaging = outerPackaging;
    }

    public Double getTaxIncluded() {
        return taxIncluded;
    }

    public void setTaxIncluded(Double taxIncluded) {
        this.taxIncluded = taxIncluded;
    }

    public Double getExcludingTax() {
        return excludingTax;
    }

    public void setExcludingTax(Double excludingTax) {
        this.excludingTax = excludingTax;
    }
    
    
    

}
