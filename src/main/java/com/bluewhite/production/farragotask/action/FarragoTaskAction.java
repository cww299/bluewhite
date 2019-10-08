package com.bluewhite.production.farragotask.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.FarragoTaskPayDao;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.TemporaryUserService;
import com.bluewhite.system.user.service.UserService;

@Controller
public class FarragoTaskAction {
	
private static final Log log = Log.getLog(FarragoTaskAction.class);
	
	@Autowired
	private FarragoTaskService farragoTaskService;
	@Autowired
	private UserService userService;
	@Autowired
	private FarragoTaskPayDao farragoTaskPayDao;
	@Autowired
	private TemporaryUserService temporaryUserService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(FarragoTask.class,"id","bacth","name","price","time","allotTime","userIds",
						"performance","performanceNumber","performancePrice","remarks","number","procedureTime","payB");
	}
	
	/**
	 * 获取杂工加绩类型列表
	 */
	@RequestMapping(value = "/farragoTask/farragoTaskPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse farragoTaskPerformance(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Map<String,Object>> mapList= ProTypeUtils.farragoTaskPerformance();
		cr.setData(mapList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 添加杂工任务（修改）
	 * 
	 */
	@RequestMapping(value = "/farragoTask/addFarragoTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addFarragoTask(HttpServletRequest request,FarragoTask farragoTask) {
		CommonResponse cr = new CommonResponse();
		//修改
		if(!StringUtils.isEmpty(farragoTask.getId())){
			FarragoTask oldTask = farragoTaskService.findOne(farragoTask.getId());
			BeanCopyUtils.copyNullProperties(oldTask,farragoTask);
			farragoTask.setCreatedAt(oldTask.getCreatedAt());
			farragoTaskService.save(farragoTask);
			cr.setMessage("新增成功");
		}else{
			//新增
			if(!StringUtils.isEmpty(farragoTask.getUserIds())){
				farragoTask.setAllotTime(ProTypeUtils.countAllotTime(farragoTask.getAllotTime()));
				farragoTaskService.addFarragoTask(farragoTask);
				cr.setMessage("任务分配成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
		}
		return cr;
	}
	
	
	
	/** 
	 * 分页查询所有任务
	 * 
	 */
	@RequestMapping(value = "/farragoTask/allFarragoTask", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allTask(HttpServletRequest request,FarragoTask farragoTask,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(farragoTaskService.findPages(farragoTask, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/farragoTask/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			farragoTaskService.deleteFarragoTask(id);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 查询该任务的所有领取人
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/farragoTask/taskUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskUser(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = new ArrayList<>();
		if (id != null) {
			FarragoTask farragoTask = farragoTaskService.findOne(id);
			if (!StringUtils.isEmpty(farragoTask.getUserIds())) {
				String[] idArr = farragoTask.getUserIds().split(",");
				if (idArr.length > 0) {
					for (int i = 0; i < idArr.length; i++) {
						Map<String, Object> userMap = new HashMap<>();
						Long userid = Long.parseLong(idArr[i]);
						User user = userService.findOne(userid);
						userMap.put("id", user.getId());
						userMap.put("userName", user.getUserName());
						list.add(userMap);
					}
				}
			}
			if(!StringUtils.isEmpty(farragoTask.getTemporaryUserIds())){
				String[] idArr = farragoTask.getTemporaryUserIds().split(",");
				if (idArr.length > 0) {
					for (int i = 0; i < idArr.length; i++) {
						Map<String, Object> userMap = new HashMap<>();
						Long userid = Long.parseLong(idArr[i]);
						TemporaryUser temporaryUser = temporaryUserService.findOne(userid);
						userMap.put("id", temporaryUser.getId());
						userMap.put("userName", temporaryUser.getUserName());
						list.add(userMap);
					}
				}
			}
			cr.setData(list);
			cr.setMessage("查询成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	
	
	/************包装********************/
	/**
	 * 通过任务id，重新分配人员的加绩工资
	 */
	@RequestMapping(value = "/farragoTask/giveTaskPerformance", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse giveTaskPerformance(HttpServletRequest request,String[] taskIds,String[] ids, String[] performance , Double[] performanceNumber,Integer update) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(taskIds)) {
			if (taskIds.length>0) {
				for (int i = 0; i < taskIds.length; i++) {
						Long id = Long.parseLong(taskIds[i]);
						FarragoTask farragoTask = farragoTaskService.findOne(id);
						if(!StringUtils.isEmpty(ids) && !StringUtils.isEmpty(performance) && !StringUtils.isEmpty(performanceNumber)){
							farragoTask.setPerformance(performance[i]);
							farragoTask.setPerformanceNumber(performanceNumber[i]);
							//任务加绩具体数值
							double performancePrice = NumUtils.round(ProTypeUtils.sumPerformancePrice(farragoTask), null);
							farragoTask.setPerformancePrice(performancePrice);
							if(update==1){
								List<FarragoTaskPay> payBListO = farragoTaskPayDao.findByTaskId(id);
								payBListO.stream().filter(PayB->PayB.getPerformancePayNumber()!=null).collect(Collectors.toList());
								if(payBListO.size()>0){
									for(FarragoTaskPay pl : payBListO){
										pl.setPerformance(null);
										pl.setPerformancePayNumber(null);
									}
									farragoTaskPayDao.save(payBListO);
								}
							}
							if (!StringUtils.isEmpty(ids)) {
								if (ids.length>0) {
									for (int ii = 0; ii < ids.length; ii++) {
										Long userid = Long.parseLong(ids[ii]);
										FarragoTaskPay farragoTaskPay = farragoTaskPayDao.findByTaskIdAndUserId(farragoTask.getId(),userid);
										farragoTaskPay.setPerformance(performance[i]);
										farragoTaskPay.setPerformancePayNumber(performancePrice/ids.length);
										farragoTaskPayDao.save(farragoTaskPay);
									}
								}
							}
							List<FarragoTaskPay> payBList = farragoTaskPayDao.findByTaskId(id);
							farragoTask.setPerformancePrice(payBList.stream().filter(FarragoTaskPay->FarragoTaskPay.getPerformancePayNumber()!=null).mapToDouble(FarragoTaskPay::getPerformancePayNumber).sum());
						}else{
							farragoTask.setPerformance(null);
							farragoTask.setPerformanceNumber(null);
							farragoTask.setPerformancePrice(0.0);
							List<FarragoTaskPay> payBListO = farragoTaskPayDao.findByTaskId(id);
							payBListO.stream().filter(PayB->PayB.getPerformancePayNumber()!=null).collect(Collectors.toList());
							if(payBListO.size()>0){
								for(FarragoTaskPay pl : payBListO){
									pl.setPerformance(null);
									pl.setPerformancePayNumber(null);
								}
								farragoTaskPayDao.save(payBListO);
							}
						}
						farragoTaskService.save(farragoTask);
				}
			}
		}
		cr.setMessage("添加成功");
		return cr;
	}
	
	
	/**
	 * 通过任务id，获取人员的加绩工资
	 * 
	 */
	@RequestMapping(value = "/farragoTask/getUserPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUserPerformance(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		List<FarragoTaskPay> payBList = farragoTaskPayDao.findByTaskId(id);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		Map<Object, List<FarragoTaskPay>> mapPayB = payBList.stream().filter(FarragoTaskPay->FarragoTaskPay.getPerformance()!=null).collect(Collectors.groupingBy(FarragoTaskPay::getPerformance,Collectors.toList()));
		for(Object ps : mapPayB.keySet()){
			map = new HashMap<String, Object>();
			List<FarragoTaskPay> psList= mapPayB.get(ps);
			List<String> userNameList = new ArrayList<String>();
			for(FarragoTaskPay farragoTaskPay : psList){
				userNameList.add(farragoTaskPay.getUserName());
			}
			map.put("performance", ps);
			map.put("username", userNameList);
			listMap.add(map);
		}
		cr.setData(listMap);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	

}
