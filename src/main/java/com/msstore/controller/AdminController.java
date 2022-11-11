package com.msstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping()
	public String index() {
		return "admin/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}
	
	@RequestMapping("/account")
	public String account() {
		return "admin/account";
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
