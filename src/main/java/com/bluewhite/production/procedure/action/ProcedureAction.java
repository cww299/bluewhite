package com.bluewhite.production.procedure.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;

@Controller
public class ProcedureAction {

	private static final Log log = Log.getLog(ProcedureAction.class);

	@Autowired
	private ProcedureService procedureService;

	@Autowired
	private BacthService bacthService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get().addRetainTerm(Procedure.class, "id", "name", "workingTime", "productId", "isDel",
						"procedureType", "residualNumber", "deedlePrice", "sourg")
				.addRetainTerm(BaseData.class, "id", "name", "remark");
	}

	/**
	 * 根据产品添加工序
	 * 
	 * type 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/production/addProcedure", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProcedur(HttpServletRequest request, Procedure procedure) {
		CommonResponse cr = new CommonResponse();
		if (procedure.getId() != null) {
			Procedure oldProcedure = procedureService.findOne(procedure.getId());
			BeanCopyUtils.copyNullProperties(oldProcedure, procedure);
			procedure.setCreatedAt(oldProcedure.getCreatedAt());
			procedure = procedureService.save(procedure);
			cr.setMessage("工序修改成功");
		} else {
			if (procedure.getProductId() != null) {
				procedure = procedureService.save(procedure);
				cr.setData(clearCascadeJSON.format(procedure).toJSON());
				;
				cr.setMessage("工序添加成功");
			} else {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("产品不能为空");
			}
		}
		procedureService.countPrice(procedure);
		return cr;
	}

	/**
	 * 根据产品查询工序
	 * 
	 * @param request
	 * @param procedure
	 * @return
	 */
	@RequestMapping(value = "/production/getProcedure", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProcedure(HttpServletRequest request, Procedure procedure) {
		CommonResponse cr = new CommonResponse();
		if (procedure.getProductId() != null) {
			cr.setData(clearCascadeJSON.format(procedureService.findList(procedure)).toJSON());
			cr.setMessage("工序查询成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}
		return cr;
	}

	/**
	 * 根据产品查询工序
	 * 
	 * @param request
	 * @param procedure
	 * @return
	 */
	@RequestMapping(value = "/production/getsoon", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSoon(HttpServletRequest request, Procedure procedure) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = procedureService.soon(procedure);
		cr.setData(clearCascadeJSON.format(list).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 根据产品和工序类型查询工序具体
	 * 
	 * @param request
	 * @param procedure
	 * @return
	 */
	@RequestMapping(value = "/production/typeToProcedure", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse typeToProcedure(Procedure procedure) {
		CommonResponse cr = new CommonResponse();
		if (procedure.getType() == 5) {
			Bacth bacth = bacthService.findOne(procedure.getBacthId());
			procedure.setSign(bacth.getSign());
		}
		if (procedure.getProductId() != null) {
			cr.setData(clearCascadeJSON.format(procedureService.findList(procedure)).toJSON());
			cr.setMessage("成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}
		return cr;
	}

	/**
	 * 根据保存工序新增
	 * 
	 * @param request
	 * @param procedure
	 * @return
	 */
	@RequestMapping(value = "/production/getAdd", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getAdd(HttpServletRequest request, Procedure procedure) {
		CommonResponse cr = new CommonResponse();
		procedureService.add(procedure);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * 删除工序
	 * 
	 * @param request
	 * @param procedure
	 * @return
	 */
	@RequestMapping(value = "/production/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		String[] idStrings = null;
		if (!StringUtils.isEmpty(ids)) {
			idStrings = ids.split(",");
		}
		if (idStrings.length > 0) {
			for (String id : idStrings) {
				procedureService.deleteProcedure(Long.valueOf(id));
			}
			cr.setMessage("工序删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("工序不能为空");
		}
		return cr;
	}


}
