package com.ecpbm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ecpbm.pojo.AdminInfo;
import com.ecpbm.pojo.Functions;
import com.ecpbm.pojo.TreeNode;
import com.ecpbm.service.AdminInfoService;
import com.ecpbm.util.JsonFactory;

@SessionAttributes(value = { "admin" })
@Controller
@RequestMapping("/admininfo")
public class AdminInfoController {
	@Autowired
	private AdminInfoService adminInfoService;

	@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(AdminInfo ai, ModelMap model) {
		// 后台登录验证
		AdminInfo admininfo = adminInfoService.login(ai);
		if (admininfo != null && admininfo.getName() != null) {
			// 验证通过后，再判断是否已为该管理员分配功能权限
			if (adminInfoService.getAdminInfoAndFunctions(admininfo.getId()).getFs().size() > 0) {
				// 验证通过且已分配功能权限，则将admininfo对象存入model中
				model.put("admin", admininfo);
				// 以JSON格式向页面发送成功信息
				return "{\"success\":\"true\",\"message\":\"登录成功\"}";
			} else {
				return "{\"success\":\"false\",\"message\":\"您没有权限，请联系超级管理员设置权限！\"}";
			}
		} else
			return "{\"success\":\"false\",\"message\":\"登录失败\"}";
	}

@RequestMapping("getTree")
@ResponseBody
public List<TreeNode> getTree(@RequestParam(value = "adminid") String adminid) {
	// 根据管理员编号，获取AdminInfo对象
	AdminInfo admininfo = adminInfoService.getAdminInfoAndFunctions(Integer.parseInt(adminid));
	List<TreeNode> nodes = new ArrayList<TreeNode>();
	// 获取关联的Functions对象集合
	List<Functions> functionsList = admininfo.getFs();
	// 对List<Functions>类型的Functions对象集合排序
	Collections.sort(functionsList);
	// 将排序后的Functions对象集合转换到List<TreeNode>类型的列表nodes
	for (Functions functions : functionsList) {
		TreeNode treeNode = new TreeNode();
		treeNode.setId(functions.getId());
		treeNode.setFid(functions.getParentid());
		treeNode.setText(functions.getName());
		nodes.add(treeNode);
	}
	// 调用自定义的工具类JsonFactory的buildtree方法，为nodes列表中的各个TreeNode元素中的
	// children属性赋值(该节点包含的子节点)
	List<TreeNode> treeNodes = JsonFactory.buildtree(nodes, 0);
	return treeNodes;
}

	// 退出
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public String logout(SessionStatus status) {
		// @SessionAttributes清除
		status.setComplete();
		return "{\"success\":\"true\",\"message\":\"注销成功\"}";
	}
}
