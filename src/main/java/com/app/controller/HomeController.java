package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.entity.Users;

@Controller
public class HomeController {

	@RequestMapping(value = {"/index","/"})
	public String home() {
		return "index";
	}
	
	@RequestMapping(value = "/login")
	public String login(ModelMap model) {
		model.addAttribute("loginForm", new Users());
		return "login";
	}
	
	
}
