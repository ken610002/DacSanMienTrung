package com.msstore.controller;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.msstore.DAO.ChucVuDAO;
import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.ChucVu;
import com.msstore.Entity.MailInfo;
import com.msstore.Entity.TaiKhoan;
import com.msstore.Service.CookieService;
import com.msstore.Service.MailerService;
import com.msstore.Service.SessionService;


@Controller
@RequestMapping("/home")
public class AccountController {
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Autowired
	ChucVuDAO cvDAO;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	MailerService mailService;

	
	
	@GetMapping("/account")
	public String account(Model model) {
		TaiKhoan taikhoan = tkDAO.findById(session.get("taiKhoan")).get();
		model.addAttribute("account", taikhoan);
		return "client/account";
	}
	@RequestMapping("/account/editProfile")
	public String accountEditProfile(Model model, @ModelAttribute("account") TaiKhoan account) {
		TaiKhoan taikhoan = tkDAO.findById(session.get("taiKhoan")).get();
		account.setTaiKhoan(taikhoan.getTaiKhoan());
		account.setMatKhau(taikhoan.getMatKhau());
		tkDAO.save(account);
		model.addAttribute("message", "Cập nhật thành công");
		return "client/account";
	}
	
	@RequestMapping("/account/logout")
	public String logout(Model model, HttpSession session) {
		session.removeAttribute("taiKhoan");
		session.removeAttribute("matKhau");
		cookie.remove("taiKhoan");
		return "client/login";
	}

	@GetMapping("/register")
	public String doGetRegister(Model model) {
		TaiKhoan account = new TaiKhoan();
		model.addAttribute("account", account);
		return "client/register";
	}

	@RequestMapping("/register")
	public String doRegister(@ModelAttribute("account") TaiKhoan account, Model model) {
		ChucVu cv = cvDAO.findAll().get(0);
		account.setCv(cv);
		tkDAO.saveAndFlush(account);
		return "redirect:/home/login";
	}



	@RequestMapping("/account/changepass")
	public String changePass(Model model, @RequestParam String taiKhoan, @RequestParam String matKhauCu,
			@RequestParam String matKhauMoi) {

		if (taiKhoan.equals(null)) {
			model.addAttribute("mess", "Vui lòng nhập tài khoản");
		} else {
			TaiKhoan account = tkDAO.findById(taiKhoan).get();
			if (!account.equals(null)) {
				if (account.getMatKhau().equals(matKhauCu)) {
					account.setMatKhau(matKhauMoi);
					tkDAO.save(account);
				} else {
					model.addAttribute("mess", "Mật khẩu cũ không đúng với tài khoản");
				}
			} else {
				model.addAttribute("mess", "Tài khoản này không tồn tại");
			}
		}

		return "client/account";
	}

	@PostMapping("/account/login")
	public String String(Model model, @RequestParam("taiKhoan") String taiKhoan,
			HttpSession session,
			@RequestParam("matKhau") String password) {
			
			TaiKhoan tk = tkDAO.findById(taiKhoan).get();
			
			if(tk != null) {
				if (!tk.getMatKhau().equals(password)) {
					model.addAttribute("message", "Sai tài khoản hoặc mật khẩu!");
					return "client/login";
				}else {
					cookie.add("taiKhoan", taiKhoan, 5);
					session.setAttribute("taiKhoan", taiKhoan);
					session.setAttribute("chucVu", tk.getCv().getMaCV());
					model.addAttribute("message", "Login successfylly");
					if(tk.getCv().getMaCV() == 1) {
						return "redirect:/home";
					}else {
						return "redirect:/admin/report";
					}
				}
			}else {
				model.addAttribute("message", "Sai tài khoản hoặc mật khẩu!");
			}
			return "client/login";

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
        return "redirect:client/index";
	}
}