package com.msstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.SanPham;

@Controller
@RequestMapping("/product")
public class ProductContronller {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@GetMapping("/{loaiSP}")
	public String doGetProduct(@PathVariable("loaiSP") String loaiSP,Model model) {
		System.out.println(loaiSP);
		List<SanPham> products = spDAO.findAllByLoaiSP(loaiSP);
		List<LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("categoris", categoris);
		model.addAttribute("products", products);
		return "client/kitchen";
	}
	
	@GetMapping("/details/{id}")
	public String doGetProductDetails(@PathVariable("id") Long id ,Model model) {
		SanPham product = spDAO.findById(id).get();
		List<LoaiSP> categoris = lspDAO.findAll();
		List<SanPham> products = spDAO.findAll();
		model.addAttribute("products", products);
		model.addAttribute("product", product);
		model.addAttribute("categoris", categoris);
		return "client/single";
	}
};
