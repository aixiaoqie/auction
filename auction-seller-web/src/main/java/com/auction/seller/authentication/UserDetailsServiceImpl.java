package com.auction.seller.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.auction.manager.service.SellerService;
import com.auction.pojo.TbSeller;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SellerService sellerService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//定义角色封装集合
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_SELLER_USER"));
		
		//调用商家服务对象,查询商家密码
		TbSeller seller = sellerService.findOneBySellerId(username);
		
		if(!(seller.getStatus().equals("1"))) {
			seller.setPassword(null);
		}
		
		return new User(username, seller.getPassword(), authorities);
	}

}
