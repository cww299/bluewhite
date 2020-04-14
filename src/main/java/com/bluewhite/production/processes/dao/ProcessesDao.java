package com.bluewhite.production.processes.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.processes.entity.Processes;

/**
 * @author ZhangLiang
 * @date 2020/04/08
 */
public interface ProcessesDao extends BaseRepository<Processes, Long> {

    /**
     * 根据包装方式
     * @param id
     * @return
     */
    List<Processes> findByPackagMethodId(Long id);
    
    /**
     * 是否公共工序
     * @return
     */
    List<Processes> findByPublicType(Integer publicType);
    
    /**
     * 根据名字和公共工序
     * @return
     */
    Processes findByPublicTypeAndNameAndSumCount(Integer publicType,String name,int sumCount);
    
    /**
     * 是否手填工序
     * @return
     */
    List<Processes> findByIsWrite(Integer isWrite);

}
