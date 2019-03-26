package com.bluewhite.finance.expenseAccount.action;

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
import com.bluewhite.finance.expenseAccount.entity.ExpenseAccount;
import com.bluewhite.finance.expenseAccount.service.ExpenseAccountService;
import com.bluewhite.system.user.entity.User;

@Controller
public class ExpenseAccountAction {

	@Autowired
	private ExpenseAccountService expenseAccountService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(ExpenseAccount.class, "id", "user","content","userId"
				,"budget","money","expenseDate","paymentMoney","paymentDate","withholdReason","remark"
				,"withholdMoney","settleAccountsMode","remark","flag")
				.addRetainTerm(User.class, "userName");
	}

	/**
	 * 分页查看报销单
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getExpenseAccount", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getExpenseAccount(HttpServletRequest request, PageParameter page,
			ExpenseAccount expenseAccount) {
		CommonResponse cr = new CommonResponse();
		PageResult<ExpenseAccount> list = expenseAccountService.findPages(expenseAccount, page);
		cr.setData(clearCascadeJSON.format(list).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 财务报销单新增
	 * 财务报销单修改
	 * （一）.未审核 1.填写页面可以修改报销单
	 * （二）.已审核 1.填写页面和出纳均不可修改
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addExpenseAccount", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addExpenseAccount(HttpServletRequest request, ExpenseAccount expenseAccount) {
		CommonResponse cr = new CommonResponse();
		if(expenseAccount.getId() != null){
			expenseAccountService.updateExpenseAccount(expenseAccount);
			cr.setMessage("修改成功");
		}else{
			expenseAccountService.addExpenseAccount(expenseAccount);
			cr.setMessage("添加成功");
		}
		return cr;
	}

	
	/**
	 * 财务报销单审核放款
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/auditExpenseAccount", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse auditExpenseAccount(HttpServletRequest request, ExpenseAccount expenseAccount) {
		CommonResponse cr = new CommonResponse();
		expenseAccountService.auditExpenseAccount(expenseAccount);
		cr.setData(clearCascadeJSON.format(expenseAccount).toJSON());
		cr.setMessage("审核成功");
		return cr;
	}

	/**
	 * 财务报销单删除
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteExpenseAccount", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteExpenseAccount(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = expenseAccountService.deleteAccountService(ids);
			cr.setMessage("成功删除" + count + "条数据");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("报销单不能为空");
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
