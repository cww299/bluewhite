package com.bluewhite.production.task.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 产品批次任务
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_task")
public class Task  extends BaseEntity<Long>{

}
