package com.msstore.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msstore.DAO.SanPhamDAO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	SanPhamDAO spDAO;
	
	@RequestMapping()
	public String index() {
		return "admin/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}
//	@RequestMapping("/account")
//	public String account() {
//		
//		return "admin/account";
//	}
	
	@RequestMapping("/report")
	public String report(Model model) {
		model.addAttribute("tkNam", spDAO.findBuThongKeNam());
		return "admin/report";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return "admin/login";
	}
	
	
}
