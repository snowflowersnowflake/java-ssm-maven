package com.ecpbm.service;

import java.util.List;
import java.util.Map;

import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;

public interface ProductInfoService {
	// 分页显示商品
	List<ProductInfo> findProductInfo(ProductInfo productInfo, Pager pager);

	// 商品计数
	Integer count(Map<String, Object> params);

	// 添加商品
	public void addProductInfo(ProductInfo pi);
	
	// 修改商品
	public void modifyProductInfo(ProductInfo pi);
	
	// 更新商品状态
	void modifyStatus(String ids, int flag);

	// 获取在售商品列表
	List<ProductInfo> getOnSaleProduct(); 
	
	// 根据产品id获取产品对象
	ProductInfo getProductInfoById(int id);
}
