package com.bluewhite.finance.tax.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

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

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.tax.entity.Tax;
import com.bluewhite.finance.tax.service.TaxService;

@Controller
public class TaxAction {

	@Autowired
	private TaxService taxService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Tax.class, "id", "taxPoint","content"
				,"money","expenseDate","paymentMoney","paymentDate","withholdReason","remark"
				,"withholdMoney","settleAccountsMode","remark","flag");
	}

	/**
	 * 分页查看税点单
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/fince/getTax", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTax(HttpServletRequest request, PageParameter page,
			Tax tax) {
		CommonResponse cr = new CommonResponse();
		PageResult<Tax> list = taxService.findPages(tax, page);
		cr.setData(clearCascadeJSON.format(list).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 财务税点单新增
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/fince/addTax", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTax(HttpServletRequest request,Tax tax) {
		CommonResponse cr = new CommonResponse();
		taxService.addTax(tax);
		cr.setData(clearCascadeJSON.format(tax).toJSON());
		cr.setMessage("添加成功");
		return cr;
	}

	/**
	 * 财务税点单修改
	 * （一）.未审核 1.填写页面可以修改税点单
	 * （二）.已审核 1.填写页面和出纳均不可修改
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/fince/updateTax", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateTax(HttpServletRequest request, Tax tax) {
		CommonResponse cr = new CommonResponse();
		taxService.updateTax(tax);
		cr.setData(clearCascadeJSON.format(tax).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 财务税点单审核放款
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/fince/auditTax", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditTax(HttpServletRequest request, Tax tax) {
		CommonResponse cr = new CommonResponse();
		taxService.auditTax(tax);
		cr.setData(clearCascadeJSON.format(tax).toJSON());
		cr.setMessage("放款成功");
		return cr;
	}

	/**
	 * 财务税点单删除
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/fince/deleteTax", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteTax(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = taxService.deleteTax(ids);
			cr.setMessage("成功删除" + count + "条数据");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("税点单不能为空");
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
