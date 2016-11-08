package com.lifeix.football.common.model;

public class User {

	private String id;
	private String name;
	private String avatar; 

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	// @JsonProperty(value = "X-Consumer-ID")
	// @JsonProperty(value = "X-Consumer-Username")
	// @JsonProperty(value = "X-Consumer-Groups")
	// @JsonProperty(value = "X-Consumer-Custom-ID")

}
