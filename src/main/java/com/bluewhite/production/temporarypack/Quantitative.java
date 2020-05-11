
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
     * 上车编号（上车时间+序号+包数）
     */
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    
	/**
	 * 量化包装时间
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
     * 单个包装物，包装产品数量
     */
    @Column(name = "product_count")
    private Integer productCount;
    
    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;
    
    /**
     * 批次外发单价
     */
    @Column(name = "out_price")
    private Double outPrice;

    /**
     * 批次部门预计生产单价
     */
    @Column(name = "department_price")
    private Double departmentPrice;

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
     * 当批用时(分钟)
     */
    @Column(name = "sum_time")
    private Double sumTime;

    /**
     * 状态，是否完成（0=未完成，1=完成）
     */
    @Column(name = "status")
    private Integer status;
    
    /**
     * 完成时间
     */
    @Column(name = "status_time")
    private Date statusTime;
	
    /**
     * 任务
     */
    @OneToMany(mappedBy = "quantitative", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<Task>();
    
    /**
     * 仓库种类id
     */
    @Column(name = "warehouse_type_id")
    private Long warehouseTypeId;

    /**
     * 仓库种类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BaseData warehouseType;
    
    
    /**以量化任务长时间未发货，自动入库
     * 是否入库
     */
    @Column(name = "warehousing")
    private Integer warehousing;
    
    /**
     * 库区
     */
    @Column(name = "reservoir_area")
    private String reservoirArea;
    
    /**
     * 库位
     */
    @Column(name = "location")
    private String location;
    
    /**
     * 入库时间
     */
    @Column(name = "warehousing_time")
    private Integer warehousingTime;
    
    
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
     * 物流点id
     */
	@Transient
    private Long logisticsId;
	
	
	
	
	public Integer getWarehousing() {
        return warehousing;
    }

    public void setWarehousing(Integer warehousing) {
        this.warehousing = warehousing;
    }

    public String getReservoirArea() {
        return reservoirArea;
    }

    public void setReservoirArea(String reservoirArea) {
        this.reservoirArea = reservoirArea;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public Long getWarehouseTypeId() {
        return warehouseTypeId;
    }

    public void setWarehouseTypeId(Long warehouseTypeId) {
        this.warehouseTypeId = warehouseTypeId;
    }

    public BaseData getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(BaseData warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Double outPrice) {
        this.outPrice = outPrice;
    }

    public Double getDepartmentPrice() {
        return departmentPrice;
    }

    public void setDepartmentPrice(Double departmentPrice) {
        this.departmentPrice = departmentPrice;
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
    public Double getSumTime() {
        return sumTime;
    }

    public void setSumTime(Double sumTime) {
        this.sumTime = sumTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
