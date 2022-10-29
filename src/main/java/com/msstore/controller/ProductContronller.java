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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.BinhLuanDAO;
import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.BinhLuan;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.SanPham;
import com.msstore.Service.SessionService;

@Controller
@RequestMapping("/product")
public class ProductContronller {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@Autowired
	BinhLuanDAO blDAO;
	
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/{loaiSP}")
	public String doGetProduct(@PathVariable("loaiSP") String loaiSP,
			Model model,
			@RequestParam("p") Optional<Integer> p) {
		System.out.println(loaiSP);
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page <SanPham> products =spDAO.findAllByLoaiSP(pageable, loaiSP);
		List <LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("categoris", categoris);
		model.addAttribute("products", products);
		return "client/kitchen";
	}
	
	@GetMapping("/details/{id}")
	public String doGetProductDetails(@PathVariable("id") Long id ,Model model,
			@RequestParam("p") Optional<Integer> p) {
		SanPham product = spDAO.findById(id).get();
		// comment 
		List<BinhLuan> comments = blDAO.findAllBySanPham(id);
		model.addAttribute("comments", comments);
		// loai sp
		List<LoaiSP> categoris = lspDAO.findAll();
		// san pham
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<SanPham> products = spDAO.findAll(pageable);
		model.addAttribute("product", product);
		model.addAttribute("products", products);
		model.addAttribute("categoris", categoris);
		return "client/single";
	}
	
	@RequestMapping("/search")
	public String search(Model model,
			@RequestParam("Keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		String kwords = kw.orElse(sessionService.get("keywords",""));
		sessionService.get("keywords",kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<SanPham> products = spDAO.findAllByKeywords(pageable,"%"+kwords+"%");
		model.addAttribute("products",products);
		return "client/search";
	}
};
