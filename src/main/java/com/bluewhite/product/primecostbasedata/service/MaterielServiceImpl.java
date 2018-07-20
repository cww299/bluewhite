package com.bluewhite.product.primecostbasedata.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public class MaterielServiceImpl extends BaseServiceImpl<Materiel, Long> implements MaterielService{
	
	@Autowired
	private MaterielDao dao;

	@Override
	public List<Materiel> findPages(Materiel materiel) {
			  List<Materiel> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (materiel.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),materiel.getId()));
					}
		        	//按编号过滤
		        	if (materiel.getNumber() != null) {
						predicate.add(cb.like(root.get("number").as(String.class),"%"+materiel.getNumber()+"%"));
					}
		        	//按产品名称过滤
		        	if (materiel.getName() != null) {
						predicate.add(cb.like(root.get("name").as(String.class),"%"+materiel.getName()+"%"));
					}
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        });
		        return pages;
	}

}
