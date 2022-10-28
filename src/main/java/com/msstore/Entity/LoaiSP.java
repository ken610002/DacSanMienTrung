package com.msstore.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity 
@Table(name = "LOAISP")
public class LoaiSP implements Serializable{
	@Id
	@Column(name="maloai")
	private String maLoai;
	@Column(name="tenloai")
	private String tenLoai;
	
	@OneToMany(mappedBy = "loaiSP")
	List<SanPham> sp;
}
