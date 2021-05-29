package com.app.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.entity.Users;

@Component
public class UserValidator  implements Validator{

	@Autowired
	com.app.service.UserService userService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.isAssignableFrom(Users.class);
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Users user = (Users) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.required");
		if(user.getId() == 0 ) {
			if(!StringUtils.isEmpty(user.getUsername())) {
				List<Users> users = userService.findAllByProperty("username", user.getUsername());		
				if(!users.isEmpty()) {
					errors.rejectValue("username", "error.exists");
				}
			}
		}
		if(!StringUtils.isEmpty(user.getEmail())) {
			List<Users> users = userService.findAllByProperty("email", user.getEmail());		
			if(user.getId() != 0) {
				if(!users.isEmpty()) {
					Users current = userService.findById(user.getId());
					if(!user.getEmail().equals(current.getEmail())) {
						errors.rejectValue("email", "error.exists");
					}
				}
			}else {
				if(!users.isEmpty()) {
					errors.rejectValue("email", "error.exists");
				}
			}
		}
	}
}
