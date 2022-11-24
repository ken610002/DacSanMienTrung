package com.msstore.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.CTDonHangID;
import com.msstore.Entity.ChiTietDonHang;
import com.msstore.Entity.TongTienCTDH;

public interface ChiTIetDonHangDAO extends JpaRepository<ChiTietDonHang, CTDonHangID>{
	
	@Query("SELECT o from ChiTietDonHang o where o.dh.maDon = ?1")
	public List<ChiTietDonHang> getAllOrderDetail(long maDon);
	
//	@Query("SELECT o from ChiTietDonHang o where o.dh.maDon = ?1")
//	public void deleteBymaDon(long maDon);
	
	@Query("SELECT SUM(o.donGia * o.soLuong) FROM ChiTietDonHang o "
			+ " WHERE o.dh.maDon = ?1")
	public TongTienCTDH getTongTienByMadon(long maDon);

}
