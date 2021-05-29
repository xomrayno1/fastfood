package com.app.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.BaseDAO;
import com.app.utils.Paging;
@Repository
@Transactional(rollbackFor = Exception.class)
public class BaseDAOImpl<E> implements BaseDAO<E> {
	@Autowired
	SessionFactory factory;
	

	public E findById(Class<E> instance, Serializable id) {
		// TODO Auto-generated method stub
 		return factory.getCurrentSession().find(instance, id);
	}

	public List<E> findAll(Paging paging) {
		// TODO Auto-generated method stub
		StringBuilder queryBuild = new StringBuilder();
		StringBuilder queryCount = new StringBuilder();
		queryBuild.append("FROM ").append(getGenericName()).append(" as model where model.activeFlag = 1 ");
		queryCount.append("SELECT COUNT(*) FROM ").append(getGenericName()).append(" as model where model.activeFlag = 1 ");
	 
		System.out.println(queryBuild);
		System.out.println(queryCount);
		Query<E> queryList = factory.getCurrentSession().createQuery(queryBuild.toString());
		Query<E> queryNumber = factory.getCurrentSession().createQuery(queryCount.toString());
 
		if(paging != null) {
			long count = (Long) queryNumber.uniqueResult();
			paging.setTotalProduct(count);
			queryList.setFirstResult(paging.getOffSet());
			queryList.setMaxResults(paging.getNumberPerPage());
		}
		return queryList.getResultList();
	}

	public List<E> findByProperty(String property, Object object) {
		StringBuilder queryBuild = new StringBuilder();
		queryBuild.append("FROM ")
						.append(getGenericName()).append(" where activeFlag = 1 ")
							.append(" and ").append(property);
		property = property.replace(".", "");
		queryBuild.append(" = :").append(property);
		Query<E> queryList = factory.getCurrentSession().createQuery(queryBuild.toString());
		queryList.setParameter(property, object);
		return queryList.getResultList();
	}

	public void insert(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------insert---------------");
		factory.getCurrentSession().persist(e);
	}

	public void update(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------update---------------");
		factory.getCurrentSession().merge(e);
	}

	public void delete(E e) {
		// TODO Auto-generated method stub
		System.out.println("-------------delete---------------");
		factory.getCurrentSession().merge(e);
	}

	public String getGenericName() {  
		String s = getClass().getGenericSuperclass().toString();
		Pattern pattern = Pattern.compile("\\<(.*?)\\>"); // tạo pattern
		Matcher m = pattern.matcher(s);  
		String generic="null"; 
		if(m.find()) { // kiểm tra 
			generic = m.group(1);
		}
		return generic;
	}


}
