package com.msstore.DAO;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Long>{
	
	@Query(value = "SELECT o FROM SanPham o WHERE o.loaiSP.tenLoai like ?1")
	Page<SanPham> findAllByLoaiSP(Pageable page, String loaisp);

	@Query(value = "SELECT o FROM SanPham o WHERE o.tenSP LIKE ?1")
	Page <SanPham> findAllByKeywords(Pageable pageable, String keywords);

}
