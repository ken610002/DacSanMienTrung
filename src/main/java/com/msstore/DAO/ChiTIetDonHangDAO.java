package com.msstore.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.msstore.Entity.CTDonHangID;
import com.msstore.Entity.ChiTietDonHang;

public interface ChiTIetDonHangDAO extends JpaRepository<ChiTietDonHang, CTDonHangID>{
 
}
