package SampleTestingMayur;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadAllNamesFromExcel {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Mayur More\\Downloads\\sample_excel.xlsx"; ////// Update the path to your file

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); /////// First sheet
            int lastRow = sheet.getLastRowNum();  /////// Get the last row index
            
            System.out.println("Total rows are : "+lastRow);

            System.out.println("Names from Excel:");

            /////// Start from 1 to skip the header row
            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell nameCell = row.getCell(0); //////// "Name" is in the first column
                    if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                        System.out.println(nameCell.getStringCellValue());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
