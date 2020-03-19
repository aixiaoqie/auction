package com.auction.manager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.FastDFSClient;

@RestController
@RequestMapping("/shop")
public class UploadController {
	
	
	//注入常量配置
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	/**
	 * 需求:商家上传图片
	 * 请求:upload
	 * 参数:MultipartFile
	 * 返回值:AuctionResult
	 */
	@RequestMapping("/upload")
	public AuctionResult uploadPic(MultipartFile file){
		
		
		try {
			//获取文件名称
			String originalFilename = file.getOriginalFilename();
			//截取文件扩展名 
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			
			//创建工具类对象
			FastDFSClient fdfs = new FastDFSClient("classpath:config/client.conf");
			//上传文件
			//gif,bmp,jpg
			//返回文件上传成功地址:group1/M00/00/02/wKhCQ1qvKG2AZqibAA1rIuRd3Es806.jpg
			String url = fdfs.uploadFile(file.getBytes(), extName);
			
			//组合图片上传成功绝对地址
			url = IMAGE_SERVER_URL+url;
			
			//上传成功
			return new AuctionResult(200, "上传成功", url);
			
		} catch (Exception e) {
			e.printStackTrace();
			//上传失败
			return new AuctionResult(100, "上传失败");
		}
		
		
	}

}
