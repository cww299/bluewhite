package com.bluewhite.finance.consumption.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.ConsumptionPoi;
import com.bluewhite.finance.consumption.entity.Custom;
import com.bluewhite.finance.consumption.service.ConsumptionService;
import com.bluewhite.finance.consumption.service.CustomService;
import com.bluewhite.system.user.entity.User;

@Controller
public class ConsumptionAction {

	@Autowired
	private ConsumptionService consumptionService;
	@Autowired
	private CustomService customService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Consumption.class, "id", "user","content","userId"
				,"budget","money","expenseDate","paymentMoney","paymentDate","withholdReason","remark"
				,"withholdMoney","settleAccountsMode","remark","flag","taxPoint","custom","user"
				,"contact","logisticsDate","contactName","batchNumber","realityDate","applyType","deleteFlag")
				.addRetainTerm(User.class, "userName")
				.addRetainTerm(BaseData.class,"id","name")
				.addRetainTerm(Custom.class, "name");
	}

	/**
	 * 分页查看
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getConsumption", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getConsumption(HttpServletRequest request, PageParameter page,
			Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		PageResult<Consumption> list = consumptionService.findPages(consumption, page);
		cr.setData(clearCascadeJSON.format(list).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 人事部汇总报销金额
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/countConsumptionMoney", method = RequestMethod.GET)  
	@ResponseBody
	public CommonResponse countConsumptionMoney(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		cr.setData(consumptionService.countConsumptionMoney());
		cr.setMessage("统计成功");
		return cr;
	}
	

	/**
	 * 财务新增
	 * 财务修改
	 * （一）.未审核 1.填写页面可以修改
	 * （二）.已审核 1.填写页面和出纳均不可修改
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		if(consumption.getId() != null){
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		//同步锁，批量新增
		synchronized (this) {
			consumptionService.addConsumption(consumption);
		}
		return cr;
	}

	/**
	 * 财务新增订单（导入）
	 * 
	 */
	@RequestMapping(value = "/fince/excel/addConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importConsumption(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request) throws IOException{
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
	    EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1,ConsumptionPoi.class), excelListener);
	    int count = consumptionService.excelAddConsumption(excelListener);
	    inputStream.close();
	    cr.setMessage("成功导入"+count+"条数据");
	    return cr;
	}	
	
	/**
	 * 财务审核放款
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/auditConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditConsumption(HttpServletRequest request, String ids, Integer flag) {
		CommonResponse cr = new CommonResponse();
		int count = consumptionService.auditConsumption(ids,flag);
		cr.setMessage("操作成功"+count+"条");
		return cr;
	}

	/**
	 * 财务删除
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteConsumption", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = consumptionService.deleteConsumption(ids);
			cr.setMessage("成功删除" + count + "条数据");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 获取财务未付款的总金额
	 */
	@RequestMapping(value = "/fince/totalAmount", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse totalAmount(Integer type,Date beginTime, Date endDate) {
		CommonResponse cr = new CommonResponse();
		double totalAmount = consumptionService.totalAmount(type,beginTime,endDate);
		cr.setData(totalAmount);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	
	/**
	 * 分頁获取客户
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/findCustomPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findCustomPage( PageParameter page,Custom custom) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Custom.class, "id", "name","type")
				.format(customService.findPages(custom, page)).toJSON());
		return cr;
	}
	
	/**
	 * 根据类型获取客户
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/findCustom", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findCustom(HttpServletRequest request,Integer type,String name) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Custom.class, "id", "name")
				.format(customService.findCustom(type,name)).toJSON());
		return cr;
	}
	
	/**
	 * 根据类型
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/findCustomBytype", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findCustomBytype(HttpServletRequest request,Integer type) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Custom.class, "id", "name")
				.format(customService.findByType(type)).toJSON());
		return cr;
	}
	
	/**
	 * 删除客户
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteCustom", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCustom(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		int count = customService.deleteCustom(ids);
		cr.setMessage("成功删除"+count+"条");
		return cr;
	}
	
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
