package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

@Service
public class UnderGoodsServiceImpl extends BaseServiceImpl<UnderGoods, Long> implements UnderGoodsService {

	@Autowired
	private UnderGoodsDao dao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private QuantitativeChildDao quantitativeChildDao;
	@Autowired
	private QuantitativeDao quantitativeDao;
	@Autowired
	private MantissaLiquidationDao mantissaLiquidationDao;
  
	
	@Override
	public PageResult<UnderGoods> findPages(UnderGoods param, PageParameter page) {
		Page<UnderGoods> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getId()));
			}
			// 是否天猫
			if (param.getInternal() != null) {
				predicate.add(cb.equal(root.get("internal").as(Integer.class), param.getInternal()));
			}
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.like(root.get("product").get("number").as(String.class),
						"%" + param.getProductNumber() + "%"));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按状态
			if (!StringUtils.isEmpty(param.getStatus())) {
				predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
			}

			// 按生成时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("allotTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<UnderGoods> result = new PageResult<>(pages, page);
		//发货剩余数量
		//贴包剩余数量
		result.getRows().forEach(r->{
			//贴包数量
			List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(r.getId());
			int numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
			r.setSurplusStickNumber(r.getNumber()-numberStickSum);
			//发货数量
			List<Long> quantitativeListId = quantitativeDao.findSendNumber(r.getId());
			List<QuantitativeChild> quantitativeList = quantitativeChildDao.findByIdIn(quantitativeListId);
			int numberSendSum = quantitativeList.stream().mapToInt(QuantitativeChild::getActualSingleNumber).sum();		
			r.setSurplusSendNumber(r.getNumber()-numberSendSum);
			//尾数清算数量
			List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
			int numberMantissaLiquidationSum = mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
			r.setSurplusStickNumber(r.getSurplusStickNumber()-numberMantissaLiquidationSum);
		});
		return result;
	}
	
	
    @Override
    public PageResult<UnderGoods> findList(UnderGoods param) {
        PageParameter page = new PageParameter(0,15);
        Page<UnderGoods> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按id过滤
            if (param.getId() != null) {
                predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
            }
            // 按产品名称
            if (!StringUtils.isEmpty(param.getProductName())) {
                predicate.add(cb.like(root.get("product").get("name").as(String.class),
                        "%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        },page);
        PageResult<UnderGoods> result = new PageResult<>(pages, page);
        result.getRows().forEach(r->{
            r.setProductName(r.getProduct().getName());
            //贴包数量
            List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(r.getId());
            int numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
            r.setSurplusStickNumber(r.getNumber()-numberStickSum);
            //尾数清算数量
            List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
            int numberMantissaLiquidationSum = mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
            r.setSurplusStickNumber(r.getSurplusStickNumber()-numberMantissaLiquidationSum);
        });
        return result;
    }
    

	@Override
	public void saveUnderGoods(UnderGoods underGoods) {
	    underGoods.setStatus(0);
		save(underGoods);
	}

	@Override
	public int excelUnderGoods(ExcelListener excelListener) {
		List<Object> excelListenerList = excelListener.getData();
		List<UnderGoods> underGoodsList = new ArrayList<>();
		for (int i = 0; i < excelListenerList.size(); i++) {
			UnderGoods underGoods = new UnderGoods();
			UnderGoodsPoi cPoi = (UnderGoodsPoi) excelListenerList.get(i);
			Product product = productDao.findByName(cPoi.getName());
			if (product != null) {
				underGoods.setProductId(product.getId());
			} else {
				throw new ServiceException("当前导入excel第" + (i + 2) + "条数据的商品不存在，请先添加");
			}
			if (cPoi.getNumber() == null) {
				throw new ServiceException("当前导入excel第" + (i + 2) + "条数据的数量不存在，请先添加");
			}
			underGoods.setNumber(cPoi.getNumber());
			underGoods.setBacthNumber(cPoi.getBacthNumber());
			underGoods.setProductId(product.getId());
			underGoods.setAllotTime(cPoi.getAllotTime());
			underGoodsList.add(underGoods);
		}
		dao.save(underGoodsList);
		return underGoodsList.size();
	}

	@Override
	public List<UnderGoods> getAll() {
		List<UnderGoods> result = dao.findAll();
		List<Long> longList = result.stream().map(UnderGoods::getId).collect(Collectors.toList());
	     //贴包数量
        List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsIdIn(longList);
		//贴包剩余数量
		result.forEach(r->{
			int numberStickSum = stickListList.stream().filter(QuantitativeChild->r.getId().equals(QuantitativeChild.getUnderGoodsId())).mapToInt(QuantitativeChild::getSingleNumber).sum();
			r.setSurplusStickNumber(r.getNumber()-numberStickSum);
			//尾数清算数量
			List<MantissaLiquidation> mantissaLiquidationList = mantissaLiquidationDao.findByUnderGoodsId(r.getId());
			int numberMantissaLiquidationSum = mantissaLiquidationList.stream().mapToInt(MantissaLiquidation::getNumber).sum();
			r.setSurplusStickNumber(r.getSurplusStickNumber()-numberMantissaLiquidationSum);
		});
		return result;
	}

 

}
