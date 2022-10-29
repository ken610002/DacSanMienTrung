package com.msstore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.HinhAnh;


public interface HinhAnhDAO extends JpaRepository<HinhAnh, Integer>{
	@Query(value = "SELECT o FROM HinhAnh o WHERE o.sp.maSP = ?1")
	List<HinhAnh> findAllByPID(Long id);
}
