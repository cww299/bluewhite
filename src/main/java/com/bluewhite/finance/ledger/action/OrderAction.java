package com.bluewhite.finance.ledger.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.action.AttendanceAction;
import com.bluewhite.finance.ledger.entity.Order;
import com.bluewhite.finance.ledger.service.OrderService;

/**
 * 财务部  订单
 * @author qiyong
 *
 */
@Controller
public class OrderAction {
	private static final Log log = Log.getLog(OrderAction.class);
	
	@Autowired
	private OrderService orderService;
	/**
	 * 分页查看订单
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrder(HttpServletRequest request,PageParameter page,Order order) {
		CommonResponse cr = new CommonResponse();
		PageResult<Order>  orderList= orderService.findPages(order, page); 
		cr.setData(orderList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 财务订单新增
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrder(HttpServletRequest request,Order order) {
		CommonResponse cr = new CommonResponse();
			orderService.save(order);
			cr.setMessage("添加成功");
		return cr;
	}
	
	
	/**
	 * 财务订单删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrder(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
			orderService.delete(id);
			cr.setMessage("删除成功");
		return cr;
	}
	
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
}
