package com.auction.user.service;
import java.util.List;

import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.pojo.TbUser;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * 增加
	*/
	public AuctionResult add(TbUser user,String smsCode);
	
	
	/**
	 * 修改
	 */
	public void update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser findOne(Long id);
	
	
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
	public PageResult findPage(TbUser user, int pageNum,int pageSize);

	/**
	 * 需求:发送短信验证码
	 * @param smsCode
	 * @return
	 */
	public AuctionResult smsCode(String phone);
	
	/**
	 * 根据用户名邮箱登录
	 * @param str
	 * @param password
	 * @return
	 */
	public AuctionResult login(String str,String password);
	
	
	public AuctionResult getUserByToken(String token);
	
}
