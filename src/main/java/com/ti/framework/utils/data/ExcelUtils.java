package com.ti.framework.utils.data;

import static com.ti.framework.config.ExcelReader.getWorkSheet;
import static com.ti.framework.config.ExcelReader.readExcel;

import com.ti.framework.base.FrameworkException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {
  private static int iNumber = 0;
  private static Sheet excelWSheet;
  private static Cell cell;
  private static Row row;

  public static int getRowCount(){
    try{
      iNumber = excelWSheet.getLastRowNum();
    }catch (Exception e){
      if (e.getMessage() != null){
        new FrameworkException("Class ExcelUtils | Method getRowCount | Exception desc: " + e.getMessage());
      }
    }
    return iNumber;
  }

  public static int getColumnCount(){
    try{
      iNumber = excelWSheet.getRow(0).getLastCellNum();
    }catch (Exception e){
      if (e.getMessage() != null){
        new FrameworkException("Class ExcelUtils | Method getRowCount | Exception desc: " + e.getMessage());
      }
    }
    return iNumber;
  }

  private static String getCellData(int rowNum, int colNum){
    String data = "";

    try{
      cell = excelWSheet.getRow(rowNum).getCell(colNum);
      if (cell == null){
        return "";
      }else if(cell.getCellType() == CellType.STRING){
        data = cell.getStringCellValue();
      }else if(cell.getCellType() == CellType.NUMERIC){
        data = String.valueOf(cell.getNumericCellValue());
      }
    }catch (Exception ex){
      new FrameworkException("Class ExcelUtils | Method getCellData | Exception desc: " + ex.getMessage());
    }
    return data;
  }

  public static Object[][] getExcelTableArray(String excelWorkBook, String excelWorkSheet){
    String[][] tabArray = null;

    try {
      readExcel(excelWorkBook);
      excelWSheet = getWorkSheet(excelWorkSheet);
      tabArray = new String[getRowCount()][getColumnCount()];

      for(int ci=0; ci< getRowCount(); ci++){
        for (int cj=0; cj<getColumnCount(); cj++){
          tabArray[ci][cj] = getCellData(ci+1, cj);
        }
      }
    }catch (Exception e){
      new FrameworkException("Class ExcelUtils | Method getExcelTableArray | Exception desc: " + e.getMessage());
    }
    return tabArray;
  }
}
