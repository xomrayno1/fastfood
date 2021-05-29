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

import com.app.entity.Category;
import com.app.service.CategoryService;
import com.app.utils.Constant;
import com.app.utils.Paging;
import com.app.validator.CategoryValidator;
 
@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	CategoryValidator categoryValidator;
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
		if(dataBinder.getTarget().getClass() == Category.class) {
			dataBinder.setValidator(categoryValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/category/list/1";
	}
	@RequestMapping("/list/{page}")
	public String listCategory(Model model,@PathVariable("page") int page,HttpSession session) {
		Paging paging = new Paging(5);
		paging.setIndexPage(page);
		List<Category> list = categoryService.findAll(paging);
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
		return "category-list";
	}
	@GetMapping(value = {"/add"})
	public String addCategory(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new Category());
		model.addAttribute("viewOnly", false);
		return "category-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewCategory(Model model,@PathVariable("id") int id) {
		Category category = categoryService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", category);
		model.addAttribute("viewOnly", true);
		return "category-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editCategory(Model model,@PathVariable("id") int id) {
		Category category = categoryService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", category);
		model.addAttribute("viewOnly", false);
		return "category-action";
	}
	@PostMapping(value = {"/save"})
	public String saveCategory(Model model,@ModelAttribute("submitForm") @Validated Category category 
			, BindingResult result, HttpSession session ) {
		if(result.hasErrors()) {
			if(category.getId() != 0) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			model.addAttribute("submitForm", category);
			return "category-action";
		}
		if(category.getId() != 0) {
			try {
				categoryService.update(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				categoryService.add(category);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/category/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteCategory(Model model,@PathVariable("id")int id,HttpSession session) {
		Category category = categoryService.findById(id);
		try {
			categoryService.delete(category);
			session.setAttribute(Constant.MSG_SUCCESS, "Xoá thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
		}

		return "redirect:/category/list/1";
	}
}
