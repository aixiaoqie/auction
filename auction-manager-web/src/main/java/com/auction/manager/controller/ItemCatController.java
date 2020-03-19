package com.auction.manager.controller;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.manager.service.ItemCatService;
import com.auction.pojo.TbItemCat;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService; 
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){			
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add")
	public AuctionResult add(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.add(itemCat);
			return new AuctionResult(200, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update")
	public AuctionResult update(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.update(itemCat);
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
	public TbItemCat findOne(Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public AuctionResult delete(Long [] ids){
		try {
			itemCatService.delete(ids);
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
	public PageResult search(@RequestBody TbItemCat itemCat, int page, int rows  ){
		return itemCatService.findPage(itemCat, page, rows);		
	}
	
	/**
	 * 需求:根据父id查询子节点
	 * 请求:/itemCat/findItemCatByParentId?parentId=
	 * 参数:Long parentId
	 * 返回值:List<tbItemCat>
	 */
	@RequestMapping("/findItemCatByParentId")
	public List<TbItemCat> findItemCatByParentId(Long parentId){
		//调用远程service服务对象
		List<TbItemCat> list = itemCatService.findItemCatByParentId(parentId);
		return list;
	}
	
}
