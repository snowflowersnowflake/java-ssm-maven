package com.ecpbm.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecpbm.pojo.Pager;
import com.ecpbm.pojo.ProductInfo;
import com.ecpbm.service.ProductInfoService;

@Controller
@RequestMapping("/productinfo")
public class ProductInfoController {
	@Autowired
	ProductInfoService productInfoService;

	// 后台商品列表分页显示
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(Integer page, Integer rows, ProductInfo productInfo) {
		// 初始化分页类对象pager
		Pager pager = new Pager();
		pager.setCurPage(page);
		pager.setPerPageRows(rows);
		// 创建params对象，封装查询条件
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productInfo", productInfo);
		// 获取满足条件的商品总数
		int totalCount = productInfoService.count(params);
		// 获取满足条件的商品列表
		List<ProductInfo> productinfos = productInfoService.findProductInfo(productInfo, pager);
		// 创建result对象，保存查询结果数据
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("total", totalCount);
		result.put("rows", productinfos);
		// 将结果以JSON格式发送到前端控制器
		return result;
	}

	// 添加商品
	@RequestMapping(value = "/addProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String addProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		// 服务器端upload文件夹物理路径
		String path = request.getSession().getServletContext().getRealPath("product_images");
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 实例化一个File对象，表示目标文件（含物理路径）
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			// 将上传文件写到服务器上指定的文件
			file.transferTo(targetFile);
			pi.setPic(fileName);
			productInfoService.addProductInfo(pi);
			return "{\"success\":\"true\",\"message\":\"商品添加成功\"}";
		} catch (Exception e) {
			return "{\"success\":\"false\",\"message\":\"商品添加失败\"}";
		}
	}

	// 修改商品
	@RequestMapping(value = "/updateProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model) {
		// 服务器端upload文件夹物理路径
		String path = request.getSession().getServletContext().getRealPath("product_images");
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 实例化一个File对象，表示目标文件（含物理路径）
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			// 将上传文件写到服务器上指定的文件
			file.transferTo(targetFile);
			pi.setPic(fileName);
			productInfoService.modifyProductInfo(pi);
			return "{\"success\":\"true\",\"message\":\"商品修改成功\"}";
		} catch (Exception e) {
			return "{\"success\":\"false\",\"message\":\"商品修改失败\"}";
		}
	}

	// 商品下架(删除商品)
	@RequestMapping(value = "/deleteProduct", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteProduct(@RequestParam(value = "id") String id, @RequestParam(value = "flag") String flag) {
		String str = "";
		try {
			productInfoService.modifyStatus(id.substring(0, id.length() - 1), Integer.parseInt(flag));
			str = "{\"success\":\"true\",\"message\":\"删除成功\"}";
		} catch (Exception e) {
			str = "{\"success\":\"false\",\"message\":\"删除失败\"}";
		}
		return str;
	}
	
	// 获取在售商品列表
	@ResponseBody
	@RequestMapping("/getOnSaleProduct")
	public List<ProductInfo> getOnSaleProduct() {
		List<ProductInfo> piList = productInfoService.getOnSaleProduct();
		return piList;
	}
	
	// 根据商品id获取商品对象
	@ResponseBody
	@RequestMapping("/getPriceById")
	public String getPriceById(@RequestParam(value = "pid") String pid) {
		if (pid != null && !"".equals(pid)) {
			ProductInfo pi = productInfoService.getProductInfoById(Integer.parseInt(pid));
			return pi.getPrice() + "";
		}else{
			return "";
		}
	}

}
