package com.msstore.AdminController;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.ChucVuDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.TaiKhoan;
import com.msstore.Service.SessionService;



@Controller
@RequestMapping("/admin/account")
public class AccountAdminController {
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Autowired
	ChucVuDAO cvDAO;
	@Autowired
	SessionService sessionService;
	
	@RequestMapping
	public String AccountAdmin(Model model,@RequestParam("p") Optional<Integer> p, @ModelAttribute("tk") TaiKhoan tk) {
		Pageable pageable = PageRequest.of(p.orElse(0), 4);
		Page <TaiKhoan> taikhoan= tkDAO.findAll(pageable);
		//List<TaiKhoan> tk = tkDAO.findAll();
		model.addAttribute("accounts", taikhoan);
		model.addAttribute("tk", tk);
		return "admin/account";
	}
	
	@PostMapping("/create")
	public String createAccountAdmin(@Valid @ModelAttribute("tk")TaiKhoan tk,Model model,Errors errors) {
		if(errors.hasErrors()) {
			model.addAttribute("accounts",tk);
			return "admin/account";
			
		}
		if(!tkDAO.existsById(tk.getTaiKhoan())) {
			tkDAO.save(tk);
			model.addAttribute("message","Tạo tài khoản thành công ");
			return "redirect:/admin/account";
		}else {
			model.addAttribute("message","Tài khoản đã tồn tại ");
		return "redirect:/admin/account";
		
		
		}
	}
	@RequestMapping("/update")
	public String updateAccountAdmin(@Validated @ModelAttribute("tk")TaiKhoan tk,Model model, BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("accounts",tk);
			return "admin/account";
			
		}
		if(tkDAO.existsById(tk.getTaiKhoan())) {
			tkDAO.save(tk);
			model.addAttribute("message","Cập nhập tài khoản thành công ");
			return "redirect:/admin/account";
		}else {
			model.addAttribute("message","Cập nhập tài khoản thất bại");
		return "redirect:/admin/account";
		
		
		}
	}
	@RequestMapping("/delete/{taiKhoan}")
	public String delete(@PathVariable("taiKhoan") String taiKhoan) {
		tkDAO.deleteById(taiKhoan);
		return "redirect:/admin/account";
	}
	
	@RequestMapping("/edit/{taiKhoan}")
	public String khachhang_edit(Model model, @PathVariable("taiKhoan") String taiKhoan,
			@RequestParam("p") Optional<Integer> p
			) {
		TaiKhoan kh = tkDAO.findById(taiKhoan).get();
		model.addAttribute("tk", kh);
		Pageable pageable = PageRequest.of(p.orElse(0),5);
		Page<TaiKhoan> pages = tkDAO.findAll(pageable);
		model.addAttribute("accounts", pages);
		return "admin/account";
	}
	@RequestMapping("/seach")
	public String seachTK(Model model,@RequestParam("Keywords") Optional<String> kw,
			@RequestParam("p") Optional<Integer> p,
			@ModelAttribute("tk") TaiKhoan tk) {
		String kwords = kw.orElse(sessionService.get("keywords",""));
		sessionService.get("keywords",kwords);
		Pageable pageable = PageRequest.of(p.orElse(0), 4);
		Page<TaiKhoan> taikhoan = tkDAO.findAllByKeywords(pageable,"%"+kwords+"%");
		model.addAttribute("accounts", taikhoan);
		model.addAttribute("tk", tk);
		return "admin/account";
		
	}
	
	
}
