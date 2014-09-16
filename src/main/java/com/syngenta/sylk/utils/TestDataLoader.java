package com.syngenta.sylk.utils;

// All column headers will be holding string cell value

// Automation assumes that the first column of all test data sheets should be non-blank

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.syngenta.sylk.libraries.CommonLibrary;
import com.syngenta.sylk.libraries.SyngentaException;

public class TestDataLoader {

	private String testdataFile;
	private HashMap<String, HashMap<String, String>> dataMap = new HashMap<String, HashMap<String, String>>();

	public TestDataLoader(String fileName) {
		this.testdataFile = fileName;
	}

	public void loadData() {
		int eachTest = 0;
		HashMap<String, String> eachRow = null;
		StringBuilder temp = new StringBuilder();

		if (this.dataMap.size() > 0) {
			return;
		}
		HashMap<String, String> columnMap = new HashMap<String, String>();
		FileInputStream file = null;
		try {

			InputStream tempIs = this.getClass().getResourceAsStream(
					"/testdata" + File.separatorChar + this.testdataFile);

			File tempFile = CommonLibrary.newFileObject(this.testdataFile);
			OutputStream outputStreamMeta = new FileOutputStream(tempFile);
			IOUtils.copy(tempIs, outputStreamMeta);

			file = new FileInputStream(tempFile);

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			outerloop : while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				if (row.getRowNum() == 0) {
					for (int cn = 0; cn < row.getLastCellNum(); cn++) {
						Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
						// All columns will be holding string cell value
						String columnName = cell.getStringCellValue();
						int cellIndex = cell.getColumnIndex();
						columnMap.put("" + cellIndex, columnName.trim());
					}
				} else {
					eachTest++;

					// System.out.println(columnMap);
					eachRow = new HashMap<String, String>();
					for (int cn = 0; cn < row.getLastCellNum(); cn++) {
						Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						temp.append(StringUtils.trim(cell.getStringCellValue()));

						// Automation assumes that the first column of all test
						// data sheets should be srno with numberic values
						if (StringUtils.isBlank(row.getCell(0)
								.getStringCellValue())) {
							break outerloop;
						}

						eachRow.put(columnMap.get("" + cell.getColumnIndex()),
								temp.toString());

						temp.delete(0, temp.capacity());

						this.dataMap.put("" + eachTest,
								new HashMap<String, String>(eachRow));
					}

				}

			}// while end
		} catch (Exception e) {
			throw new SyngentaException(new CommonLibrary().getStackTrace(e));
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
				}
			}
		}

		// System.out.println(this.dataMap);

		for (Map.Entry<String, HashMap<String, String>> row : this.dataMap
				.entrySet()) {
			HashMap<String, String> myRow = row.getValue();
			String key = row.getKey();

			System.out.println("row num:" + key);
			System.out.println("Columns:" + myRow);
		}
	}

	public HashMap<String, HashMap<String, String>> getTestData() {
		return this.dataMap;
	}
}
