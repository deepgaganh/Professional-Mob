package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Dao.UsersDao;
import com.niit.model.Error;
import com.niit.model.Users;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
	@Autowired
	private UsersDao usersDao;

	
	/*
	 * 	ResponseEntity<T> -> T is type of data
	 * ResponseEntity<Error> -> it will add Error type of data in HttpResponse
	 * ResponseEntity<Void> -> Void data in HttpResponse
	 * ResponseEntity<?>  -> it can send any type of data.
	 * Response 
	 * 	*/
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> registration(@RequestBody Users user) {
		try{
		user.setEnabled(true);
		List<Users> users=usersDao.getAllUsers();
		//for (T var: collection)
		for(Users u: users){
			if(u.getUsername()==user.getUsername()){
				Error error=new Error(2,"Username already exists");
				return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		System.out.println(user.getUsername() + ""+ user.getFirstname()+" "+user.getEmail());
		usersDao.registration(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
		}catch(Exception e){
			Error error=new Error(1,"Cannot register user details");
			return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	
	/*
	 * login method is for authentictaion
	 * if user is authenticated, return users.ok
	 * else return 401
	 * 
	*/

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Users users, HttpSession session){
		System.out.println("Is session new for" + users.getUsername()+ session.isNew());
		Users validUser=usersDao.login(users);
		if(validUser==null){
			Error error =new Error(3, "Invalid Username and password. please enter valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
					
		}else{
			validUser.setOnline(true);
			validUser=usersDao.updateUser(validUser);
			session.setAttribute("user",validUser);
			return new ResponseEntity<Users>(validUser,HttpStatus.OK);
		}
			
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session){
		
		Users users=(Users)session.getAttribute("user");
	//	System.out.println("Is session new for" + users.getUsername() + session.isNew());
		if(users==null)
		{
			Error error=new Error(3,"Unauthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		System.out.println("Is session New for"+users.getUsername() + session.isNew());
		users.setOnline(false);
		usersDao.updateUser(users);
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getuserdetails", method=RequestMethod.GET)
	public ResponseEntity<?> getUserDetails(HttpSession session){
		Users users=(Users)session.getAttribute("user");
		//	System.out.println("Is session new for" + users.getUsername() + session.isNew());
			if(users==null)
			{
				Error error=new Error(3,"Unauthorized user");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
			users=usersDao.getUserByUsername(users.getUsername());
			return new ResponseEntity<Users>(users,HttpStatus.OK);
	}
	@RequestMapping(value="/updateprofile", method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody Users user,HttpSession session){
		Users users=(Users)session.getAttribute("user");
			if(users==null)
			{
				Error error=new Error(3,"Unauthorized user");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
			usersDao.updateUser(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
