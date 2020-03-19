package com.auction.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.auction.common.utils.AuctionResult;
import com.auction.manager.service.TestService;

@RestController
public class Test {
	
	@Reference(timeout=3000)
	private TestService testService;
	
	@RequestMapping("/testController")
	public AuctionResult testController() {
		return AuctionResult.build(200, "前端配置成功");
	}
	@RequestMapping("/testService")
	public AuctionResult testService() {
		return testService.findAll();
	}
}
