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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@NotEmpty(message = "Tên sản phẩm không được bỏ trống")
	private String tenSP;
	@Column(name="soluong")
	@NotNull(message = "Số lượng không được bỏ trống")
	@PositiveOrZero(message = "Giá trị phải là số nguyên dương")
	private int soLuong;
	@Column(name="dongia")
	@NotNull(message = "Đơn giá không được bỏ trống")
	@PositiveOrZero(message = "Giá trị phải là số nguyên dương")
	private Double donGia;
	@Column(name="mota")
	@NotEmpty(message = "Mô tả không được bỏ trống")
	private String moTa;
	
	@ManyToOne
	@JoinColumn(name = "maloai")
	LoaiSP loaiSP;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sp")
	List<BinhLuan> bl;
	@JsonIgnore
	@OneToMany(mappedBy = "sp")
	List<HinhAnh> hinhAnh;
	@JsonIgnore
	@OneToMany(mappedBy = "sp")
	List<ChiTietDonHang> ctdh;
}
