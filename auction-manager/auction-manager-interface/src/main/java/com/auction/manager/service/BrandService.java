package com.auction.manager.service;

import java.util.List;
import java.util.Map;

import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.pojo.TbBrand;


public interface BrandService {
	
	/**
	 * 需求:查询所有品牌数据
	 */
	public List<TbBrand> findAll();
	/**
	 * 需求:分页展示品牌列表
	 * 参数:Integer page,Integer rows
	 * 返回值:分页包装类对象PageResult
	 */
	public PageResult findPage(Integer page,Integer rows);
	/**
	 * 需求:添加品牌数据
	 * 参数:TbBrand brand
	 * 返回值:AuctionResult
	 * 
	 */
	public AuctionResult add(TbBrand brand);
	/**
	 * 需求:根据id查询品牌数据
	 * 参数:Long id
	 * 返回值:TbBrand
	 */
	public TbBrand findOne(Long id);
	/**
	 * 需求:更新品牌数据
	 * 参数:TbBrand brand
	 * 返回值:AuctionResult
	 */
	public AuctionResult update(TbBrand brand);
	/**
	 * 需求:品牌条件查询
	 * 参数:TbBrand brand
	 * 返回值:AuctionResult
	 */
	public PageResult findBrandByPage(TbBrand brand,Integer page,Integer rows);
	/**
	 * 需求: 批量删除品牌数据
	 * 参数: Long[] id
	 * 返回值:AuctionResult
	 */
	public AuctionResult deleteIds(Long[] ids);
	/**
	 * 需求:查询所有品牌
	 * 参数:无
	 * 返回值:List<Map>
	 */
	public List<Map> findBrandWithTemplate();
}
