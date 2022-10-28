package com.msstore.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "BINHLUAN")
public class BinhLuan implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mabl")
	private long maBL;
	@Column(name="noidung")
	private String noiDung;

	
	@ManyToOne
	@JoinColumn(name = "maSP")
	SanPham sp;
	
	@ManyToOne
	@JoinColumn(name = "taikhoan")
	TaiKhoan tk;
}
