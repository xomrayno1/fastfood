package com.app.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ProductDAO;
import com.app.entity.Products;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductDAOImpl extends BaseDAOImpl<Products>implements ProductDAO<Products>{

	public Products findByName(String name) {
		// TODO Auto-generated method stub
		Query query = 	factory.getCurrentSession()
				.createQuery(" FROM Products as model where model.activeFlag = 1 and model.name = :name ");
		query.setParameter("name", name);
		if(query.getSingleResult() == null) {
			return null;
		}else {
			return (Products) query.getSingleResult();
		}
	}

//	@Override
//	public Products findByName(String name) {
//		// TODO Auto-generated method stub
//		Query query = 	factory.getCurrentSession()
//				.createQuery(" FROM Products as model where model.activeFlag = 1 and model.name = :name ");
//		query.setParameter("name", name);
//		if(query.getSingleResult() == null) {
//			return null;
//		}else {
//			return (Products) query.getSingleResult();
//		}
//	}

}
