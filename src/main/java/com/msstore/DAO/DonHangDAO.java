package com.msstore.DAO;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.msstore.Entity.DoanhThuNgay;
import com.msstore.Entity.DonHang;

public interface DonHangDAO extends JpaRepository<DonHang, Long>{
	@Query("SELECT o from DonHang o where o.tk.taiKhoan = ?1")
	public List<DonHang> getAllOrder(String username,Sort sort);
	
	@Query(value = "SELECT MONTH(d.ngayTao) as month, COUNT(d.maDon) as totalCode, COUNT(c.id.maSP) as totalProduct, "
			+ "MIN(c.donGia) as minPrice, MAX(c.donGia) as maxPrice, AVG(c.donGia) as mediumPrice, SUM(c.donGia) as totalPrice "
			+ "FROM DonHang d JOIN ChiTietDonHang c ON d.maDon = c.id.maDon "
			+ "GROUP BY MONTH(d.ngayTao)")
	List<Object[]> getRevenusMonth();
	
	@Query("SELECT  new DoanhThuNgay(COUNT(o.dh.maDon),COUNT(o.sp.maSP),SUM(o.donGia)) " + " FROM ChiTietDonHang o "
			+ " WHERE DAY(o.dh.ngayTao) = DAY(getdate())")
	public List<DoanhThuNgay> getDoanhThuNgay();
}
