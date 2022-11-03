package com.msstore.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msstore.Entity.TaiKhoan;

public interface TaiKhoanDAO extends JpaRepository<TaiKhoan, String>{

	Optional<TaiKhoan> findByEmail(String email);
}
