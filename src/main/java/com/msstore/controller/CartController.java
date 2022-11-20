package com.msstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.msstore.DAO.ChiTIetDonHangDAO;
import com.msstore.DAO.DonHangDAO;
import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.CTDonHangID;
import com.msstore.Entity.ChiTietDonHang;
import com.msstore.Entity.DonHang;
import com.msstore.Entity.HinhAnh;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.TaiKhoan;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CartController {
	@Autowired
	SanPhamDAO spDAO;
	
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Autowired
	DonHangDAO dhDAO;
	
	@Autowired
	ChiTIetDonHangDAO ctdhDAO;
	
	@Autowired
	HinhAnhDAO haDAO;

	@GetMapping("/home/cart/get-product")
	public List<SanPham> getSP(){
		return spDAO.findAll();
	}
	
	@GetMapping("/home/check-out/detail")
	public List<SanPham> getOrder(){
		return spDAO.findAll();
	}
	
	@PostMapping("/home/cart/check-out")
	public void checkout(@RequestBody ArrayList<Object> obj) {
		double total = 0;
		TaiKhoan tk = new TaiKhoan();
		SanPham sp = new SanPham();

		//Lấy username
		Map<String, Object> map_first = (Map<String, Object>) obj.get(0);
		Set<String> set_first = map_first.keySet();
		for (String key : set_first) {
			if(key == "username") {
				tk = tkDAO.findById(String.valueOf(map_first.get(key))).get();
			}
        }
		
		List<ChiTietDonHang> ctlist = new ArrayList<>(); 
		
		for(Object ob : obj) {
			Map<String, Object> map = (Map<String, Object>) ob;
			Set<String> set = map.keySet(); 
			ChiTietDonHang ct = new ChiTietDonHang();
			for (String key : set) {
				
				// Tạo sản phẩm
				if(key == "id") {
					sp = spDAO.findById(Long.parseLong(String.valueOf(map.get(key)))).get();
					ct.setSp(sp);
				}
				
				if(key == "quantity") {
					ct.setSoLuong(Integer.parseInt(String.valueOf(map.get(key))));
					sp.setSoLuong(sp.getSoLuong() - (Integer.parseInt(String.valueOf(map.get(key)))));
				}
				if(key == "price") ct.setDonGia(Double.parseDouble(String.valueOf(map.get(key))));
				if(key == "total")  total += Double.parseDouble(String.valueOf(map.get(key)));
	        }
			ctlist.add(ct);

		}
		
		DonHang dh = new DonHang();
		dh.setTrangthai(1);
		dh.setTong(total);
		dh.setTk(tk);
		dh = dhDAO.save(dh);
		
		for(ChiTietDonHang ctdh : ctlist) {
			ctdh.setId(new CTDonHangID(dh.getMaDon(),ctdh.getSp().getMaSP()));
			ctdh.setDh(dh);
			ctdhDAO.save(ctdh);

		}
	}
	@GetMapping("/home/check-out/{username}/histories")
	public List<DonHang> pageCheckOutHistory(@PathVariable("username") String username) {		
		return dhDAO.getAllOrder(username,Sort.by("maDon").descending());
	}
	
	@GetMapping("/home/check-out/{id}/history-detail")
	public List<ChiTietDonHang> pageCheckOutHistoryDetail(@PathVariable("id") Long id) {
		return ctdhDAO.getAllOrderDetail(id);
	}
	
	@GetMapping("/home/check-out/{id}/image")
	public List<HinhAnh> getImage(@PathVariable("id") Long id) {
		return haDAO.findAllByPID(id);
	}
	
	
}
