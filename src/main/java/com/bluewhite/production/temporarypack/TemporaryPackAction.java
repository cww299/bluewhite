package com.bluewhite.production.temporarypack;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

@Controller
public class TemporaryPackAction {

    @Autowired
    private UnderGoodsService underGoodsService;
    @Autowired
    private QuantitativeService quantitativeService;
    @Autowired
    private QuantitativeChildDao quantitativeChildDao;
    @Autowired
    private MantissaLiquidationService mantissaLiquidationService;
    @Autowired
    private SendOrderService sendOrderService;

    private ClearCascadeJSON clearCascadeJSON;
    {
        clearCascadeJSON = ClearCascadeJSON.get()
            .addRetainTerm(UnderGoods.class, "id", "remarks", "product", "number", "bacthNumber", "status", "allotTime",
                "surplusStickNumber", "surplusSendNumber", "internal", "productName")
            .addRetainTerm(Product.class, "id", "name");
    }
    private ClearCascadeJSON clearCascadeJSONQuantitative;
    {
        clearCascadeJSONQuantitative = ClearCascadeJSON.get()
            .addRetainTerm(Quantitative.class, "id", "quantitativeNumber", "time", "sumPackageNumber", "time",
                "quantitativeChilds", "packingMaterials", "user", "flag", "print", "customer", "audit", "sendTime",
                "vehicleNumber", "packagMethod", "outPrice", "departmentPrice", "regionalPrice", "sumTaskPrice",
                "sumTime", "status", "number", "productCount", "location", "reservoirArea", "reconciliation")
            .addRetainTerm(Customer.class, "id", "name")
            .addRetainTerm(QuantitativeChild.class, "id", "underGoods", "sumPackageNumber", "singleNumber", "number",
                "actualSingleNumber", "checks", "remarks")
            .addRetainTerm(PackingMaterials.class, "id", "packagingMaterials", "packagingCount")
            .addRetainTerm(User.class, "id", "userName")
            .addRetainTerm(BaseData.class, "id", "name")
            .addRetainTerm(UnderGoods.class, "id", "remarks", "product", "number", "bacthNumber", "status", "allotTime")
            .addRetainTerm(Product.class, "id", "name");
    }
    private ClearCascadeJSON clearCascadeJSONMantissaLiquidation;
    {
        clearCascadeJSONMantissaLiquidation = ClearCascadeJSON.get()
            .addRetainTerm(MantissaLiquidation.class, "id", "underGoods", "mantissaNumber", "number", "time", "remarks",
                "type", "surplusNumber")
            .addRetainTerm(UnderGoods.class, "id", "remarks", "product", "number", "bacthNumber", "status",
                "allotTime");
    }
    private ClearCascadeJSON clearCascadeJSONSendOrder;
    {
        clearCascadeJSONSendOrder = ClearCascadeJSON.get()
            .addRetainTerm(SendOrder.class, "id", "customer", "sendOrderChild", "sendTime", "sumPackageNumber",
                "number", "sendPackageNumber", "logistics", "outerPackaging", "logisticsNumber", "tax", "singerPrice",
                "sendPrice", "extraPrice", "logisticsPrice", "audit", "warehouseType","remarks")
            .addRetainTerm(BaseData.class, "id", "name")
            .addRetainTerm(Customer.class, "id", "name");
    }

    /**
     * 新增下货单
     * 
     */
    @RequestMapping(value = "/temporaryPack/saveUnderGoods", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveUnderGoods(UnderGoods underGoods) {
        CommonResponse cr = new CommonResponse();
        if (underGoods.getId() == null) {
            underGoodsService.saveUnderGoods(underGoods);
            cr.setMessage("新增成功");
        } else {
            underGoodsService.updateUnderGoods(underGoods);
            cr.setMessage("修改成功");
        }
        return cr;
    }

    /**
     * 分页查询下货单
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
     */
    @RequestMapping(value = "/temporaryPack/findUnderGoods", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse findUnderGoods(UnderGoods underGoods) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSON.format(underGoodsService.findList(underGoods).getRows()).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

    /**
     * 查询下货单
     */
    @RequestMapping(value = "/temporaryPack/findAllUnderGoods", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse findAllUnderGoods() {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSON.format(underGoodsService.getAll()).toJSON());
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
    public CommonResponse saveQuantitative(Quantitative quantitative, String ids) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.saveUpdateQuantitative(quantitative, ids);
        if (StringUtils.isEmpty(ids)) {
            cr.setMessage("新增成功");
        } else {
            cr.setMessage("修改成功");
        }
        return cr;
    }

    /**
     * 审核 量化单
     */
    @RequestMapping(value = "/temporaryPack/auditQuantitative", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse auditQuantitative(String ids, Integer audit) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.auditQuantitative(ids, audit);
        cr.setMessage("成功");
        return cr;
    }

    /**
     * 对账 量化单
     */
    @RequestMapping(value = "/temporaryPack/reconciliationQuantitative", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse reconciliationQuantitative(String ids, Integer reconciliation) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.reconciliationQuantitative(ids, reconciliation);
        cr.setMessage("成功");
        return cr;
    }

    /**
     * 对 量化单 进行实际发货数字的补录
     */
    @RequestMapping(value = "/temporaryPack/setActualSingleNumber", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse setActualSingleNumber(Long id, Integer actualSingleNumber) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.setActualSingleNumber(id, actualSingleNumber);
        cr.setMessage("实际发货数字修正成功");
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
        cr.setMessage("成功");
        return cr;
    }

    /**
     * 对 量化单 实际数字和贴包数字进行核对
     */
    @RequestMapping(value = "/temporaryPack/checkNumber", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse checkNumber(Long id) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.checkNumber(id);
        cr.setMessage("核对成功");
        return cr;
    }

    /**
     * 发货 上车编号
     */
    @RequestMapping(value = "/temporaryPack/sendQuantitative", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse sendQuantitative(String ids, Integer flag, String vehicleNumber, Long logisticsId,
        Long outerPackagingId) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.sendQuantitative(ids, flag, vehicleNumber, logisticsId, outerPackagingId);
        if (flag == 0) {
            cr.setMessage("取消发货");
        } else {
            cr.setMessage("成功发货");
        }
        return cr;
    }

    /**
     * 批量修改量化单的发货时间
     */
    @RequestMapping(value = "/temporaryPack/updateQuantitativeSendTime", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse updateQuantitativeSendTime(String ids, Date sendTime) {
        CommonResponse cr = new CommonResponse();
        int count = quantitativeService.updateQuantitativeSendTime(ids, sendTime);
        cr.setMessage("成功修改" + count + "条量化单的发货时间");
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
     * 删除量化子单
     */
    @RequestMapping(value = "/temporaryPack/deleteQuantitativeChild", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteQuantitativeChild(Long id) {
        CommonResponse cr = new CommonResponse();
        quantitativeChildDao.delete(id);
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
        EasyExcel.read(inputStream, UnderGoodsPoi.class, excelListener).sheet().doRead();
        int count = underGoodsService.excelUnderGoods(excelListener);
        inputStream.close();
        cr.setMessage("成功导入" + count + "条下货单");
        return cr;
    }

    /**
     * 新增尾数清算单
     */
    @RequestMapping(value = "/temporaryPack/saveMantissaLiquidation", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveMantissaLiquidation(MantissaLiquidation mantissaLiquidation) {
        CommonResponse cr = new CommonResponse();
        if (mantissaLiquidation.getId() == null) {
            cr.setMessage("新增成功");
        } else {
            cr.setMessage("修改成功");
        }
        mantissaLiquidationService.saveMantissaLiquidation(mantissaLiquidation);
        return cr;
    }

    /**
     * 分页查询尾数清算单
     */
    @RequestMapping(value = "/temporaryPack/findPagesMantissaLiquidation", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse findPagesMantissaLiquidation(@RequestParam Map<String, Object> params) {
        CommonResponse cr = new CommonResponse();
        if (MapUtil.isEmpty(params)) {
            throw new ServiceException("参数不能为空");
        } ;
        PageParameter page = PageUtil.mapToPage(params);
        cr.setData(
            clearCascadeJSONMantissaLiquidation.format(mantissaLiquidationService.findPages(params, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

    /**
     * 删除尾数清算单
     */
    @RequestMapping(value = "/temporaryPack/deleteMantissaLiquidation", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteMantissaLiquidation(String ids) {
        CommonResponse cr = new CommonResponse();
        int count = mantissaLiquidationService.delete(ids);
        cr.setMessage("成功删除" + count + "条数据");
        return cr;
    }

    /**
     * 尾数出库成为下货单
     */
    @RequestMapping(value = "/temporaryPack/mantissaLiquidationToUnderGoods", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse mantissaLiquidationToUnderGoods(MantissaLiquidation mantissaLiquidation) {
        CommonResponse cr = new CommonResponse();
        mantissaLiquidationService.mantissaLiquidationToUnderGoods(mantissaLiquidation);
        cr.setMessage("出库成功");
        return cr;
    }

    /**
     * 发货单(导出)
     * 
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/temporaryPack/import/excelQuantitative", method = RequestMethod.GET)
    @ResponseBody
    public void excelQuantitative(HttpServletResponse response, Quantitative quantitative) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=sendorder.xlsx");
        List<QuantitativePoi> quantitativePoiList = structureQuantitativeList(quantitative);
        // 按合并策略 合并单元格
        AutoMergeStrategy autoMergeStrategy =
            new AutoMergeStrategy(quantitativePoiList, getGroupData(quantitativePoiList));
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(response.getOutputStream(), QuantitativePoi.class)
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).registerWriteHandler(autoMergeStrategy)
            .sheet("发货单").doWrite(quantitativePoiList);
    }

    private List<Integer> getGroupData(List<QuantitativePoi> quantitativePoiList) {
        List<Integer> integerList = new ArrayList<Integer>();
        LinkedHashMap<Long, List<QuantitativePoi>> mapList = quantitativePoiList.stream()
            .collect(Collectors.groupingBy(QuantitativePoi::getId, LinkedHashMap::new, Collectors.toList()));
        for (Long ps : mapList.keySet()) {
            List<QuantitativePoi> quantitativeList = mapList.get(ps);
            integerList.add(quantitativeList.size());
        }
        return integerList;
    }

    /**
     * 构建实际发货单
     * 
     * @param quantitative
     * @return
     */
    private List<QuantitativePoi> structureQuantitativeList(Quantitative quantitative) {
        List<QuantitativePoi> quantitativePoiList = new ArrayList<QuantitativePoi>();
        List<Quantitative> quantitativeList =
            quantitativeService.findPages(quantitative, new PageParameter(0, Integer.MAX_VALUE)).getRows();
        quantitativeList.stream().forEach(q -> {
            q.getQuantitativeChilds().forEach(c -> {
                QuantitativePoi quantitativePoi = new QuantitativePoi();
                if (q.getCustomer() != null) {
                    quantitativePoi.setCustomerName(q.getCustomer().getName());
                    quantitativePoi.setCustomerName1(q.getCustomer().getName());
                }
                if (q.getUser() != null) {
                    quantitativePoi.setUserName(q.getUser().getUserName());
                }
                quantitativePoi.setId(q.getId());
                quantitativePoi.setQuantitativeNumber(q.getQuantitativeNumber());
                quantitativePoi.setVehicleNumber(q.getVehicleNumber());
                quantitativePoi.setName(c.getUnderGoods().getProduct().getName());
                quantitativePoi.setName1(c.getUnderGoods().getProduct().getName());
                quantitativePoi.setNumber(c.getActualSingleNumber());
                quantitativePoi.setNumber1(c.getActualSingleNumber());
                quantitativePoi.setRemark(c.getRemarks());
                quantitativePoi.setSendTime(q.getSendTime());
                quantitativePoi.setSendTime1(q.getSendTime());
                quantitativePoi.setBacth(c.getUnderGoods().getBacthNumber());
                quantitativePoi.setBacth1(c.getUnderGoods().getBacthNumber());
                quantitativePoiList.add(quantitativePoi);
            });
        });
        return quantitativePoiList;
    }

    /***************************** 发货单 ***************************/
    /**
     * 分页查看发货单
     * 
     * @return cr
     */
    @RequestMapping(value = "/ledger/sendOrderPage", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse sendOrderPage(@RequestParam Map<String, Object> params) {
        CommonResponse cr = new CommonResponse();
        if (MapUtil.isEmpty(params)) {
            throw new ServiceException("参数不能为空");
        } ;
        PageParameter page = PageUtil.mapToPage(params);
        cr.setData(clearCascadeJSONSendOrder.format(sendOrderService.findPages(params, page)).toJSON());
        cr.setMessage("查询成功");
        return cr;
    }

    /**
     * 修改发货单
     */
    @RequestMapping(value = "/temporaryPack/updateSendOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateSendOrder(SendOrder sendOrder) {
        CommonResponse cr = new CommonResponse();
        sendOrderService.updateSendOrder(sendOrder);
        cr.setMessage("修改成功");
        return cr;
    }

    /**
     * 查看发货单实际已发货的贴包明细
     */
    @RequestMapping(value = "/temporaryPack/getQuantitativeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse getQuantitativeList(Long id) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSONQuantitative.format(sendOrderService.getQuantitativeList(id)).toJSON());
        cr.setMessage("成功");
        return cr;
    }

    /**
     * 生成物流费用（审核发货单之后,已经产生费用的贴包单，无法删除，同时在财务物流费用中自动生成费用） 取消审核，费用自动从物流费用中删除
     */
    @RequestMapping(value = "/temporaryPack/auditSendOrder", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse auditSendOrder(String ids, Date expenseDate, Date expectDate) {
        CommonResponse cr = new CommonResponse();
        sendOrderService.auditSendOrder(ids, expenseDate, expectDate);
        cr.setMessage("物流费用生成成功");
        return cr;
    }

    /**
     * 批量修改
     * 
     * @param ids
     * @param audit
     * @return
     */
    @RequestMapping(value = "/temporaryPack/bacthUpdate", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse bacthUpdate(SendOrder sendOrder, String ids) {
        CommonResponse cr = new CommonResponse();
        int count = sendOrderService.bacthUpdate(sendOrder, ids);
        cr.setMessage("成功修改" + count + "数据");
        return cr;
    }
    

    /**
     * 从发货明细中重新生成发货单
     */
    @RequestMapping(value = "/temporaryPack/reCreatSendOrder", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse putWarehousing(String ids) {
        CommonResponse cr = new CommonResponse();
        quantitativeService.reCreatSendOrder(ids);
        cr.setMessage("生成成功");
        return cr;
    }
    

    /**
     * 扫码发货页面
     */
    @RequestMapping(value = "/twoDimensionalCode/scanSendOrder", method = RequestMethod.GET)
    public ModelAndView scanSend(Long id) {
        ModelAndView mav = new ModelAndView();
        Quantitative quantitative = quantitativeService.findOne(id);
        if (quantitative.getFlag() == 1) {
            mav.setViewName("/visitor/scanSend");
            mav.addObject("data", clearCascadeJSONQuantitative.format(quantitative).toJSON());
            return mav;
        }
        // 通过量化单的发货时间进行查询出当前上车的编号进度
        if (quantitative.getCustomerId() != null && quantitative.getCustomerId() == (long)363) {
            List<Quantitative> quantitativeList = quantitativeService.findBySendTime(DateUtil.beginOfDay(new Date()),
                DateUtil.endOfDay(new Date()), quantitative.getWarehouseTypeId());
            compareVehicleNumber(quantitativeList);
            if (quantitativeList.size() > 0) {
                int count = 0;
                // 获取最后一个上车编号
                String vehicleNumber = StrUtil.sub(quantitativeList.get(0).getVehicleNumber(), 12, 16);
                // 获取上车发货总包数
                long number = quantitativeList.stream()
                    .filter(Quantitative -> StrUtil.sub(Quantitative.getVehicleNumber(), 12, 16).equals(vehicleNumber))
                    .count();
                count = Integer.valueOf(vehicleNumber);
                quantitative.setVehicleNumber(count + "-" + (number + 1));
            } else {
                quantitative.setVehicleNumber(1 + "-" + 1);
            }
        }
        mav.setViewName("/visitor/scanSend");
        mav.addObject("data", clearCascadeJSONQuantitative.format(quantitative).toJSON());
        return mav;
    }

    /**
     * 按上车重新排序
     * 
     * @param list
     * @return
     */
    private List<Quantitative> compareVehicleNumber(List<Quantitative> list) {
        Collections.sort(list, new Comparator<Quantitative>() {
            @Override
            public int compare(Quantitative q1, Quantitative q2) {
                int a = Integer.valueOf(StrUtil.sub(q1.getVehicleNumber(), 12, 16));
                int b = Integer.valueOf(StrUtil.sub(q2.getVehicleNumber(), 12, 16));
                return b - a;
            }
        });
        return list;
    }

    /**
     * 自动检测 量化单以量化时间过长为发货的进行入库
     */
    @RequestMapping(value = "/temporaryPack/checkWarehousing", method = RequestMethod.GET)
    @ResponseBody
    public CommonResponse warehousing(int page, int size) {
        CommonResponse cr = new CommonResponse();
        cr.setData(clearCascadeJSONQuantitative.format(quantitativeService.warehousing(page, size)).toJSON());
        cr.setMessage("成功");
        return cr;
    }

    /**
     * 入库 库位库区
     */
    @RequestMapping(value = "/temporaryPack/putWarehousing", method = RequestMethod.POST)
    @ResponseBody
    public CommonResponse putWarehousing(String ids, String location, String reservoirArea) {
        CommonResponse cr = new CommonResponse();
        int count = quantitativeService.putWarehousing(ids, location, reservoirArea);
        cr.setMessage("成功入库" + count + "条数据");
        return cr;
    }

}
