package com.bluewhite.finance.consumption.action;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.ConsumptionPoi;
import com.bluewhite.finance.consumption.service.ConsumptionService;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

@Controller
public class ConsumptionAction {

	@Autowired
	private ConsumptionService consumptionService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Consumption.class, "id", "user", "customer", "orderProcurement", "budget", "money",
						"expenseDate", "paymentMoney", "paymentDate", "withholdReason", "remark", "withholdMoney",
						"settleAccountsMode", "remark", "flag", "taxPoint","batchNumber", "realityDate", "deleteFlag",
						"orgName","content","orderOutSource","logistics","expectDate","budgetMoney", 
						"logisticsNumber", "sendOrderId", "sendOrder")
				.addRetainTerm(OrderOutSource.class, "id","remark", "outsourceTask","outSourceNumber","materialRequisition")
				.addRetainTerm(MaterialRequisition.class, "id","order")
				.addRetainTerm(User.class, "id","userName")
				.addRetainTerm(Customer.class,"name","id")
				.addRetainTerm(OrderProcurement.class,"orderProcurementNumber", "placeOrderNumber", "arrivalTime","order",
						"materielLocation", "price","expectPaymentTime", "materiel", "gramPrice", "interest", "paymentMoney")
				.addRetainTerm(Order.class,"bacthNumber","orderNumber")
				.addRetainTerm(Materiel.class, "name", "number", "materialQualitative")
				.addRetainTerm(BaseData.class, "id", "name");
	}

	/**
	 * 分页查看
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/fince/getConsumption", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getConsumption(PageParameter page, Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(consumptionService.findPages(consumption, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
   /**
     * 按客户统计物流费用
     * 
     * @param request
     * @return cr
     */
    @RequestMapping(value = "/fince/logisticsConsumption", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse logisticsConsumption(Consumption consumption) {
        CommonResponse cr = new CommonResponse();
        cr.setData(consumptionService.logisticsConsumption(consumption));
        cr.setMessage("成功");
        return cr;
    }
	

	/**
	 * 人事部汇总报销金额
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/fince/countConsumptionMoney", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse countConsumptionMoney(Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		cr.setData(consumptionService.countConsumptionMoney(consumption));
		cr.setMessage("统计成功");
		return cr;
	}

	/**
	 * 财务新增 财务修改 （一）.未审核 1.填写页面可以修改 （二）.已审核 1.填写页面和出纳均不可修改
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/fince/addConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		if (consumption.getId() != null) {
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("添加成功");
		}
		// 同步锁，批量新增
		synchronized (this) {
			consumptionService.addConsumption(consumption);
		}
		return cr;
	}

	/**
	 * 财务审核放款
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/fince/auditConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditConsumption(String ids, Integer flag) {
		CommonResponse cr = new CommonResponse();
		int count = consumptionService.auditConsumption(ids, flag);
		cr.setMessage("操作成功" + count + "条");
		return cr;
	}

	/**
	 * 财务删除
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/fince/deleteConsumption", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(String ids) {
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
	public CommonResponse totalAmount(Consumption consumption) {
		CommonResponse cr = new CommonResponse();
		double totalAmount = consumptionService.totalAmount(consumption);
		cr.setData(totalAmount);
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 财务新增订单（导入）
	 * 
	 */
	@RequestMapping(value = "/fince/excel/addConsumption", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importConsumption(@RequestParam(value = "file", required = false) MultipartFile file,Integer type) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcel.read(inputStream, ConsumptionPoi.class, excelListener).sheet().doRead();
		int count = consumptionService.excelAddConsumption(excelListener,type);
		inputStream.close();
		cr.setMessage("成功导入" + count + "条数据");
		return cr;
	}
	

}
