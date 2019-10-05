package com.bluewhite.system.sys.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.system.sys.entity.Files;
import com.bluewhite.system.sys.service.FilesService;

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
	 * 照片上传
	 * @param files
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upload(@RequestParam(value = "file", required = true) MultipartFile files,
			HttpServletRequest request,Long filesTypeId) {
			CommonResponse cr = new CommonResponse();
			// 循环获取file数组中得文件
			Files fi = fileService.upFile(files, request,filesTypeId);
			cr.setMessage("成功上传");
			cr.setData(clearCascadeJSON.format(fi).toJSON());
		return cr;
	}
	
	


}
