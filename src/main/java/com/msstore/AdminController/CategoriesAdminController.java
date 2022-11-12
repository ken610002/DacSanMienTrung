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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.LoaiSPDAO;
import com.msstore.Entity.LoaiSP;

@Controller
@RequestMapping("/admin")
public class CategoriesAdminController {

	@Autowired
	LoaiSPDAO lspDAO;
	
	@GetMapping("/categories")
	public String doGetCategoriestAdmin(Model model,@RequestParam("page") Optional<Integer> page,
			@ModelAttribute("loaisp") LoaiSP loaisp) {

		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<LoaiSP> listLoaiSP = lspDAO.findAll(pageAble);
		
		model.addAttribute("listLoaiSP", listLoaiSP);
		return "admin/categories";
	}
	
	
	@RequestMapping("/categories/create")
	public String doPostCreate(Model model,@Valid @ModelAttribute("loaisp") LoaiSP loaisp,
			BindingResult bindingResult ,@RequestParam("page") Optional<Integer> page) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<LoaiSP> listLoaiSP = lspDAO.findAll(pageAble);
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("loaisp", loaisp);
			model.addAttribute("listLoaiSP", listLoaiSP);
			return"admin/categories";
		}else {
			lspDAO.save(loaisp);
			return "redirect:/admin/categories";
		}

	}
//	
	@RequestMapping("/categories/update/{maloai}")
	public String doPostUpdate(Model model,@Valid @ModelAttribute("loaisp") LoaiSP loaisp,
			BindingResult bindingResult, @RequestParam("page") Optional<Integer> page) {

		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<LoaiSP> listLoaiSP = lspDAO.findAll(pageAble);
		
		if (!lspDAO.existsById(loaisp.getMaLoai())) {
			model.addAttribute("message", "Loại sản phẩm không tồn tại");
			model.addAttribute("listLoaiSP", listLoaiSP);
			return"admin/categories";
		}else if(bindingResult.hasErrors()) {
			model.addAttribute("loaisp", loaisp);
			model.addAttribute("listLoaiSP", listLoaiSP);
			return "admin/categories";
		}else {
			lspDAO.save(loaisp);
			return "redirect:/admin/categories/edit/" + loaisp.getMaLoai();
		}
	}
//	
	@RequestMapping("/categories/delete/{maloai}")
	public String doPostDelete(@PathVariable("maloai") String maloai,Model model,
			@ModelAttribute("loaisp") LoaiSP loaisp, @RequestParam("page") Optional<Integer> page) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<LoaiSP> listLoaiSP = lspDAO.findAll(pageAble);
		
		if(maloai.contains("")){
			model.addAttribute("message", "Vui lòng chọn loại sản phẩm để xóa");
			model.addAttribute("listLoaiSP", listLoaiSP);
			return "admin/categories";
		}else {
			lspDAO.deleteById(maloai);
			return "redirect:/admin/categories";
		}
	}
	
	@RequestMapping("/categories/reset")
	public String doPostDelete(Model model) {	
		return "redirect:/admin/categories";
	}
	
	@RequestMapping("/categories/edit/{maloai}")
	public String doEditCategories(Model model, @PathVariable("maloai") String maloai,
			@RequestParam("page") Optional<Integer> page) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<LoaiSP> listLoaiSP = lspDAO.findAll(pageAble);
		LoaiSP loai = lspDAO.findById(maloai).get();
		
		model.addAttribute("listLoaiSP", listLoaiSP);
		model.addAttribute("loaisp", loai);
		return "admin/categories";
	}
}
