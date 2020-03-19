package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.ledger.entity.Customer;

/**
 * 财务发货单
 * 
 * @author ZhangLiang
 * @date 2020/03/18
 */
@Entity
@Table(name = "pro_send_order")
public class SendOrder extends BaseEntity<Long> {

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
     * 发货单编号
     */
    @Column(name = "send_order_number")
    private String sendOrderNumber;

    /**
     * 子单list
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "send_order_id")
    private List<SendOrderChild> sendOrderChild = new ArrayList<>();

    /**
     * 发货时间（多包发货时取最后发货时间）
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 总包数
     */
    @Column(name = "sum_package_number")
    private Integer sumPackageNumber;

    /**
     * 总数量
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 已发货包数
     */
    @Column(name = "send_package_number")
    private Integer sendPackageNumber;

    /**
     * 已发货数量
     */
    @Column(name = "send_number")
    private Integer sendNumber;

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
     * 物流编号(货运单号)
     */
    @Column(name = "logistics_number")
    private String logisticsNumber;

    /**
     * 是否含税
     */
    @Column(name = "tax")
    private Integer tax;

    /**
     * 实际单包费用
     */
    @Column(name = "singer_price")
    private Double singerPrice;
    
    /**
     * 已发货费用(包数*单价)
     */
    @Column(name = "send_price")
    private Double sendPrice;
    
    /**
     * 额外费用
     */
    @Column(name = "extra_price")
    private Double extraPrice;
    
    /**
     * 物流总费用（已发货费用+额外费用）
     */
    @Column(name = "logistics_price")
    private Double logisticsPrice;
    

    
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

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Double getSingerPrice() {
        return singerPrice;
    }

    public void setSingerPrice(Double singerPrice) {
        this.singerPrice = singerPrice;
    }

    public Double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public List<SendOrderChild> getSendOrderChild() {
        return sendOrderChild;
    }

    public void setSendOrderChild(List<SendOrderChild> sendOrderChild) {
        this.sendOrderChild = sendOrderChild;
    }

    public Integer getSendPackageNumber() {
        return sendPackageNumber;
    }

    public void setSendPackageNumber(Integer sendPackageNumber) {
        this.sendPackageNumber = sendPackageNumber;
    }

    public Integer getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(Integer sendNumber) {
        this.sendNumber = sendNumber;
    }

    public Double getLogisticsPrice() {
        return logisticsPrice;
    }

    public void setLogisticsPrice(Double logisticsPrice) {
        this.logisticsPrice = logisticsPrice;
    }

    public Double getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(Double sendPrice) {
        this.sendPrice = sendPrice;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
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

    public String getSendOrderNumber() {
        return sendOrderNumber;
    }

    public void setSendOrderNumber(String sendOrderNumber) {
        this.sendOrderNumber = sendOrderNumber;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSumPackageNumber() {
        return sumPackageNumber;
    }

    public void setSumPackageNumber(Integer sumPackageNumber) {
        this.sumPackageNumber = sumPackageNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

}
