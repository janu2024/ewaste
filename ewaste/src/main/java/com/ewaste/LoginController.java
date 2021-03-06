package com.ewaste;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login") // controller
public class LoginController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	UserService service;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@PostMapping(value = "/saveUser") // methods
	public String saveUserInfo(@ModelAttribute UserInfo u, Model model) {

		UserInfo dbInfo = service.findByEmail(u.getEmail());
		if (dbInfo != null) {
			model.addAttribute("user", u);
			model.addAttribute("userexists", "true");
			return "register";

		} else {
			u.setRole("ROLE_USER");

			service.saveUser(u);

			securityService.autologin(u.getEmail(), u.getPassword());

			return "redirect:/user/index";
		}
	}

	@GetMapping
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		modelAndView.addObject("user", new UserInfo());// create blank object of UserInfo(POJO class)

		return modelAndView;
	}

	@GetMapping("/doLogin")
	public ModelAndView relogin() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		modelAndView.addObject("user", new UserInfo());// create blank object of UserInfo(POJO class)

		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView registeration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register");
		modelAndView.addObject("user", new UserInfo());// blank object
		modelAndView.addObject("userexists", "false");

		return modelAndView;
	}

	@GetMapping(value = "/forgotpassword")
	public ModelAndView forgotpassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgotpassword");
		modelAndView.addObject("user", new UserInfo());// blank object

		return modelAndView;
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute UserInfo user, Model model) {
		UserInfo existingUser = service.findByEmail(user.getEmail());
		if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
			securityService.autologin(existingUser.getEmail(), existingUser.getPassword());
			if (existingUser.getRole().equalsIgnoreCase("ROLE_TRANSPORTER")) {
				return "redirect:/productselling/transport";
			}
			else if(existingUser.getRole().equalsIgnoreCase("ROLE_ADMIN")){
				return "redirect:/admin/userList";
			}

			return "redirect:/user/index";

		} else {
			model.addAttribute("user", user);
			model.addAttribute("inValidInfo", "true");
			return "login";

		}

	}

	@RequestMapping(value = "/generatePassword", method = RequestMethod.POST)
	public String generatePassword(@ModelAttribute UserInfo user, Model model) {
		UserInfo existingUser = service.findByEmail(user.getEmail());
		model.addAttribute("user", user);// blank object

		if (existingUser == null) {
			model.addAttribute("userNotFound", "true");
			model.addAttribute("emailSent", "false");
			return "forgotpassword";
		}

		else {
			try {
				String uuid = UUID.randomUUID().toString();
				existingUser.setPassword(uuid);
				smtpMailSender.send(user.getEmail(), "New Password", "Your temporary password is :" + uuid);
				service.saveUser(existingUser);
				model.addAttribute("userNotFound", "false");
				model.addAttribute("emailSent", "true");

			} catch (Exception e) {
				model.addAttribute("userNotFound", "true");
				model.addAttribute("emailSent", "false");

			}

		}
		return "forgotpassword";

	}

}
