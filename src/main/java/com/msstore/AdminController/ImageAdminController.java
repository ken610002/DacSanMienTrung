package com.msstore.AdminController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.HinhAnh;
import com.msstore.Entity.HinhAnhForm;
import com.msstore.Entity.SanPham;
import com.msstore.Service.SessionService;

@Controller
@RequestMapping("/admin")
public class ImageAdminController {
	@Autowired
	HinhAnhDAO haDAO;

	@Autowired
	SessionService sessionService;

	@Autowired
	SanPhamDAO spDAO;

	Path currenPath = Paths.get(".");
	Path asolutePath = currenPath.toAbsolutePath();
	Path path = Paths.get(asolutePath + "\\src\\main\\resources\\static\\images\\");

	@RequestMapping("/image")
	public String image(Model model, @RequestParam("p") Optional<Integer> page,
			@ModelAttribute("hinhanh") HinhAnh hinhanh) {
		Sort sort = Sort.by("sp.maSP").ascending();
		Pageable pageAble = PageRequest.of(page.orElse(0), 5, sort);
		Page<HinhAnh> pages = haDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();

		model.addAttribute("listsp", listSP);
		model.addAttribute("pages", pages);
		return "admin/image";
	}

	@RequestMapping("/image/edit/{maHinh}")
	public String imageEdit(Model model, @RequestParam("p") Optional<Integer> page,
			@PathVariable("maHinh") Integer maHinh) {
		Sort sort = Sort.by("sp.maSP").ascending();
		Pageable pageAble = PageRequest.of(page.orElse(0), 5, sort);
		Page<HinhAnh> pages = haDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();

		HinhAnh anh = haDAO.findById(maHinh).get();

		model.addAttribute("listsp", listSP);
		model.addAttribute("hinhanh", anh);
		model.addAttribute("pages", pages);
		return "admin/image";
	}

	@RequestMapping("/image/create")
	public String imageCreate(Model model, @Valid @ModelAttribute("hinhanh") HinhAnh hinhAnh,
			BindingResult bindingResult, @RequestParam("p") Optional<Integer> page,
			@RequestParam("file") MultipartFile file) {
		Sort sort = Sort.by("sp.maSP").ascending();
		Pageable pageAble = PageRequest.of(page.orElse(0), 5, sort);
		Page<HinhAnh> pages = haDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();

		String fileName = file.getOriginalFilename().toString();
		hinhAnh.setTenHinh(fileName);

		if (file.isEmpty()) {
			model.addAttribute("message", "Vui lòng chọn hình ảnh");
			model.addAttribute("pages", pages);
			model.addAttribute("listsp", listSP);
			return "admin/image";
		} else {
			try {
				FileCopyUtils.copy(file.getBytes(), new File(path + "\\" + fileName));
				System.out.println(path + "\\" + fileName);
				haDAO.save(hinhAnh);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return "redirect:/admin/image";
		}
	}

	@RequestMapping("/image/update/{mahinh}")
	public String imageUpdate(Model model, @Valid @ModelAttribute("hinhanh") HinhAnh hinhAnh,
			BindingResult bindingResult, @RequestParam("p") Optional<Integer> page,
			@RequestParam("file") MultipartFile file, @PathVariable("mahinh") Integer maHinh) {
		Sort sort = Sort.by("sp.maSP").ascending();
		Pageable pageAble = PageRequest.of(page.orElse(0), 5, sort);
		Page<HinhAnh> pages = haDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();

		String fileName = file.getOriginalFilename().toString();
		hinhAnh.setTenHinh(fileName);

		if (file.isEmpty()) {
			model.addAttribute("message", "Vui lòng chọn hình ảnh");
			model.addAttribute("pages", pages);
			model.addAttribute("listsp", listSP);
			return "admin/image";
		} else if (haDAO.existsById(maHinh)) {
			try {
				hinhAnh.setMaHinh(maHinh);
				FileCopyUtils.copy(file.getBytes(), new File(path + "\\" + fileName));
				System.out.println(path + "\\" + fileName);
				haDAO.save(hinhAnh);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "redirect:/admin/image";
		} else {
			model.addAttribute("message", "Vui lòng chọn hình ảnh để chỉnh sửa");
			model.addAttribute("pages", pages);
			model.addAttribute("listsp", listSP);
			model.addAttribute("hinhanh", hinhAnh);
			return "admin/image";
		}
	}

	@RequestMapping("/image/delete/{mahinh}")
	public String imageDelete(Model model, @ModelAttribute("hinhanh") HinhAnh hinhAnh,
			@RequestParam("file") MultipartFile file, @RequestParam("p") Optional<Integer> page,
			@PathVariable("mahinh") Integer maHinh) {
		Sort sort = Sort.by("sp.maSP").ascending();
		Pageable pageAble = PageRequest.of(page.orElse(0), 5, sort);
		Page<HinhAnh> pages = haDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();
		String fileName = file.getOriginalFilename().toString();

		if (maHinh.equals(0)) {
			model.addAttribute("message", "Vui lòng chọn hình ảnh để xóa");
			model.addAttribute("listsp", listSP);
			model.addAttribute("pages", pages);
			return "admin/image";
		} else {
			hinhAnh.setTenHinh(fileName);
			haDAO.deleteById(maHinh);
			return "redirect:/admin/image";
		}
	}

	@RequestMapping("/image/new")
	public String imageNew(Model model) {
		return "redirect:/admin/image";
	}

	@RequestMapping("/image/search")
	public String Search(Model model, @RequestParam("p") Optional<Integer> page,
			@ModelAttribute("hinhanh") HinhAnh product, @RequestParam("Keyword") Optional<String> kw) {
		String kwords = kw.orElse(sessionService.get("keywords", ""));
		sessionService.get("keywords", kwords);
		List<SanPham> listSP = spDAO.findAll();

		Pageable pageable = PageRequest.of(page.orElse(0), 6);

		Page<HinhAnh> pages = haDAO.findAllByKeyWords(pageable, "%" + kwords + "%");
		model.addAttribute("pages", pages);
		model.addAttribute("listsp", listSP);
		return "admin/image";
	}

}
