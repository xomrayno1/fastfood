package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.CategoryDAO;
import com.app.entity.Category;
import com.app.utils.Constant;
import com.app.utils.Paging;

@Service
public class CategoryService implements BaseService<Category>{
	@Autowired
	private CategoryDAO<Category> categoryDAO;

	public void add(Category e) {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.ACTIVE);
		categoryDAO.insert(e);
	}

	public void delete(Category e) {
		// TODO Auto-generated method stub
		e.setActiveFlag(Constant.NOT_ACTIVE);
		categoryDAO.delete(e);
	}

	public void update(Category e) {
		// TODO Auto-generated method stub
		categoryDAO.update(e);
	}

	public List<Category> findAll(Paging paging) {
		// TODO Auto-generated method stub
		return categoryDAO.findAll(paging);
	}

	public Category findById(long id) {
		// TODO Auto-generated method stub
		return categoryDAO.findById(Category.class, id);
	}

	public List<Category> findAllByProperty(String property, Object value) {
		// TODO Auto-generated method stub
		return categoryDAO.findByProperty(property, value);
	}

	 

}
