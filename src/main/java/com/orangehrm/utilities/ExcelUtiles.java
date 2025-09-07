package com.orangehrm.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtiles {
	public static Object[][] getdata(String excelpath, String sheetname) throws IOException {
        File file = new File(excelpath);
        FileInputStream fs = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fs);
        XSSFSheet sheet = workbook.getSheet(sheetname);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] data = new String[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (sheet.getRow(i) != null && sheet.getRow(i).getCell(j) != null) {
                    data[i][j] = sheet.getRow(i).getCell(j).toString();
                } else {
                    data[i][j] = "";
                }
            }
        }
        workbook.close();
        fs.close();
        return data;
    }
}