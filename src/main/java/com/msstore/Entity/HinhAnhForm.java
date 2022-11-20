package com.msstore.Entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor

public class HinhAnhForm {
	private int maHinh;
	private MultipartFile tenHinh;
	SanPham  sp;
}
