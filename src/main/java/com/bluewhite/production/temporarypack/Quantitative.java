
package com.bluewhite.production.temporarypack;

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
import javax.persistence.UniqueConstraint;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.User;

/**
 * 量化单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_quantitative",uniqueConstraints = {@UniqueConstraint(columnNames="quantitative_number")} )
public class Quantitative extends BaseEntity<Long> {
	
    /**
     * 发货单id
     * 
     */
    @Column(name = "send_order_id")
    private Long sendOrderId;
	
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
	 * 量化编号
	 */
	@Column(name = "quantitative_number")
	private String quantitativeNumber;
	
	/**
     * 上车编号（上车时间+序号）
     */
    @Column(name = "vehicle_number")
    private String vehicleNumber;
	
	/**
	 * 包装时间
	 */
	@Column(name = "time")
	private Date time;
	
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
	 * 包装数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 子单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "quantitative_id")
	private List<QuantitativeChild> quantitativeChilds = new ArrayList<>();
	
	/**
	 * 包装物list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "quantitative_id")
	private List<PackingMaterials> packingMaterials = new ArrayList<>();
	
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
	
	
	/****** 量化数值 *******/
	
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
    @Column(name = "sum_time")
    private Double sumTime;

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
     * 状态，是否完成（0=未完成，1=完成）
     */
    @Column(name = "status")
    private Integer status;
	
	
	/**
	 * 产品名称
	 */
	@Transient
	private String name;
	
	/**
	 * 产品名称
	 */
	@Transient
	private String productName;

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
	 * 子单
	 */
	@Transient
	private String child;
	
	/**
	 * 批次号
	 */
	@Transient
	private String bacthNumber;
	
	/**
	 * 新增包装物json数据
	 */
	@Transient
	private String packingMaterialsJson;
	
	/**
	 * 客户name
	 */
	@Transient
	private String customerName;
	
	/**
	 * ids
	 */
	@Transient
	private String ids;
	
    /**
     * 物流点id
     */
	@Transient
    private Long logisticsId;
	
	
	
	
	public Long getSendOrderId() {
        return sendOrderId;
    }

    public void setSendOrderId(Long sendOrderId) {
        this.sendOrderId = sendOrderId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getPackingMaterialsJson() {
		return packingMaterialsJson;
	}

	public void setPackingMaterialsJson(String packingMaterialsJson) {
		this.packingMaterialsJson = packingMaterialsJson;
	}

	public List<PackingMaterials> getPackingMaterials() {
		return packingMaterials;
	}

	public void setPackingMaterials(List<PackingMaterials> packingMaterials) {
		this.packingMaterials = packingMaterials;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public List<QuantitativeChild> getQuantitativeChilds() {
		return quantitativeChilds;
	}

	public void setQuantitativeChilds(List<QuantitativeChild> quantitativeChilds) {
		this.quantitativeChilds = quantitativeChilds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getQuantitativeNumber() {
		return quantitativeNumber;
	}

	public void setQuantitativeNumber(String quantitativeNumber) {
		this.quantitativeNumber = quantitativeNumber;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
