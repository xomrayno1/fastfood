package com.app.controller;

import java.util.Collections;
import java.util.Comparator;
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
import com.app.entity.Products;
import com.app.service.CategoryService;
import com.app.service.ProductService;
import com.app.utils.Constant;
import com.app.utils.Paging;
import com.app.validator.ProductValidator;
 

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductValidator productValidator;
	@Autowired
	CategoryService categoryService;
 
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		if(dataBinder.getTarget() == null) {
			return;
		}
	 
		if(dataBinder.getTarget().getClass() == Products.class) {
			dataBinder.setValidator(productValidator);
		}
	}
	@GetMapping(value = {"/list/","/list"})
	public String redirect() {
		return "redirect:/products/list/1";
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
		innitSelect(model);
		return "product-list";
	}
	@GetMapping(value = {"/add"})
	public String addProduct(Model model) {
		model.addAttribute("title", "Add");
		model.addAttribute("submitForm", new Products());
		model.addAttribute("viewOnly", false);
		innitSelect(model);
		return "product-action";
	}
	@GetMapping(value = {"/view/{id}"})
	public String viewProduct(Model model,@PathVariable("id") int id) {
		Products products = productService.findById(id);
		model.addAttribute("title", "View");
		model.addAttribute("submitForm", products);
		model.addAttribute("viewOnly", true);
		return "product-action";
	}
	@GetMapping(value = {"/edit/{id}"})
	public String editProduct(Model model,@PathVariable("id") int id) {
		Products products = productService.findById(id);
		model.addAttribute("title", "Edit");
		model.addAttribute("submitForm", products);
		model.addAttribute("viewOnly", false);
		innitSelect(model);
		return "product-action";
	}
	@PostMapping(value = {"/save"})
	public String saveProduct(Model model,@ModelAttribute("submitForm") @Validated Products products 
			, BindingResult result, HttpSession session ) {
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
 
		
		if(result.hasErrors()) {
			if(products.getId() != 0) {
				model.addAttribute("title", "Edit");
			}else {
				model.addAttribute("title", "Add");
			}
			innitSelect(model);
			model.addAttribute("submitForm", products);
			return "product-action";
		}
		if(products.getId() != 0) {
			try {
				 
				productService.update(products);
				session.setAttribute(Constant.MSG_SUCCESS, "Cập nhật thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Cập nhật thất bại");
			}
		}else {
			try {
				 
				productService.add(products);
				session.setAttribute(Constant.MSG_SUCCESS, "Thêm thành công");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Thêm thất bại");
			}
		}
		return "redirect:/products/list/1";
	}
	@GetMapping(value = {"/delete/{id}"})
	public String deleteProduct(Model model,@PathVariable("id")int id,HttpSession session) {
		Products productDTO = productService.findById(id);
		try {
			productService.delete(productDTO);
			session.setAttribute(Constant.MSG_SUCCESS, "Xoá thành công");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute(Constant.MSG_ERROR, "Xoá thất bại");
		}

		return "redirect:/products/list/1";
	}
	public void innitSelect(Model model) {
		List<Category> categories = categoryService.findAll(null);
		Collections.sort(categories,new Comparator<Category>() {
			public int compare(Category o1, Category o2) {
				// TODO Auto-generated method stub
				return o1.getName().compareTo(o2.getName());
			}		
		});
		model.addAttribute("listCate", categories);
	}
	
 
	
	
	
}
