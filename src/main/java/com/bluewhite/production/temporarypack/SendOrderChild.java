 package com.bluewhite.production.temporarypack;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;

/**发货单子单，用于记录发货单具体产品数据
 * @author ZhangLiang
 * @date 2020/03/18
 */
 @Entity
 @Table(name = "pro_send_order_child")
public class SendOrderChild extends BaseEntity<Long>{
     
     /**
      * 产品id
      */
     @Column(name = "product_id")
     private Long productId;
     /**
      * 产品名称
      */
     @Column(name = "product_name")
     private String productName;
     
     /**
      * 批次号
      */
     @Column(name = "bacth_number")
     private String bacthNumber;
     
     /**
      * 单包个数
      */
     @Column(name = "single_number")
     private Integer singleNumber;
     
     

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBacthNumber() {
        return bacthNumber;
    }

    public void setBacthNumber(String bacthNumber) {
        this.bacthNumber = bacthNumber;
    }

    public Integer getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(Integer singleNumber) {
        this.singleNumber = singleNumber;
    }
     
     
     
     

}
