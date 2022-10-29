package com.msstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.HinhAnh;
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
	HinhAnhDAO haDAO;
	
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/{loaiSP}")
	public String doGetProduct(@PathVariable("loaiSP") String loaiSP,Model model,
			@RequestParam("p") Optional<Integer> p) {
		System.out.println(loaiSP);
		Pageable pageable = PageRequest.of(p.orElse(0), 8);
		Page<SanPham> products = spDAO.findAllByLoaiSP(loaiSP,pageable);
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> imgAll = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		imgAll = imgAll.stream()
		            .filter(e -> nameSet.add(e.getSp().getMaSP()))
		            .collect(Collectors.toList());
		model.addAttribute("imagesall", imgAll);
		model.addAttribute("categoris", categoris);
		model.addAttribute("products", products);
		return "client/kitchen";
	}
	
	@GetMapping("/details/{id}")
	public String doGetProductDetails(@PathVariable("id") Long id ,Model model) {
		Pageable sortedByRelateDesc = PageRequest.of(0, 8);
		SanPham product = spDAO.findById(id).get();
		List<LoaiSP> categoris = lspDAO.findAll();
		List<SanPham> products = spDAO.findAllByLoaiSPID(product.getLoaiSP().getMaLoai(),sortedByRelateDesc);
		
		List<HinhAnh> img = haDAO.findAllByPID(id);
		List<HinhAnh> imgAll = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		imgAll = imgAll.stream()
		            .filter(e -> nameSet.add(e.getSp().getMaSP()))
		            .collect(Collectors.toList());

		model.addAttribute("imagesall", imgAll);
		model.addAttribute("images", img);
		model.addAttribute("products", products);
		model.addAttribute("product", product);
		model.addAttribute("categoris", categoris);
		return "client/single";
	}
	
	@RequestMapping("/search")
	public String search(Model model,@RequestParam("Keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p) {
		List<HinhAnh> imgAll = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		imgAll = imgAll.stream()
		            .filter(e -> nameSet.add(e.getSp().getMaSP()))
		            .collect(Collectors.toList());
		String kwords = kw.orElse(sessionService.get("keywords",""));
		sessionService.get("keywords",kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 12);
		Page<SanPham> products = spDAO.findAllByKeywords(pageable,"%"+kwords+"%");
		List<LoaiSP> categoris = lspDAO.findAll();
		model.addAttribute("products",products);
		model.addAttribute("imagesall", imgAll);
		model.addAttribute("categoris", categoris);
		return "client/search";
	}
};
