package com.niit.Dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

public interface BlogPostDao {
	
	void saveBlogPost(BlogPost blogPost);
	List<BlogPost> getAllBlogs(int approved);
	public BlogPost getBlogById(int id);
	void updateBlogPost(BlogPost blogPost);
	void addComment(BlogComment blogComment);
	List<BlogComment> getBlogComments(int blogId);

}
