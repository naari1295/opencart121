package utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    FileInputStream fi;
    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFRow row;
    XSSFCell cell;

    String path;

    // Constructor
    public ExcelUtility(String path)
    {
        this.path = path;
    }

    // Get Row Count
    public int getRowCount(String sheetName)
            throws IOException
    {
        fi = new FileInputStream(path);

        wb = new XSSFWorkbook(fi);

        sheet = wb.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();

        wb.close();
        fi.close();

        return rowCount;
    }

    // Get Cell Count
    public int getCellCount(String sheetName,
                            int rowNum)
            throws IOException
    {
        fi = new FileInputStream(path);

        wb = new XSSFWorkbook(fi);

        sheet = wb.getSheet(sheetName);

        row = sheet.getRow(rowNum);

        int cellCount = row.getLastCellNum();

        wb.close();
        fi.close();

        return cellCount;
    }

    // Get Cell Data
    public String getCellData(
            String sheetName,
            int rowNum,
            int colNum)
            throws IOException
    {
        fi = new FileInputStream(path);

        wb = new XSSFWorkbook(fi);

        sheet = wb.getSheet(sheetName);

        row = sheet.getRow(rowNum);

        cell = row.getCell(colNum);

        String data;

        try
        {
            data = cell.toString();
        }
        catch(Exception e)
        {
            data = "";
        }

        wb.close();
        fi.close();

        return data;
    }
}