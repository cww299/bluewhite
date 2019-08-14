package com.bluewhite.common.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationUtil {
	
	public static <T, Y> Specification<T> getSpec( T t){
        return new Specification<T>() {
            @SuppressWarnings("unused")
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Field[] fields =  t.getClass().getDeclaredFields();
        		List<Predicate> predicates = new ArrayList<>();
        		for (int i = 0; i < fields.length; i++) {
        			fields[i].setAccessible(true);
        			String name = fields[i].getName();
        			predicates.add(cb.equal(root.get(name), name));
        		}
        		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
	
}
