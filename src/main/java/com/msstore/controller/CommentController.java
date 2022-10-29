package com.msstore.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msstore.DAO.BinhLuanDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.BinhLuan;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.TaiKhoan;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CommentController {
	@Autowired
	BinhLuanDAO blDAO;
	@Autowired
	TaiKhoanDAO tkDAO;
	@Autowired
	SanPhamDAO spDAO;
	
	
	@GetMapping("/home/{id}/product-comment")
	public List<BinhLuan> getCommment(@PathVariable("id") Long id){
		return blDAO.getCommentByPID(id);
	}
	
	@PostMapping("/home/product-comment")
	public BinhLuan sendCommment(@RequestBody ArrayList<String> data) throws JsonMappingException, JsonProcessingException, ParseException{
		BinhLuan bl = new BinhLuan();
		TaiKhoan tk = tkDAO.findById(data.get(0)).get();
		SanPham sp = spDAO.findById(Long.parseLong(data.get(1))).get();
		bl.setSp(sp);
		bl.setTk(tk);
		bl.setNoiDung(data.get(2));
				
		bl.setNgayTao(new Date());
		System.out.println(new Date());
		return blDAO.save(bl);
	}
}
