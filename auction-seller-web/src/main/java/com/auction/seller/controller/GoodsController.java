package com.auction.seller.controller;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.manager.service.GoodsService;
import com.auction.pojo.TbGoods;
import com.auction.vo.Goods;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference(timeout=10000000)
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public AuctionResult add(@RequestBody Goods goods){
		
		//获取当前商家帐号
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//把商家编号设置到商品对象中
		goods.getGoods().setSellerId(sellerId);
		
		//调用远程服务方法
		AuctionResult result = goodsService.add(goods);
		return result;
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public AuctionResult update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new AuctionResult(200, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);	
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public AuctionResult delete(String [] ids){
		try {
			goodsService.delete(ids);
			return new AuctionResult(200, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		//根据商家id查询
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//把商家id设置参数对象goods
		goods.setSellerId(sellerId);
		
		return goodsService.findPage(goods, page, rows);		
	}
	
	@RequestMapping("/updateAuditStatus")
	public AuctionResult updateGoodsStatus(String[] ids,String status) {
		try {
			goodsService.updateGoodsStatus(ids,status);
			return new AuctionResult(200,"提交审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100,"提交审核失败");
		}
	}
	
}
