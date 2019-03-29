package com.bluewhite.base;

import java.util.List;

public interface BaseCRUDService<T,ID> {
	/**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public T save(T t) ;
    
    
	/**
     * 保存多个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public List<T> save( List<T> t) ;
    
    /**
     * 更新单个实体
     *
     * @param t 实体
     * @return 返回更新的实体
     */
    public T update(T t,T ot) ;
    
    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    public void delete(ID id);
    
    /**
     * 批量删除
     *
     * @param id 主键
     */
    public int delete(String ids);
    
    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findOne(ID id);
    
    /**
     * 查询所有实体
     *
     * @return 返回所有实体
     */
    public List<T> findAll();
    
    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() ;
}
