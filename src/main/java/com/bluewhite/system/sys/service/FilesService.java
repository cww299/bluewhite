package com.bluewhite.system.sys.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.system.sys.entity.Files;
@Service
public interface FilesService  extends BaseCRUDService<Files, Long>{
	
	public Files upFile( MultipartFile file, HttpServletRequest request);

}
