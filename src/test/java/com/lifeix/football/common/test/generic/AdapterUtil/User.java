package com.lifeix.football.common.test.generic.AdapterUtil;

public class User {
	private String id;
	private String nickname;
	private String avatar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public User() {
		super();
	}
	
	public User(String id, String nickname, String avatar) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.avatar = avatar;
	}
}