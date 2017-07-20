package com.niit.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.model.Users;

@Repository
@Transactional
public class UsersDaoImpl implements UsersDao{

			@Autowired
	private SessionFactory sessionFactory; 

	public void registration(Users users) {
		Session session= sessionFactory.openSession();
		session.save(users);
		session.flush();
		session.close();
	}
	
	public Users login(Users users){
		
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Users where username=? and password=? and enabled=?");
		query.setString(0,users.getUsername());// for assigning the value to the parameter username.
		query.setString(1,users.getPassword());
		query.setBoolean(2, true);
		Users validUsers=(Users)query.uniqueResult();
		session.close();
		return validUsers;
	}
/*
 * (non -Javadoc)
 * @ see com.niit.dao.UsersDao#getAllUsers()
 * to check for duplicate username
 * 	
*/	
	public List<Users> getAllUsers() {
		Session session=sessionFactory.openSession();
		Query query=session.createQuery("from Users");
		List<Users> users=query.list();
		session.close();
		return users;
	}

	public Users updateUser(Users validUser) {
		Session session=sessionFactory.openSession();
		session.update(validUser);
		session.flush();
		session.close();
		return validUser;
	}

	public Users getUserByUsername(String username) {
		Session session=sessionFactory.openSession();
		Users users=(Users)session.get(Users.class, username);
		session.close();
		return users;
	}
	
}
