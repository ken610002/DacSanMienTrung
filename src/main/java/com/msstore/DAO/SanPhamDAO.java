package com.msstore.DAO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.msstore.Entity.SanPham;
import com.msstore.Entity.SanPhamTop8;
import com.msstore.Entity.ThongKeNam;

public interface SanPhamDAO extends JpaRepository<SanPham, Long>{
	
	@Query(value = "SELECT o FROM SanPham o WHERE o.loaiSP.tenLoai like ?1")
	Page<SanPham> findAllByLoaiSP(String loai, Pageable pageable);
	
	@Query(value = "SELECT o FROM SanPham o WHERE o.loaiSP.maLoai = ?1")
	List<SanPham> findAllByLoaiSPID(String loai,Pageable pageable);
	
	
	@Query("SELECT new SanPhamTop8(p.sp,sum(p.soLuong),sum(p.soLuong * p.sp.donGia))"+
			" FROM ChiTietDonHang p" +
			" GROUP BY p.sp"+
			" ORDER BY sum(p.soLuong) desc")
	Page<SanPhamTop8> findByTop8(Pageable pageable);
	
	@Query("SELECT new ThongKeNam(year(dh.ngayTao),count(ct.dh.maDon),min(ct.donGia),max(ct.donGia),avg(ct.donGia),sum(dh.tong))" +
	       " FROM DonHang dh " +
			" INNER JOIN ChiTietDonHang ct on ct.dh.maDon = dh.maDon " +
	       "GROUP BY YEAR(dh.ngayTao)")
	List<ThongKeNam> findBuThongKeNam();
	
	@Query(value = "SELECT o FROM SanPham o WHERE o.tenSP LIKE ?1")
	Page <SanPham> findAllByKeywords(Pageable pageable, String keywords);
}
