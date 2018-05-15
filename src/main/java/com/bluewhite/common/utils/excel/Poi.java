package com.bluewhite.common.utils.excel;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target( { java.lang.annotation.ElementType.FIELD })  
public @interface Poi {
	
	  /** 
     * 导出到Excel中的名字. 
     */           
    public abstract String name();  

    /** 
     * 配置列的名称,对应A,B,C,D.... 
     */  
    public abstract String column();  

    /** 
     * 设置了提示信息则鼠标放上去提示 
     */  
    public abstract String prompt() default "";  

    /** 
     * 设置只能选择不能输入的列内容. 
     */  
    public abstract String[] combo() default {};  

    /** 
     * 是否导出数据
     */  
    public abstract boolean isExport() default true;  
    /**
     * 是否设置单元格式
     */
    public abstract boolean isStyle() default false;
    /**
     * 宽度
     * @return
     */
    public abstract int width() default 20;

}
