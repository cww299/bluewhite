package com.bluewhite.finance.ledger.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.action.AttendanceAction;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Order;
import com.bluewhite.finance.ledger.service.OrderService;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.system.user.entity.User;

/**
 * 财务部 订单
 * 
 * @author qiyong
 *
 */
@Controller
public class OrderAction {
	private static final Log log = Log.getLog(OrderAction.class);

	@Autowired
	private OrderService orderService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Order.class, "id", "salesNumber", "contractTime", "batchNumber", "planNumbers",
						"productName", "contractNumber", "contractPrice", "remarksPrice", "firstNames", "partyNames",
						"price", "firstNamesId", "partyNamesId", "contact", "ashoreNumber", "ashoreTime",
						"ashoreCheckr", "disputeNumber", "roadNumber", "disputePrice", "ashorePrice")
				.addRetainTerm(Contact.class, "id", "conPartyNames", "conPhone", "conWechat");
	}

	/**
	 * 分页查看订单
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrder(HttpServletRequest request, PageParameter page, Order order) {
		CommonResponse cr = new CommonResponse();
		HttpSession session = request.getSession();
		CurrentUser user = (CurrentUser) session.getAttribute("user");
		if(user.getIsAdmin()==false){
			order.setFirstNamesId(user.getId());
		}
		PageResult<Order> orderList = orderService.findPages(order, page);
		cr.setData(clearCascadeJSON.format(orderList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 财务订单新增
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrder(HttpServletRequest request, Order order) {
		CommonResponse cr = new CommonResponse();
		if (order.getId() != null) {
			Order oldOrder = orderService.findOne(order.getId());
			BeanCopyUtils.copyNullProperties(oldOrder, order);
			order.setCreatedAt(oldOrder.getCreatedAt());
		}
		orderService.addOrder(order);
		cr.setData(clearCascadeJSON.format(order).toJSON());
		cr.setMessage("添加成功");
		return cr;
	}

	/**
	 * 财务订单删除
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			orderService.deleteOrder(ids);
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
