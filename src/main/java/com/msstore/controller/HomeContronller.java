package com.msstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String doGetHome(Model model,@RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0),8);
		Page <SanPham> products = spDAO.findAll(pageable);
		List <LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("products", products);
		model.addAttribute("categoris", categoris);
		return "client/index";
	}
	
}
