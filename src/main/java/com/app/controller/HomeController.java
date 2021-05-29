package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.Users;
import com.app.service.UserService;
import com.app.validator.LoginValidator;
 

@Controller
public class HomeController {

	@Autowired
	LoginValidator loginValidator;
	@Autowired
	UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		if(dataBinder.getTarget().getClass() == Users.class) {
			dataBinder.setValidator(loginValidator);
		}
	}
	
	@RequestMapping(value = {"/index","/"})
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String login(ModelMap model) {
		model.addAttribute("loginForm", new Users());
		return "login";
	}
	@RequestMapping(value = "/processLogin")
	public String processLogin(ModelMap model, @ModelAttribute("loginForm") @Validated Users users, BindingResult result,HttpSession 
			session) {
		if(result.hasErrors()) {
			return "login";
		}
		Users userInfo = userService.findByUsername(users.getUsername());
		session.setAttribute("userInfo",userInfo);
		return "redirect:/index";
	}
	@RequestMapping(value = "/logout")
	public String logout(ModelMap model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:/login";
	}
	
}
