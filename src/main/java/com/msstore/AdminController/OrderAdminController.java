package com.msstore.AdminController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.ChiTIetDonHangDAO;
import com.msstore.DAO.DonHangDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.Entity.CTDonHangID;
import com.msstore.Entity.ChiTietDonHang;
import com.msstore.Entity.DonHang;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.TongTienCTDH;

@Controller
@RequestMapping("/admin")
public class OrderAdminController {
	@Autowired
	DonHangDAO dhDAO;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	ChiTIetDonHangDAO ctdhDAO;

	@RequestMapping("/order")
	public String order(Model model, @RequestParam("p") Optional<Integer> page,
			@ModelAttribute("donhang") DonHang donhang) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<DonHang> listDH = dhDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();
		
		TongTienCTDH tongTien = new TongTienCTDH();
		tongTien.setTongTien(0.0);
		
		model.addAttribute("listdh", listDH);
		model.addAttribute("listsp", listSP);
		model.addAttribute("tongtien", tongTien);
		return "admin/index";
	}

	@RequestMapping("/order/edit/{madon}")
	public String orderEdit(Model model, @PathVariable("madon") Long maDon, @RequestParam("p") Optional<Integer> page) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<DonHang> listDH = dhDAO.findAll(pageAble);
		List<SanPham> listSP = spDAO.findAll();

		DonHang donhang = dhDAO.findById(maDon).get();
		TongTienCTDH tongtien = ctdhDAO.getTongTienByMadon(maDon);

		model.addAttribute("listdh", listDH);
		model.addAttribute("listsp", listSP);
		model.addAttribute("donhang", donhang);
		model.addAttribute("tongtien", tongtien);

		return "admin/index";
	}

	@RequestMapping("/order/reset")
	public String oderReset() {
		return "redirect:/admin/order";
	}

	@RequestMapping("/order/update")
	public String doPostUpdate(Model model, @ModelAttribute("donhang") DonHang donhang, @RequestParam("p") Optional<Integer> page,
			@RequestParam("tongtien") Double tongtien) {

		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<DonHang> listDH = dhDAO.findAll(pageAble);

		if (!dhDAO.existsById(donhang.getMaDon())) {
			model.addAttribute("message", "Đơn hàng không tồn tại");
			model.addAttribute("listdh", listDH);
			return "admin/index";
		} else {

			donhang.setTong(tongtien);		
			dhDAO.save(donhang);
			model.addAttribute("donhang", donhang);
			return "redirect:/admin/order/edit/" + donhang.getMaDon();
		}
	}

	@RequestMapping("/order/delete/{maDon}")
	public String orderDelete(Model model, @ModelAttribute("donhang") DonHang donhang,
			@RequestParam("p") Optional<Integer> page, @PathVariable("maDon") Long maDon) {
		Pageable pageAble = PageRequest.of(page.orElse(0), 5);
		Page<DonHang> listDH = dhDAO.findAll(pageAble);
		List<ChiTietDonHang> ctdh = ctdhDAO.getAllOrderDetail(maDon);

		if (maDon.equals(0)) {
			model.addAttribute("message", "Vui lòng chọn đơn hàng để xóa");
			model.addAttribute("listdh", listDH);
			return "admin/index";
		} else {
			ctdhDAO.deleteAll(ctdh);
			dhDAO.deleteById(maDon);
			return "redirect:/admin/order";
		}
	}

	@RequestMapping("/order/delete/product/{maDon}/{maSP}")
	public String orderDeleteProduct(Model model, @PathVariable("maDon") Long maDon, @PathVariable("maSP") Long maSP,
			 @ModelAttribute("donhang") DonHang donhang) {
		CTDonHangID id = new CTDonHangID();
		id.setMaSP(maSP);
		id.setMaDon(maDon);

		ctdhDAO.deleteById(id);
		TongTienCTDH tongtien = ctdhDAO.getTongTienByMadon(maDon);

		donhang.setTong(tongtien.getTongTien());
		dhDAO.save(donhang);
		System.out.println(donhang);
		return "redirect:/admin/order/edit/" + maDon;
	}

	@RequestMapping("/order/add/product/{maDon}")
	public String orderUpdateProduct(Model model, @PathVariable("maDon") Long maDon, @RequestParam("masp") Long maSP,
			@ModelAttribute("donhang") DonHang donhang, @RequestParam("soluong") Integer soLuong) {
		SanPham sp = spDAO.findById(maSP).get();

		CTDonHangID id = new CTDonHangID();
		id.setMaSP(maSP);
		id.setMaDon(maDon);

		ChiTietDonHang ctdh = new ChiTietDonHang();
		ctdh.setId(id);
		ctdh.setDonGia(sp.getDonGia());
		ctdh.setSoLuong(soLuong);
		ctdhDAO.save(ctdh);

		TongTienCTDH tongtien = ctdhDAO.getTongTienByMadon(maDon);

		model.addAttribute("tongtien", tongtien);

		return "redirect:/admin/order/edit/" + maDon;
	}
}
