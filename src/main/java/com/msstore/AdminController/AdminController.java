package com.msstore.AdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}

	
	@RequestMapping("/report")
	public String report() {
		return "admin/report";
	}
	

	@RequestMapping("/logout")
	public String logout() {
		return "admin/login";
	}
}
