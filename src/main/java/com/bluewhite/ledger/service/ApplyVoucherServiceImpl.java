package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.ApplyVoucherDao;
import com.bluewhite.ledger.entity.ApplyVoucher;

@Service
public class ApplyVoucherServiceImpl extends BaseServiceImpl<ApplyVoucher, Long> implements ApplyVoucherService {

	@Autowired
	private ApplyVoucherDao dao;

	@Override
	public void saveApplyVoucher(ApplyVoucher applyVoucher) {
		applyVoucher.setTime(new Date());
		applyVoucher.setApplyNumber(
				Constants.SQD + StringUtil.getDate() + StringUtil.get0LeftString((int) (count() + 1), 8));
		save(applyVoucher);
	}

	@Override
	public PageResult<ApplyVoucher> findPages(ApplyVoucher param, PageParameter page) {
		Page<ApplyVoucher> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名字
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("sendGoods").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.like(root.get("sendGoods").get("product").get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductNumber()) + "%"));
			}
			// 按被申请人员id
			if (param.getApprovalUserId() != null) {
				predicate.add(cb.equal(root.get("approvalUserId").as(Long.class), param.getApprovalUserId()));
			}
			// 按申请人员id
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 是否通过
			if (param.getPass() != null) {
				predicate.add(cb.equal(root.get("pass").as(Integer.class), param.getPass()));
			}
			// 按申请类型
			if (param.getApplyVoucherTypeId() != null) {
				predicate.add(cb.equal(root.get("applyVoucherTypeId").as(Long.class), param.getApplyVoucherTypeId()));
			}
			// 按申请种类
			if (param.getApplyVoucherKindId() != null) {
				predicate.add(cb.equal(root.get("applyVoucherKindId").as(Long.class), param.getApplyVoucherKindId()));
			}
			// 按申请日期日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())&& !StringUtils.isEmpty(param.getTime())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按申请通过日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())&& !StringUtils.isEmpty(param.getPassTime())) {
				predicate.add(cb.between(root.get("passTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<ApplyVoucher> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int passApplyVoucher(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				ApplyVoucher applyVoucher = findOne(id);
				if (applyVoucher.getPass() == 1) {
					throw new ServiceException("请求已通过，请勿重复操作");
				}
				applyVoucher.setPass(1);
				save(applyVoucher);
				i++;
			}
		}
		return i;
	}

	@Override
	public int deleteApplyVoucher(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				ApplyVoucher applyVoucher = dao.findOne(id);
				if (applyVoucher.getPass() == 1) {
					throw new ServiceException("请求已通过，无法撤销申请，请联系被申请人,取消通过");
				}
				delete(applyVoucher);
				i++;
			}
		}
		return i;
	}

	@Override
	public int cancelApplyVoucher(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				ApplyVoucher applyVoucher = dao.findOne(id);
				applyVoucher.setPass(0);
				save(applyVoucher);
				i++;
			}
		}
		return i;
	}
}
