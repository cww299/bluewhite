package com.bluewhite.production.bacth.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.bacth.entity.Bacth;
@Service
public interface BacthService extends BaseCRUDService<Bacth,Long>{
	
	public PageResult<Bacth>  findPages(Bacth param,PageParameter page);

}
