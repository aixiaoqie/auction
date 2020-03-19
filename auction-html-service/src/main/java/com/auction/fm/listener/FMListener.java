package com.auction.fm.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;

import com.auction.common.utils.FMUtils;
import com.auction.mapper.TbGoodsDescMapper;
import com.auction.mapper.TbGoodsMapper;
import com.auction.mapper.TbItemCatMapper;
import com.auction.mapper.TbItemMapper;
import com.auction.pojo.TbGoods;
import com.auction.pojo.TbGoodsDesc;
import com.auction.pojo.TbGoodsExample;
import com.auction.pojo.TbItem;
import com.auction.pojo.TbItemCat;
import com.auction.pojo.TbItemExample;
import com.auction.pojo.TbItemExample.Criteria;

/**
 * 需求:同步静态页面
 * 
 * @author Administrator 业务: 商品上架,商品删除时候,同步静态页面操作 步骤: 1,接受消息(商品ids)
 *         2,根据商品id查询商品信息 3,根据商品生成html页面
 * 
 */
public class FMListener implements MessageListener {

	// 注入mapper接口代理对象
	@Autowired
	private TbGoodsMapper goodsMapper;

	// 注入商品描述mapper接口代理对象
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	// 注入tbitemMapper接口代理对象
	@Autowired
	private TbItemMapper itemMapper;

	// 注入分类接口代理对象
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public void onMessage(Message message) {
		try {
			// 接受消息
			String[] ids = null;
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage m = (ActiveMQObjectMessage) message;
				// 获取消息
				ids = (String[]) m.getObject();
			}
			// 循环ids
			for (String id : ids) {

				// 创建example对象
				TbGoodsExample goodsExample = new TbGoodsExample();
				// 创建criteria对象
				com.auction.pojo.TbGoodsExample.Criteria createCriteria2 = goodsExample.createCriteria();
				// 设置查询参数
				createCriteria2.andIdEqualTo(Long.parseLong(id));
				// 设置查询参数
				createCriteria2.andCategory1IdIsNotNull();
				createCriteria2.andCategory2IdIsNotNull();
				createCriteria2.andCategory3IdIsNotNull();

				// 查询
				List<TbGoods> list = goodsMapper.selectByExample(goodsExample);

				// 判断商品数据是否存在
				if (list != null && list.size() > 0) {

					// 获取对象
					TbGoods tbGoods = list.get(0);

					// 根据id查询商品描述数据
					TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(tbGoods.getId());
					// 查询sku信息
					// 创建example对象
					TbItemExample example = new TbItemExample();
					// 创建criteria对象
					Criteria createCriteria = example.createCriteria();
					// 设置查询参数
					createCriteria.andGoodsIdEqualTo(tbGoods.getId());
					// 执行查询
					List<TbItem> itemList = itemMapper.selectByExample(example);

					// 根据分类id查询分类名称
					// 设置
					// 查询第一级分类名称
					TbItemCat itemcat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id());

					// 判断如何为空
					if (itemcat1 == null) {
						continue;
					}

					// 查询第二级分类名称
					TbItemCat itemcat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id());

					// 查询第三级分类名称
					TbItemCat itemcat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());

					// 创建map对象
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("itemList", itemList);
					maps.put("goodsDesc", tbGoodsDesc);

					// 创建
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("itemCat1", itemcat1.getName());
					map.put("itemCat2", itemcat2.getName());
					map.put("itemCat3", itemcat3.getName());
					// 封装分类
					maps.put("map", map);

					// 封装商品数据
					maps.put("goods", tbGoods);

					// 创建工具类对象
					FMUtils fUtils = new FMUtils();
					// 调用生成页面方法
					fUtils.ouputFile("item.ftl", tbGoods.getId() + ".html", maps);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
