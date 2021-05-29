package com.app.dao;

import java.io.Serializable;
import java.util.List;

import com.app.utils.Paging;

public interface BaseDAO<E> {
	E findById(Class<E> instance,Serializable id);
	List<E> findAll(Paging paging );
	List<E> findByProperty(String property , Object object);
	void insert(E e);
	void update(E e);
	void delete(E e);
}
