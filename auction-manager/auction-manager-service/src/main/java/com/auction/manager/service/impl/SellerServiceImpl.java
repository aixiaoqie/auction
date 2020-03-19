package com.auction.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.auction.common.utils.AuctionResult;
import com.auction.common.utils.PageResult;
import com.auction.manager.service.SellerService;
import com.auction.mapper.TbSellerMapper;
import com.auction.pojo.TbSeller;
import com.auction.pojo.TbSellerExample;
import com.auction.pojo.TbSellerExample.Criteria;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 服务实现层
 * 
 * @author Administrator
 * 
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSeller> page = (Page<TbSeller>) sellerMapper
				.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller seller) {
		seller.setCreateTime(new Date());
		sellerMapper.insertSelective(seller);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller seller) {
		sellerMapper.updateByPrimaryKey(seller);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbSeller findOne(Integer id) {
		return sellerMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public TbSeller findOneBySellerId(String sellerId) {
		
		return sellerMapper.findOneBySellerId(sellerId);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void delete(Integer[] ids) {
		for (Integer id : ids) {
			sellerMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSellerExample example = new TbSellerExample();
		Criteria criteria = example.createCriteria();

		if (seller != null) {
			if (seller.getSellerId() != null
					&& seller.getSellerId().length() > 0) {
				criteria.andSellerIdLike("%" + seller.getSellerId() + "%");
			}
			if (seller.getName() != null && seller.getName().length() > 0) {
				criteria.andNameLike("%" + seller.getName() + "%");
			}
			if (seller.getNickName() != null
					&& seller.getNickName().length() > 0) {
				criteria.andNickNameLike("%" + seller.getNickName() + "%");
			}
			if (seller.getPassword() != null
					&& seller.getPassword().length() > 0) {
				criteria.andPasswordLike("%" + seller.getPassword() + "%");
			}
			if (seller.getEmail() != null && seller.getEmail().length() > 0) {
				criteria.andEmailLike("%" + seller.getEmail() + "%");
			}
			if (seller.getMobile() != null && seller.getMobile().length() > 0) {
				criteria.andMobileLike("%" + seller.getMobile() + "%");
			}
			if (seller.getTelephone() != null
					&& seller.getTelephone().length() > 0) {
				criteria.andTelephoneLike("%" + seller.getTelephone() + "%");
			}
			if (seller.getStatus() != null && seller.getStatus().length() > 0) {
				criteria.andStatusEqualTo(seller.getStatus());
			}
			if (seller.getAddressDetail() != null
					&& seller.getAddressDetail().length() > 0) {
				criteria.andAddressDetailLike("%" + seller.getAddressDetail()
						+ "%");
			}
			if (seller.getLinkmanName() != null
					&& seller.getLinkmanName().length() > 0) {
				criteria.andLinkmanNameLike("%" + seller.getLinkmanName() + "%");
			}
			if (seller.getLinkmanQq() != null
					&& seller.getLinkmanQq().length() > 0) {
				criteria.andLinkmanQqLike("%" + seller.getLinkmanQq() + "%");
			}
			if (seller.getLinkmanMobile() != null
					&& seller.getLinkmanMobile().length() > 0) {
				criteria.andLinkmanMobileLike("%" + seller.getLinkmanMobile()
						+ "%");
			}
			if (seller.getLinkmanEmail() != null
					&& seller.getLinkmanEmail().length() > 0) {
				criteria.andLinkmanEmailLike("%" + seller.getLinkmanEmail()
						+ "%");
			}
			if (seller.getLicenseNumber() != null
					&& seller.getLicenseNumber().length() > 0) {
				criteria.andLicenseNumberLike("%" + seller.getLicenseNumber()
						+ "%");
			}
			if (seller.getTaxNumber() != null
					&& seller.getTaxNumber().length() > 0) {
				criteria.andTaxNumberLike("%" + seller.getTaxNumber() + "%");
			}
			if (seller.getOrgNumber() != null
					&& seller.getOrgNumber().length() > 0) {
				criteria.andOrgNumberLike("%" + seller.getOrgNumber() + "%");
			}
			if (seller.getLogoPic() != null && seller.getLogoPic().length() > 0) {
				criteria.andLogoPicLike("%" + seller.getLogoPic() + "%");
			}
			if (seller.getBrief() != null && seller.getBrief().length() > 0) {
				criteria.andBriefLike("%" + seller.getBrief() + "%");
			}
			if (seller.getLegalPerson() != null
					&& seller.getLegalPerson().length() > 0) {
				criteria.andLegalPersonLike("%" + seller.getLegalPerson() + "%");
			}
			if (seller.getLegalPersonCardId() != null
					&& seller.getLegalPersonCardId().length() > 0) {
				criteria.andLegalPersonCardIdLike("%"
						+ seller.getLegalPersonCardId() + "%");
			}
			if (seller.getBankUser() != null
					&& seller.getBankUser().length() > 0) {
				criteria.andBankUserLike("%" + seller.getBankUser() + "%");
			}
			if (seller.getBankName() != null
					&& seller.getBankName().length() > 0) {
				criteria.andBankNameLike("%" + seller.getBankName() + "%");
			}

		}

		Page<TbSeller> page = (Page<TbSeller>) sellerMapper
				.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 需求:审核商家信息,改变商品状态
	 * 参数:String sellerId,String status
	 * 返回值:pygResult
	 */
	public AuctionResult updateStatus(String id, String status) {
		try {
			// 根据商家编号查询商家对象
			TbSeller seller = sellerMapper.selectByPrimaryKey(Integer.parseInt(id));
			//设置商家状态
			seller.setStatus(status);
			//更新
			sellerMapper.updateByPrimaryKeySelective(seller);
			//更新成功
			return new AuctionResult(200, "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new AuctionResult(100, "更新失败");
		}
		
	}




}
