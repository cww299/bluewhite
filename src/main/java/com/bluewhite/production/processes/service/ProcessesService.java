 package com.bluewhite.production.processes.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.processes.entity.Processes;

/**
 * @author ZhangLiang
 * @date 2020/04/08
 */
@Service
public interface ProcessesService extends BaseCRUDService<Processes,Long>{
    
    /**
     * 分页查看发货单
     * 
     * @param params
     * @param page
     * @return
     */
    public PageResult<Processes> findPages(Map<String, Object> params, PageParameter page);
    
    /**
     * 根据包装方式和工序数量组装出需要的工序和时间，任务总数量
     * 返回公共工序，包装方式工序
     * @param id
     * @return
     */
    List<Processes> findByPackagMethodId(Long id,Integer count,Integer taskNumber,Long quantitativeId);

    /**新增工序
     * @param processes
     */
    public void saveProcesses(Processes processes);
    

}
