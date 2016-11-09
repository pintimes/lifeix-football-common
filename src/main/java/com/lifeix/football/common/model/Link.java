package com.lifeix.football.common.model;

import java.util.Map;

/**
 * 行为Link
 * 
 * @author zengguangwei
 *
 */
public class Link {
	// post,decision
	private String type;

	private String id;

	private String url;

	private Map<String, String> params;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
