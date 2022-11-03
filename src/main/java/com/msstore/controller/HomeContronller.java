package com.msstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msstore.DAO.HinhAnhDAO;
import com.msstore.DAO.LoaiSPDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.HinhAnh;
import com.msstore.Entity.LoaiSP;
import com.msstore.Entity.MailInfo;
import com.msstore.Entity.SanPham;
import com.msstore.Entity.SanPhamTop8;
import com.msstore.Entity.TaiKhoan;
import com.msstore.Service.MailerService;


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
	MailerService mailService;
	
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
	
	@GetMapping("/login")
	public String doGetLogin() {
		return "client/login";
	}
	
	@GetMapping("/register")
	public String doGetRegister() {
		return "client/register";
	}
	
	@GetMapping("/forgot")
	public String doGetForgot() {
		return "client/forgot-password";
	}
	
	@PostMapping("/forgot")
	public String doForgotPass(@RequestParam String username,@RequestParam String email, Model model) {
        Optional<TaiKhoan> acc = tkDAO.findById(username);
        if (!acc.isPresent()) {
            model.addAttribute("message", "Tài khoản không tồn tại");
        }else {
            String newPass = ((long) Math.floor(Math.random() * (999999999 - 100000000 + 1) + 100000000)) +"";
            acc.get().setMatKhau(newPass);
            tkDAO.save(acc.get());
            try {
                MailInfo mail = new MailInfo();
                mail.setFrom("tiachop26042002@gmail.com");
                mail.setTo(email);
                mail.setSubject("Lấy lại mật khẩu");
                mail.setBody("Chào " + acc.get().getHoTen() + ", mật khẩu mới của bạn được đặt lại là: " + newPass + ""
                		+ ". Vui lòng không được gửi mật khẩu này cho bất cứ để tránh mất thông tin.");
                mailService.send(mail);
                model.addAttribute("message","Gửi thành công!");
            } catch (MessagingException e) {
            	 model.addAttribute("message","Gửi thất bại!");
                e.printStackTrace();
            }
        }
        return "client/index";
	}
}
