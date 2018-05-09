package ui.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExcelReader {
    private static final Logger logger = Logger.getLogger(ExcelReader.class);

    private  Workbook  xlsWorkBook;
    private  File file;
    private  FileInputStream inputStream;


    public ExcelReader(String dataFileName) {
        file= new File(dataFileName);
        try {
            inputStream = new FileInputStream(file);

            try {
                xlsWorkBook = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public  Map<String,String> getExcelData(String sheetName,String dataName) {
        Sheet sheet1= xlsWorkBook.getSheet(sheetName);
        Map<String,String> hashMap= new HashMap<String,String>();
            int totalNoOfRows=  sheet1.getLastRowNum()-sheet1.getFirstRowNum();
           int matchingRowNum=getRowNumber(sheetName,dataName);

        Row row = sheet1.getRow(matchingRowNum);

        for(int i=1;i<row.getLastCellNum();i++){
            Cell headerCell=sheet1.getRow(0).getCell(i);
            if(headerCell!=null) {
                headerCell.setCellType(1);
                Cell headerValueCell = row.getCell(i);
                String columnHeader = headerCell.getStringCellValue();
                String columnValue;
                if(headerValueCell!=null) {
                    headerValueCell.setCellType(1);
                    columnValue = headerValueCell.getStringCellValue();
                }
                else {
                    columnValue = "";
                }
                hashMap.put(columnHeader, columnValue);
            }
            }

        return hashMap;
    }

    public List<String> getColumnData(String sheetName, String columnName){
        Sheet sheet1= xlsWorkBook.getSheet(sheetName);
        List<String> columnDataList= new ArrayList<String>();

        int totalNoOfRows=  sheet1.getLastRowNum()-sheet1.getFirstRowNum();
        int cellNo =getCellNumber(sheetName, columnName);

        for(int i=1;i<=totalNoOfRows;i++){
            Row row1=sheet1.getRow(i);
            Cell cellValue=row1.getCell(cellNo);
            if(cellValue!=null) {
                cellValue.setCellType(1);
                columnDataList.add(cellValue.getStringCellValue());
            }
        }

        return columnDataList;
    }

    public int getRowNumber(String sheetName,String dataName)
    {
      Sheet sheet1=  xlsWorkBook.getSheet(sheetName);

       for(int i=1;i<=sheet1.getLastRowNum();i++)
       {
           Cell cell=sheet1.getRow(i).getCell(0);
           cell.setCellType(1);
           String testData=cell.getStringCellValue();
           if(testData.equalsIgnoreCase(dataName))
               return i;
       }
        return 0;

    }

    public int getCellNumber(String sheetName,String columnName)
    {
        Sheet sheet1=  xlsWorkBook.getSheet(sheetName);
        Row row = sheet1.getRow(0);
        for(int i=0;i<=row.getLastCellNum();i++)
        {
            Cell cell=row.getCell(i);
            cell.setCellType(1);
            String cellName=cell.getStringCellValue();
            if(cellName.equalsIgnoreCase(columnName))
                return i;
        }
        return 0;
    }
    public int getLastRowNum(String sheetName){

        Sheet sheet=  xlsWorkBook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }

    //Getting full excel data
    public  Map<String,List<String>> getFullExcelData(String sheetName) throws Exception{
        Sheet sheet1= xlsWorkBook.getSheet(sheetName);
        Map<String,List<String>> hashMap= new HashMap<String,List<String>>();
        int totalNoOfRows=  sheet1.getLastRowNum()-sheet1.getFirstRowNum();

        for(int i = 1;i<=totalNoOfRows;i++) {
            Row row = sheet1.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell headerCell = sheet1.getRow(0).getCell(j);
                headerCell.setCellType(1);
                Cell headerValueCell = row.getCell(j);
                headerValueCell.setCellType(1);
                String columnHeader = headerCell.getStringCellValue();
                String columnValue = headerValueCell.getStringCellValue();

                List<String> valueList = hashMap.get(columnHeader);
                if(valueList == null){
                    valueList = new ArrayList<String>();
                    hashMap.put(columnHeader, valueList);
                }
                valueList.add(columnValue);
            }
        }
        return hashMap;
    }


}
