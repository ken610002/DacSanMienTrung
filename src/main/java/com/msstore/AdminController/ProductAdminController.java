package com.msstore.AdminController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import com.msstore.Entity.TaiKhoan;
import com.msstore.Service.SessionService;

@Controller
@RequestMapping("/admin")
public class ProductAdminController {
	
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	HinhAnhDAO haDAO;
	
	@Autowired
	LoaiSPDAO lspDAO;
	
	@Autowired
	SessionService sessionService;
	
	@GetMapping("/product")
	public String doGetProductAdmin(Model model,@RequestParam("page") Optional<Integer> page,
			@ModelAttribute("product") SanPham product) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		
		model.addAttribute("categoris", categoris);
		model.addAttribute("products", listProduct);
		model.addAttribute("images", img);
		return "admin/product";
	}
	
	@GetMapping("/product/edit/{productId}")
	public String doEditProduct(Model model,@RequestParam("page") Optional<Integer> page, @PathVariable("productId") long productId) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
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
	public String doPostCreate(@Valid @ModelAttribute("product") SanPham product,BindingResult bindingResult,
			@RequestParam("page") Optional<Integer> page, Model model) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("images", img);
			model.addAttribute("categoris", categoris);
			model.addAttribute("products", listProduct);
			return"admin/product";
		}else {
			spDAO.save(product);
		}
		return "redirect:/admin/product";
	}
	
	@RequestMapping("/product/update/{productId}")
	public String doPostUpdate(@Valid @ModelAttribute("product") SanPham product, BindingResult bindingResult,
			@PathVariable("productId") long productId, @RequestParam("page") Optional<Integer> page, Model model) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		
		if(!spDAO.existsById(productId)) {
			model.addAttribute("images", img);
			model.addAttribute("categoris", categoris);
			model.addAttribute("products", listProduct);
			model.addAttribute("message", "Sản phẩm không tồn tại");
			return"admin/product";
		}else if(bindingResult.hasErrors()){
			model.addAttribute("images", img);
			model.addAttribute("categoris", categoris);
			model.addAttribute("products", listProduct);
			return"admin/product";
		}else {
			product.setMaSP(productId);
			spDAO.save(product);
			return "redirect:/admin/product/edit/" + product.getMaSP();
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping("/product/delete/{productId}")
	public String doPostDelete(@PathVariable("productId") Long productId,Model model,
			@ModelAttribute("product") SanPham product,@RequestParam("page") Optional<Integer> page) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<SanPham> listProduct = spDAO.findAll(pageAble);
		
		List<LoaiSP> categoris = lspDAO.findAll();
		List<HinhAnh> img = haDAO.findAll();
		Set<Long> nameSet = new HashSet<>();
		img = img.stream().filter(e -> nameSet.add(e.getSp().getMaSP())).collect(Collectors.toList());
		
		if(product.getMaSP() == 0) {
			model.addAttribute("images", img);
			model.addAttribute("categoris", categoris);
			model.addAttribute("products", listProduct);
			model.addAttribute("message", "Vui lòng chọn sản phẩm để xóa");
			return"admin/product";
		}else {
			spDAO.deleteById(productId);
			return "redirect:/admin/product";
		}

	}
	
	@RequestMapping("/product/search")
	public String Search(Model model,@RequestParam("page") Optional<Integer> page,
			@ModelAttribute("product") SanPham product, @RequestParam("Keyword") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywords",""));
		sessionService.get("keywords",kwords);
		Pageable pageable = PageRequest.of(page.orElse(0), 10);
		
		Page<SanPham> sp = spDAO.findAllByKeywords(pageable, "%" + kwords + "%");
		model.addAttribute("products", sp);

		return"admin/product";		
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
