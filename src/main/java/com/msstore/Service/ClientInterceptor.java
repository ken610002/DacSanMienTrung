package com.msstore.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.TaiKhoan;
@Component
public class ClientInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	SessionService sessionService;
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		System.out.println(request.getRequestURI() + " hi");
		
		if(sessionService.get("taiKhoan") == null) {
			return true;
		}
		
		if(request.getRequestURI().equals("/home/account/logout")) {
			response.sendRedirect("/home");
			return true;
		}	
		
		TaiKhoan tk = tkDAO.findById(sessionService.get("taiKhoan")).get();
        if(tk.getCv().getMaCV() == 2 || tk.getCv().getMaCV() == 3) {
        	response.sendRedirect("/admin/report");
			return false;
        }
        
		
        return true;
    }
}
