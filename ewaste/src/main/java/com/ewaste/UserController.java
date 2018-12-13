package com.ewaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService service;

	@GetMapping(value = "/saveUser")
	public ModelAndView saveUserInfo() {

		UserInfo u = new UserInfo();
		u.setFirstName("abc");
		u.setAge(1);
		u.setLastName("def");

		UserInfo u2 = service.saveUser(u);
		service.deleteUser(u2);
		System.out.println(u2);

		return null;

	}
}
