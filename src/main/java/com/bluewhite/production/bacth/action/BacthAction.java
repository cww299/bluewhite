package com.bluewhite.production.bacth.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.system.user.service.UserService;

@Controller
public class BacthAction {
	
private static final Log log = Log.getLog(BacthAction.class);
	
	@Autowired
	private BacthService bacthService;
	
	@Autowired
	private UserService userService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Bacth.class,"id","name","price","type")
				.addRetainTerm(Product.class,"id","number","name","departmentPrice");
	}
	
	/**
	 * 给产品添加批次（修改）
	 * 
	 * 是否完成（0=默认未完成，1=完成，status传参）
	 * 
	 */
	@RequestMapping(value = "/Bacth/addBacth", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addBacth(HttpServletRequest request,Bacth bacth) {
		CommonResponse cr = new CommonResponse();
		if(bacth.getId()!=null){
			Bacth oldBacth = bacthService.findOne(bacth.getId());
			BeanCopyUtils.copyNullProperties(oldBacth,bacth);
			bacth.setCreatedAt(oldBacth.getCreatedAt());
			bacthService.update(bacth);
			cr.setMessage("修改成功");
		}else{
			if(bacth.getProductId()!=null){
				bacthService.save(bacth);
				cr.setMessage("添加成功");
			}else{
				cr.setMessage("产品不能为空");
			}
		}
		return cr;
	}
	
	/**
	 * 查询所有批次
	 * 
	 */
	@RequestMapping(value = "/Bacth/allBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allBacth(HttpServletRequest request,Bacth bacth,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		if(bacth.getProductId()!=null){
			cr.setMessage("添加成功");
			cr.setData(clearCascadeJSON.format(bacthService.findPages(bacth, page)).toJSON());
		}else{
			cr.setMessage("产品不能为空");
		}
		return cr;
	}
	
	/**
	 * 删除批次
	 * 
	 */
	@RequestMapping(value = "/Bacth/deleteBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteBacth(HttpServletRequest request,Bacth bacth) {
		CommonResponse cr = new CommonResponse();
		if(bacth.getProductId()!=null){
			bacthService.delete(bacth.getId());
			cr.setMessage("删除成功");
		}else{
			cr.setMessage("产品不能为空");
		}
		return cr;
	}

}
