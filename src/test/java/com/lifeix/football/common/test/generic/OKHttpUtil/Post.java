package com.lifeix.football.common.test.generic.OKHttpUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 帖子
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
	private String id = null;

	private String title = null;

	private Author author = null;

	private String content = null;
	
	private String image;

	private boolean containVideo = false;

	private List<String> images = new ArrayList<String>();

	private Date createTime = null;

	private String source;

	private String sourceId;

	private String url;
	
	private String shareUrl;//资讯分享页面地址
	
	private List<String> categoryIds = new ArrayList<String>();
	
	private Integer status = 1;//是否可见 1:可见 0:不可见
	
	private Map<String, String> linkMap;
	
	private int pageviews;//阅读数
	
	private int commentNum;//评论数
	

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getPageviews() {
		return pageviews;
	}

	public void setPageviews(int pageviews) {
		this.pageviews = pageviews;
	}
	
	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public Map<String, String> getLinkMap() {
		return linkMap;
	}

	public void setLinkMap(Map<String, String> linkMap) {
		this.linkMap = linkMap;
	}

	public Post() {
		super();
	}

	public Post(String id) {
		super();
		this.id = id;
	}

	/**
	 * 内容
	 **/
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * id  
	 **/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * post创建日期
	 **/
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 **/
	@JsonProperty("author")
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * 标题
	 **/
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 图片地址
	 **/
	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isContainVideo() {
		return containVideo;
	}

	public void setContainVideo(boolean containVideo) {
		this.containVideo = containVideo;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title +"]";
	}
	
	
}