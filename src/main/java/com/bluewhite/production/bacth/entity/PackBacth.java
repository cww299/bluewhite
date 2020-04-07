package com.bluewhite.production.bacth.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.User;

/**包装量化
 * @author ZhangLiang
 * @date 2020/01/14
 */
//@Entity
//@Table(name = "pro_pack_bacth")
public class PackBacth extends BaseEntity<Long> {

    /**
     * 量化编号
     */
    @Column(name = "quantitative_number")
    private String quantitativeNumber;
    
    /**
     * 包装方式id
     */
    @Column(name = "packag_method_id")
    private Long packagMethodId;

    /**
     * 包装方式
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packag_method_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BaseData packagMethod;

    /**
     * 发货时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 总包数
     */
    @Column(name = "sum_package_number")
    private Integer sumPackageNumber;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 任务
     */
    @OneToMany(mappedBy = "bacth", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<Task>();

    /**
     * 批次外发单价
     */
    @Column(name = "bacth_out_price")
    private Double bacthOutPrice;

    /**
     * 批次部门预计生产单价
     */
    @Column(name = "bacth_department_price")
    private Double bacthDepartmentPrice;

    /**
     * 地区差价
     */
    @Column(name = "regional_price")
    private Double regionalPrice;

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
     * 当批用时(分钟)
     */
    @Column(name = "time")
    private Double time;

    /**
     * 贴包人id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 贴包人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * 任务分配人id
     */
    @Column(name = "oper_user_id")
    private Long operUserId;

    /**
     * 任务分配人
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oper_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User operUser;

    /**
     * 子单list
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "pack_bacth_id")
    private List<PackBacthChild> packBacthChilds = new ArrayList<>();

    /**
     * 包装物list
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "pack_bacth_id")
    private List<PackingMaterials> packingMaterials = new ArrayList<>();

    /**
     * 是否发货
     */
    @Column(name = "flag")
    private Integer flag;

    /**
     * 是否打印
     */
    @Column(name = "print")
    private Integer print;

    /**
     * 是否审核
     */
    @Column(name = "audit")
    private Integer audit;

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
     * 贴包子单内容
     */
    @Transient
    private String childJson;
    
    /**
     * 新增包装物json数据
     */
    @Transient
    private String packingMaterialsJson;
    

    
    public Long getPackagMethodId() {
        return packagMethodId;
    }

    public void setPackagMethodId(Long packagMethodId) {
        this.packagMethodId = packagMethodId;
    }

    public BaseData getPackagMethod() {
        return packagMethod;
    }

    public void setPackagMethod(BaseData packagMethod) {
        this.packagMethod = packagMethod;
    }

    public String getPackingMaterialsJson() {
        return packingMaterialsJson;
    }

    public void setPackingMaterialsJson(String packingMaterialsJson) {
        this.packingMaterialsJson = packingMaterialsJson;
    }

    public List<PackBacthChild> getPackBacthChilds() {
        return packBacthChilds;
    }

    public void setPackBacthChilds(List<PackBacthChild> packBacthChilds) {
        this.packBacthChilds = packBacthChilds;
    }

    public List<PackingMaterials> getPackingMaterials() {
        return packingMaterials;
    }

    public void setPackingMaterials(List<PackingMaterials> packingMaterials) {
        this.packingMaterials = packingMaterials;
    }

    public String getChildJson() {
        return childJson;
    }

    public void setChildJson(String childJson) {
        this.childJson = childJson;
    }

    public String getQuantitativeNumber() {
        return quantitativeNumber;
    }

    public void setQuantitativeNumber(String quantitativeNumber) {
        this.quantitativeNumber = quantitativeNumber;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Double getBacthDepartmentPrice() {
        return bacthDepartmentPrice;
    }

    public void setBacthDepartmentPrice(Double bacthDepartmentPrice) {
        this.bacthDepartmentPrice = bacthDepartmentPrice;
    }

    public Double getBacthOutPrice() {
        return bacthOutPrice;
    }

    public void setBacthOutPrice(Double bacthOutPrice) {
        this.bacthOutPrice = bacthOutPrice;
    }

    public Double getRegionalPrice() {
        return regionalPrice;
    }

    public void setRegionalPrice(Double regionalPrice) {
        this.regionalPrice = regionalPrice;
    }

    public Double getSumTaskPrice() {
        return sumTaskPrice;
    }

    public void setSumTaskPrice(Double sumTaskPrice) {
        this.sumTaskPrice = sumTaskPrice;
    }

    public Date getAllotTime() {
        return allotTime;
    }

    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
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

    public Long getOperUserId() {
        return operUserId;
    }

    public void setOperUserId(Long operUserId) {
        this.operUserId = operUserId;
    }

    public User getOperUser() {
        return operUser;
    }

    public void setOperUser(User operUser) {
        this.operUser = operUser;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

}
