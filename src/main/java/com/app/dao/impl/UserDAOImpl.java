package com.app.dao.impl;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserDAO;
import com.app.entity.Users;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users>implements UserDAO<Users>{

	@Override
	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		StringBuilder query =
				new StringBuilder("SELECT u  FROM Users u where u.activeFlag = 1 and u.username = :username");
		Query queryQ = factory.getCurrentSession().createQuery(query.toString());
		queryQ.setParameter("username", username);
		Users user = (Users)  queryQ.getSingleResult();
		if(user == null) {
			return null;
		}
		return  user;
	}

 
  

}
