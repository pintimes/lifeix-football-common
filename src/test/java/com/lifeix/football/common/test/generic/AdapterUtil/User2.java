package com.lifeix.football.common.test.generic.AdapterUtil;

import java.util.List;

public class User2{
	private String id;
	private String name;
	private List<String> avatar;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAvatar() {
		return avatar;
	}
	public void setAvatar(List<String> avatar) {
		this.avatar = avatar;
	}
	@Override
	public String toString() {
		return "User2 [id=" + id + ", name=" + name + ", avatar=" + avatar
				+ "]";
	}
	
}
