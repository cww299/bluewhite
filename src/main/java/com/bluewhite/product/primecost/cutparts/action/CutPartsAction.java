package com.bluewhite.product.primecost.cutparts.action;

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

import com.alibaba.excel.EasyExcel;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.entity.poi.CutPartsPoi;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Controller
public class CutPartsAction {

    @Autowired
    private CutPartsService cutPartsService;

    private ClearCascadeJSON clearCascadeJSON;
    {
        clearCascadeJSON = ClearCascadeJSON.get()
            .addRetainTerm(CutParts.class, "id", "productId", "number", "tailorId", "overstock", "materiel",
                "cutPartsName", "loss", "cutPartsNumber", "perimeter", "allPerimeter", "oneMaterial", "unit",
                "scaleMaterial", "addMaterial", "manualLoss", "composite", "doubleComposite", "complexMateriel",
                "compositeManualLoss", "batchMaterial", "batchMaterialPrice", "complexBatchMaterial",
                "batchComplexMaterialPrice", "batchComplexAddPrice", "oneCutPartsPrice")
            .addRetainTerm(Materiel.class, "id", "number", "name", "price", "unit", "convertUnit")
            .addRetainTerm(BaseOne.class, "id", "name", "type");
    }

    /**
     * 裁片填写
     * 
     */
    @RequestMapping(value = "/product/addCutParts", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addCutParts(CutParts cutParts) {
        CommonResponse cr = new CommonResponse();
        if (StringUtils.isEmpty(cutParts.getProductId())) {
            cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            cr.setMessage("产品不能为空");
        } else {
            cutPartsService.saveCutParts(cutParts);
            cr.setMessage("添加成功");
        }
        return cr;
    }
    
    /**
     * 裁片导入
     * @throws IOException 
     * 
     */
    @RequestMapping(value = "/product/uploadCutParts", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse uploadCutParts(@RequestParam(value = "file", required = false) MultipartFile file,
    		Long productId) throws IOException {
        CommonResponse cr = new CommonResponse();
        if(productId == null) {
        	cr.setCode(1500);
        	cr.setMessage("商品id不能为空");
    		return cr;
        }
        InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcel.read(inputStream, CutPartsPoi.class, excelListener).sheet().doRead();
		int count = cutPartsService.uploadCutParts(excelListener, productId);
		inputStream.close();
		cr.setMessage("成功导入" + count + "条数据");
		return cr;
    }

    /**
     * 裁片修改
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/updateCutParts", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateCutParts(CutParts cutParts) {
        CommonResponse cr = new CommonResponse();
        if (StringUtils.isEmpty(cutParts.getId())) {
            cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            cr.setMessage("裁片不能为空");
        } else {
            CutParts oldCutParts = cutPartsService.findOne(cutParts.getId());
            BeanCopyUtils.copyNotEmpty(cutParts, oldCutParts, "");
            cutPartsService.saveCutParts(oldCutParts);
            cr.setMessage("修改成功");
        }
        return cr;
    }

    /**
     * 分页查看cc裁片
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/getCutParts", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getCutParts(PageParameter page, CutParts cutParts) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSON.format(cutPartsService.findPages(cutParts, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

    /**
     * 删除cc裁片
     * 
     */
    @RequestMapping(value = "/product/deleteCutParts", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteCutParts(String ids) {
        CommonResponse cr = new CommonResponse();
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    cutPartsService.deleteCutParts(id);
                }
            }
            cr.setMessage("删除成功");
        } else {
            cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            cr.setMessage("裁片id不能为空");
        }
        return cr;
    }

}
