package com.niit.Dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.Users;

@Repository
public class FriendDaoImpl implements FriendDao{
			@Autowired
	private SessionFactory sessionFactory;
	public List<Users> listOfSuggestedUsers(String username) {
		Session session=sessionFactory.openSession();
		SQLQuery sqlQuery=session.createSQLQuery("select * from user_mob where username in (select username from user_mob where username!=? minus (select fromId from friend_mob where toId=? union select toId from friend_mob where fromId=?))");
		sqlQuery.setString(0, username);
		sqlQuery.setString(1, username);
		sqlQuery.setString(2, username);
		sqlQuery.addEntity(Users.class);
			List<Users> suggestedUsersList=sqlQuery.list();
			session.close();
			return suggestedUsersList;
	}
	public void friendRequest(String fromUsername, String toUsername) {
		Session session=sessionFactory.openSession();
		Friend friend=new Friend();
		friend.setFromId(fromUsername);
		friend.setToId(toUsername);
		friend.setStatus('P');
		session.save(friend);
		session.flush();
		session.close();
		
	}
	
	

}
