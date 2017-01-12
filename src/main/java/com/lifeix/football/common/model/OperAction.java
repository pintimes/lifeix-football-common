package com.lifeix.football.common.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 行为Link，任何包含链接的类都要引用该类
 * @author zengguangwei
 * @author xule
 * @version 2016-11-10 15:01:00
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperAction {
	
	private String type;// post,decision
	private String targetId;//目标页id，如资讯id
	private String targetTitle;//目标页标题，如资讯标题
	private String url;//链接页面地址
	private Map<String, String> params;

	public String getTargetTitle() {
		return targetTitle;
	}

	public void setTargetTitle(String targetTitle) {
		this.targetTitle = targetTitle;
	}


	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
