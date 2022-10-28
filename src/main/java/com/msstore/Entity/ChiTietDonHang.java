package com.msstore.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CHITIETDONHANG")
public class ChiTietDonHang implements Serializable{
	@EmbeddedId
	private CTDonHangID id;
	
	@ManyToOne
	@JoinColumn(name = "madon", referencedColumnName = "madon", insertable=false, updatable=false)
	DonHang dh;
	
	@ManyToOne	
	@JoinColumn(name = "masp", referencedColumnName = "masp", insertable=false, updatable=false)
	SanPham sp;
	
	@Column(name="dongia")
	private Double donGia;
	@Column(name="soluong")
	private int soluong;
	
	
}
