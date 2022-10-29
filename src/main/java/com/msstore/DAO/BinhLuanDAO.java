package com.msstore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.BinhLuan;

public interface BinhLuanDAO extends JpaRepository<BinhLuan, Long>{
	
	@Query(value ="SELECT o from BinhLuan o where o.sp.maSP like ?1")
	List<BinhLuan> findAllBySanPham(Long masp);
	
}
