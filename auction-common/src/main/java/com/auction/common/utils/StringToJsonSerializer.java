package com.auction.common.utils;

import java.io.IOException;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

public class StringToJsonSerializer implements ObjectSerializer{

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		// TODO Auto-generated method stub
		serializer.write(JSONObject.parseObject(object.toString()));
	}

}
