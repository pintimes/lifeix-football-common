package com.lifeix.football.common.model;

public class Label {

	// 文本内容
	private String text;

	// 内容颜色：oxffff0000
	private String color;

	// 背景颜色：oxffff0000
	private String bgcolor;

	// 背景图片
	private String image;

	// 居中图标
	private String icon;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
