package com.auction.seller.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.common.utils.AuctionResult;

@RestController
@RequestMapping("/login")
public class LoginController {
	/**
	 * 获取登录的用户名
	 * 通过spring security安全框架获取
	 * @return
	 */
	@RequestMapping("/loadLoginName")
	public AuctionResult loadLoginName(ModelMap map) {
		//获取用户名
		//使用安全框架获取用户登录名
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		map.addAttribute("loginName", username);
		return AuctionResult.build(map);
	}
}
