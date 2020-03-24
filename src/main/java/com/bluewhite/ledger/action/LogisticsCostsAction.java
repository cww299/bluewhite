 package com.bluewhite.ledger.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.LogisticsCosts;
import com.bluewhite.ledger.service.LogisticsCostsService;

import cn.hutool.core.map.MapUtil;

/**
 * @author ZhangLiang
 * @date 2020/03/19
 */
@Controller
public class LogisticsCostsAction {
    
    @Autowired
    private LogisticsCostsService logisticsCostsService;
    
    
    
    private ClearCascadeJSON clearCascadeJSON;
    {
        clearCascadeJSON = ClearCascadeJSON.get()
                .addRetainTerm(LogisticsCosts.class, "id", "customer", "logistics", "outerPackaging", "taxIncluded", "excludingTax",
                        "settlement", "payment")
                .addRetainTerm(Customer.class, "id", "name")
                .addRetainTerm(BaseData.class, "id", "name");
    }
    
    
    /**
     * 分页查看物流单价
     * 
     * @return cr
     */
    @RequestMapping(value = "/ledger/logisticsCostsPage", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse logisticsCostsPage(@RequestParam Map<String, Object> params) {
        CommonResponse cr = new CommonResponse();
        if(MapUtil.isEmpty(params)){
            throw new ServiceException("参数不能为空");
        };
        PageParameter page = PageUtil.mapToPage(params);
        cr.setData(clearCascadeJSON.format(logisticsCostsService.findPages(params, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }
    
    /**
     * 新增物流单价
     * 
     * @return cr
     */
    @RequestMapping(value = "/ledger/addlogisticsCosts", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addlogisticsCosts(LogisticsCosts logisticsCosts) {
        CommonResponse cr = new CommonResponse();
        logisticsCostsService.save(logisticsCosts);
        cr.setMessage("添加成功");
        return cr;
    }

    /**
     * 删除物流单价
     * 
     * @return cr
     */
    @RequestMapping(value = "/ledger/deleteLogisticsCosts", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteLogisticsCosts(String ids) {
        CommonResponse cr = new CommonResponse();
        int count = logisticsCostsService.deleteLogisticsCosts(ids);
        cr.setMessage("成功删除" + count + "条数据");
        return cr;
    }

    /**
     * 按条件查询价格
     * @return cr
     */
    @RequestMapping(value = "/ledger/findLogisticsCostsPrice", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse findLogisticsCostsPrice(LogisticsCosts logisticsCosts) {
        CommonResponse cr = new CommonResponse();
        List<LogisticsCosts> logisticsCostsList = logisticsCostsService.findAll(logisticsCosts);
        List<Double> list = new ArrayList<Double>();
        if(logisticsCostsList.size()>0){
            list = logisticsCostsList.stream().map(l->l.getTaxIncluded()).collect(Collectors.toList());
            //不含税
            if(logisticsCosts.getTax()!=null && logisticsCosts.getTax()==1) {
                list = logisticsCostsList.stream().map(l->l.getExcludingTax()).collect(Collectors.toList());
            }   
        }
        cr.setData(list);
        return cr;
    }
    

}
