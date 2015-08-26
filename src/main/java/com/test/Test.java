package com.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//test
public class Test implements Serializable{
	private static final long serialVersionUID = 8981314942877158521L;
	private String name="kkk";
	private int code=100;
	private Map<String,Object> map=new HashMap<String, Object>();
	public Test(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
