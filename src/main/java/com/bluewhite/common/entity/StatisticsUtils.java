package com.bluewhite.common.entity;

import java.util.*;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.NumUtils;


public class StatisticsUtils
{

	private static final String DISTINCT_FIELD_FLAG = "^";
	private static final String FIELD_SPLIT_CHAR = "\\.";
	private String countFields[];
	private Map countCashMap;

	private StatisticsUtils(String countFields[])
	{
		this.countFields = null;
		countCashMap = new HashMap();
		this.countFields = countFields;
	}

	public static  StatisticsUtils c(String fields[])
	{
		return new StatisticsUtils(fields);
	}

	public static String distinct(String field)
	{
		return (new StringBuilder()).append("^").append(field).toString();
	}

	public Object[] count(List data)
	{
		if (countFields == null)
			throw new ServiceException(".");
		Iterator iterator = data.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			Object obj = iterator.next();
			try
			{
				countOne(obj);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} while (true);
		Object result[] = new Object[countFields.length];
		for (int i = 0; i < result.length; i++)
		{
			String field = countFields[i];
			Object value = countCashMap.get(field);
			if (value == null)
			{
				result[i] = Integer.valueOf(0);
				continue;
			}
			if (value instanceof Set)
				result[i] = Integer.valueOf(((Set)value).size());
			else
				result[i] = value;
		}

		return result;
	}

	private Object getFieldValue(String field, Object value)
		throws Exception
	{
		for (int i = 0; i < field.split("\\.").length; i++)
		{
			String filedI = field.split("\\.")[i];
			value = getFiledValueCompose(value, filedI);
			if (value == null)
				return null;
		}

		return value;
	}

	private Object getFiledValueCompose(Object value, String field)
		throws Exception
	{
		if (value instanceof Map)
			return ((Map)value).get(field);
		Object result = ReflectUtils.getFieldValue(value, field);
		if (result == null)
			result = ReflectionUtils.findMethod(value.getClass(), (new StringBuilder()).append("get").append(field.substring(0, 1).toUpperCase()).append(field.substring(1)).toString(), null).invoke(value, new Object[0]);
		return result;
	}

	private void countOne(Object object)
		throws Exception
	{
		String as[] = countFields;
		int i = as.length;
		for (int j = 0; j < i; j++)
		{
			String field = as[j];
			Object value = getFieldValue(field.replace("^", ""), object);
			Object countNowValue = countCashMap.get(field);
			if (value == null)
				continue;
			if (field.contains("^"))
			{
				if (countNowValue == null)
					countNowValue = new HashSet();
				((Set)countNowValue).add(value);
			} else
			if (value instanceof Double)
			{
				if (countNowValue == null)
					countNowValue = Double.valueOf(0.0D);
				countNowValue =  NumUtils.round(Double.valueOf(((Double)countNowValue).doubleValue() + ((Double)value).doubleValue()),null);
			} else
			if (value instanceof Integer)
			{
				if (countNowValue == null)
					countNowValue = Integer.valueOf(0);
				countNowValue = Integer.valueOf(((Integer)countNowValue).intValue() + ((Integer)value).intValue());
			} else
			if (value instanceof Long)
			{
				if (countNowValue == null)
					countNowValue = Long.valueOf(0L);
				countNowValue = Long.valueOf(((Long)countNowValue).longValue() + ((Long)value).longValue());
			}
			if (countNowValue == null)
				throw new ServiceException("不能为null");
			countCashMap.put(field, countNowValue);
		}

	}
}
