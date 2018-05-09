package ui.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelWriter {
    private static final Logger logger = Logger.getLogger(ExcelWriter.class);
    private HSSFWorkbook xlsWorkBook;
    private File file;
    //    private FileOutputStream outputStream;
    private  FileInputStream inputStream;


    public ExcelWriter(String dataFileName) {
        file= new File(dataFileName);
        try {
            inputStream = new FileInputStream(file);
            try {
                xlsWorkBook = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void writeDataToExcel(String sheetName, List<String> fillData) throws Exception{
        HSSFRow sheetRow = null;
        HSSFCell sheetCell = null;
        HSSFSheet worksheet = xlsWorkBook.getSheet(sheetName);
        for(int rowNum = 1; rowNum<=fillData.size(); rowNum++){
            sheetRow = getRow(worksheet,rowNum);
            sheetCell = sheetRow.createCell(0);
            sheetCell.setCellValue((String) fillData.get(rowNum-1));
        }

        try {

            FileOutputStream outputStream = new FileOutputStream(file);
            xlsWorkBook.write(outputStream);
            outputStream.close();
            logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected final HSSFRow getRow(HSSFSheet sheet, int rowNum)
    {
        HSSFRow sheetRow = null ;

        sheetRow = sheet.getRow(rowNum);
        if (null == sheetRow)
            sheetRow = sheet.createRow(rowNum);

        return sheetRow;
    }
    public void writeDataToExcel(String sheetName, String fillData, String dataName, String colName) throws Exception{
        Map<String,String> hashMap= new HashMap<String,String>();
        HSSFSheet worksheet = xlsWorkBook.getSheet(sheetName);
        for(int row = 1; row<=worksheet.getLastRowNum();row++) {
            Row rowNum = worksheet.getRow(row);
            int lastCellNum = worksheet.getRow(0).getLastCellNum();
            if(rowNum.getCell(0).toString().equals(dataName)) {
                for(int col = 0; col<lastCellNum; col++){
//                if(rowNum.getCell(0).toString().equals(dataName)) {
                    Cell headerCell = worksheet.getRow(0).getCell(col);
                    if (headerCell != null) {
                        if (headerCell.toString().equals(colName)) {
                            Cell cell = rowNum.createCell(col);
                            if(cell!=null)
                                cell.setCellValue(fillData);
                                break;
                        }

                    }
                }
                break;
            }

        }

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            xlsWorkBook.write(outputStream);
            outputStream.close();
            logger.info("ThreadID: "+Thread.currentThread().getId()+"  "+Thread.currentThread().getName()+" "+"Excel written the data "+fillData+" to row "+dataName+" column Name "+colName+" successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
