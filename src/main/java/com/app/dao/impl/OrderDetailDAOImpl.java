package com.app.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.OrderDetailDAO;
import com.app.entity.OrderDetail;

@Repository
@Transactional(rollbackFor = Exception.class)
public class OrderDetailDAOImpl extends BaseDAOImpl<OrderDetail>implements OrderDetailDAO<OrderDetail>{

}
