package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comment_mob")
public class BlogComment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	@JoinColumn(name="username")
	private Users commentedBy;
	private Date commentedOn;

	@ManyToOne
	@JoinColumn(name="blogpost_id")
	private BlogPost blogPost;
	@Lob
	private String body;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Users commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Date getCommentedOn() {
		return commentedOn;
	}

	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}

	public BlogPost getBlogPost() {
		return blogPost;
	}

	public void setBlogPost(BlogPost blogPost) {
		this.blogPost = blogPost;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
