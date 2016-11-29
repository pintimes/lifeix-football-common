package com.lifeix.football.common.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 行为Link，任何包含链接的类都要引用该类
 * @author zengguangwei
 * @author xule
 * @version 2016-11-28 14:35:00
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link {
	// post,decision
	private String type;//跳转类型，app: app,html,h5,page   web:html,page
	private String page;//跳转页面，如app端page=”coachlist_page”
	private String targetId;//目标页id，如资讯id
	private String targetTitle;//目标页标题，如资讯标题
	private String url;//链接页面地址
	private String urlScheme;//打开另一个app需要的参数
	private Map<String, Object> params;//额外的参数，根据项目不同有不同的需求

	public String getUrlScheme() {
		return urlScheme;
	}

	public void setUrlScheme(String urlScheme) {
		this.urlScheme = urlScheme;
	}

	public String getTargetTitle() {
		return targetTitle;
	}

	public void setTargetTitle(String targetTitle) {
		this.targetTitle = targetTitle;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	

}
