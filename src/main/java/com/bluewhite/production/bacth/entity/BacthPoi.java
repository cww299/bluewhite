package com.bluewhite.production.bacth.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;


/**
 * @author zhangliang
 * @date 2021/1/22 17:04
 */
public class BacthPoi extends BaseRowModel {

    /**
     * 批次分配时间（默认当前时间前一天）
     */
    @ExcelProperty(index = 0)
    private Date allotTime;

    /**
     * 产品名称
     */
    @ExcelProperty(index = 1)
    private String name;

    /**
     * 批次号
     */
    @ExcelProperty(index = 2)
    private String bacthNumber;

    /**
     * 数量
     *
     */
    @ExcelProperty(index = 3)
    private Integer number;

    /**
     * 备注
     */
    @ExcelProperty(index = 4)
    private String remarks;


    public Date getAllotTime() {
        return allotTime;
    }
    public void setAllotTime(Date allotTime) {
        this.allotTime = allotTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
