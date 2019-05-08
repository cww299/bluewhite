package com.bluewhite.system.sys.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.system.sys.entity.Files;
import com.bluewhite.system.sys.service.FilesService;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

@Controller
public class FilesAction {
	
	private final static Log log = Log.getLog(FilesAction.class);
	
	@Autowired
	private FilesService fileService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Files.class,"id","url");
	}


	
	/**
	 * 员工相片上传
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upload(@RequestParam(value = "file", required = true) MultipartFile files,
			HttpServletRequest request) {
			CommonResponse cr = new CommonResponse();
			// 循环获取file数组中得文件
			Files fi = fileService.upFile(files, request);
			cr.setMessage("成功上传");
			cr.setData(clearCascadeJSON.format(fi).toJSON());
		return cr;
	}
	
	


}
