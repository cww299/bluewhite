package com.bluewhite.production.processes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 工序实体
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
