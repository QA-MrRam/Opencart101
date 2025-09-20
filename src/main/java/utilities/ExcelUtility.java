package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

	private static Workbook workbook;

	// load Excel sheet

	public static void loadExcel(String filePath) {
		try (FileInputStream fis = new FileInputStream(filePath)) {
			workbook = WorkbookFactory.create(fis);
		} catch (IOException e) {
			throw new RuntimeException("❌ Failed to load Excel file: " + filePath, e);
		}
	}

	// Close Excel sheet

	public static void closeExcel() {
		try {
			if (workbook != null) {
				workbook.close();
			}

		} catch (IOException e) {
			throw new RuntimeException("❌ Failed to close Excel file", e);
		}
	}

	// get Row Count (actual count, not last index)

	public static int getRowCount(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getPhysicalNumberOfRows();
	}
	// get column count (based on first non-empty row )

	public static int getColCount(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(0);
		return (row == null) ? 0 : row.getPhysicalNumberOfCells();
	}

	// Get single cell data safely
	public static String getData(String sheetName, int row, int col) {

		if (workbook == null) {
			throw new IllegalStateException("⚠️ Excel file not loaded. Call loadExcel() first.");
		}
		Sheet sheet = workbook.getSheet(sheetName);
		Row r = sheet.getRow(row);
		if (r == null)
			return "";
		Cell cell = r.getCell(col);
		if (cell == null)
			return "";

		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	public static Object[][] getSheetData(String sheetName, boolean skipHeader) {

		if (workbook == null) {
			throw new IllegalStateException("⚠️ Excel file not loaded. Call loadExcel() first.");
		}

		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();
		System.out.println("Row Count: " + rowCount);

		int colCount = getColCount(sheetName);
		System.out.println("Column Count: " + colCount);

		int startRow = skipHeader ? 1 : 0;
		Object[][] data = new Object[rowCount - startRow + 1][colCount];

		DataFormatter formatter = new DataFormatter();

		for (int i = startRow; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {

				Cell cell = (row == null) ? null : row.getCell(j);
				data[i - startRow][j] = (cell == null) ? "" : formatter.formatCellValue(cell);
			}
		}
		return data;
	}

	public static void main(String[] args) {

		// 1 Load Excel sheet
		ExcelUtility.loadExcel(".\\testData\\OpenCart_LoginTestData.xlsx");

		// Example: print multiple cells
//		System.out.println("No of Rows: " + ExcelUtility.getRowCount("Sheet1"));
//		System.out.println("No of Col: " + ExcelUtility.getColCount("Sheet1"));
//		System.out.println("Row 1 Col 0: " + ExcelUtility.getData("Sheet1", 1, 0));
//		System.out.println("Row 1 Col 1: " + ExcelUtility.getData("Sheet1", 1, 1));
//		System.out.println("Row 2 Col 0: " + ExcelUtility.getData("Sheet1", 2, 0));

		// 2️ Read full sheet into 2D array (skipHeader = true → ignore 1st row)
		Object[][] sheetData = ExcelUtility.getSheetData("Sheet1", false);

		System.out.println("length of sheet data:" + sheetData.length);
		// 3 Print the data row by row
		for (int i = 0; i < sheetData.length; i++) {
			for (int j = 0; j < sheetData[i].length; j++) {
				System.out.print(sheetData[i][j] + "\t");
			}
			System.out.println();
		}

		// 4 close excel sheet
		ExcelUtility.closeExcel();

	}
}