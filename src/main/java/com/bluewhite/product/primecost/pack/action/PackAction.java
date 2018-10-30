package com.bluewhite.product.primecost.pack.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.pack.entity.Pack;
import com.bluewhite.product.primecost.pack.service.PackService;
import com.bluewhite.product.primecost.tailor.service.TailorService;

public class PackAction {
	
	
private final static Log log = Log.getLog(PackAction.class);
	
	@Autowired
	private PackService packService;
	
	@Autowired
	private TailorService tailorService;
	
	
	/**
	 * 内外包装和杂工填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addPack", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addPack(HttpServletRequest request,Pack pack) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(pack.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			if(pack.getId()!=null){
				Pack oldPack = packService.findOne(pack.getId());
				BeanCopyUtils.copyNullProperties(oldPack,pack);
				pack.setCreatedAt(oldPack.getCreatedAt());
			}
			packService.savePack(pack);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	/**
	 * 分页查看 绣花填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getPack", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPack(HttpServletRequest request,PageParameter page,Pack pack) {
		CommonResponse cr = new CommonResponse();
		cr.setData(packService.findPages(pack,page));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除绣花填写
	 * 
	 */
	@RequestMapping(value = "/product/deletePack", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePack(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					packService.deleteEmbroidery(id);
				}
			}
				cr.setMessage("删除成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("裁片id不能为空");
			}
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
