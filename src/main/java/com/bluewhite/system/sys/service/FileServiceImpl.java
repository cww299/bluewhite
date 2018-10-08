package com.bluewhite.system.sys.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.system.sys.dao.FilesDao;
import com.bluewhite.system.sys.entity.Files;

@Service
public class FileServiceImpl extends BaseServiceImpl<Files, Long> implements
FilesService {
	
	@Autowired
	private FilesDao filesDao;

	@Override
	public Files upFile(MultipartFile file, HttpServletRequest request) {
		Files files = new Files();
		String fileName = file.getOriginalFilename();
		String type = file.getContentType();
		long size = file.getSize() ;
		String filePath = "D:/upload/img/";  
		 File targetFile = new File(filePath);  
		 if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }  
	        //保存  
	        try {  
	        	file.transferTo(new File(filePath+fileName));  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        files.setName(fileName);
	        files.setType(type);
	        files.setUrl("/upload/img/"+fileName);
	        files.setSize(size);
	        filesDao.save(files);
		return files;
	}


}
