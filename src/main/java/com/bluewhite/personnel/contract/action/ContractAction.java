package com.bluewhite.personnel.contract.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.personnel.contract.entity.Contract;
import com.bluewhite.personnel.contract.service.ContractService;
import com.bluewhite.system.sys.entity.Files;

@Controller
public class ContractAction {

	@Autowired
	private ContractService contractService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(Contract.class, "id", "contractKind", "contractType",
				"duration", "pictureUrl", "startTime", "code","isRenew","endTime", "content","amount","flag","company","fileSet","paymentWay","paymentTime")
				.addRetainTerm(Files.class, "id", "url")
				.addRetainTerm(BaseData.class, "id", "name");
	}

	/**
	 * 新增修改合同
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/contract/addContract", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getAllUser(Contract contract) {
		CommonResponse cr = new CommonResponse();
		if (contract != null) {
			if(contract.getIsRenew()==1)
				cr.setMessage("续签成功");
			else
				cr.setMessage("修改成功");
		} else {
			cr.setMessage("新增成功");
		}
		contractService.addContract(contract);
		return cr;
	}

	/**
	 * 查找修改合同
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/contract/findContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findContract(Contract contract, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(contractService.findContractPage(contract, page)).toJSON());
		cr.setMessage("查找成功");
		return cr;
	}

	/**
	 * 删除合同
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/contract/deleteContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteContract(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = contractService.delete(ids);
		cr.setMessage("成功删除" + count + "条合同");
		return cr;
	}

	/**
	 * 合同提醒
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/contract/remindContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse remindContract() {
		CommonResponse cr = new CommonResponse();
		cr.setData(contractService.remindContract());
		return cr;
	}
	
	/**
	 * 合同提醒
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/contract/getOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse remindContract(Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(contractService.findOne(id)).toJSON());
		return cr;
	}

}
