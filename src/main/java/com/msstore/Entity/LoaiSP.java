package com.msstore.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity 
@Table(name = "LOAISP")
public class LoaiSP implements Serializable{
	@Id
	@Column(name="maloai")
	@NotEmpty(message = "Mã loại không được để trống")
	private String maLoai;
	@Column(name="tenloai")
	@NotEmpty(message = "Tên loại không được để trống")
	private String tenLoai;
	
	@JsonIgnore
	@OneToMany(mappedBy = "loaiSP")
	List<SanPham> sp;
}
