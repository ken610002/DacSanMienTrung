package com.msstore.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "CHUCVU")
public class ChucVu implements Serializable{
	@Id
	private int maCV;
	private String tenCV;

	@JsonIgnore
	@OneToMany(mappedBy = "cv")
	List<TaiKhoan> taiKhoan;
}
