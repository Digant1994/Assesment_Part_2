package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *  Description: this class is contains methods for reading
 *         and writing excel files
 *
 */
public class ExcelUtils
{

	static Logger log = CoreUtilities.getLogger(ExcelUtils.class);

	/**
	 *
	 * @param filePath
	 * @param filename
	 * @param sheetName
	 * @return Mar 25, 2020 4:06:08 PM Description: Read data from excel file
	 */
	public static String[][] getDataFromExcel(String filePath, String filename, String sheetName)
	{
		String[][] data = null;
		File file = new File(filePath + File.separator + filename);
		try (FileInputStream inputStream = new FileInputStream(file))
		{
			int row = 0;
			int column = 0;
			DataFormatter dataFormatter = new DataFormatter();
			Workbook testData = null;
			String fileExtensionName = FilenameUtils.getExtension(filename);
			if (fileExtensionName.equals("xlsx"))
				testData = new XSSFWorkbook(inputStream);
			else if (fileExtensionName.equals("xls"))
				testData = new HSSFWorkbook(inputStream);
			Sheet testDataSheet = null;
			if (testData != null)
			{
				testDataSheet = testData.getSheet(sheetName);
			}
			if (testDataSheet != null)
			{
				row = testDataSheet.getLastRowNum() - testDataSheet.getFirstRowNum();
				column = testDataSheet.getRow(0).getLastCellNum() - testDataSheet.getRow(0).getFirstCellNum();
			}
			data = new String[row + 1][column];

			for (Row rows : testDataSheet)
				for (Cell cell : rows)
					data[rows.getRowNum()][cell.getColumnIndex()] = dataFormatter.formatCellValue(cell);

		}
		catch (Exception ex)
		{
			log.error(ex.getMessage());
		}
		return (data);
	}

	/**
	 * 
	 * @param filepath
	 * @param filename
	 * @param sheetName
	 * @param row
	 * @param column
	 * @param value
	 *            Mar 25, 2020 4:06:31 PM Description: Write data to excel file for
	 *            specific row and column
	 */
	public static void writeToExcel(String filepath, String filename, String sheetName, int row, int column, String value)
	{
		File file = new File(filepath + File.separator + filename);

		try (FileInputStream inputStream = new FileInputStream(file); FileOutputStream outputStream = new FileOutputStream(file))
		{
			Workbook testData = null;
			String fileExtensionName = FilenameUtils.getExtension(filename);
			if (fileExtensionName.equals("xlsx"))
			{
				testData = new XSSFWorkbook(inputStream);
			}
			else if (fileExtensionName.equals("xls"))
			{
				testData = new HSSFWorkbook(inputStream);
			}
			Sheet testDataSheet = null;
			if (testData != null)
			{
				testDataSheet = testData.getSheet(sheetName);
			}
			if (testDataSheet != null)
			{
				testDataSheet.getRow(row).getCell(column).setCellValue(value);
			}

			if (testData != null)
			{
				testData.write(outputStream);
			}
			file = null;
			testDataSheet = null;
			if (testData != null)
			{
				testData.close();
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
		}
	}
}
