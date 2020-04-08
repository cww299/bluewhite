 package com.bluewhite.production.processes.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.processes.entity.Processes;
import com.bluewhite.production.temporarypack.SendOrder;

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
     * 根据包装方式
     * @param id
     * @return
     */
    List<Processes> findByPackagMethodId(Long id);
    

}
