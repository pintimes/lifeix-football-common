package com.lifeix.football.common.model;

import java.util.Date;

public class Message {

	private User from;

	private User to;

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

}
