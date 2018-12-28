package com.bluewhite.product.primecost.needlework.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.machinist.service.MachinistService;
import com.bluewhite.product.primecost.needlework.dao.NeedleworkDao;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public class NeedleworkServiceImpl extends BaseServiceImpl<Needlework, Long> implements NeedleworkService {
	@Autowired
	private NeedleworkDao dao;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	@Autowired
	private MachinistService machinistService;
	@Autowired
	private TailorService tailorService;
	
	
	

	@Override
	@Transactional
	public Needlework saveNeedlework(Needlework needlework) {
		
		//自动将类型为null的属性赋值为0
		NumUtils.setzro(needlework);
		
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("needlework");
		// 单工序单只时间
		needlework.setSingleTime(
				needlework.getSecondaryPrice() == 0 ? needlework.getSeconds() : needlework.getSecondaryPrice());
		// 单工序单只放快手时间
		needlework.setSingleQuickTime(needlework.getSingleTime() * 1.08 * 1.25);
		// 工价/单只（含快手)
		needlework.setLabourCost(needlework.getHelpWorkPrice() * needlework.getSingleQuickTime());
		// 设备折旧和房水电费
		if(needlework.getHelpWork()!=null){
			needlework.setEquipmentPrice(needlework.getHelpWork().equals("冲棉技工")
					? (primeCoefficient.getDepreciation() + primeCoefficient.getLaserTubePriceSecond()
					+ primeCoefficient.getMaintenanceChargeSecond() + primeCoefficient.getPerSecondPrice())
							: (primeCoefficient.getDepreciation() + primeCoefficient.getLaserTubePriceSecond()
							+ primeCoefficient.getMaintenanceChargeSecond() + primeCoefficient.getPerSecondPriceTwo())
							* needlework.getSingleQuickTime());
		}
		// 管理人员费用
		needlework.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()
				* needlework.getSingleQuickTime() );
		//针工工序费用==针工价格
		needlework.setNeedleworkPrice(needlework.getLabourCost()+needlework.getEquipmentPrice()+needlework.getAdministrativeAtaff());
		//入成本批量价格
		needlework.setAllCostPrice(needlework.getNeedleworkPrice()*needlework.getNumber());
		//上道压价（整个皮壳）(从机工页面中获取所有为针工准备的压价  +  从裁剪页面中获取所有为机工准备的压价)
		List<Machinist> machinistList = machinistService.findByProductId(needlework.getProductId());
		//从机工页面中获取所有为针工准备的压价
		double needleworkPriceDown = machinistList.stream().filter(Machinist->Machinist.getNeedleworkPriceDown()!=null).mapToDouble(Machinist::getNeedleworkPriceDown).sum();
		List<Tailor> tailorList = tailorService.findByProductId(needlework.getProductId());
		//从裁剪页面中获取所有为机工准备的压价
		double machinistPriceDown = tailorList.stream().filter(Tailor->Tailor.getMachinistPriceDown()!=null).mapToDouble(Tailor::getMachinistPriceDown).sum();
		needlework.setPriceDown(machinistPriceDown+needleworkPriceDown);
		return dao.save((Needlework)NumUtils.setzro(needlework));

	}

	@Override
	public PageResult<Needlework> findPages(Needlework param, PageParameter page) {
		Page<Needlework> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Needlework> result = new PageResult<Needlework>(pages, page);

		return result;
	}

	@Override
	@Transactional
	public void deleteNeedlework(Long id) {
		dao.delete(id);

	}

	@Override
	public List<Needlework> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
