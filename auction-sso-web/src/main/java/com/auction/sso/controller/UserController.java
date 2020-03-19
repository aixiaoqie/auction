package com.auction.sso.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.CookieUtils;
import com.auction.common.utils.JsonUtils;
import com.auction.common.utils.PageResult;
import com.auction.common.utils.StringToJsonSerializer;
import com.auction.pojo.TbUser;
import com.auction.user.service.UserService;
/**
 * controller
 * @author Administrator
 *
 */
//@RestController
@Controller
@RequestMapping("/user")
public class UserController {

	@Reference(timeout=10000000)
	private UserService userService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbUser> findAll(){			
		return userService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	@ResponseBody
	public PageResult  findPage(int page,int rows){			
		return userService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public AuctionResult add(@RequestBody TbUser user,String smsCode){
				
			AuctionResult result = userService.add(user,smsCode);
			
			return result;
		
	}
	/**
	 * 检查用户名是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkUsername")
	@ResponseBody
	public AuctionResult checkUsername(@RequestParam("username") String username) {
		System.out.println(username);
		return null;
	}
	
	/**
	 * 发送短信
	 * @param user
	 * @return
	 */
	@RequestMapping("/smsCode")
	@ResponseBody
	public AuctionResult smsCode(String phone){
				
			AuctionResult result = userService.smsCode(phone);
			
			return result;
		
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public AuctionResult update(@RequestBody TbUser user){
		try {
			userService.update(user);
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
	@ResponseBody
	public TbUser findOne(Long id){
		return userService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public AuctionResult delete(Long [] ids){
		try {
			userService.delete(ids);
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
	@ResponseBody
	public PageResult search(@RequestBody TbUser user, int page, int rows  ){
		return userService.findPage(user, page, rows);		
	}
//	
//	@Value("${TT_TOKEN_KEY}")
//	private String TT_TOKEN_KEY;
	//TT_TOKEN
	
	@RequestMapping("/login")
	@ResponseBody
	public AuctionResult login(HttpServletRequest request,HttpServletResponse response,@RequestBody TbUser user ){
		
		AuctionResult result = userService.login(user.getUsername(), user.getPassword());		
		//将token设置到cookie中 使用工具类CookieUtils跨域访问
		if(result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, "TT_TOKEN", result.getData().toString());
		}
		return result;
	}
	
	
	@RequestMapping(value="/token/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {
		//判断是否是Jsonp请求
		System.out.println(token);
		System.out.println("--");
		if(StringUtils.isNotBlank(callback)){
			System.out.println(callback);
			//如果是jsonp 需要拼接 类似于fun({id:1});
			AuctionResult result = userService.getUserByToken(token);
			String str = callback+"("+JsonUtils.objectToJson(result)+")";
			return str;
		}
		//如果不是jsonp
		//1.调用服务
		AuctionResult result = userService.getUserByToken(token);
		return  JsonUtils.objectToJson(result);
	}
	
}
