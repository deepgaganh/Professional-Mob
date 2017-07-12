package com.niit.Dao;

import java.util.List;

import com.niit.model.Users;

public interface UsersDao {
	
	public void registration(Users users);		
	List<Users> getAllUsers();
	Users login(Users users);
	Users updateUser(Users validUser);

}
