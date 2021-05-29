package com.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.OrderDAO;
import com.app.dao.OrderDetailDAO;
import com.app.entity.OrderDetail;
import com.app.entity.Orders;
import com.app.utils.Constant;
import com.app.utils.Paging;

@Service
public class OrderService {
	@Autowired
	private OrderDAO<Orders> orderDAO;
	@Autowired
	private OrderDetailDAO<OrderDetail> orderDetailDAO;

	public long orderFood(Orders e) {
		// TODO Auto-generated method stub
		//e.calTotalPrice();
		e.setActiveFlag(Constant.ACTIVE);
		e.setDate(new Date());
		long id = orderDAO.saveOrder(e);
		for(OrderDetail item : e.getListOrder()) {
			item.setOrders(new Orders(id));
			orderDetailDAO.insert(item);
		}
		return 0;
	}

	public void delete(Orders e) {
		// TODO Auto-generated method stub
		orderDAO.delete(e);
	}
 
	public List<Orders> findAll(Paging paging) {
		// TODO Auto-generated method stub
		return orderDAO.findAll(paging);
	}

	public Orders findById(long id) {
		// TODO Auto-generated method stub
		return orderDAO.findById(Orders.class, id);
	}

	public List<OrderDetail> findOrderDetailByOrder(long idOrder) {
		// TODO Auto-generated method stub
		return orderDetailDAO.findByProperty("orders.id", idOrder);
	}

}
