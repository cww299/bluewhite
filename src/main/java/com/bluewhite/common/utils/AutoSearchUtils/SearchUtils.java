package com.bluewhite.common.utils.AutoSearchUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.bluewhite.common.utils.StringUtil;

import cn.hutool.core.util.ReflectUtil;

/**
 *  field_oper_type : tableColumnName_SqlOperator_convertDataType
 *	字段名_sql操作_转换类型
 * @author zhangliang
 *
 */
public class SearchUtils {

	 public static CriteriaQuery<?> autoBuildQuery(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, Map<String,Object> model) {
	        if (model == null || model.size() == 0) {
	            return null;
	        }
	        List<Predicate> predicates = new ArrayList<Predicate>();
	        List<Order> orderList = new ArrayList<Order>();
	        List<Expression<?>> groupList = new ArrayList<Expression<?>>();
	        for (Map.Entry<String, Object> entry : model.entrySet()) {
	            String key = entry.getKey();
	            String[] keys = key.split("_");
	            Object value = entry.getValue();
	            // value is null
	            boolean valueIsNull = null == value || (value instanceof String && StringUtils.isBlank((String) value))
	                    || (value instanceof Collection && CollectionUtils.isEmpty((Collection<?>) value));
	            // field_oper_type : tableColumnName_SqlOperator_convertDataType
	            //字段名_sql操作_转换类型
	            //字段名可能是多表查询 table.field
	            String field = keys[0];
	            String oper = keys.length > 1 ? keys[1] : "";
	            String type = keys.length > 2 ? keys[2] : "";
	            // 验证字段以防止SQL注入。
	            if (validateFieldKey(root.getJavaType(), field) == false) {
	            	System.out.println("查询字段[" + field + "]不存在");
	                continue;
	            }
	            //多表查询
	            Path<String> path = simpleExpression(field, root);
	            if (keys.length == 1) {
	                if (valueIsNull == false) {
	                    predicates.add(cb.equal(path, convertQueryParamsType(type, value)));
	                }
	                continue;
	            }
	            if (keys.length > 1) {
	                if (valueIsNull == false) {
	                    if (SearchType.EQ.equals(oper)) {
	                        predicates.add(cb.equal(path, convertQueryParamsType(type, value)));
	                        continue;
	                    }
	                    if (SearchType.NE.equals(oper)) {
	                        predicates.add(cb.notEqual(path, convertQueryParamsType(type, value)));
	                        continue;
	                    }
	                    if (SearchType.LT.equals(oper)) {
	                        predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
	                        continue;
	                    }
	                    if (SearchType.LE.equals(oper)) {
	                        predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
	                        continue;
	                    }
	                    if (SearchType.GT.equals(oper)) {
	                        predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
	                        continue;
	                    }
	                    if (SearchType.GE.equals(oper)) {
	                        predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
	                        continue;
	                    }
	                    if (SearchType.LIKE.equals(oper)) {
	                        StringBuilder stringBuilder = new StringBuilder();
	                        stringBuilder.append("%" +  StringUtil.specialStrKeyword(escapeSQLLike(convertQueryParamsType(type, value))) + "%");
	                        predicates.add(cb.like(path, stringBuilder.toString()));
	                        continue;
	                    }
	                    if (SearchType.NOTLIKE.equals(oper)) {
	                        StringBuilder stringBuilder = new StringBuilder();
	                        stringBuilder.append("%" +  StringUtil.specialStrKeyword(escapeSQLLike(convertQueryParamsType(type, value))) + "%");
	                        predicates.add(cb.notLike(path, stringBuilder.toString()));
	                        continue;
	                    }
	                    if (SearchType.IN.equals(oper)) {
	                        predicates.add(path.in(convertQueryParamsType(type, value)));
	                        continue;
	                    }
	                    //介于
	                    if (SearchType.BE.equals(oper)) {
	                    	 String[] values = ((StringUtils) value).split("_");
//	                    	 predicates.add(cb.between(path,convertQueryParamsType(type, values[0]),convertQueryParamsType(type,  values[1])));
	                        continue;
	                    }
	                    if (SearchType.NOTIN.equals(oper)) {
	                        predicates.add(path.in(convertQueryParamsType(type, value)).not());
	                        continue;
	                    }
	                    if (SearchType.ORDERBY.equals(oper)) {
	                        if (StringUtils.equals(value.toString(), "desc")) {
	                            orderList.add(cb.desc(path));
	                        } else {
	                            orderList.add(cb.asc(path));
	                        }
	                        continue;
	                    }
	                } else {
	                    if (SearchType.IS.equals(oper)) {
	                        predicates.add(cb.isNull(path));
	                        continue;
	                    }
	                    if (SearchType.ISNOT.equals(oper)) {
	                        predicates.add(cb.isNotNull(path));
	                        continue;
	                    }
	                    // ORDERBY's value may be null
	                    if (SearchType.ORDERBY.equals(oper)) {
	                        orderList.add(cb.asc(path));
	                        continue;
	                    }
	                    // GROUPBY's value must be null.
	                    if (SearchType.GROUPBY.equals(oper)) {
	                        groupList.add(path);
	                        continue;
	                    }
	                }
	            }
	        }
	        
	    	Predicate[] pre = new Predicate[predicates.size()];
			query.where(predicates.toArray(pre));
	        query.orderBy(orderList);
	        query.groupBy(groupList);
	        return query;
	    }

	    /**
	     * 转换数据类型的参数
	     * @param type
	     * @param value
	     * @return
	     */
	    private static Object convertQueryParamsType(String type, Object value) {
	        if (StringUtils.isBlank(type)) {
	            return value.toString();
	        }
	        if (SearchType.TOINT.equals(type)) {
	            if (value instanceof Collection) {
	                if (CollectionUtils.isNotEmpty((Collection<?>) value)) {
	                    value = CollectionUtils.collect((Collection<?>) value, new Transformer() {
	                        @Override
	                        public Integer transform(Object input) {
	                            return Integer.parseInt((String) input);
	                        }
	                    });
	                    return value;
	                }
	            } else if (!(value instanceof Integer)) {
	                return Integer.parseInt((String) value);
	            }
	        } else if (SearchType.TODATE.equals(type)) {
	            if (value instanceof Collection) {
	                if (CollectionUtils.isNotEmpty((Collection<?>) value)) {
	                    value = CollectionUtils.collect((Collection<?>) value, new Transformer() {
	                        @Override
	                        public Date transform(Object input) {
	                            try {
	                                return DateUtils.parseDate(input.toString());
	                            } catch (ParseException e) {
	                                e.printStackTrace();
	                            }
	                            return null;
	                        }

	                    });
	                    return value;
	                }
	            } else if (!(value instanceof Date)) {
	                try {
	                    return DateUtils.parseDate(value.toString());
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                }
	                return null;
	            }
	        }
	        return value.toString();
	    }

	    /**
	     * 转换BuildQuery数据类型的参数，并组装查询。
	     * field_oper_type : 类型为null无需转换
	     * @param oper
	     * @param cb
	     * @param path
	     * @param type
	     * @param value
	     * @return Predicate
	     */
	    private static Predicate convertParamsTypeAndBuildQuery(String oper, CriteriaBuilder cb, Path<String> path, String type, Object value) {
	        
	        if (StringUtils.isBlank(type)) {
	            String valueStr = value.toString();
	            if (SearchType.LT.equals(oper)) {
	                return cb.lessThan(path, valueStr);
	            }
	            if (SearchType.LE.equals(oper)) {
	                return cb.lessThanOrEqualTo(path, valueStr);
	            }
	            if (SearchType.GT.equals(oper)) {
	                return cb.greaterThan(path, valueStr);
	            }
	            if (SearchType.GE.equals(oper)) {
	                return cb.greaterThanOrEqualTo(path, valueStr);
	            }
	        }
	        if (SearchType.TOINT.equals(type)) {
	            boolean isTOINT = false;
	            if (value instanceof Collection) {
	                if (CollectionUtils.isNotEmpty((Collection<?>) value)) {
	                    value = CollectionUtils.collect((Collection<?>) value, new Transformer() {
	                        @Override
	                        public Integer transform(Object input) {
	                            return Integer.parseInt((String) input);
	                        }
	                    });
	                }
	                isTOINT = true;
	            }
	            if (!(value instanceof Integer)) {
	                isTOINT = true;
	            }
	            if (isTOINT) {
	                int valueInt = Integer.parseInt((String) value);
	                if (SearchType.LT.equals(oper)) {
	                    return cb.lessThan(path.as(Integer.class), valueInt);
	                }
	                if (SearchType.LE.equals(oper)) {
	                    return cb.lessThanOrEqualTo(path.as(Integer.class), valueInt);
	                }
	                if (SearchType.GT.equals(oper)) {
	                    return cb.greaterThan(path.as(Integer.class), valueInt);
	                }
	                if (SearchType.GE.equals(oper)) {
	                    return cb.greaterThanOrEqualTo(path.as(Integer.class), valueInt);
	                }
	            }
	        }
	        if (SearchType.TODATE.equals(type)) {
	            boolean isTODATE = false;
	            if (value instanceof Collection) {
	                if (CollectionUtils.isNotEmpty((Collection<?>) value)) {
	                    value = CollectionUtils.collect((Collection<?>) value, new Transformer() {
	                        @Override
	                        public Date transform(Object input) {
	                            try {
	                                return DateUtils.parseDate(input.toString());
	                            } catch (ParseException e) {
	                                e.printStackTrace();
	                            }
	                            return null;
	                        }
	                    });
	                    isTODATE = true;
	                }
	            }
	            if (!(value instanceof Date)) {
	                isTODATE = true;
	            }
	            if (isTODATE) {
	                Date valueDate = null;
	                try {
	                    valueDate = DateUtils.parseDate(value.toString());
	                } catch (ParseException e) {
	                    e.printStackTrace();
	                }
	                if (SearchType.LT.equals(oper)) {
	                    return cb.lessThan(path.as(Date.class), valueDate);
	                }
	                if (SearchType.LE.equals(oper)) {
	                    return cb.lessThanOrEqualTo(path.as(Date.class), valueDate);
	                }
	                if (SearchType.GT.equals(oper)) {
	                    return cb.greaterThan(path.as(Date.class), valueDate);
	                }
	                if (SearchType.GE.equals(oper)) {
	                    return cb.greaterThanOrEqualTo(path.as(Date.class), valueDate);
	                }
	            }
	        }
	        return null;
	    }

	    
	    /**
	     * @param entityClassMap
	     * @param fieldName
	     * @return
	     */
	    private static boolean validateFieldKey(String fieldName) {
	        if (fieldName.contains(".")) {
	            fieldName = fieldName.split("\\.")[0];
	        }
	        return  (Boolean) null;
	    }



	    /**
	     * 验证字段名合法是否合法
	     * @param entityClass
	     * @param fieldName
	     * @return
	     */
	    private static boolean validateFieldKey(Class<?> entityClass, String fieldName) {
	    	String getterMethodName = "get" + StringUtils.capitalize(fieldName);
	    	Method method = ReflectUtil.getMethod(entityClass, "getId");
	    	return method != null;
	    }

	    /**
	     * 防SQL注入
	     * @param likeStr
	     * @return
	     */
	    private static String escapeSQLLike(Object likeStr) {
	        String str = likeStr.toString();
	        str = StringUtils.replace(str, "_", "/_");
	        str = StringUtils.replace(str, "%", "/%");
	        str = StringUtils.replace(str, "/", "//");
	        return str;
	    }

	    /**
	     * 多表查询
	     * @param fieldName
	     * @param root
	     * @return
	     */
	    private static Path simpleExpression(String fieldName,Root<?> root) {
	    	Path expression;
			// 此处是表关联数据，注意仅限一层关联，如user.address，
			// 查询user的address集合中，address的name为某个值
			if (fieldName.contains(".")) {
				String[] names = StringUtils.split(fieldName, ".");
				// 获取该属性的类型，Set？List？Map？
				expression = root.get(names[0]);
				Class clazz = expression.getJavaType();
				if (clazz.equals(Set.class)) {
					SetJoin setJoin = root.joinSet(names[0]);
					expression = setJoin.get(names[1]);
				} else if (clazz.equals(List.class)) {
					ListJoin listJoin = root.joinList(names[0]);
					expression = listJoin.get(names[1]);
				} else if (clazz.equals(Map.class)) {
					MapJoin mapJoin = root.joinMap(names[0]);
					expression = mapJoin.get(names[1]);
				} else {
					// 是many to one时
					expression = expression.get(names[1]);
				}

			} else {
				// 单表查询
				expression = root.get(fieldName);
			}
			return expression;
		}

}
