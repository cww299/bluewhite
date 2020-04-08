package com.bluewhite.production.bacth.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.User;

/**
 * 产品批次实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_bacth", indexes = {@Index(name = "bacth_index_1", columnList = "type"),
    @Index(name = "bacth_index_1", columnList = "allot_time")})
public class Bacth extends BaseEntity<Long> {

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
     * 批次号
     */
    @Column(name = "bacth_number")
    private String bacthNumber;

    /** 
     * 该批次产品数量
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 状态，是否完成（0=未完成，1=完成，完成时，会转入包装列表中）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 是否是返工标识符（0=不是，1=是）
     */
    @Column(name = "flag")
    private Integer flag;

    /**
     * 下货完成时间
     */
    @Column(name = "status_time")
    private Date statusTime;

    /**
     * 包装 是否接收（0=未接收，1=已接收）
     */
    @Column(name = "receive")
    private Integer receive;

    /**
     * 任务
     */
    @OneToMany(mappedBy = "bacth", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<Task>();

    /**
     * 工序所属部门类型 (1= 质检，2 = 包装，3 = 针工，4 = 机工，5 = 裁剪)
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 批次外发价格
     */
    @Column(name = "bacth_hair_price")
    private Double bacthHairPrice;

    /**
     * 外发总价
     */
    @Column(name = "sum_out_price")
    private Double sumOutPrice;
    
    /**
     * 批次部门预计生产价格
     */
    @Column(name = "bacth_department_price")
    private Double bacthDepartmentPrice;

    /**
     * 批次针工价格
     */
    @Column(name = "bacth_deedle_price")
    private Double bacthDeedlePrice;

    /**
     * 地区差价
     */
    @Column(name = "regional_price")
    private Double regionalPrice;

    /**
     * 总返工任务价值(实际返工成本费用总和)
     */
    @Column(name = "sum_rework_price")
    private Double sumReworkPrice;

    /**
     * 八号仓库特殊业务，同一种产品会有会有激光和冲床两种类型工序，同时会产生不同的外发单价（0=激光，1=冲床）
     */
    @Column(name = "sign")
    private Integer sign;

    /**
     * 总任务价值(实际成本费用总和)
     */
    @Column(name = "sum_task_price")
    private Double sumTaskPrice;

    /**
     * 批次分配时间（默认当前时间前一天）
     */
    @Column(name = "allot_time")
    private Date allotTime;

    /**
     * 当批用时/分
     */
    @Column(name = "time")
    private Double time;

    /**
     * 任务分配人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 任务分配人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
    
    
    /**
     * 产品名称
     */
    @Transient
    private String name;

    /**
     * 产品编号
     */
    @Transient
    private String productNumber;

    /**
     * 查询字段
     */
    @Transient
    private Date orderTimeBegin;
    /**
     * 查询字段
     */
    @Transient
    private Date orderTimeEnd;

    /**
     * 产值
     */
    @Transient
    private Double productPrice;

    /**
     * 不同部门外发价格
     */
    @Transient
    private Double hairPrice;
    
    
    public Double getSumOutPrice() {
        return sumOutPrice;
    }

    public void setSumOutPrice(Double sumOutPrice) {
        this.sumOutPrice = sumOutPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getBacthDeedlePrice() {
        return bacthDeedlePrice;
    }

    public void setBacthDeedlePrice(Double bacthDeedlePrice) {
        this.bacthDeedlePrice = bacthDeedlePrice;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public Double getProductPrice() {
        return (number == null ? 0.0 : number) * (hairPrice == null ? 0.0 : hairPrice);
    }

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    public Double getHairPrice() {
        return hairPrice;
    }

    public void setHairPrice(Double hairPrice) {
        this.hairPrice = hairPrice;
    }

    public Double getSumReworkPrice() {
        return sumReworkPrice;
    }

    public void setSumReworkPrice(Double sumReworkPrice) {
        this.sumReworkPrice = sumReworkPrice;
    }

    public Date getAllotTime() {
        return allotTime;
    }

    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }

    public Double getSumTaskPrice() {
        return sumTaskPrice;
    }

    public void setSumTaskPrice(Double sumTaskPrice) {
        this.sumTaskPrice = sumTaskPrice;
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

    public String getBacthNumber() {
        return bacthNumber;
    }

    public void setBacthNumber(String bacthNumber) {
        this.bacthNumber = bacthNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getBacthHairPrice() {
        return bacthHairPrice;
    }

    public void setBacthHairPrice(Double bacthHairPrice) {
        this.bacthHairPrice = bacthHairPrice;
    }

    public Double getBacthDepartmentPrice() {
        return bacthDepartmentPrice;
    }

    public void setBacthDepartmentPrice(Double bacthDepartmentPrice) {
        this.bacthDepartmentPrice = bacthDepartmentPrice;
    }

    public Double getRegionalPrice() {
        return regionalPrice;
    }

    public void setRegionalPrice(Double regionalPrice) {
        this.regionalPrice = regionalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Date getOrderTimeBegin() {
        return orderTimeBegin;
    }

    public void setOrderTimeBegin(Date orderTimeBegin) {
        this.orderTimeBegin = orderTimeBegin;
    }

    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

}
