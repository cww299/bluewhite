package com.bluewhite.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.common.BeanCopyUtils;

/**
 * <p>抽象service层基类 提供一些简便方法
 * <p/>
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 */
public abstract class BaseServiceImpl<T extends AbstractEntity<ID>, ID extends Serializable> {

    protected BaseRepository<T, ID> baseRepository;
    

    @Autowired
    public void setBaseRepository(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }
    
	@PersistenceContext
	protected EntityManager entityManager;
   

    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public T save(T t) {
        return baseRepository.save(t);
        
    }
    
    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public List<T> save(List<T> t) {
        return baseRepository.save(t);
    }
    

    /**
     * 更新单个实体
     *
     * @param t 实体
     * @return 返回更新的实体
     */
    public T update(T t,T ot,String... ignoreProperties) {
    	BeanCopyUtils.copyNotEmpty(t,ot,ignoreProperties);
        return baseRepository.save(ot);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    public void delete(ID id) {
        baseRepository.delete(id);
    }
    
    
    /**
     * 批量删除
     *
     * @param id 主键
     */
    public int delete(String ids) {
    	List<T> tList = new ArrayList<>();
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.parseLong(arrIds[i]);
			T t = baseRepository.findOne((ID)id);
			tList.add(t);
		}
		baseRepository.deleteInBatch(tList);
		return tList.size();
    }

    /**
     * 删除实体
     *
     * @param t 实体
     */
    public void delete(T t) {
        baseRepository.delete(t);
    }


    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    public T findOne(ID id) {
        return baseRepository.findOne(id);
    }

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    public long count() {
        return baseRepository.count();
    }

    /**
     * 查询所有实体
     *
     * @return 返回所有实体
     */
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    /**
     * 按照顺序查询所有实体
     *
     * @param sort 排序
     * @return 返回所有实体并排序
     */
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    /**
     * 分页及排序查询实体
     *
     * @param pageable 分页及排序数据
     * @return 返回分页实体
     */
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    /**
     * 查询实体
     *
     * @return 返回分页实体
     */
    public List<T> findAll( Specification<T> t) {
        return baseRepository.findAll(t);
    }
    
    @Transactional
    public <S extends T> Iterable<S> batchSave(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
        	entityManager.persist(iterator.next());
            index++;
            if (index % 500 == 0){
            	entityManager.flush();
            	entityManager.clear();
            }
        }
        if (index % 500 != 0){
        	entityManager.flush();
        	entityManager.clear();
        }
        entityManager.close();
        return var1;
    }
    
    public <S extends T> Iterable<S> batchUpdate(Iterable<S> var1) {
        Iterator<S> iterator = var1.iterator();
        int index = 0;
        while (iterator.hasNext()){
        	entityManager.merge(iterator.next());
            index++;
            if (index % 500 == 0){
            	entityManager.flush();
            	entityManager.clear();
            }
        }
        if (index % 500 != 0){
        	entityManager.flush();
        	entityManager.clear();
        }
        return var1;
    }

}
