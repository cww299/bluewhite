package com.bluewhite.production.work.common;

public class TaskConstant {
	
	// 任务类型,按产品、按工序
	public static final int TASK_PRODUCT = 0;
	public static final int TASK_PROCESSES = 1;
	
	// 任务状态 0 未分配  1 进行中  2结束
	public static final int TASK_UNALLOCATION = 0;
	public static final int TASK_PROCESS = 1;
	public static final int TASK_END = 2;
	
	// 分配任务状态
	public static final int ALLOCATION_PROCESS = 0;
	public static final int ALLOCATION_PAUSE = 1;
	public static final int ALLOCATION_END = 2;
	
	// 进度类型： 0.交付 1.分配  2.完成  3.退回 4.暂停  5.开始  6.结束
	public static final int PROCESS_DELIVER = 0;
	public static final int PROCESS_ALLOCATION = 1;
	public static final int PROCESS_FINISH = 2;
	public static final int PROCESS_RETURN = 3;
	public static final int PROCESS_PAUSE = 4;
	public static final int PROCESS_START = 5;
	public static final int PROCESS_END = 6;
	
	// 相关进度描述
	public static final String REMARK_ALLOCATION = "分配任务，数量:{}";
	public static final String REMARK_RETURN = "任务退回:{}";
	public static final String REMARK_FINISH = "任务完成，数量:{}";
	public static final String REMARK_START = "开始任务";
	public static final String REMARK_PAUSE = "暂停任务";
	public static final String REMARK_DELIVER = "交付任务，数量:{}";
	
	// 产品、工序名 分割符
	public static final String NAME_SPLIT = " -- ";
}
