package com.bluewhite.system.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.system.user.entity.Menu;
import com.bluewhite.system.user.service.MenuService;


/**
 * menu
 * 
 * @author zhangliang
 * @date 2017年4月21日 上午10:16:54
 */
@Controller
public class MenuAction {

	private static final Log log = Log.getLog(MenuAction.class);

	
	
	@Autowired
	private MenuService menuService;

	

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(Menu.class,
				"identity","span", "url", "icon", "name","children","isHighLight");
	}

	/**
	 * 查询当前登录用户的菜单
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/menus", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMenus(HttpServletRequest request) {
		List<Object> result = new ArrayList<Object>();
		CurrentUser cu = SessionManager.getUserSession();
		List<Menu> menus = menuService.findHasPermissionMenusByUsernameNew(cu
				.getUserName());
		result.add(menus);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(result)
				.toJSON());
		return cr;
	}
	
	

	


}
