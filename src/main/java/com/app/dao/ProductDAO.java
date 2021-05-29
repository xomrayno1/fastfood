package com.app.dao;

import com.app.entity.Products;

public interface ProductDAO<E> extends BaseDAO<E>{
	Products findByName(String name);
}
