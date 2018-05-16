package com.bluewhite.system.sys.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.system.sys.entity.SysLog;

@Service
public interface SysLogService extends BaseCRUDService<SysLog, Long>{
	/*记录系统日志*/
	public void log(SysLog log);
	
	/**
	 * 行为数据接口
	 * @param time 
	 * @return
	 */
	public Map<String ,Object> findLog(SysLog log,PageParameter page);
}
