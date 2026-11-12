package com.automation.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.testng.annotations.DataProvider;


public class ExcelReader {

  
	//@DataProvider(name = "loginMatrixProvider", parallel = true)
	    public static synchronized Object[][] getSheetMatrix(String filePath, String sheetName) {
	        Object[][] dataMatrix = null;
	        try {
	            FileInputStream fileInputStream = new FileInputStream(filePath);
	            
	            // FIXED: Moved workbook and sheet variable instantiation inside local method stack block
	            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
	            XSSFSheet sheet = workbook.getSheet(sheetName);
	            
	            //int physicalRows = sheet.getPhysicalNumberOfRows();
	            int totalRows = sheet.getLastRowNum(); 
	            int totalCols = sheet.getRow(0).getLastCellNum();
	            
	            System.out.println("[EXCEL-SYSTEM] Reading Sheet: " + sheetName);
	            //System.out.println("[EXCEL-SYSTEM] Physical Rows on Sheet: " + physicalRows);
	            System.out.println("[EXCEL-SYSTEM] Last Row 0-Based Index: " + totalRows);
	            System.out.println("[EXCEL-SYSTEM] Column Count Detected: " + totalCols);
	            
	            dataMatrix = new Object[totalRows][totalCols];
	            DataFormatter formatter = new DataFormatter();
	            
	            for (int i = 1; i <= totalRows; i++) {
	                for (int j = 0; j < totalCols; j++) {
	                    var row = sheet.getRow(i);
	                    if (row != null) {
	                        XSSFCell cell = row.getCell(j);
	                        if (cell == null) {
	                            dataMatrix[i - 1][j] = "";
	                        } else {
	                            dataMatrix[i - 1][j] = formatter.formatCellValue(cell);
	                        }
	                    } else {
	                        dataMatrix[i - 1][j] = "";
	                    }
	                }
	            }
	                        
	            workbook.close();
	            fileInputStream.close();
	            
	        } catch (Exception e) {
	            System.err.println("[UTILS-ERROR] Failed to compile data matrix from Excel sheet!");
	            e.printStackTrace();
	        }
	        return dataMatrix;
	    }
	    
	    //USING MAP
	    
	    public static synchronized Object[][] getSheetMatrixAsMap(String filePath, String sheetName) {
	        Object[][] dataMatrix = null;
	        try (FileInputStream fileInputStream = new FileInputStream(filePath);
	             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
	            
	            XSSFSheet sheet = workbook.getSheet(sheetName);
	           
	            
	         // 🛡️ THE SAFETY SHIELD: Catch a missing tab before it triggers a NullPointerException
	            if (sheet == null) {
	                // === START OF DIAGNOSTIC AUTOMATION LOGGING ===
	                System.err.println("=======================================================================");
	                System.err.println("[EXCEL-DIAGNOSTIC] TAB MISMATCH DETECTED FOR: [" + sheetName + "]");
	                System.err.println("[EXCEL-DIAGNOSTIC] Total Sheet Tabs Discovered: " + workbook.getNumberOfSheets());
	                System.err.println("[EXCEL-DIAGNOSTIC] Listing exact sheet names extracted from Excel memory array:");
	                
	                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
	                    String dynamicSheetName = workbook.getSheetName(i);
	                    System.err.println("  -> Index [" + i + "]: [" + dynamicSheetName + "] | Length: " + dynamicSheetName.length());
	                    
	                    // Extra verification: Check for case-insensitive or trimmed matches
	                    if (dynamicSheetName.trim().equalsIgnoreCase(sheetName.trim())) {
	                        System.err.println("     ⚠️ MATCH WARNING: Found near-match! Check for hidden spaces or casing differences.");
	                    }
	                }
	                System.err.println("=======================================================================");
	                // === END OF DIAGNOSTIC AUTOMATION LOGGING ===
	                throw new IllegalArgumentException("Fatal Error: The requested sheet tab named '" + sheetName 
	                        + "' does not exist inside the target Excel file workbook!");
	            }
	            
	            int totalRows = sheet.getLastRowNum(); // Total data rows (excluding header)
	            int totalCols = sheet.getRow(0).getLastCellNum(); // Header count
	            
	            // Core Change: The matrix has rows equal to data rows, but exactly 1 column holding the Map!
	            dataMatrix = new Object[totalRows][1];
	            
	            // 1. Capture Header Names as Keys
	            String[] headers = new String[totalCols];
	            for (int col = 0; col < totalCols; col++) {
	                headers[col] = sheet.getRow(0).getCell(col).toString().trim();
	            }
	            
	            // 2. Iterate through data rows and parse into Maps
	            for (int row = 1; row <= totalRows; row++) {
	                var currentRow = sheet.getRow(row);
	                if (currentRow == null) continue;
	                
	                Map<String, String> rowMap = new HashMap<>();
	                for (int col = 0; col < totalCols; col++) {
	                    var cell = currentRow.getCell(col);
	                    String cellValue = (cell == null) ? "" : cell.toString().trim();
	                    rowMap.put(headers[col], cellValue);
	                }
	                // Store the entire map into the single column slot for this row
	                dataMatrix[row - 1][0] = rowMap;
	            }
	            
	            System.out.println("[EXCEL-SYSTEM] Successfully mapped " + sheetName + " into dynamic Map Injection structure.");
	            
	        } catch (Exception e) {
	            System.err.println("[EXCEL-ERROR] Failed to map Excel matrix: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return dataMatrix;
	    }
	}
