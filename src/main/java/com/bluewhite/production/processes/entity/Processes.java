package com.bluewhite.production.processes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 包装工序实体
 * 
 * @author ZhangLiang
 * @date 2020/04/03
 */
@Entity
@Table(name = "pro_processes")
public class Processes extends BaseEntity<Long> {

    /**
     * 工序名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 工序耗时
     */
    @Column(name = "time")
    private Double time;
    
    /**
     * 是否为公共属性
     */
    @Column(name = "public_type")
    private Integer publicType;
    
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

    

    public Integer getPublicType() {
        return publicType;
    }

    public void setPublicType(Integer publicType) {
        this.publicType = publicType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
