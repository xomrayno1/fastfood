package com.app.dao;

import com.app.entity.Orders;

public interface OrderDAO<E> extends BaseDAO<E>{
	
	long saveOrder(Orders orders);
}
