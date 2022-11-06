package com.msstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> c95f7e2a594848dacc393c032484c8bd5823106d
import org.springframework.web.bind.annotation.RequestMapping;

import com.msstore.DAO.ChiTIetDonHangDAO;
import com.msstore.DAO.DonHangDAO;
import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.ChiTietDonHang;
import com.msstore.Entity.DonHang;
import com.msstore.Entity.HinhAnh;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.SanPhamTop8;


@Controller
@RequestMapping("/home")
public class HomeContronller {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@Autowired
	HinhAnhDAO haDAO;
	
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Autowired
	DonHangDAO dhDAO;
	
	@Autowired
	ChiTIetDonHangDAO ctdhDAO;
	
	@Autowired
	DonHangDAO dhDAO;
	
	@Autowired
	ChiTIetDonHangDAO ctdhDAO;
	
	@GetMapping
	public String doGetHome(Model model) {
		Pageable sortedByIDDesc = 
				  PageRequest.of(0, 8, Sort.by("maSP").descending());
		Pageable sortedByHighlightDesc = 
				  PageRequest.of(0, 8);
		List<SanPham> products = spDAO.findAll(sortedByIDDesc).getContent();
		List<SanPhamTop8> productshi = spDAO.findByTop8(sortedByHighlightDesc).getContent();
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream()
		            .filter(e -> nameSet.add(e.getSp().getMaSP()))
		            .collect(Collectors.toList());

		model.addAttribute("products", products);
		model.addAttribute("productshi", productshi);
		model.addAttribute("categoris", categoris);
		model.addAttribute("images", img);
		return "client/index";
	}
	
	@GetMapping("/cart")
	public String pageCart(Model model) {
		List<LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("categoris", categoris);
		return "client/cart";
	}
	
	@GetMapping("/check-out/history")
	public String pageCO(Model model) {
		List<LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("categoris", categoris);
		return "client/check-out-history";
	}
	
<<<<<<< HEAD
=======
	
	
>>>>>>> c95f7e2a594848dacc393c032484c8bd5823106d
	@GetMapping("/login")
	public String doGetLogin() {
		return "client/login";
	}
}
