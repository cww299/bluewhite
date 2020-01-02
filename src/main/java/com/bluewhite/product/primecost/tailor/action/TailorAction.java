package com.bluewhite.product.primecost.tailor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.OrdinaryLaserService;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;

@Controller
public class TailorAction {

    @Autowired
    private TailorService tailorService;
    @Autowired
    private OrdinaryLaserService ordinaryLaserService;

    private ClearCascadeJSON clearCascadeJSON;
    {
        clearCascadeJSON = ClearCascadeJSON.get()
            .addRetainTerm(Tailor.class, "id", "productId", "number", "cutPartsId", "ordinaryLaserId", "embroideryId",
                "tailorName", "tailorNumber", "bacthTailorNumber", "tailorSize", "tailorType", "tailorTypeId",
                "managePrice", "experimentPrice", "ratePrice", "costPrice", "costPriceSelect", "allCostPrice",
                "scaleMaterial", "priceDown", "noeMbroiderPriceDown", "embroiderPriceDown", "machinistPriceDown",
                "oneCutPrice")
            .addRetainTerm(BaseThree.class, "id", "ordinaryLaser");
    }

    private ClearCascadeJSON clearCascadeJSONOrdinaryLaser;
    {
        clearCascadeJSONOrdinaryLaser = ClearCascadeJSON.get()
            .addRetainTerm(OrdinaryLaser.class, "id", "productId", "number", "tailorId", "tailorName", "tailorNumber",
                "bacthTailorNumber", "tailorSize", "tailorType", "tailorTypeId", "managePrice", "perimeter",
                "layerNumber", "typesettingNumber", "stallPoint", "singleDouble", "time", "embroiderTime",
                "otherTimeOne", "otherTimeTwo", "otherTimeThree", "rabbTime", "overlappedSeconds", "punchingSeconds",
                "electricSeconds", "singleLaserTime", "singleLaserHandTime", "singleLaserHandTime", "permSeconds",
                "tearingSeconds", "manualSeconds", "permPrice", "tearingPrice", "labourCost", "equipmentPrice",
                "administrativeAtaff", "stallPrice", "lamination", "oneCutPrice")
            .addRetainTerm(BaseThree.class, "id", "ordinaryLaser");
    }

    /**
     * 裁剪填写
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/addTailor", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addTailor(Tailor tailor) {
        CommonResponse cr = new CommonResponse();
        Tailor oldTailor = tailorService.findOne(tailor.getId());
        BeanCopyUtils.copyNotEmpty(tailor, oldTailor, "");
        NumUtils.setzro(oldTailor);
        tailorService.saveTailor(oldTailor);
        cr.setMessage("添加成功");
        return cr;
    }

    /**
     * 分页查看裁剪
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/getTailor", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getTailor(PageParameter page, Tailor tailor) {
        CommonResponse cr = new CommonResponse();
        PageResult<Tailor> tailorList = tailorService.findPages(tailor, page);
        cr.setData(clearCascadeJSON.format(tailorList).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

    /**
     * (裁剪普通激光,绣花定位激光，冲床，电烫，电推，手工剪刀)填写修改
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/addOrdinaryLaser", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addOrdinaryLaser(OrdinaryLaser ordinaryLaser) {
        CommonResponse cr = new CommonResponse();
        OrdinaryLaser oldOrdinaryLaser = ordinaryLaserService.findOne(ordinaryLaser.getId());
        BeanCopyUtils.copyNotEmpty(ordinaryLaser, oldOrdinaryLaser, "");
        ordinaryLaserService.saveOrdinaryLaser(oldOrdinaryLaser);
        cr.setMessage("修改成功");
        return cr;
    }

    /**
     * 分页查看裁减类型实体
     * 
     * @return cr
     */
    @RequestMapping(value = "/product/getOrdinaryLaser", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getOrdinaryLaser(PageParameter page, OrdinaryLaser ordinaryLaser) {
        CommonResponse cr = new CommonResponse();
        PageResult<OrdinaryLaser> ordinaryLaserList = ordinaryLaserService.findPages(ordinaryLaser, page);
        cr.setData(clearCascadeJSONOrdinaryLaser.format(ordinaryLaserList).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

}
