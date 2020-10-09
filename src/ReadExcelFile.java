import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	public int ReadCellData(int vRow, int vColumn) {
		int value = 0; // variable for storing the cell value
		Workbook wb = null; // initialize Workbook null
		try {
			// reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream("./tables/tb.xlsx");
			// constructs an XSSFWorkbook object, by buffering the whole stream into the
			// memory
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Sheet sheet = wb.getSheetAt(0); // getting the XSSFSheet object at given index
		Row row = sheet.getRow(vRow); // returns the logical row
		Cell cell = row.getCell(vColumn); // getting the cell representing the given column
		value = (int) (cell.getNumericCellValue()); // getting cell value
		return value; // returns the cell value
	}

	public int[][] fillTable() {
		int[][] stable = new int[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				stable[i][j] = (ReadCellData(i, j));
			}
		return stable;
	}
}
