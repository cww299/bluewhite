package com.bluewhite.system.sys.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.sys.entity.Files;
import com.bluewhite.system.sys.service.FilesService;

@Controller
public class FilesAction {
	
	private final static Log log = Log.getLog(FilesAction.class);
	
	@Autowired
	private FilesService fileService;


	
	/**
	 * 文件上传
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upload(@RequestParam(value = "file", required = true) MultipartFile files,
			HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Files> filesList = new ArrayList<Files>();
			// 循环获取file数组中得文件
			Files fi = fileService.upFile(files, request);
			filesList.add(fi);
			cr.setMessage("成功上传");
			cr.setData(filesList);
		return cr;
	}
	
	


}
