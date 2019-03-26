package com.ewaste;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalControlerAdvise {

	@Autowired
	SecurityService service;

	@ModelAttribute
	public void handleRequest(HttpServletRequest request, Model model) {
		UserInfo userInfo = service.getLoggedInUser();
		if (userInfo != null) {
			model.addAttribute("userEmail", userInfo.getEmail());
			model.addAttribute("userRole", userInfo.getRole());
			model.addAttribute("userName", userInfo.getFirstName()+" "+userInfo.getLastName());
		} else {
			model.addAttribute("userName", "");
			model.addAttribute("userRole", "");
			model.addAttribute("userName", "");


		}
	}
}
