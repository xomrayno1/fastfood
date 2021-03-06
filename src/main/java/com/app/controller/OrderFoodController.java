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
				orders.calTotalPrice();
			}
			session.setAttribute(Constant.MSG_SUCCESS, "?????t th??nh c??ng");
		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute(Constant.MSG_ERROR, "?????t th???t b???i");
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
				session.setAttribute(Constant.MSG_SUCCESS, "Xo?? th??nh c??ng");
				session.setAttribute("invoice", orders);
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Xo?? th???t b???i");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xo?? th???t b???i");
		}
 
		session.setAttribute("invoice", orders);
		return "redirect:/orderfood/list/1";
	}
	@GetMapping(value = {"/cancel"})
	public String cancelInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			Orders orders = (Orders) session.getAttribute("invoice");
			if(orders != null) {
				session.removeAttribute("invoice");
				session.setAttribute(Constant.MSG_SUCCESS, "Hu??? th??nh c??ng");
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Hu??? th???t b???i");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Hu??? th???t b???i");
		}
		return "redirect:/orderfood/list/1";
	}
	
	@GetMapping(value = {"/pay"})
	public String payInvoice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Orders orders = (Orders) session.getAttribute("invoice");
		try {
			if(orders != null) {
				orderService.orderFood(orders);
				session.removeAttribute("invoice");
				session.setAttribute(Constant.MSG_SUCCESS, "Thanh to??n th??nh c??ng");
			}else {
				session.setAttribute(Constant.MSG_ERROR, "Thanh to??n th???t b???i");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Thanh to??n th???t b???i");
		}
		return "redirect:/orderfood/list/1";
	}
}
