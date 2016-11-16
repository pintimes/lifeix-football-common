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
public class Link {
	// post,decision
	private String type;//跳转类型，app: app,html,h5,page   web:html,page
	private String page;//跳转页面，app和web端返回不同的值，例如app端page=”coachlist_page”，web端page=”coach”
	private String targetId;//目标页id，如资讯id
	private String url;//链接页面地址
	private String shareUrl;//链接页面分享地址
	private Map<String, String> params;//额外的参数，根据项目不同有不同的需求

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

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
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
