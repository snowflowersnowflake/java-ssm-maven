package com.ecpbm.service;

import java.util.List;
import java.util.Map;
import com.ecpbm.pojo.*;

public interface OrderInfoService {
	// 分页显示订单
	List<OrderInfo> findOrderInfo(OrderInfo orderInfo, Pager pager);

	// 订单计数
	Integer count(Map<String, Object> params);

	// 添加订单主表
	public int addOrderInfo(OrderInfo oi);

	// 添加订单明细
	public int addOrderDetail(OrderDetail od);

	// 根据订单编号获取订单信息
	public OrderInfo getOrderInfoById(int id);

	// 更加订单编号获取订单明细信息
	public List<OrderDetail> getOrderDetailByOid(int oid);

	// 删除订单
	public int deleteOrder(int id);
}
