package com.msstore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.BinhLuan;

public interface BinhLuanDAO extends JpaRepository<BinhLuan, Long>{
	
	@Query("SELECT bl from BinhLuan bl where bl.sp.maSP = ?1")
	public List<BinhLuan> getCommentByPID (Long id);
}
