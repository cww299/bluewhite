package com.bluewhite.production.farragotask.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.farragotask.dao.FarragoTaskDao;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
@Service
public class FarragoTaskServiceImpl extends BaseServiceImpl<FarragoTask, Long> implements FarragoTaskService{

	@Autowired
	private FarragoTaskDao dao;
	
	@Override
	public PageResult<FarragoTask> findPages(FarragoTask param, PageParameter page) {
		Page<FarragoTask> pages = dao.findAll((root,query,cb) -> {
        	List<Predicate> predicate = new ArrayList<>();
        	//按id过滤
        	if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
			}
        	//按类型
        	if(!StringUtils.isEmpty(param.getType())){
        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
        	}
        	//按类型
        	if(!StringUtils.isEmpty(param.getName())){
        		predicate.add(cb.equal(root.get("name").as(String.class),"%"+ param.getName()+"%"));
        	}
            //按时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
				predicate.add(cb.between(root.get("allotTime").as(Date.class),
						param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
        }, page);
        PageResult<FarragoTask> result = new PageResult<FarragoTask>(pages,page);
        return result;
}

	@Override
	public FarragoTask addFarragoTask(FarragoTask farragoTask) {
		//杂工任务价值
		farragoTask.setPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(farragoTask.getTime(), farragoTask.getType())));
		return dao.save(farragoTask);
	}
}
