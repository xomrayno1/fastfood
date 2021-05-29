package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.Users;
import com.app.service.UserService;
import com.app.utils.Constant;
import com.app.utils.Paging;
import com.app.validator.UserValidator;
 

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserValidator userValidator;
 
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		 
		if(dataBinder.getTarget().getClass() == Users.class) {
			dataBinder.setValidator(userValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/user/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listUser(Model model,@PathVariable("page") int page,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Users> list = userService.findAll(paging);
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
		return "user-list";
	}
	@GetMapping(value = {"/add"})
	public String addUser(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new Users());
		model.addAttribute("viewOnly", false);
 
		return "user-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewUser(Model model,@PathVariable("id") int id) {
		Users users = userService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", users);
		model.addAttribute("viewOnly", true);
		 
		return "user-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editUser(Model model,@PathVariable("id") int id) {
		Users users = userService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", users);
		model.addAttribute("viewOnly", false);
		 
		return "user-action";
	}
	@PostMapping(value = {"/save"})
	public String saveUser(Model model,@ModelAttribute("submitForm") @Validated Users users 
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			 
			if(users.getId() != 0) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", users);
			return "user-action";
		}
		if(users.getId() != 0) {
			try {
				userService.update(users);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				userService.add(users);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/user/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteUser(Model model,@PathVariable("id")int id,HttpSession session) {
		Users users = userService.findById(id);
		try {
			userService.delete(users);
			session.setAttribute(Constant.MSG_SUCCESS, "Xoá thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
		}
		return "redirect:/user/list/1";
	}
}
