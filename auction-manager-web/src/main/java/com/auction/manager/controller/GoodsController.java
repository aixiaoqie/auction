package com.auction.manager.controller;
import java.util.List;

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

	@Reference(timeout=10000)
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
	public AuctionResult delete(String[] ids){
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
		return goodsService.findPage(goods, page, rows);		
	}
	/**
	 * 需求:更新商品状态(运营商审核商家商品)
	 * 请求:updateGoodsStatus
	 * 参数:Long[] ids,String status
	 * 返回值:AuctionResult
	 */
	@RequestMapping("/updateGoodsStatus")
	public AuctionResult updateGoodsStatus(String[] ids,String status){
		//调用服务
		AuctionResult result = goodsService.updateGoodsStatus(ids, status);
		return result;
	}
	
}
