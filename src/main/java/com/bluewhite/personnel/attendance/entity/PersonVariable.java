package com.bluewhite.personnel.attendance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 人事变量
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_variable")
public class PersonVariable extends BaseEntity<Long>{
	
	/**
	 * 变量值1
	 */
	@Column(name = "key_value")
	private String keyValue;
	
	/**
	 * 变量值2
	 */
	@Column(name = "key_value_two")
	private String keyValueTwo;
	
	/**
	 * 变量值3
	 */
	@Column(name = "key_value_three")
	private String keyValueThree;
	
	
	/**
	 * 变量类型(0=休息日期,1=早中晚三餐价值（取消）,2=水费，3=电费，4=设定早中晚三餐对于吃饭统计而延迟的分钟数,5=公司对于食堂水电房租占比)
	 * 
	 */
	@Column(name = "type")
	private Integer type;

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getKeyValueThree() {
		return keyValueThree;
	}

	public void setKeyValueThree(String keyValueThree) {
		this.keyValueThree = keyValueThree;
	}

	public String getKeyValueTwo() {
		return keyValueTwo;
	}

	public void setKeyValueTwo(String keyValueTwo) {
		this.keyValueTwo = keyValueTwo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
	

}
