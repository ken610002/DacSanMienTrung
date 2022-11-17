package com.msstore.AdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ImageAdminController {
	@RequestMapping("/image")
	public String image() {
		return"admin/image";
	}
}
