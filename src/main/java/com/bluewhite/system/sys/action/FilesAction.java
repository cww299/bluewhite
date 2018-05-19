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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.system.sys.entity.Files;
import com.bluewhite.system.sys.entity.SysLog;
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
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upload(@RequestParam(value = "file", required = true) MultipartFile[] files,
			HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Files> filesList = new ArrayList<Files>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				Files fi = fileService.upFile(file, request);
				filesList.add(fi);
			}
		}
		if(filesList.size()>0){
			cr.setMessage("成功上传"+filesList.size()+"条");
			cr.setData(filesList);
		}else{
			cr.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
			cr.setMessage("上传失败");
		}
		return cr;
	}
	
	


}
