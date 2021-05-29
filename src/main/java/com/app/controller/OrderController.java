package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.Category;
import com.app.entity.OrderDetail;
import com.app.entity.Orders;
import com.app.service.OrderService;
import com.app.utils.Paging;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	 
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/order/list/1";
	}
	
	@RequestMapping("/list/{page}")
	public String listCategory(Model model,@PathVariable("page") int page,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Orders> list = orderService.findAll(paging);
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", paging);
		return "order-list";
	}
	
	@GetMapping(value = {"/view/{id}"})
	public String viewCategory(Model model,@PathVariable("id") int id) {
		List<OrderDetail> list = orderService.findOrderDetailByOrder(id);
		model.addAttribute("list",list);
		return "order-detail";
	}
}
