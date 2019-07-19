//package com.bluewhite.finance.ledger.action;
//
//import java.text.SimpleDateFormat;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
//
//import com.bluewhite.common.ClearCascadeJSON;
//import com.bluewhite.common.DateTimePattern;
//import com.bluewhite.common.Log;
//import com.bluewhite.common.entity.CommonResponse;
//import com.bluewhite.common.entity.PageParameter;
//import com.bluewhite.common.entity.PageResult;
//import com.bluewhite.finance.ledger.entity.Actualprice;
//import com.bluewhite.finance.ledger.entity.Mixed;
//import com.bluewhite.finance.ledger.service.ActualpriceService;
//
///**
// * 财务部  客户信息
// * @author qiyong
// *
// */
//@Controller
//public class ActualpriceAction {
//	private static final Log log = Log.getLog(ActualpriceAction.class);
//	
//	@Autowired
//	private ActualpriceService service;
//	
//	private ClearCascadeJSON clearCascadeJSON;
//
//	{
//		clearCascadeJSON = ClearCascadeJSON
//				.get()
//				.addRetainTerm(Mixed.class,"id","batchNumber","productName","budgetPrice","combatPrice");
//	}
//	
//	/**
//	 * 分页查看杂支明细
//	 * 
//	 * @param request 请求
//	 * @return cr
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/fince/actualprice", method = RequestMethod.GET)
//	@ResponseBody
//	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Actualprice actualprice) {
//		CommonResponse cr = new CommonResponse();
//		PageResult<Actualprice>  actualpriceList= service.findPages(actualprice, page); 
//		cr.setData(clearCascadeJSON.format(actualpriceList).toJSON());
//		cr.setMessage("查询成功");
//		return cr;
//	}
//	
//	
//	
//	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
//				DateTimePattern.DATEHMS.getPattern());
//		binder.registerCustomEditor(java.util.Date.class, null,
//				new CustomDateEditor(dateTimeFormat, true));
//		binder.registerCustomEditor(byte[].class,
//				new ByteArrayMultipartFileEditor());
//	}
//}
