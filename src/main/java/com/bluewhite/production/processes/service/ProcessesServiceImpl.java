package com.bluewhite.production.processes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.processes.dao.ProcessesDao;
import com.bluewhite.production.processes.entity.Processes;

/**
 * @author ZhangLiang
 * @date 2020/04/08
 */
@Service
public class ProcessesServiceImpl extends BaseServiceImpl<Processes, Long> implements ProcessesService {

    @Autowired
    private ProcessesDao dao;

    @Override
    public void saveProcesses(Processes processes) {
        if (processes.getPublicType() != null && processes.getSumCount() != null) {
            Processes ps = dao.findByPublicTypeAndNameAndSumCount(processes.getPublicType(), processes.getName(),
                processes.getSumCount());
            if (ps != null) {
                throw new ServiceException("发货单已生成物流费用，无法修改");
            }
        }
        dao.save(processes);
    }

    @Override
    public PageResult<Processes> findPages(Map<String, Object> params, PageParameter page) {
        return findAll(page, params);
    }

    @Override
    public List<Processes> findByPackagMethodId(Long id, int count, int taskNumber) {
        List<Processes> newProcessesList = new ArrayList<Processes>();
        // 通过工序查找
        List<Processes> processesList = dao.findByPackagMethodId(id);
        // 计算耗时
        processesList.forEach(p -> {
            p.setTime(NumUtils.div(p.getTime(), taskNumber, 5));
        });
        newProcessesList.addAll(processesList);
        // 是否公共查找
        List<Processes> processesListPub = dao.findByPublicType(1);
        // 过滤出相符数量工序
        processesListPub = processesListPub.stream().filter(Processes -> Processes.getSumCount().equals(count))
            .collect(Collectors.toList());
        processesListPub.forEach(p -> {
            p.setTime(NumUtils.div(p.getTime(), count, 5));
        });
        newProcessesList.addAll(processesListPub);
        // 是否手填
        List<Processes> processesIsWrite = dao.findByIsWrite(1);
        newProcessesList.addAll(processesIsWrite);
        return newProcessesList;
    }

}
