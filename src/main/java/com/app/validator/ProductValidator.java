package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entity.Products;
import com.app.service.ProductService;

@Component
public class ProductValidator  implements Validator{

	@Autowired
	ProductService productService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(Products.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Products products = (Products) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.required");
		if(!StringUtils.isEmpty(products.getName())) {
			List<Products> produts = productService.findAllByProperty("name", products.getName());		
			if(products.getId() != 0) {
				if(!produts.isEmpty()) {
					Products current = productService.findById(products.getId());
					if(!products.getName().equals(current.getName())) {
						errors.rejectValue("name", "error.exists");
					}
				}
			}else {
				if(!produts.isEmpty()) {
					errors.rejectValue("name", "error.exists");
				}
			}
		}
		if(products.getId() == 0) {
			if(products.getMultipartFile().isEmpty()) {
				errors.rejectValue("multipartFile", "error.required");
			}
		}
		if(products.getCateId() == 0) {
			errors.rejectValue("cateId", "error.required");
		}
	 
	 
	}
}
