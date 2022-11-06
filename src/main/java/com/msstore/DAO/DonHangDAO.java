package com.msstore.DAO;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.DonHang;

public interface DonHangDAO extends JpaRepository<DonHang, Long>{
	@Query("SELECT o from DonHang o where o.tk.taiKhoan = ?1")
	public List<DonHang> getAllOrder(String username,Sort sort);
}
