package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repo;
	
	@GetMapping(value = "/index")
	public ModelAndView index() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("index");// html page
		return m;

	}
	
	@GetMapping(value = "/registraion")
	public ModelAndView registraion() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("registration");// html page
		return m;

	}

	

	@GetMapping(value = "/index2")
	public ModelAndView index2() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("index2");// html page
		return m;

	}

	@GetMapping(value = "/productselling")
	public ModelAndView productselling2() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("productselling");// html page
		return m;

	}

	@GetMapping(value = "/product")
	public ModelAndView product2() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("product2");// html page
		return m;

	}

	@GetMapping(value = "/features")
	public ModelAndView shoppingcart2(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("shopping-cart2");
		return m;

	}

	@GetMapping(value = "/blog")
	public ModelAndView blog2(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("blog2");
		return m;

	}

	@GetMapping(value = "/about")
	public ModelAndView about2(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("about2");
		return m;

	}

	@GetMapping(value = "/contact")
	public ModelAndView contact(@ModelAttribute UserInfo u) {
		UserInfo u2 = service.saveUser(u);

		ModelAndView m = new ModelAndView();
		m.addObject("user", u2);
		m.setViewName("contact2");
		return m;

	}

	@GetMapping(value = "/admin")
	public ModelAndView admin() {

		ModelAndView m = new ModelAndView();
		m.addObject("userList", repo.findAll());// blank object
		m.setViewName("admin");// html page
		return m;

	}

	@GetMapping(value = "/admin/{userId}")
	public ModelAndView getUserDetails(@PathVariable Long userId) {

		ModelAndView m = new ModelAndView();
		m.addObject("userObj", repo.findById(userId).get());// blank object
		m.setViewName("admin");// html page
		return m;

	}

	@GetMapping(value = "/tv")
	public ModelAndView tv() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("tv");// html page
		return m;

	}

	@GetMapping(value = "/laptop")
	public ModelAndView laptop() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("laptop");// html page
		return m;

	}

	@GetMapping(value = "/washing")
	public ModelAndView mobile() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("washing");// html page
		return m;

	}

	@GetMapping(value = "/fridge")
	public ModelAndView fridge() {

		ModelAndView m = new ModelAndView();
		m.addObject("user", new UserInfo());// blank object
		m.setViewName("fridge");// html page
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
