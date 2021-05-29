package com.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.OrderDAO;
import com.app.entity.Orders;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDAOImpl extends BaseDAOImpl<Orders>implements OrderDAO<Orders> {

	public long saveOrder(Orders orders) {
		// TODO Auto-generated method stub
		Long id =	(Long)factory.getCurrentSession().save(orders);
		return id;
	}

	 

}
