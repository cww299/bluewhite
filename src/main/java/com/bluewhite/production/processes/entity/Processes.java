package com.bluewhite.production.processes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

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
