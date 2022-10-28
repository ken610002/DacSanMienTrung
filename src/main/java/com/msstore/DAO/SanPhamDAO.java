package com.msstore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Long>{
	
	@Query(value = "SELECT o FROM SanPham o WHERE o.loaiSP.tenLoai like ?1")
	List<SanPham> findAllByLoaiSP(String loai);

}
