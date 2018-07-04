package com.bluewhite.base;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

//	/**
//     * 根据主键删除
//     *
//     * @param ids
//     */
//    public void delete(ID[] ids);
//
//    /*
//   * (non-Javadoc)
//   * @see org.springframework.data.repository.CrudRepository#findAll()
//   */
//    List<T> findAll();
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
//     */
//    List<T> findAll(Sort sort);
//
//
//    /**
//     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
//     *
//     * @param pageable
//     * @return a page of entities
//     */
//    Page<T> findAll(Pageable pageable);
//
//    /**
//     * 根据条件查询所有
//     * 条件 + 分页 + 排序
//     *
//     * @param searchable
//     * @return
//     */
//    public Page<T> findAll(Searchable searchable);
//
//
//    /**
//     * 根据条件统计所有记录数
//     *
//     * @param searchable
//     * @return
//     */
//    public long count(Searchable searchable);
}
