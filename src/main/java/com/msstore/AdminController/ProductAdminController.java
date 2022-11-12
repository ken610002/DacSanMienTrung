package com.msstore.AdminController;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.HinhAnh;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.SanPham;

@Controller
@RequestMapping("/admin")
public class ProductAdminController {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	HinhAnhDAO haDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@GetMapping("/product")
	public String doGetProductAdmin(Model model,@RequestParam("page") Optional<Integer> page) {
		int currentPage = page.orElse(0);
		Pageable pageAble = PageRequest.of(currentPage, 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		
		model.addAttribute("products", listProduct);
		model.addAttribute("images", img);
		return "admin/product";
	}
	
	@GetMapping("/product/edit/{productId}")
	public String doEditProduct(Model model,@RequestParam("page") Optional<Integer> page, @PathVariable("productId") long productId) {
		int currentPage = page.orElse(0);
		Pageable pageAble = PageRequest.of(currentPage, 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		model.addAttribute("images", img);
		model.addAttribute("categoris", categoris);
		model.addAttribute("products", listProduct);
		
		Optional<SanPham> product = spDAO.findById(productId);
		model.addAttribute("product", product.get());
		return "admin/product";
	}
	
	@PostMapping("/product/create")
	public String doPostCreate(@ModelAttribute("product") SanPham product) {
		spDAO.save(product);
		return "redirect:/admin/product";
	}
	
	@RequestMapping("/product/update/{productId}")
	public String doPostUpdate(@ModelAttribute("product") SanPham product,@PathVariable("productId") long productId) {
		product.setMaSP(productId);
		System.out.println(product.getMaSP());
		spDAO.save(product);
		return "redirect:/admin/product/edit/" + product.getMaSP();
	}
	
	@RequestMapping("/product/delete/{productId}")
	public String doPostDelete(@PathVariable("productId") long productId) {
		spDAO.deleteById(productId);
		return "redirect:/admin/product";
	}
	
	@RequestMapping("/product/reset")
	public String doPostDelete(Model model) {
		SanPham product = new SanPham();
		product.setTenSP("");
		product.setSoLuong(0);
		product.setDonGia(0.0);
		product.setMoTa("");
		product.setHinhAnh(null);
		model.addAttribute("product", product);
		return "redirect:/admin/product";
	}
}
