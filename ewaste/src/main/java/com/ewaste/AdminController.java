package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	UserService service;

	@Autowired
	UserRepository repo;

	@GetMapping(value = "/userList")
	public ModelAndView admin() {

		ModelAndView m = new ModelAndView();
		m.addObject("userList", repo.findAll());// blank object
		m.setViewName("admin");// html page
		return m;

	}

	@GetMapping(value = "/editUser/{userId}")
	public ModelAndView getUserDetails(@PathVariable Long userId) {

		ModelAndView m = new ModelAndView();
		m.addObject("user", repo.findById(userId).get());// blank object
		m.setViewName("updateUser");// html page
		return m;

	}

	@PostMapping(value = "/saveUser")
	public String saveUserInfo(@ModelAttribute UserInfo u, Model model) {

		UserInfo dbInfo = service.findByEmail(u.getEmail());
		dbInfo.setRole(u.getRole());
		service.saveUser(dbInfo);

		return "redirect:/admin/userList";

	}

}
