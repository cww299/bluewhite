package com.bluewhite.common.utils.AutoSearchUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

import org.apache.commons.lang3.StringUtils;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.StringUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

/**
 * field_oper_type : tableColumnName_SqlOperator_convertDataType 字段名_sql操作_转换类型
 * 
 * @author zhangliang
 *
 */
public class SearchUtils {

    public static CriteriaQuery<?> autoBuildQuery(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb,
        Map<String, Object> model) {
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
            boolean valueIsNull = ObjectUtil.isNull(value);
            // field_oper_type : tableColumnName_SqlOperator_convertDataType
            // 字段名_sql操作_转换类型
            // 字段名可能是多表查询 table.field
            String field = keys[0];
            // sql操作
            String oper = keys.length > 1 ? keys[1] : "";
            // 转换类型
            String type = keys.length > 2 ? keys[2] : "";
            // 验证字段以防止SQL注入。
            validateFieldKey(root.getJavaType(), field);
            // 多表查询
            Path<String> path = simpleExpression(field, root);
            if (keys.length == 1) {
                if (!valueIsNull) {
                    predicates.add(cb.equal(path, convertQueryParamsType(type, value)));
                }
                continue;
            }
            if (keys.length > 1) {
                if (!valueIsNull) {
                    switch (oper) {
                        case SearchType.EQ:
                            predicates.add(cb.equal(path, convertQueryParamsType(type, value)));
                            break;
                        case SearchType.NE:
                            predicates.add(cb.notEqual(path, convertQueryParamsType(type, value)));
                            break;
                        case SearchType.LT:
                            predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
                            break;
                        case SearchType.LE:
                            predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
                            break;
                        case SearchType.GT:
                            predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
                            break;
                        case SearchType.GE:
                            predicates.add(convertParamsTypeAndBuildQuery(oper, cb, path, type, value));
                            break;
                        case SearchType.LIKE:
                            predicates.add(cb.like(path,
                                "%" + StringUtil.specialStrKeyword(escapeSQLLike(convertQueryParamsType(type, value)))
                                    + "%"));
                            break;
                        case SearchType.NOTLIKE:
                            predicates.add(cb.notLike(path,
                                "%" + StringUtil.specialStrKeyword(escapeSQLLike(convertQueryParamsType(type, value)))
                                    + "%"));
                            break;
                        case SearchType.IN:
                            predicates.add(path.in(convertQueryParamsType(type, value)));
                            break;
                        case SearchType.BE:
                            // 介于操作会出现开始和结束参数
                            String[] values = String.valueOf(value).split("_");
                            predicates.add(convertParamsTypeAndBuildQuery(SearchType.GE, cb, path, type, values[0]));
                            predicates.add(convertParamsTypeAndBuildQuery(SearchType.LE, cb, path, type, values[1]));
                            break;
                        case SearchType.NOTIN:
                            predicates.add(path.in(convertQueryParamsType(type, value)).not());
                            break;
                        case SearchType.ORDERBY:
                            if (StringUtils.equals(value.toString(), "desc")) {
                                orderList.add(cb.desc(path));
                            } else {
                                orderList.add(cb.asc(path));
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (oper) {
                        case SearchType.IS:
                            predicates.add(cb.isNull(path));
                            break;
                        case SearchType.ISNOT:
                            predicates.add(cb.isNotNull(path));
                            break;
                        case SearchType.ORDERBY:
                            orderList.add(cb.asc(path));
                            break;
                        case SearchType.GROUPBY:
                            groupList.add(path);
                            break;
                        default:
                            break;
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
     * 
     * @param type
     * @param value
     * @return
     */
    private static Object convertQueryParamsType(String type, Object value) {
        Object object = null;
        switch (type) {
            case SearchType.TOINT:
                object = Integer.parseInt((String)value);
                break;
            case SearchType.TODATE:
                object = DateUtil.parseDateTime(String.valueOf(value));
                break;
            default:
                object = value.toString();
                break;
        }
        return object;
    }

    /**
     * 转换BuildQuery数据类型的参数，并组装查询。 field_oper_type : 类型为null无需转换
     * 
     * @param oper
     * @param cb
     * @param path
     * @param type
     * @param value
     * @return Predicate
     */
    private static Predicate convertParamsTypeAndBuildQuery(String oper, CriteriaBuilder cb, Path<String> path,
        String type, Object value) {
        Predicate predicate = null;
        String valueStr = value.toString();
        if (StringUtils.isBlank(type)) {
            switch (oper) {
                case SearchType.LT:
                    predicate = cb.lessThan(path, valueStr);
                    break;
                case SearchType.LE:
                    predicate = cb.lessThanOrEqualTo(path, valueStr);
                    break;
                case SearchType.GT:
                    predicate = cb.greaterThan(path, valueStr);
                    break;
                case SearchType.GE:
                    predicate = cb.greaterThanOrEqualTo(path, valueStr);
                    break;
                default:
                    break;
            }
        }
        if (SearchType.TOINT.equals(type)) {
            int valueInt = Integer.parseInt((String)value);
            switch (oper) {
                case SearchType.LT:
                    predicate = cb.lessThan(path.as(Integer.class), valueInt);
                    break;
                case SearchType.LE:
                    predicate = cb.lessThanOrEqualTo(path.as(Integer.class), valueInt);
                    break;
                case SearchType.GT:
                    predicate = cb.greaterThan(path.as(Integer.class), valueInt);
                    break;
                case SearchType.GE:
                    predicate = cb.greaterThanOrEqualTo(path.as(Integer.class), valueInt);
                    break;
                default:
                    break;
            }
        }
        if (SearchType.TODATE.equals(type)) {
            Date valueDate = DateUtil.parseDateTime(value.toString());
            switch (oper) {
                case SearchType.LT:
                    predicate = cb.lessThan(path.as(Date.class), valueDate);
                    break;
                case SearchType.LE:
                    predicate = cb.lessThanOrEqualTo(path.as(Date.class), valueDate);
                    break;
                case SearchType.GT:
                    predicate = cb.greaterThan(path.as(Date.class), valueDate);
                    break;
                case SearchType.GE:
                    predicate = cb.greaterThanOrEqualTo(path.as(Date.class), valueDate);
                    break;
                default:
                    break;
            }
        }
        return predicate;
    }

    /**
     * 验证字段名合法是否合法
     * 
     * @param entityClass
     * @param fieldName
     * @return
     */
    private static void validateFieldKey(Class<?> entityClass, String fieldName) {
        boolean check = true;
        if (fieldName.contains(".")) {
            String[] names = StringUtils.split(fieldName, ".");
            if (names.length > 1) {
                // 循环判断字段是否存在
                for (int i = 0; i < names.length; i++) {
                    // 获取字段类型
                    Field field = ReflectUtil.getField(entityClass, names[i]);
                    // 判断是否存在该字段
                    if (field == null) {
                        throw new ServiceException("查询字段[" + names[i] + "]不存在");
                    }
                    entityClass = field.getType();
                }
            }
        } else {
            check = ReflectUtil.hasField(entityClass, fieldName);
            if (!check) {
                throw new ServiceException("查询字段[" + fieldName + "]不存在");
            }
        }
    }

    /**
     * 防SQL注入
     * 
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
     * 
     * @param fieldName
     * @param root
     * @return
     */
    private static Path<String> simpleExpression(String fieldName, Root<?> root) {
        Path<String> expression = null;
        // 此处是表关联数据，注意仅限一层关联，如user.address，
        // 查询user的address集合中，address的name为某个值
        if (fieldName.contains(".")) {
            String[] names = StringUtils.split(fieldName, ".");
            expression = root.get(names[0]);
            Class<?> clazz = expression.getJavaType();
            for (int i = 1; i < names.length; i++) {
                // 获取该属性的类型，Set？List？Map？
                if (clazz.equals(Set.class)) {
                    SetJoin<?, ?> setJoin = root.joinSet(names[0]);
                    expression = setJoin.get(names[i]);
                } else if (clazz.equals(List.class)) {
                    ListJoin<?, ?> listJoin = root.joinList(names[0]);
                    expression = listJoin.get(names[i]);
                } else if (clazz.equals(Map.class)) {
                    MapJoin<?, ?, ?> mapJoin = root.joinMap(names[0]);
                    expression = mapJoin.get(names[i]);
                } else {
                    // manyToOne
                    expression = expression.get(names[i]);
                }
            }
        } else {
            // 单表查询
            expression = root.get(fieldName);
        }
        return expression;
    }

    /**
     * or 查询
     * 
     * @param root
     * @param query
     * @param cb
     * @param oper
     * @return
     */
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder cb, String oper) {
        List<Predicate> predicates = new ArrayList<>();
        switch (oper) {
            case SearchType.OR:
                return cb.or(predicates.toArray(new Predicate[predicates.size()]));
            default:
                return null;
        }
    }

}
