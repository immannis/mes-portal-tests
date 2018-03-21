package com.si.mes.io;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ExcelReader {
	
	private HSSFWorkbook wb;
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	public void readExcel(String excelFilePath) {
		File excelFile = new File(excelFilePath);
		try {
			FileInputStream fis = new FileInputStream(excelFile);
			wb = new HSSFWorkbook(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readCell(int sheetIdx, int rowIdx, int cellIdx) {
		HSSFSheet sheet = wb.getSheetAt(sheetIdx);
		return sheet.getRow(rowIdx).getCell(cellIdx).getStringCellValue();
	}
	
	public HSSFSheet getSheet(int sheetIdx) {
		return wb.getSheetAt(sheetIdx);
	}
	
	public static String getValue(Cell cell) {
		if(cell == null) {
			return "";
		}
		return cell.getStringCellValue();
	}
	
	/*public String readCell(String sheetName, int rowIdx, int cellIdx) {
		HSSFSheet sheet = wb.getSheet(sheetName);
		return sheet.getRow(rowIdx).getCell(cellIdx).getStringCellValue();
	}
	
	public String readCell(int rowIdx, int cellIdx) {
		HSSFSheet sheet = wb.getSheetAt(0);
		return sheet.getRow(rowIdx).getCell(cellIdx).getStringCellValue();
	} */
	
}
