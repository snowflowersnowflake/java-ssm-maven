package com.ecpbm.service;

import com.ecpbm.pojo.AdminInfo;

public interface AdminInfoService {
	// 登录验证
	public AdminInfo login(AdminInfo ai);

	// 根据管理员编号，获取管理员对象及关联的功能权限
	public AdminInfo getAdminInfoAndFunctions(Integer id);
}
