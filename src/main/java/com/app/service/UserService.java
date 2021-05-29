package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.UserDAO;
import com.app.entity.Users;
import com.app.utils.Constant;
import com.app.utils.Paging;

@Service
public class UserService implements BaseService<Users>{
	@Autowired
	private UserDAO<Users> userDAO;

	public void add(Users e) {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.ACTIVE);
		userDAO.insert(e);
	}

	public void delete(Users e) {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.NOT_ACTIVE);
		userDAO.delete(e);
	}

	public void update(Users e) {
		// TODO Auto-generated method stub
		userDAO.update(e);
	}

	public List<Users> findAll(Paging paging) {
		// TODO Auto-generated method stub
		return userDAO.findAll(paging);
	}

	public Users findById(long id) {
		// TODO Auto-generated method stub
		return userDAO.findById(Users.class, id);
	}

	public List<Users> findAllByProperty(String property, Object value) {
		// TODO Auto-generated method stub
		return userDAO.findByProperty(property, value);
	}
 

}
