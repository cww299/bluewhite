package com.bluewhite.production.temporarypack;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * @author ZhangLiang
 * @date 2020/01/10
 */
@Entity
@Table(name = "pro_mantissa_liquidation")
public class MantissaLiquidation extends BaseEntity<Long>{

    /**
     * 下货单id
     */
    @Column(name = "underGoods_id")
    private Long underGoodsId;

    /**
     * 下货单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underGoods_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UnderGoods underGoods;

    /**
     * 数量
     */
    @Column(name = "number")
    private Integer number;

    /**
     * 编号
     */
    @Column(name = "mantissa_number")
    private String mantissaNumber;

    /**
     * 时间
     */
    @Column(name = "time")
    private Date time;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 类型（1.尾数找回，进行入库，2.尾数丢失，清报财务）
     */
    @Column(name = "type")
    private String type;

    public Long getUnderGoodsId() {
        return underGoodsId;
    }

    public void setUnderGoodsId(Long underGoodsId) {
        this.underGoodsId = underGoodsId;
    }

    public UnderGoods getUnderGoods() {
        return underGoods;
    }

    public void setUnderGoods(UnderGoods underGoods) {
        this.underGoods = underGoods;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMantissaNumber() {
        return mantissaNumber;
    }

    public void setMantissaNumber(String mantissaNumber) {
        this.mantissaNumber = mantissaNumber;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
