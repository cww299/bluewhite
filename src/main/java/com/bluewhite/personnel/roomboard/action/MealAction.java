package com.bluewhite.personnel.roomboard.action;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.roomboard.entity.Meal;
import com.bluewhite.personnel.roomboard.service.MealService;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;

@Controller
public class MealAction {

	@Autowired
	private MealService service;

	@Autowired
	private PersonVariableDao personVariableDao;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Meal.class, "id","userName", "user","temporaryUser", "mode", "tradeDaysTime", "price","orgNameId")
				.addRetainTerm(User.class, "id", "userName", "orgName", "orgNameId")
				.addRetainTerm(TemporaryUser.class, "id", "userName");
	}

	/**
	 * 分页查看报餐
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/personnel/getMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(  PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		PageResult<Meal> mealList = service.findPage(meal, page);
		cr.setData(clearCascadeJSON.format(mealList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/fince/addMeal", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(  Meal meal) {
		CommonResponse cr = new CommonResponse();
		if (meal.getId() != null) {
			Meal ot = service.findOne(meal.getId());
			service.update(meal, ot, "");
			cr.setMessage("修改成功");
		}else {
			service.addMeal(meal);
			cr.setMessage("添加成功");
		}
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
	@RequestMapping(value = "/fince/deleteMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(  String[] ids) {
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
	 * 查看字典表报餐价格
	 * 
	 */
	@RequestMapping(value = "/personnel/getpersonVariabledao", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPersonVariabledao(  PageParameter page, Integer type) {
		CommonResponse cr = new CommonResponse();
		PersonVariable personVariable = service.findByType(type);
		cr.setData(clearCascadeJSON.format(personVariable).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改
	 * 
	 */
	@RequestMapping(value = "/personnel/addPersonVaiable", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updatePerson(  PersonVariable personVariable) {
		CommonResponse cr = new CommonResponse();
		if (personVariable.getId() != null) {
			PersonVariable personVariable2 = personVariableDao.findOne(personVariable.getId());
			BeanCopyUtils.copyNullProperties(personVariable2, personVariable);
			personVariable2.setCreatedAt(personVariable2.getCreatedAt());
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("添加成功");
		}
		service.updateperson(personVariable);
		return cr;
	}

	/**
	 * 报餐汇总
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getSummaryMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSummaryMeal(  PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findMealSummary(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 水电
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/personnel/getfindElectric", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getfindElectric(  Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findElectric(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 每天汇总
	 * 
	 */
	@RequestMapping(value = "/personnel/getSummaryWage", method = RequestMethod.GET)
	@ResponseBody
    public CommonResponse getSummaryWage( PageParameter page, Meal meal) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = service.findWage(meal);
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 同步报餐记录
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getEatType", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getEatType(AttendanceTime attendanceTime) throws ParseException {
		CommonResponse cr = new CommonResponse();
		int list = service.initMeal(attendanceTime);
		cr.setMessage("成功同步" + list + "条吃饭记录");
		return cr;
	}

}
