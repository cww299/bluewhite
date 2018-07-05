package com.bluewhite.product.primecostbasedata.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.excel.Poi;
/**
 * 基础数据1(成本计算)
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_one")
public class BaseOne extends BaseEntity<Long>{
	
	
    /**
     * 基础数据名称（裁片常用词填写，裁剪方式，压货环节，针工固定工序）
     */
	@Column(name = "name")
	@Poi(name = "", column = "A")
    private String name;
	
    /**
     * 基础数据类型
     */
	@Column(name = "type")
    private String type;
	
	
	/**
	 * 名称和时间关联，进行联查
	 */
	@OneToMany(mappedBy = "baseOne",cascade = CascadeType.REMOVE, orphanRemoval = true)
	private Set<BaseOneTime> baseOneTimes = new HashSet<BaseOneTime>();


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Set<BaseOneTime> getBaseOneTimes() {
		return baseOneTimes;
	}


	public void setBaseOneTimes(Set<BaseOneTime> baseOneTimes) {
		this.baseOneTimes = baseOneTimes;
	}






	
	
	
	
	
	

}
