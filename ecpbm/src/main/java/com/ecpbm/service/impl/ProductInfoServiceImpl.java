package com.ecpbm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecpbm.dao.ProductInfoDao;
import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;
import com.ecpbm.service.ProductInfoService;

@Service("productInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	ProductInfoDao productInfoDao;

	public List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager) {
		// 创建对象params
		Map<String, Object> params = new HashMap<String, Object>();
		// 将封装有查询条件的productInfo对象放入params
		params.put("productInfo", productInfo);
		// 根据条件计算商品总数
		int recordCount = productInfoDao.count(params);
		// 给pager对象设置rowCount属性值(记录总数)
		pager.setRowCount(recordCount);
		if (recordCount > 0) {
			// 将page对象放入params
			params.put("pager", pager);
		}
		// 分页获取商品信息
		return productInfoDao.selectByPage(params);
	}

	public Integer count(Map<String, Object> params) {
		return productInfoDao.count(params);
	}

	public void addProductInfo(ProductInfo pi) {
		productInfoDao.save(pi);
	}

	public void modifyProductInfo(ProductInfo pi) {
		productInfoDao.edit(pi);
	}

	public void modifyStatus(String ids, int flag) {
		productInfoDao.updateState(ids, flag);
	}

	public List<ProductInfo> getOnSaleProduct() {
		return productInfoDao.getOnSaleProduct();
	}

	public ProductInfo getProductInfoById(int id) {
		return productInfoDao.getProductInfoById(id);
	}
}
