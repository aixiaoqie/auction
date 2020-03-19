package com.auction.manager.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.manager.service.SellerService;
import com.auction.pojo.TbSeller;

/**
 * SellerController 卖家Controller
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference(timeout = 10000)
	private SellerService sellerService;

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll() {
		return sellerService.findAll();
	}

	/**
	 * 返回全部列表
	 * 
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows) {
		return sellerService.findPage(page, rows);
	}

	/**
	 * 增加
	 * 
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public AuctionResult add(@RequestBody TbSeller seller) {
		try {
			// 给商家密码加密
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String newpwd = passwordEncoder.encode(seller.getPassword());
			// 把加密后密码设置到对象中
			seller.setPassword(newpwd);
			// 保存
			sellerService.add(seller);
			return new AuctionResult(200, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "增加失败");
		}
	}

	/**
	 * 修改
	 * 
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public AuctionResult update(@RequestBody TbSeller seller) {
		try {
			sellerService.update(seller);
			return new AuctionResult(200, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "修改失败");
		}
	}

	/**
	 * 获取实体
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(Integer id) {
		return sellerService.findOne(id);
	}

	/**
	 * 获取实体
	 * 
	 * @param sellerId
	 * @return
	 */
	@RequestMapping("/findOneBySellerId")
	public TbSeller findOneBySellerId(String sellerId) {
		String str = null;
		try {
			str = new String(sellerId.getBytes("ISO8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sellerService.findOneBySellerId(str);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/delete")
	public AuctionResult delete(Integer[] ids) {
		try {
			sellerService.delete(ids);
			return new AuctionResult(200, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "删除失败");
		}
	}

	/**
	 * 查询+分页
	 * 
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int rows) {
		return sellerService.findPage(seller, page, rows);
	}

	@RequestMapping("/updateSellerStatus")
	public AuctionResult updateSellerStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
		return sellerService.updateStatus(id, status);

	}

}
