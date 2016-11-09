package com.lifeix.football.common.model;

import java.util.Date;

public class Message {

	private String id;

	//未读，已读
	private boolean read;

	private User from;

	private User to;

	//属于哪个APP
	private String app;

	//什么类型的消息，APP系统自定义比如：裁判选派，通知，系统消息等等
	private String type;
	
	private Label[] labels;

	private Link link;

	private String content;

	private String[] images;

	private Date createTime;

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Label[] getLabels() {
		return labels;
	}

	public void setLabels(Label[] labels) {
		this.labels = labels;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

}
