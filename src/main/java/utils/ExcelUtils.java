package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	public static String TestDataSheet = System.getProperty("user.dir");
	static Workbook book;
	static Sheet sheet;
	public static FileInputStream fis;

	public static Object[][] getTestData(String sheetName) {
		try {
			fis = new FileInputStream(TestDataSheet + "/" + "testdata.xlsx");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(1).getLastCellNum()];
		System.out.println(
				sheet.getLastRowNum() + " ROW's and ===========" + sheet.getRow(0).getLastCellNum() + " COLUMN's");
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString(); // i+1 cell--0
				System.out.println(data[i][j] + "  ");
			}
		}

		return data;

	}
}