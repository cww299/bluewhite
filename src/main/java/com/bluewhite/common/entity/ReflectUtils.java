// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReflectUtils.java

package com.bluewhite.common.entity;

import java.lang.reflect.*;

public class ReflectUtils
{

	public ReflectUtils()
	{
	}

	public static Class findParameterizedType(Class clazz, int index)
	{
		Type parameterizedType = clazz.getGenericSuperclass();
		if (!(parameterizedType instanceof ParameterizedType))
			parameterizedType = clazz.getSuperclass().getGenericSuperclass();
		if (!(parameterizedType instanceof ParameterizedType))
			return null;
		Type actualTypeArguments[] = ((ParameterizedType)parameterizedType).getActualTypeArguments();
		if (actualTypeArguments == null || actualTypeArguments.length == 0)
			return null;
		else
			return (Class)actualTypeArguments[0];
	}

	public static int getFieldModifier(Class clazz, String field)
		throws Exception
	{
		Field fields[] = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			if (fields[i].getName().equals(field))
				return fields[i].getModifiers();

		throw new Exception((new StringBuilder()).append(clazz).append(" has no field \"").append(field).append("\"").toString());
	}

	public static int getMethodModifier(Class clazz, String method)
		throws Exception
	{
		Method m[] = clazz.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
			if (m[i].getName().equals(m))
				return m[i].getModifiers();

		throw new Exception((new StringBuilder()).append(clazz).append(" has no method \"").append(m).append("\"").toString());
	}

	public static Object getFieldValue(Object clazzInstance, Object field)
		throws Exception
	{
		Field fields[] = getClassFieldsAndSuperClassFields(clazzInstance.getClass());
		for (int i = 0; i < fields.length; i++)
			if (fields[i].getName().equals(field))
			{
				fields[i].setAccessible(true);
				return fields[i].get(clazzInstance);
			}

		return null;
	}

	public static Object getFieldValue(Class clazz, String field)
		throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		Field fields[] = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
			if (fields[i].getName().equals(field))
			{
				fields[i].setAccessible(true);
				return fields[i].get(clazz.newInstance());
			}

		return null;
	}

	public static String[] getFields(Class clazz)
	{
		Field fields[] = clazz.getDeclaredFields();
		String fieldsArray[] = new String[fields.length];
		for (int i = 0; i < fields.length; i++)
			fieldsArray[i] = fields[i].getName();

		return fieldsArray;
	}

	public static Field[] getFields(Class clazz, boolean superClass)
		throws Exception
	{
		Field fields[] = clazz.getDeclaredFields();
		Field superFields[] = null;
		if (superClass)
		{
			Class superClazz = clazz.getSuperclass();
			if (superClazz != null)
				superFields = superClazz.getDeclaredFields();
		}
		Field allFields[] = null;
		if (superFields == null || superFields.length == 0)
		{
			allFields = fields;
		} else
		{
			allFields = new Field[fields.length + superFields.length];
			for (int i = 0; i < fields.length; i++)
				allFields[i] = fields[i];

			for (int i = 0; i < superFields.length; i++)
				allFields[fields.length + i] = superFields[i];

		}
		return allFields;
	}

	public static Field[] getClassFieldsAndSuperClassFields(Class clazz)
		throws Exception
	{
		Field fields[] = clazz.getDeclaredFields();
		if (clazz.getSuperclass() == null)
			throw new Exception((new StringBuilder()).append(clazz.getName()).append("û�и���").toString());
		Field superFields[] = clazz.getSuperclass().getDeclaredFields();
		Field allFields[] = new Field[fields.length + superFields.length];
		for (int i = 0; i < fields.length; i++)
			allFields[i] = fields[i];

		for (int i = 0; i < superFields.length; i++)
			allFields[fields.length + i] = superFields[i];

		return allFields;
	}

	public static Object invoke(Class clazz, String method)
		throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
	{
		Object instance = clazz.newInstance();
		Method m = clazz.getMethod(method, new Class[0]);
		return m.invoke(instance, new Object[0]);
	}

	public static Object invoke(Object clazzInstance, String method)
		throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
	{
		Method m = clazzInstance.getClass().getMethod(method, new Class[0]);
		return m.invoke(clazzInstance, new Object[0]);
	}

	public static Object invoke(Class clazz, String method, Class paramClasses[], Object params[])
		throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Object instance = clazz.newInstance();
		Method _m = clazz.getMethod(method, paramClasses);
		return _m.invoke(instance, params);
	}

	public static Object invoke(Object clazzInstance, String method, Class paramClasses[], Object params[])
		throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Method _m = clazzInstance.getClass().getMethod(method, paramClasses);
		return _m.invoke(clazzInstance, params);
	}
}
