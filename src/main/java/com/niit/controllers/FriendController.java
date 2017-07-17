package com.niit.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.FriendDao;
import com.niit.model.Error;
import com.niit.model.Users;

@Controller
@RequestMapping(value = "/friend")
public class FriendController {

	@Autowired
	private FriendDao friendDao;

	@RequestMapping(value = "/suggesteduserslist", method = RequestMethod.GET)
	public ResponseEntity<?> getSuggestedUsersList(HttpSession session) {
		Users users = (Users) session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Users> suggestedUsers = friendDao.listOfSuggestedUsers(users.getUsername());
		return new ResponseEntity<List<Users>>(suggestedUsers, HttpStatus.OK);

	}
	
	
	public ResponseEntity<?> friendrequest(@PathVariable String toUsername, HttpSession session){
		Users users = (Users) session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		String fromUsername=users.getUsername();
		friendDao.friendRequest(fromUsername);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
