package com.bluewhite.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    /**
     * 保存单个实体
     *
     * @param t 实体
     * @return 返回保存的实体
     */
    public T save(T t) {
        return baseRepository.save(t);
    }
//
//    public M saveAndFlush(M m) {
//        m = save(m);
//        baseRepository.flush();
//        return m;
//    }

    /**
     * 更新单个实体
     *
     * @param t 实体
     * @return 返回更新的实体
     */
    public T update(T t) {
        return baseRepository.save(t);
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
     * 删除实体
     *
     * @param t 实体
     */
    public void delete(T t) {
        baseRepository.delete(t);
    }

//    /**
//     * 根据主键删除相应实体
//     *
//     * @param ids 实体
//     */
//    public void delete(ID[] ids) {
//        baseRepository.delete(ids);
//    }


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

//    /**
//     * 按条件分页并排序查询实体
//     *
//     * @param searchable 条件
//     * @return
//     */
//    public Page<T> findAll(Searchable searchable) {
//        return baseRepository.findAll(searchable);
//    }
//
//    /**
//     * 按条件不分页不排序查询实体
//     *
//     * @param searchable 条件
//     * @return
//     */
//    public List<T> findAllWithNoPageNoSort(Searchable searchable) {
//        searchable.removePageable();
//        searchable.removeSort();
//        return Lists.newArrayList(baseRepository.findAll(searchable).getContent());
//    }

//    /**
//     * 按条件排序查询实体(不分页)
//     *
//     * @param searchable 条件
//     * @return
//     */
//    public List<T> findAllWithSort(Searchable searchable) {
//        searchable.removePageable();
//        return Lists.newArrayList(baseRepository.findAll(searchable).getContent());
//    }
//
//
//    /**
//     * 按条件分页并排序统计实体数量
//     *
//     * @param searchable 条件
//     * @return
//     */
//    public Long count(Searchable searchable) {
//        return baseRepository.count(searchable);
//    }


}
