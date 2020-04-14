package com.bluewhite.system.sys.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.sys.service.RegionAddressService;

@Controller
public class RegionAddressAction {

	@Autowired
	private RegionAddressService regionAddressService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(RegionAddress.class, "id", "regionCode", "regionName",
				"parentId", "regionNameEn");
	}

	
	@RequestMapping(value = "/regionAddress/queryProvince", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse queryprovince(Long parentId) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(regionAddressService.queryProvince(parentId)).toJSON());
		return cr;
	}

	

}
