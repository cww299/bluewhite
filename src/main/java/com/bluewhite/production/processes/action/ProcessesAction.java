package com.bluewhite.production.processes.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageUtil;
import com.bluewhite.production.processes.entity.Processes;
import com.bluewhite.production.processes.service.ProcessesService;

import cn.hutool.core.map.MapUtil;

/**
 * @author ZhangLiang
 * @date 2020/04/08
 */
@Controller
public class ProcessesAction {

    @Autowired
    private ProcessesService processesService;

    private ClearCascadeJSON clearCascadeJSON;
    {
        clearCascadeJSON =
            ClearCascadeJSON.get()
                .addRetainTerm(Processes.class, "id", "name", "time", "publicType", "packagMethod",
                    "isWrite","sumCount","surplusCount","orderNo")
                .addRetainTerm(BaseData.class, "id", "name");
    }
    
    
    /**
     * 新增工序
     */
    @RequestMapping(value = "/processes/addProcesses", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addProcesses(Processes processes) {
        CommonResponse cr = new CommonResponse();
        if(processes.getId()==null ) {
            cr.setMessage("新增成功");
        }else {
            cr.setMessage("修改成功");
        }
        processesService.saveProcesses(processes);
        return cr;
    }
    
    /**
     * 分页查看
     * @return cr
     */
    @RequestMapping(value = "/processes/processesPage", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse sendOrderPage(@RequestParam Map<String, Object> params) {
        CommonResponse cr = new CommonResponse();
        if (MapUtil.isEmpty(params)) {
            throw new ServiceException("参数不能为空");
        } ;
        PageParameter page = PageUtil.mapToPage(params);
        cr.setData(clearCascadeJSON.format(processesService.findPages(params, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }
    
    /**
     * 删除
     * @return cr
     */
    @RequestMapping(value = "/processes/deleteProcesses", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteProcesses(String ids) {
        CommonResponse cr = new CommonResponse();
        cr.setMessage("成功删除"+processesService.delete(ids) +"条数据");
        return cr;
    }
    
    /**
     * 根据包装方式查找
     * @return cr
     */
    @RequestMapping(value = "/processes/getProcesses", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getProcesses(Long id,int count,int taskNumber,Long quantitativeId) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSON.format(processesService.findByPackagMethodId(id,count,taskNumber,quantitativeId)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }
    
    

}
