package com.auction.search.listener;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import com.auction.mapper.TbGoodsMapper;
import com.auction.mapper.TbItemMapper;
import com.auction.pojo.TbGoods;
import com.auction.pojo.TbGoodsExample;
import com.auction.pojo.TbGoodsExample.Criteria;
import com.auction.pojo.TbItem;
import com.auction.pojo.TbItemExample;

/**
 * 需求:监听器接受消息,同步索引库
 * 
 * @author 孙志伟 业务流程: 1,接受消息,消息内容是 商品id 2,根据商品id查询商品数据信息 3,把查询到商品数据同步到索引库即可.
 *
 */
public class SolrListener implements MessageListener {

	// 注入商品服务
	@Autowired
	private TbGoodsMapper goodsMapper;

	// 注入tbItem接口代理对象
	@Autowired
	private TbItemMapper itemMapper;

	// 注入solr模版对象
	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public void onMessage(Message message) {
		try {
			// 定义空数组 spu id
			String[] ids = null;
			// 接受消息
			if (message instanceof ActiveMQObjectMessage) {
				// 强制转换
				ActiveMQObjectMessage m = (ActiveMQObjectMessage) message;
				ids = (String[]) m.getObject();
			}
			// 循环ids
			for (String id : ids) {
				// 创建example对象
				TbGoodsExample example = new TbGoodsExample();
				// 创建criteria对象
				Criteria createCriteria = example.createCriteria();
				// 设置外键spu id
				createCriteria.andIdEqualTo(Long.parseLong(id));
				// 设置查询参数
				createCriteria.andIsDeleteEqualTo("0");
				// 执行查询 sku 集合
				List<TbGoods> list = goodsMapper.selectByExample(example);

				// 判断操作使用删除操作,还是修改,添加
				if (list != null && list.size() > 0) {
					// 修改,添加,应该上架更新索引库
					// 创建example对象
					TbItemExample example2 = new TbItemExample();
					// 创建criteria对象
					com.auction.pojo.TbItemExample.Criteria createCriteria2 = example2.createCriteria();
					// 设置查询参数
					createCriteria2.andGoodsIdEqualTo(Long.parseLong(id));

					// 执行查询集合
					List<TbItem> itemList = itemMapper.selectByExample(example2);

					// 写入索引库
					solrTemplate.saveBeans(itemList);
					// 提交
					solrTemplate.commit();
				} else {
					// 删除
					
					TbItemExample example3 = new TbItemExample();
					com.auction.pojo.TbItemExample.Criteria createCriteria2 = example3.createCriteria();
					// 设置查询参数
					createCriteria2.andGoodsIdEqualTo(Long.parseLong(id));
					// 执行查询集合
					List<TbItem> itemList = itemMapper.selectByExample(example3);
					
					ArrayList<String> deleteIds = new ArrayList<>();
					
					for (TbItem tbItem : itemList) {
						String item_id = tbItem.getId()+"";
						deleteIds.add(item_id);
					}
					solrTemplate.deleteById(deleteIds);
					solrTemplate.commit();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
