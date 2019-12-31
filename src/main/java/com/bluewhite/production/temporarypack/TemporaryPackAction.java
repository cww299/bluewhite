package com.bluewhite.production.temporarypack;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

@Controller
public class TemporaryPackAction {

	@Autowired
	private UnderGoodsService underGoodsService;
	@Autowired
	private QuantitativeService quantitativeService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(UnderGoods.class, "id", "remarks", "product", "number",
				"bacthNumber", "status", "allotTime", "surplusStickNumber", "surplusSendNumber","internal")
				.addRetainTerm(Product.class, "id", "name");
	}
	private ClearCascadeJSON clearCascadeJSONQuantitative;
	{
		clearCascadeJSONQuantitative = ClearCascadeJSON.get()
				.addRetainTerm(Quantitative.class, "id", "quantitativeNumber", "time", "sumPackageNumber", "time",
						"quantitativeChilds", "packingMaterials", "user", "flag", "print","customer","audit","sendTime")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(QuantitativeChild.class, "id", "underGoods", "sumPackageNumber", "singleNumber",
						"number","actualSingleNumber","checks","remarks")
				.addRetainTerm(PackingMaterials.class, "id", "packagingMaterials", "packagingCount")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(BaseData.class, "id", "name")
				.addRetainTerm(UnderGoods.class, "id", "remarks", "product", "number", "bacthNumber", "status",
						"allotTime")
				.addRetainTerm(Product.class, "id", "name");
	}

	/**
	 * 新增下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/saveUnderGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveUnderGoods(UnderGoods underGoods) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.saveUnderGoods(underGoods);
		if (underGoods.getId() == null) {
			cr.setMessage("新增成功");
		} else {
			cr.setMessage("修改成功");
		}
		return cr;
	}

	/**
	 * 分页查询下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesUnderGoods(UnderGoods underGoods, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(underGoodsService.findPages(underGoods, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 查询下货单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findAllUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAllUnderGoods() {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(underGoodsService.findAll()).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除下货单
	 */
	@RequestMapping(value = "/temporaryPack/deleteUnderGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteUnderGoods(String ids) {
		CommonResponse cr = new CommonResponse();
		underGoodsService.delete(ids);
		cr.setMessage("删除成功");
		return cr;
	}

	/**
	 * 新增修改量化单
	 */
	@RequestMapping(value = "/temporaryPack/saveQuantitative", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveQuantitative(Quantitative quantitative) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(quantitative.getIds())) {
			if (quantitative.getSumPackageNumber() > 0) {
				for (int i = 0; i < quantitative.getSumPackageNumber(); i++) {
					Quantitative ot = new Quantitative();
					BeanCopyUtils.copyNotEmpty(quantitative, ot, "");
					quantitative.setId(null);
					quantitativeService.saveQuantitative(ot);
				}
			}
			cr.setMessage("新增成功");
		} else {
			String[] idArr = quantitative.getIds().split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative ot = new Quantitative();
					BeanCopyUtils.copyNotEmpty(quantitative, ot, "");
					ot.setId(id);
					//子单内容无法批量修改
					if(idArr.length > 1){
						Quantitative qt = quantitativeService.findOne(ot.getId());
						JSONArray jsonArray = new JSONArray();
						if(qt.getQuantitativeChilds().size()>0){
							for(QuantitativeChild quantitativeChild : qt.getQuantitativeChilds()){
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("id", quantitativeChild.getId());
								jsonObject.put("underGoodsId", quantitativeChild.getUnderGoodsId());
								jsonObject.put("singleNumber", quantitativeChild.getSingleNumber());
								jsonArray.add(jsonObject);
							}
						}
						ot.setChild(jsonArray.toJSONString());
					}
					quantitativeService.saveQuantitative(ot);
				}
			}
			cr.setMessage("修改成功");
		}
		return cr;
	}
	
	
	/**
	 * 审核 量化单
	 */
	@RequestMapping(value = "/temporaryPack/auditQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.auditQuantitative(ids);
		cr.setMessage("审核成功");
		return cr;
	}
	
	
	/**
	 * 对 量化单 进行实际发货数字的补录
	 */
	@RequestMapping(value = "/temporaryPack/setActualSingleNumber", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse setActualSingleNumber(Long id,Integer actualSingleNumber) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.setActualSingleNumber(id,actualSingleNumber);
		cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 对 量化单子单 进行修改
	 */
	@RequestMapping(value = "/temporaryPack/updateActualSingleNumber", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateActualSingleNumber(QuantitativeChild quantitativeChild) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.updateActualSingleNumber(quantitativeChild);
		cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 对 量化单 实际数字和贴包数字进行核对
	 */
	@RequestMapping(value = "/temporaryPack/checkNumber", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse checkNumber(Long id,Integer check) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.checkNumber(id,check);
		cr.setMessage("核对成功");
		return cr;
	}


	/**
	 * 发货 量化单
	 */
	@RequestMapping(value = "/temporaryPack/sendQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse sendQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.sendQuantitative(ids);
		cr.setMessage("发货成功");
		return cr;
	}

	/**
	 * 打印 量化单
	 */
	@RequestMapping(value = "/temporaryPack/printQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse printQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.printQuantitative(ids);
		cr.setMessage("打印成功");
		return cr;
	}

	/**
	 * 查询量化单
	 * 
	 */
	@RequestMapping(value = "/temporaryPack/findPagesQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findPagesQuantitative(Quantitative quantitative, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONQuantitative.format(quantitativeService.findPages(quantitative, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除量化单
	 */
	@RequestMapping(value = "/temporaryPack/deleteQuantitative", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteQuantitative(String ids) {
		CommonResponse cr = new CommonResponse();
		quantitativeService.deleteQuantitative(ids);
		cr.setMessage("删除成功");
		return cr;
	}

	/**
	 * 新增下货单(导入)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/temporaryPack/import/excelUnderGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelOutProcurement(@RequestParam(value = "file", required = false) MultipartFile file,
			Long userId, Long warehouseId) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, UnderGoodsPoi.class), excelListener);
		int count = underGoodsService.excelUnderGoods(excelListener);
		inputStream.close();
		cr.setMessage("成功导入" + count + "条下货单");
		return cr;
	}

}
