package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.entity.PackingMaterials;

import cn.hutool.core.date.DateUtil;

@Service
public class QuantitativeServiceImpl extends BaseServiceImpl<Quantitative, Long> implements QuantitativeService {

	@Autowired
	private QuantitativeDao dao;
	@Autowired
	private UnderGoodsDao underGoodsDao;
	@Autowired
	private QuantitativeChildDao quantitativeChildDao;

	@Override
	public PageResult<Quantitative> findPages(Quantitative param, PageParameter page) {
		Page<Quantitative> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 是否打印
			if (param.getPrint() != null) {
				predicate.add(cb.equal(root.get("print").as(Integer.class), param.getPrint()));
			}
			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按商品名称过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				Join<Quantitative, QuantitativeChild> join = root
						.join(root.getModel().getList("quantitativeChilds", QuantitativeChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("underGoods").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号过滤
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(
						cb.equal(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按发货日期
            if (!StringUtils.isEmpty(param.getSendTime()) && !StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("sendTime").as(Date.class), param.getOrderTimeBegin(),
                        param.getOrderTimeEnd()));
            }
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			query.distinct(true);
			return null;
		}, page);
		PageResult<Quantitative> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void saveQuantitative(Quantitative quantitative) {
		CurrentUser cu = SessionManager.getUserSession();
		if (quantitative.getId() != null) {
			Quantitative ot = dao.findOne(quantitative.getId());
			if (ot.getAudit() == 1 && cu.getRole().contains("stickBagAccount")) {
				throw new ServiceException("已审核，无法修改");
			}
			if (ot.getFlag() == 1) {
				throw new ServiceException("已发货，无法修改");
			}
			quantitative.setQuantitativeNumber(ot.getQuantitativeNumber());
			quantitative.setAudit(ot.getAudit());
			quantitative.setPrint(ot.getPrint());
			quantitative.setFlag(ot.getFlag());
		} else {
			int count = dao.findByTimeBetween(DatesUtil.getfristDayOftime(quantitative.getTime()), DatesUtil.getLastDayOftime(quantitative.getTime())).size();
			quantitative.setQuantitativeNumber(Constants.LHTB + DateUtil.format(quantitative.getTime(), "yyyyMMdd") + 
					StringUtil.get0LeftString((count+1),4));
			quantitative.setAudit(0);
			quantitative.setPrint(0);
			quantitative.setFlag(0);
		}
		// 新增子单
		if (!StringUtils.isEmpty(quantitative.getChild())) {
			JSONArray jsonArray = JSON.parseArray(quantitative.getChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				//下货单id
				Long underGoodsId = jsonObject.getLong("underGoodsId");
				UnderGoods underGoods = underGoodsDao.findOne(underGoodsId);
				if (underGoods.getNumber() == null) {
					throw new ServiceException("贴包数量未填写，无法新增");
				}
				//子单，通过子单id查看是新增还是修改
				Long id = jsonObject.getLong("id");
				QuantitativeChild quantitativeChild = null;
				if(id!=null){
					quantitativeChild = quantitativeChildDao.findOne(id); 
				}else{
					//新增初始赋值
					quantitativeChild = new QuantitativeChild();
					quantitativeChild.setChecks(0);
					quantitativeChild.setUnderGoodsId(underGoodsId);
					quantitativeChild.setSingleNumber(0);
				}
				// 获取贴包数量，用于判断是否可以新增或者修改
				List<QuantitativeChild> stickListList = quantitativeChildDao.findByUnderGoodsId(underGoodsId);
				int numberStickSum = 0;
				if (stickListList.size() > 0) {
					numberStickSum = stickListList.stream().mapToInt(QuantitativeChild::getSingleNumber).sum();
				}
				underGoods.setSurplusStickNumber(underGoods.getNumber() - (numberStickSum -  quantitativeChild.getSingleNumber()));
				if (jsonObject.getInteger("singleNumber") > underGoods.getSurplusStickNumber()) {
					throw new ServiceException("剩余贴包数量不足，无法新增或修改");
				}
				
				quantitativeChild.setSingleNumber(jsonObject.getInteger("singleNumber"));
				quantitativeChild.setActualSingleNumber((id==null || quantitative.getAudit()==0 )? jsonObject.getInteger("singleNumber") : quantitativeChild.getActualSingleNumber());
				quantitative.getQuantitativeChilds().add(quantitativeChild);
			}
		}
		// 新增贴包物
		if (!StringUtils.isEmpty(quantitative.getPackingMaterialsJson())) {
			JSONArray jsonArrayMaterials = JSON.parseArray(quantitative.getPackingMaterialsJson());
			for (int i = 0; i < jsonArrayMaterials.size(); i++) {
				PackingMaterials packingMaterials = new PackingMaterials();
				JSONObject jsonObject = jsonArrayMaterials.getJSONObject(i);
				packingMaterials.setPackagingId(jsonObject.getLong("packagingId"));
				packingMaterials.setPackagingCount(jsonObject.getInteger("packagingCount"));
				quantitative.getPackingMaterials().add(packingMaterials);
			}
		}
		save(quantitative);
	}

	@Override
	public int auditQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					if (quantitative.getAudit() == 1) {
						throw new ServiceException("已审核请勿多次审核");
					}
					quantitative.setAudit(1);
					dao.save(quantitative);
				}
			}
		}
		return count;
	}

	@Override
	public int printQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					quantitative.setPrint(1);
					dao.save(quantitative);
				}
			}
		}
		return count;
	}

	@Override
	public int deleteQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					if (quantitative.getFlag() == 1) {
						throw new ServiceException("已发货无法删除");
					}
					dao.delete(id);
				}
			}
		}
		return count;
	}

	@Override
	public int sendQuantitative(String ids,Integer flag) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					if (flag == 1 && quantitative.getFlag() == 1) {
						throw new ServiceException("已发货请勿多次发货");
					}
					if (flag == 0 && quantitative.getFlag() == 0) {
                        throw new ServiceException("未发货请勿取消发货");
                    }
					quantitative.setSendTime(new Date());
					quantitative.setFlag(flag);
					dao.save(quantitative);
				}
			}
		}
		return count;
	}

	@Override
	public void setActualSingleNumber(Long id, Integer actualSingleNumber) {
		QuantitativeChild quantitativeChild = quantitativeChildDao.findOne(id);
		quantitativeChild.setActualSingleNumber(actualSingleNumber);
		if (quantitativeChild.getSingleNumber() != actualSingleNumber) {
			quantitativeChild.setChecks(1);
		}else{
			quantitativeChild.setChecks(0);
		}
		quantitativeChildDao.save(quantitativeChild);
	}

	@Override
	public void checkNumber(Long id, Integer check) {
		QuantitativeChild quantitativeChild = quantitativeChildDao.findOne(id);
		quantitativeChild.setChecks(check);
		quantitativeChildDao.save(quantitativeChild);
	}

	@Override
	public void updateActualSingleNumber(QuantitativeChild quantitativeChild) {
		QuantitativeChild ot = quantitativeChildDao.findOne(quantitativeChild.getId());
		BeanCopyUtils.copyNotEmpty(quantitativeChild, ot, "");
		quantitativeChildDao.save(ot);
	}

    @Override
    public int updateQuantitativeSendTime(String ids, Date sendTime) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    Quantitative quantitative = dao.findOne(id);
                    if (quantitative.getFlag() == 0) {
                        throw new ServiceException("第"+(count+1)+"条记录未发货无法修改");
                    }
                    quantitative.setSendTime(sendTime);
                    dao.save(quantitative);
                    count++;
                }
            }
        }
        return count;
    }

}
