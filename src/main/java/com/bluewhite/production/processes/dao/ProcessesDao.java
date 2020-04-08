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

}
