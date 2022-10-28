package com.msstore.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity 
@Table(name = "TAIKHOAN")
public class TaiKhoan implements Serializable{
	@Id
	@Column(name="taikhoan")
	private String taiKhoan;
	@Column(name="matkhau")
	private String matKhau;
	@Column(name="hoten")
	private String hoTen;
	private String email;
	private String sdt;
	@Column(name="diachi")
	private String diaChi;
	@Column(name="ngaysinh")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngaySinh;
	
	
	@ManyToOne @JoinColumn(name="maCV")
	ChucVu cv;
	
	@OneToMany(mappedBy = "tk")
	List<BinhLuan> bl;
	
	@OneToMany(mappedBy = "tk")
	List<DonHang> donHang;
}
