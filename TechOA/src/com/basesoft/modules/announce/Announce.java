package com.basesoft.modules.announce;

public class Announce {

	public static final String TYPE_NEWS = "1";			//新闻
	public static final String TYPE_NOTICE = "2";		//通知
	public static final String TYPE_OTHERS = "3";		//其他
	
	String id;
	String type;
	String title;
	String content;
	String pubdate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
