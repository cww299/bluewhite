package com.bluewhite.ledger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Actualprice;

@Service
public interface ActualpriceService extends BaseCRUDService<Actualprice,Long>{
	public List<Actualprice> findByProductNameLikeAndBatchNumber(String productName,String batchNumber);
	public List<Actualprice>  findPages(Actualprice customer);
	public PageResult<Actualprice>  findPages(Actualprice actualprice, PageParameter page);
	public void addActualprice(Actualprice actualprice);
}
