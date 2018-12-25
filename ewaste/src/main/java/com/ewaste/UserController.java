package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping(value = "/login")
	public ModelAndView userCreate() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());
		m.setViewName("login");
		return m;

	}

	@PostMapping(value = "/saveUser")
	public ModelAndView saveUserInfo(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("register");
		return m;

	}
}
