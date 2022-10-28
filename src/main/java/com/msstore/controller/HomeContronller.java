package com.msstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.SanPham;

@Controller
@RequestMapping("/home")
public class HomeContronller {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@GetMapping
	public String doGetHome(Model model) {
		List<SanPham> products = spDAO.findAll();
		List<LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("products", products);
		model.addAttribute("categoris", categoris);
		return "client/index";
	}
	
}
