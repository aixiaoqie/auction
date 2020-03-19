package com.auction.common.utils;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 系统返回的JSON格式
 * @author 孙志伟
 *	status--响应业务状态
 *	msg	--响应消息
 *	data--响应中的数据
 */
public class AuctionResult implements Serializable{
	 // 响应业务状态
	private Integer status;
	
	// 响应消息
	private String msg;
	
	// 响应中的数据
	private Object data;
	
    //构建其他状态的AuctionResult对象
    public static AuctionResult build(Integer status, String msg, Object data) {
        return new AuctionResult(status, msg, data);
    }
    
    public static AuctionResult build(Integer status, String msg) {
        return new AuctionResult(status, msg, null);
    }

    public static AuctionResult build(Object data) {
        return new AuctionResult(data);
    }

    public static AuctionResult build() {
        return new AuctionResult(null);
    }
    

	public AuctionResult() {
		super();
	}

	public AuctionResult(Object data) {
		super();
		this.data = data;
	}

	public AuctionResult(Integer status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public AuctionResult(Integer status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
