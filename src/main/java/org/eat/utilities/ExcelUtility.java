package org.eat.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtility {

    private static XSSFWorkbook ExcelWBook;
    private static XSSFSheet ExcelWSheet;

    public static void setExcelFile(String path, String sheetName) throws Exception {
        try {
            // Open the Excel File
            FileInputStream ExcelFile = new FileInputStream(path);

            // Access the Excel data sheet
            XSSFWorkbook excelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e) {
            throw (e);
        }
    }

    public static String[][] getTestData(String tableName) {
        String[][] testData = null;

        try {
            // Handle numbers and strings
            DataFormatter formatter = new DataFormatter();
            XSSFCell[] boundaryCell = findCells(tableName);
            XSSFCell startCell = boundaryCell[0];
            XSSFCell endCell = boundaryCell[1];

            int startRow = startCell.getRowIndex() + 1;
            int endRow = endCell.getRowIndex() - 1;
            int startCol = startCell.getColumnIndex() + 1;
            int endCol = endCell.getColumnIndex() - 1;

            testData = new String[endRow - startRow + 1][endCol - startCol + 1];
            for (int i=startRow; i<endRow+1; i++) {
                for (int j=startCol; j<endCol+1; j++) {
                    Cell cell = ExcelWSheet.getRow(i).getCell(j);
                    testData[i - startRow][j - startCol] = formatter.formatCellValue(cell);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testData;
    }

    public static XSSFCell[] findCells(String tableName) {
        DataFormatter formatter = new DataFormatter();
        String pos = "begin";
        XSSFCell[] cells = new XSSFCell[2];

        for (Row row: ExcelWSheet) {
            for (Cell cell: row) {
                if (tableName.equals(formatter.formatCellValue(cell))) {
                    if (pos.equals("begin")) {
                        cells[0] = (XSSFCell) cell;
                        pos = "end";
                    } else {
                        cells[1] = (XSSFCell) cell;
                    }
                }
            }
        }
        return cells;
    }
}