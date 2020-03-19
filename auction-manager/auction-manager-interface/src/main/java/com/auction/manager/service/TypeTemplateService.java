package com.auction.manager.service;
import java.util.List;
import java.util.Map;

import com.auction.common.utils.PageResult;
import com.auction.pojo.TbTypeTemplate;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum,int pageSize);

	/**
	 * 需求: 查询模版中存储关键规格属性对应规格选项
	 * 请求: findSpecOptionListByTypeId
	 * 参数:模版id
	 * 返回值: List<Map>
	 */
	public List<Map> findSpecOptionListByTypeId(Long typeId);
	
}
