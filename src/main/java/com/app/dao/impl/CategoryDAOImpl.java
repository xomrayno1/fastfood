package com.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CategoryDAO;
import com.app.entity.Category;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl  extends BaseDAOImpl<Category>implements CategoryDAO<Category> {

}
