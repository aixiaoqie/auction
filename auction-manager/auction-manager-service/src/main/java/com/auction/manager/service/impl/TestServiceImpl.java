package com.auction.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.auction.common.utils.AuctionResult;
import com.auction.manager.service.TestService;
import com.auction.mapper.TbUserMapper;
import com.auction.pojo.TbUser;
@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Override 
	public AuctionResult findAll() {
		List<TbUser> list = tbUserMapper.selectByExample(null);
		return AuctionResult.build(200, "数据库连接", list);
	}

}
