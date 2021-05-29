package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entity.Category;
import com.app.service.CategoryService;

@Component
public class CategoryValidator  implements Validator{
	@Autowired
	CategoryService categoryService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(Category.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Category category = (Category) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
		if(!StringUtils.isEmpty(category.getName())) {
			List<Category> categories = categoryService.findAllByProperty("name", category.getName());		
			if(category.getId() != 0) {
				if(!categories.isEmpty()) {
					Category current = categoryService.findById(category.getId());
					if(!category.getName().equals(current.getName())) {
						errors.rejectValue("name", "error.exists");
					}
				}
			}else {
				if(!categories.isEmpty()) {
					errors.rejectValue("name", "error.exists");
				}
			}
		}
	}
}
