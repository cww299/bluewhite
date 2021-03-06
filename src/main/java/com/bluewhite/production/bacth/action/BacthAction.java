package com.bluewhite.production.bacth.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.excel.EasyExcel;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.production.bacth.entity.BacthPoi;
import com.bluewhite.production.temporarypack.UnderGoodsPoi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BacthAction {

    @Autowired
    private BacthService bacthService;
    @Autowired
    private ProcedureDao procedureDao;

    private ClearCascadeJSON clearCascadeJSON;

    {
        clearCascadeJSON = ClearCascadeJSON.get()
                .addRetainTerm(Bacth.class, "id", "machinist", "sign", "time", "statusTime", "bacthDeedlePrice",
                        "receive", "sumReworkPrice", "sumTaskPrice", "regionalPrice", "name", "allotTime", "number",
                        "createdAt", "remarks", "status", "bacthDepartmentPrice", "bacthHairPrice", "bacthNumber",
                        "price", "type", "product", "sumOutPrice")
                .addRetainTerm(Product.class, "id", "number", "name");
    }


    /**
     * 给产品添加批次（修改）
     * <p>
     * 是否完成（0=默认未完成，1=完成，status传参）
     * type (1=一楼质检，2=一楼包装，3=二楼针工,4=二楼机工)
     */
    @RequestMapping(value = "/bacth/addBacth", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse addBacth(Bacth bacth) {
        CommonResponse cr = new CommonResponse();
        if (bacth.getId() != null) {
            Bacth oldBacth = bacthService.findOne(bacth.getId());
            BeanCopyUtils.copyNullProperties(oldBacth, bacth);
            if (bacth.getFlag() == 0 && bacth.getRegionalPrice() != null) {
                bacth.setRegionalPrice(NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth.getSumTaskPrice(), bacth.getBacthHairPrice(), bacth.getBacthDepartmentPrice()), null));
            }
            List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(bacth.getProductId(), bacth.getType(), bacth.getFlag());
            double time = procedureList.stream().mapToDouble(Procedure::getWorkingTime).sum();
            if (procedureList.size() > 0) {
                bacth.setTime(NumUtils.div(NumUtils.mul(time, bacth.getNumber()), 60, 3));
            }
            bacthService.save(bacth);
            cr.setMessage("修改成功");
        } else {
            if (bacth.getProductId() != null) {
                bacthService.saveBacth(bacth);
                cr.setMessage("添加成功");
            } else {
                cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
                cr.setMessage("产品不能为空");
            }
        }
        return cr;
    }

    /**
     * 查询所有批次
     */
    @RequestMapping(value = "/bacth/allBacth", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse allBacth(Bacth bacth, PageParameter page) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSON.format(bacthService.findPages(bacth, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }


    /**
     * 删除批次
     */
    @RequestMapping(value = "/bacth/deleteBacth", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteBacth(String ids) {
        CommonResponse cr = new CommonResponse();
        if (ids != null) {
            bacthService.deleteBacth(ids);
            cr.setMessage("删除成功");
        } else {
            cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            cr.setMessage("批次不能为空");
        }
        return cr;
    }

    /**
     * 一键完成批次，改变status，会转入包装列表
     */
    @RequestMapping(value = "/bacth/statusBacth", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse statusBacth(HttpServletRequest request, String ids, Date time) {
        CommonResponse cr = new CommonResponse();
        int count = bacthService.statusBacth(ids, time, request);
        cr.setMessage("成功完成" + count + "批次");
        return cr;
    }

    /**************************** 一楼包装 *************************/

    /**
     * 一键接收批次，改变批次的数量，重新变成批次到包装列表，type=2
     */
    @RequestMapping(value = "/bacth/receiveBacth", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse receiveBacth(String[] ids, String[] numbers) {
        CommonResponse cr = new CommonResponse();
        int count = 0;
        try {
            count = bacthService.receiveBacth(ids, numbers);
        } catch (Exception e) {
            cr.setMessage(e.getMessage());
            cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            return cr;
        }
        cr.setMessage("成功完成" + count + "条批次");
        return cr;
    }


    /**************************** 二楼针工 *************************/

    /**
     * 针工导入批次
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/bacth/importBacths", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse excelOutProcurement(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        CommonResponse cr = new CommonResponse();
        InputStream inputStream = file.getInputStream();
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(inputStream, BacthPoi.class, excelListener).sheet().doRead();
        int count = bacthService.importBacths(excelListener);
        inputStream.close();
        cr.setMessage("成功导入" + count + "条批次");
        return cr;
    }


}
