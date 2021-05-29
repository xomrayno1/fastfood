package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.OrderDetail;
import com.app.entity.Orders;
import com.app.entity.Products;
import com.app.service.OrderService;
import com.app.service.ProductService;
import com.app.utils.Constant;
import com.app.utils.Paging;

@Controller
@RequestMapping("/orderfood")
public class OrderFoodController {
	@Autowired
	ProductService productService;
	@Autowired
	OrderService orderService;
	 
	
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/orderfood/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listProduct(Model model,@PathVariable("page") int page,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Products> list = productService.findAll(paging);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", paging);
		if(session.getAttribute(Constant.MSG_ERROR) != null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		if(session.getAttribute(Constant.MSG_SUCCESS) != null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		return "order-food-list";
	}
	
	@GetMapping(value = {"/order/{id}"})
	public String orderFood(@PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Orders orders = (Orders) session.getAttribute("invoice");
		Products products = productService.findById(id);
		
		try {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setCount(1);
			orderDetail.setTotalPrice(products.getPrice() * 1);
			orderDetail.setProduct(products);
			orderDetail.setPrice(products.getPrice());
			orderDetail.setOrders(orders);
			orderDetail.setActiveFlag(Constant.ACTIVE);
			
			if(orders == null) {
				orders = new Orders();
				orders.addItem(orderDetail);
			}else {
				boolean flag = true;
				for(OrderDetail item : orders.getListOrder()) {
					if(item.getProduct().getId() == id) {
						item.setCount(item.getCount() + 1);
						item.setTotalPrice(item.getPrice() * item.getCount());
						flag = false;
					}
				}
				if(flag) {
					orders.addItem(orderDetail);
				}
			}
			session.setAttribute(Constant.MSG_SUCCESS, "Đặt thành công");
		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute(Constant.MSG_ERROR, "Đặt thất bại");
		}
		
		session.setAttribute("invoice", orders);
		return "redirect:/orderfood/list/1";
	}
	@GetMapping(value = {"/remove/{id}"})
	public String removeItem(@PathVariable("id") long id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Orders orders = (Orders) session.getAttribute("invoice");
		try {
			if(orders != null) {
				orders.removeItem(id);
				session.setAttribute(Constant.MSG_SUCCESS, "Xoá thành công");
				session.setAttribute("invoice", orders);
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
		}
 
		session.setAttribute("invoice", orders);
		return "redirect:/orderfood/list/1";
	}
	@GetMapping(value = {"/cancel"})
	public String cancelInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			session.removeAttribute("invoice");
			session.setAttribute(Constant.MSG_SUCCESS, "Huỷ thành công");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Huỷ thất bại");
		}
		return "redirect:/orderfood/list/1";
	}
}
