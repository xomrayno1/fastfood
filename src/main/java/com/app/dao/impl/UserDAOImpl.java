package com.app.dao.impl;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.UserDAO;
import com.app.entity.Users;

@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOImpl extends BaseDAOImpl<Users>implements UserDAO<Users>{

	public long getTotalUser() {
		// TODO Auto-generated method stub
		StringBuilder queryCount =
				new StringBuilder("SELECT COUNT(*) FROM Users as model where model.activeFlag = 1 ");
		Query   queryNumber = factory.getCurrentSession().createQuery(queryCount.toString());
		long count = (Long) queryNumber.uniqueResult();
		return count;
	}

	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		StringBuilder queryCount =
				new StringBuilder("SELECT *  FROM Users as model where model.activeFlag = 1 and model.username = "+username);
		Query   queryNumber = factory.getCurrentSession().createQuery(queryCount.toString());
		Users user = (Users)  queryNumber.getSingleResult();
		return  user;
	}

}
