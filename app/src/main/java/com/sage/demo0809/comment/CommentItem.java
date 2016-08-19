package com.sage.demo0809.comment;

import java.io.Serializable;

/**
 * 
* @ClassName: CommentItem 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2016-5-10下午3:44:38
*
 */
public class CommentItem implements Serializable {

	private String id;
	private CommentUser user;
	private CommentUser toReplyUser;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public CommentUser getUser() {
		return user;
	}
	public void setUser(CommentUser user) {
		this.user = user;
	}
	public CommentUser getToReplyUser() {
		return toReplyUser;
	}
	public void setToReplyUser(CommentUser toReplyUser) {
		this.toReplyUser = toReplyUser;
	}
	
}
