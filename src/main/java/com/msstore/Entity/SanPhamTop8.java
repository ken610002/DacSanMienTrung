package com.msstore.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamTop8 {
	@Id
	Serializable group;
	long soLuong;
	double tong;
}
