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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "HINHANH")
public class HinhAnh implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="mahinh")
	private int maHinh;
	@Column(name="tenhinh")
//	@NotEmpty(message = "Vui lòng chọn một hình ảnh")
	private String tenHinh;

	
	@ManyToOne
	@JoinColumn(name = "maSP")
	SanPham sp;
}
