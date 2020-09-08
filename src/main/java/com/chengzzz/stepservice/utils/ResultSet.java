package com.chengzzz.stepservice.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 界面返回数据
 * @author: Chenkf
 * @create: 2020/2/17
 **/
public class ResultSet extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ResultSet() {
		put("code", 0);
	}
	
	public static ResultSet error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static ResultSet error(String msg) {
		return error(500, msg);
	}
	
	public static ResultSet error(int code, String msg) {
		ResultSet r = new ResultSet();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultSet ok(String msg) {
		ResultSet r = new ResultSet();
		r.put("msg", msg);
		return r;
	}
	
	public static ResultSet ok(Map<String, Object> map) {
		ResultSet r = new ResultSet();
		r.putAll(map);
		return r;
	}
	
	public static ResultSet ok() {
		return new ResultSet();
	}

	@Override
	public ResultSet put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
