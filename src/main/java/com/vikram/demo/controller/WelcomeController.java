package com.vikram.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikram.demo.constant.CommonConstants;
import com.vikram.demo.form.UserForm;
import com.vikram.demo.service.UserService;

@Controller
public class WelcomeController {

	@Autowired
	private UserService service;

	@Value("${app.page.welcomemsg:test}")
	private String message = "Hello World";

	/**
	 * END POINT FOR WEB APPLICATION
	 * 
	 * @return
	 */
	@RequestMapping({ "/", "/welcome" })
	@Secured({ CommonConstants.ROLE_EMPLOYEE, CommonConstants.ROLE_ADMIN })
	public String welcome(Map<String, Object> model, Authentication authentication) {
		model.put("message", this.message);
		final String authority = authentication.getAuthorities().stream().map(obj -> obj.getAuthority()).findFirst().get();
		model.put(CommonConstants.CURRENT_ROLE, authority);
		return CommonConstants.PAGE_WELCOME;
	}

	/**
	 * END POINT FOR WEB APPLICATION
	 * 
	 * @return
	 */
	@RequestMapping("/register")
	@Secured(CommonConstants.ROLE_ADMIN)
	public String register(Model model, Authentication authentication) {
		model.addAttribute(CommonConstants.ATTRIBUTE_USERFORM, new UserForm());
		final String authority = authentication.getAuthorities().stream().map(obj -> obj.getAuthority()).findFirst().get();
		model.addAttribute(CommonConstants.CURRENT_ROLE, authority);
		return CommonConstants.PAGE_REGISTER;
	}

	/**
	 * END POINT FOR WEB APPLICATION
	 * 
	 * @return
	 */
	@RequestMapping("/userlist")
	@Secured({ CommonConstants.ROLE_EMPLOYEE, CommonConstants.ROLE_ADMIN })
	public String userList(Model model, Authentication authentication) {
		model.addAttribute(CommonConstants.ATTRIBUTE_USERS, this.service.findAll());
		final String authority = authentication.getAuthorities().stream().map(obj -> obj.getAuthority()).findFirst().get();
		model.addAttribute(CommonConstants.CURRENT_ROLE, authority);		
		return CommonConstants.PAGE_USERLIST;
	}
	
}