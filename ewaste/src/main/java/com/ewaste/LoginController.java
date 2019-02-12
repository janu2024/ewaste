package com.ewaste;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	UserService service;

	@PostMapping(value = "/getUserByEmail")
	@ResponseBody
	public String saveUserInfo(HttpServletRequest request) {
		UserInfo user = service.findByEmail(request.getParameter("emailId"));
		if (user != null) {
			return "true";
		}
		return "false";

	}

	@PostMapping(value = "/saveUser")
	public String saveUserInfo(@ModelAttribute UserInfo u) {
		u.setRole("ROLE_USER");
		
		service.saveUser(u);

		securityService.autologin(u.getEmail(), u.getPassword());

		return "redirect:/user/index";

	}

	@GetMapping
	public ModelAndView home2() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		modelAndView.addObject("user", new UserInfo());// blank object

		return modelAndView;
	}
}
