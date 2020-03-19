package com.auction.vo;

import java.io.Serializable;
import java.util.List;

import com.auction.pojo.TbSpecification;
import com.auction.pojo.TbSpecificationOption;

/**
 * 保存前台录入的规格属性及对应选项的pojo
 * @author 孙志伟
 *
 */
public class Specification implements Serializable{
	
	//包装规格对象
	private TbSpecification tbSpecification;
	
	//包装规格列表
	private List<TbSpecificationOption> specificationOptionList;

	public TbSpecification getTbSpecification() {
		return tbSpecification;
	}

	public void setTbSpecification(TbSpecification tbSpecification) {
		this.tbSpecification = tbSpecification;
	}

	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}

	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}

	

}
