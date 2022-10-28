package com.msstore.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity 
@Table(name = "SANPHAM")
public class SanPham implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="masp")
	private long maSP;
	@Column(name="tensp")
	private String tenSP;
	@Column(name="soluong")
	private int soLuong;
	@Column(name="dongia")
	private Double donGia;
	@Column(name="mota")
	private String moTa;
	
	@ManyToOne
	@JoinColumn(name = "maloai")
	LoaiSP loaiSP;
	
	
	@OneToMany(mappedBy = "sp")
	List<BinhLuan> bl;
	
	@OneToMany(mappedBy = "sp")
	List<HinhAnh> hinhAnh;
	
	@OneToMany(mappedBy = "sp")
	List<ChiTietDonHang> ctdh;
}
