package com.msstore.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.msstore.DAO.TaiKhoanDAO;
import com.msstore.Entity.TaiKhoan;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	SessionService sessionService;
	@Autowired
	TaiKhoanDAO tkDAO;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		if(sessionService.get("taiKhoan") == null) {
			response.sendRedirect("/home");
			return false;
		}	
		
		TaiKhoan tk = tkDAO.findById(sessionService.get("taiKhoan")).get();
        if(tk.getCv().getMaCV() == 1) {
        	response.sendRedirect("/home");
			return false;
        }
        
		
        return true;
    }
}
