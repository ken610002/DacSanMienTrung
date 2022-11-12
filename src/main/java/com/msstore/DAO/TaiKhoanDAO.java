package com.msstore.DAO;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.msstore.Entity.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{

	Optional<TaiKhoan> findByEmail(String email);
	
	
	@Query(value = "SELECT o FROM TaiKhoan o WHERE o.taiKhoan like ?1")
	Page <TaiKhoan> findAllByKeywords(Pageable pageable, String keywords);
}
