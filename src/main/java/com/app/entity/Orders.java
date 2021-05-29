package com.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date date;
	private double totalPrice;
	private int activeFlag;
	
	@OneToMany(mappedBy = "orders")	
	private List<OrderDetail> listOrder = new ArrayList<OrderDetail>();
 

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderDetail> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<OrderDetail> listOrder) {
		this.listOrder = listOrder;
	}
	
	
	
	public void addItem(OrderDetail detail){
		listOrder.add(detail);
		calTotalPrice();
	}
	public void calTotalPrice() {
		double total = 0;
		for(OrderDetail item : listOrder) {
			total += item.getTotalPrice();
		}
		this.totalPrice = total;
	}
	
	public void removeItem(long id) {
		Iterator<OrderDetail> iterator =	listOrder.iterator();
		while(iterator.hasNext()) {
			OrderDetail od = 	iterator.next();
			if(od.getProduct().getId() == id) {
				iterator.remove();
			}
		}
		calTotalPrice();
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", date=" + date + ", totalPrice=" + totalPrice + ", activeFlag=" + activeFlag
				 + "]";
	}
	
	
}
