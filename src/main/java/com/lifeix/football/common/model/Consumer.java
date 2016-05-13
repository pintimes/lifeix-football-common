package com.lifeix.football.common.model;

public class Consumer {

	private String id;
	private String custom_id;
	private String username;
	private String groups; // admin,user

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustom_id() {
		return custom_id;
	}

	public void setCustom_id(String custom_id) {
		this.custom_id = custom_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	// @JsonProperty(value = "X-Consumer-ID")
	// @JsonProperty(value = "X-Consumer-Username")
	// @JsonProperty(value = "X-Consumer-Groups")
	// @JsonProperty(value = "X-Consumer-Custom-ID")

}
