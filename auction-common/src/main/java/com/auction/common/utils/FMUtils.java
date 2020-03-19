package com.auction.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FMUtils {

	/**
	 * 
	 * @param ftlName
	 *            ：模板名字
	 * @param fileName
	 *            ：生成的html的名字
	 * @param map
	 *            ：数据,在freemarket模板中取数据都使用map
	 * @throws Exception
	 */
	public void ouputFile(String ftlName, String fileName,
			Map<String, Object> map) throws Exception {
		// 创建fm的配置
		Configuration config = new Configuration(Configuration.getVersion());
		// 指定默认编码格式
		config.setDefaultEncoding("UTF-8");
		config.setNumberFormat("#");
		// 设置模板的包路径
		config.setClassForTemplateLoading(this.getClass(), "/com/auction/ftl");
		// 获得包的模板
		Template template = config.getTemplate(ftlName);
		// 指定文件输出的路径
		String path = "F:\\template\\out\\";
		
		File file = new File(path + "/" + fileName);
		
		// 定义输出流，注意的必须指定编码
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)));
		// 生成模板
		template.process(map, out);
		
		//同时把生成html页面上传到静态页面服务器
		//连接linux服务
		ChannelSftp channelSftp = SftpUtil.connect("192.168.120.70", 22, "root", "19971104");
		//上传
		InputStream in = new FileInputStream(file);
		//
		SftpUtil.upload("/data/htmls", in, fileName, channelSftp);
		//关闭
		out.close();
	}

}
