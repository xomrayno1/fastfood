package com.app.service;

import java.io.IOException;
import java.util.List;

import com.app.utils.Paging;

public interface  BaseService<E> {
	public void add(E e) throws IOException;
	
	public void delete(E e);
	
	public void update(E e) throws IOException ;
	
	public List<E> findAll(Paging paging);
	
	public E findById(long id);
	
	public List<E> findAllByProperty(String property, Object value);
	
	

}
