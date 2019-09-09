package com.bluewhite.personnel.roomboard.action;

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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Advertisement;
import com.bluewhite.personnel.roomboard.entity.Recruit;
import com.bluewhite.personnel.roomboard.service.AdvertisementService;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class AdvertisementAction {

	@Autowired
	private AdvertisementService service;
	@Autowired
	private UserService userService;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Advertisement.class, "id", "time", "platformId", "platform", "price", "startTime",
						"endTime", "recruitId", "recruitName", "train", "trainPrice", "userId", "user", "qualified",
						"type", "number", "number1", "number2", "number3", "number4")
				.addRetainTerm(User.class, "id", "userName", "number")
				.addRetainTerm(Recruit.class, "recruitId", "recruitName", "name");
	}

	/**
	 * 分页查看招聘成本
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getAdvertisement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request, PageParameter page, Advertisement advertisement) {
		CommonResponse cr = new CommonResponse();
		PageResult<Advertisement> result = service.findPage(advertisement, page);
		cr.setData(clearCascadeJSON.format(result).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/addAdvertisement", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Advertisement advertisement) {
		CommonResponse cr = new CommonResponse();
		if (advertisement.getId() != null) {
			Advertisement advertisement2 = service.findOne(advertisement.getId());
			BeanCopyUtils.copyNullProperties(advertisement2, advertisement);
			advertisement.setCreatedAt(advertisement2.getCreatedAt());
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("添加成功");
		}
		service.addAdvertisement(advertisement);
		return cr;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteAdvertisement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				service.delete(id);
				count++;
			}
		}
		cr.setMessage("成功删除" + count + "条");
		return cr;
	}

	/**
	 * 查询单个人的培训汇总
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/findRecruitId", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findRecruitId(HttpServletRequest request, Long recruitId) {
		CommonResponse cr = new CommonResponse();
		Advertisement advertisement = service.findRecruitId(recruitId);
		cr.setData(clearCascadeJSON.format(advertisement).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
