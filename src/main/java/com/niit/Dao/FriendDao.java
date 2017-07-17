package com.niit.Dao;

import java.util.List;

import com.niit.model.Users;

public interface FriendDao {
	
	List<Users> listOfSuggestedUsers(String username);
	void friendRequest(String fromUsername, String toUsername);
	

}
