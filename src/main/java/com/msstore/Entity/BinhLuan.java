package com.msstore.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	@Column(name="ngaytao")
	private Date ngayTao;
	
	@ManyToOne
	@JoinColumn(name = "maSP")
	SanPham sp;
	
	@ManyToOne
	@JoinColumn(name = "taikhoan")
	TaiKhoan tk;
}
