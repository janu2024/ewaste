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
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("login");// html page
		return m;

	}

	@GetMapping(value = "/index")
	public ModelAndView index() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("index");// html page
		return m;

	}

	@GetMapping(value = "/productselling")
	public ModelAndView productselling() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("productselling");// html page
		return m;

	}

	@GetMapping(value = "/product")
	public ModelAndView product() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("product");// html page
		return m;

	}

	@PostMapping(value = "/features")
	public ModelAndView shoppingcart(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("features");
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
