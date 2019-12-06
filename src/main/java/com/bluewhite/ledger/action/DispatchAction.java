package com.bluewhite.ledger.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.ledger.entity.ApplyVoucher;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.service.ApplyVoucherService;

@Controller
public class DispatchAction {

	@Autowired
	private ApplyVoucherService applyVoucherService;
	
	
	/**
	 * 请求申请
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/dispatch/saveApplyVoucher", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveApplyVoucher(ApplyVoucher applyVoucher) {
		CommonResponse cr = new CommonResponse();
		applyVoucherService.saveApplyVoucher(applyVoucher);
		cr.setMessage("申请成功");
		return cr;
	}

}
