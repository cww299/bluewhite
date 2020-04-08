 package com.bluewhite.production.processes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.processes.dao.ProcessesDao;
import com.bluewhite.production.processes.entity.Processes;

/**
 * @author ZhangLiang
 * @date 2020/04/08
 */
 @Service
public class ProcessesServiceImpl extends BaseServiceImpl<Processes, Long> implements ProcessesService{
     
     @Autowired
     private ProcessesDao dao;
     
     @Override
     public PageResult<Processes> findPages(Map<String, Object> params, PageParameter page) {
         return findAll(page, params);
     }

    /* (non-Javadoc)
     * @see com.bluewhite.production.processes.service.ProcessesService#findByPackagMethodId(java.lang.Long)
     */
    @Override
    public List<Processes> findByPackagMethodId(Long id) {
         return dao.findByPackagMethodId(id);
    }
     
     


}
