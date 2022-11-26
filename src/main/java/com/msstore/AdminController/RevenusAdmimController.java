package com.msstore.AdminController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.msstore.Entity.DoanhThuNgay;
import com.msstore.DAO.DonHangDAO;
import com.msstore.DAO.SanPhamDAO;
import com.msstore.DTO.ThongKeDTO;
import com.msstore.Entity.SanPhamTop8;
import com.msstore.util.Excel;

@Controller
@RequestMapping("/admin")
public class RevenusAdmimController {
	@Autowired
	DonHangDAO dhDAO;
	
	@Autowired
	SanPhamDAO spDAO;
	
	@GetMapping("/report")
	public String report(Model model) {
		List<Object[]> listDonHang = dhDAO.getRevenusMonth();
		List<ThongKeDTO> list = new ArrayList<>();
		for (Object[] objects : listDonHang) {
			ThongKeDTO dto = new ThongKeDTO(
					objects[0].toString(),objects[1].toString(),
					objects[2].toString(),objects[3].toString(),
					objects[4].toString(),objects[5].toString(),
					objects[6].toString());
			list.add(dto);
		}
		Pageable pageable = PageRequest.of(0, 5);
		Page<SanPhamTop8> top8SP = spDAO.findByTop8(pageable);
		List<DoanhThuNgay> doanhThuNgay = dhDAO.getDoanhThuNgay();

		model.addAttribute("doanhThuNgay", doanhThuNgay);
		model.addAttribute("top5sp", top8SP);
		model.addAttribute("revenusMonth", list);
		model.addAttribute("tkNam", spDAO.findBuThongKeNam());
		return "admin/report";
	} 
	
	@GetMapping("/report/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=thongketheothang_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);
		
		List<Object[]> listDonHang = dhDAO.getRevenusMonth();
		List<ThongKeDTO> list = new ArrayList<>();
		for (Object[] objects : listDonHang) {
			ThongKeDTO dto = new ThongKeDTO(
					objects[0].toString(),objects[1].toString(),
					objects[2].toString(),objects[3].toString(),
					objects[4].toString(),objects[5].toString(),
					objects[6].toString());
			list.add(dto);
		}
		
		Excel excelExporter = new Excel(list);
		
		excelExporter.export(response);		
	}
	

}
