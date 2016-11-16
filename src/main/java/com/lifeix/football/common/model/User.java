package com.lifeix.football.common.model;

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

	// @JsonProperty(value = "X-Consumer-ID")
	// @JsonProperty(value = "X-Consumer-Username")
	// @JsonProperty(value = "X-Consumer-Groups")
	// @JsonProperty(value = "X-Consumer-Custom-ID")

}
