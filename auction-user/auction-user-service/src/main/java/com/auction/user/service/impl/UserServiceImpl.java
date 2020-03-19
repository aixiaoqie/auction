package com.auction.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.mapper.TbUserMapper;
import com.auction.pojo.TbUser;
import com.auction.pojo.TbUserExample;
import com.auction.pojo.TbUserExample.Criteria;
import com.auction.user.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 服务实现层
 * 
 * @author Administrator
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;

	// 注入redis模版
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public AuctionResult add(TbUser user, String smsCode) {
		try {
			// 从redis中获取验证码
			Long number = (Long) redisTemplate.boundHashOps("smsCode").get(user.getPhone());
			String code = number + "";
			// 判断验证码是否正确
			if (!smsCode.equals(code)) {
				return new AuctionResult(100, "验证码错误");
			}
			// 密码加密
			String newPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			// 设置到用户对象
			user.setPassword(newPwd);

			// 创建时间
			Date date = new Date();
			user.setCreated(date);
			user.setUpdated(user.getCreated());

			userMapper.insert(user);
			// 注册成功
			return new AuctionResult(200, "注册成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 注册失败
			return new AuctionResult(100, "注册失败");
		}
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbUser user) {
		userMapper.updateByPrimaryKey(user);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			userMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (user != null) {
			if (user.getUsername() != null && user.getUsername().length() > 0) {
				criteria.andUsernameLike("%" + user.getUsername() + "%");
			}
			if (user.getPassword() != null && user.getPassword().length() > 0) {
				criteria.andPasswordLike("%" + user.getPassword() + "%");
			}
			if (user.getPhone() != null && user.getPhone().length() > 0) {
				criteria.andPhoneLike("%" + user.getPhone() + "%");
			}
			if (user.getEmail() != null && user.getEmail().length() > 0) {
				criteria.andEmailLike("%" + user.getEmail() + "%");
			}
			if (user.getNickName() != null && user.getNickName().length() > 0) {
				criteria.andNickNameLike("%" + user.getNickName() + "%");
			}
			if (user.getName() != null && user.getName().length() > 0) {
				criteria.andNameLike("%" + user.getName() + "%");
			}
			if (user.getHeadPic() != null && user.getHeadPic().length() > 0) {
				criteria.andHeadPicLike("%" + user.getHeadPic() + "%");
			}
			if (user.getQq() != null && user.getQq().length() > 0) {
				criteria.andQqLike("%" + user.getQq() + "%");
			}
			if (user.getIsMobileCheck() != null && user.getIsMobileCheck().length() > 0) {
				criteria.andIsMobileCheckLike("%" + user.getIsMobileCheck() + "%");
			}
			if (user.getIsEmailCheck() != null && user.getIsEmailCheck().length() > 0) {
				criteria.andIsEmailCheckLike("%" + user.getIsEmailCheck() + "%");
			}
			if (user.getSex() != null && user.getSex().length() > 0) {
				criteria.andSexLike("%" + user.getSex() + "%");
			}

		}

		Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	// 注入消息发送模版
	@Autowired
	private JmsTemplate jmsTemplate;

	/**
	 * 需求:发送短信验证码
	 * 
	 * @param smsCode
	 * @return
	 */
	public AuctionResult smsCode(String phone) {
		try {
			// 生成6位数验证码
			// 0.11233243434
			long number = (long) (Math.random() * 1000000);
			// 把6位数验证码保存在redis服务器
			redisTemplate.boundHashOps("smsCode").put(phone, number);

			// 创建map对象
			Map<String, String> mapMessage = new HashMap<String, String>();
			mapMessage.put("mobile", phone);

			// 设置签名
			mapMessage.put("signName", "物品交易系统");

			// 创建map
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", number + "");

			mapMessage.put("number", JSON.toJSONString(map));

			// 给短信发送网关服务发送消息 pyg-sms
			jmsTemplate.convertAndSend("smsQueue", JSON.toJSONString(mapMessage));

			// 发送消息成功
			return new AuctionResult(200, "发送消息成功");

		} catch (JmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return new AuctionResult(100, "发送消息失败");
		}
	}

	@Value("${USER_INFO}")
	private String USER_INFO;

	@Value("${EXPIRE_TIME}")
	private Integer EXPIRE_TIME;

	@Override
	public AuctionResult login(String str, String password) {
		// 1.注入mapper
		// 2.校验用户名和密码是否为空
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(password)) {
			return AuctionResult.build(100, "用户名密码错误");
		}
		// 3.先校验用户名
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(str);
		List<TbUser> list = userMapper.selectByExample(example);// select * from ｔｂ_ｕｓｅｒ ｗｈｅｒｅ ｕｓｅｒｎａｍｅ＝１２３
		if (list == null && list.size() == 0) {
			return AuctionResult.build(100, "用户名或密码错误");
		}
		// 4.再校验密码
		TbUser user = list.get(0);
		// 先加密密码再比较
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!md5DigestAsHex.equals(user.getPassword())) {// 表示用户的密码不正确
			return AuctionResult.build(100, "用户名或密码错误");
		}
		// 5.如果校验成功
		// 6.生成token : uuid生成 ，还需要设置token的有效性期来模拟session 用户的数据存放在redis
		// (key:token,value:用户的数据JSON)
		String token = UUID.randomUUID().toString();
		// 存放用户数据到redis中，使用jedis的客户端,为了管理方便加一个前缀"kkk:token"
		// 设置密码为空
		user.setPassword(null);
		redisTemplate.boundValueOps(USER_INFO + ":" + token).set(user);
//		client.set(USER_INFO + ":" + token, JsonUtils.objectToJson(user));
		redisTemplate.boundValueOps(USER_INFO + ":" + token).expire(EXPIRE_TIME, TimeUnit.SECONDS);
		// 设置过期时间 来模拟session
//		client.expire(USER_INFO + ":" + token, EXPIRE_TIME);
		// 7.把token设置cookie当中 在表现层设置
		return AuctionResult.build(200,"",token);
	}
	
	@Override
	public AuctionResult getUserByToken(String token) {
		Object obj = redisTemplate.boundValueOps(USER_INFO+":"+token).get();
		
		if(obj != null) {
			TbUser user = (TbUser) obj;
			redisTemplate.boundValueOps(USER_INFO + ":" + token).expire(EXPIRE_TIME, TimeUnit.SECONDS);
			return AuctionResult.build(200,"用户已经登陆",user);
		}
		return AuctionResult.build(100, "用户token已过期");
	}

}
