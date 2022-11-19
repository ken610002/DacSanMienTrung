package com.msstore.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.msstore.DTO.ThongKeDTO;

public class Excel {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<ThongKeDTO> listRevenusMoths;
	
	public Excel(List<ThongKeDTO> listRevenusMoths) {
		this.listRevenusMoths = listRevenusMoths;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("RevenusMoths");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "Tháng", style);
		createCell(row, 1, "Tổng đơn", style);
		createCell(row, 2, "Tổng sản phẩm", style);
		createCell(row, 3, "Giá thấp nhất", style);
		createCell(row, 4, "Giá cao nhất", style);
		createCell(row, 5, "Giá trung bình", style);
		createCell(row, 6, "Tổng", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (ThongKeDTO list : listRevenusMoths) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, list.getMonth(), style);
			createCell(row, columnCount++, list.getTotalCode(), style);
			createCell(row, columnCount++, list.getTotalProduct(), style);
			createCell(row, columnCount++, list.getMinPrice(), style);
			createCell(row, columnCount++, list.getMaxPrice(), style);
			createCell(row, columnCount++, list.getMediumPrice(), style);
			createCell(row, columnCount++, list.getTotalPrice(), style);

		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
