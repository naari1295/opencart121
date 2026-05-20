package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getData() throws IOException {

        String path =
                System.getProperty("user.dir")
                + "\\testData\\Opencart_LoginData.xlsx";

        ExcelUtility xlutil =
                new ExcelUtility(path);

        String sheetName = "Sheet1";

        int totalRows =
                xlutil.getRowCount(sheetName);

        int totalCells =
                xlutil.getCellCount(sheetName, 1);

        // Temporary large array
        Object temp[][] =
                new Object[totalRows][totalCells];

        int actualRows = 0;

        for (int r = 1; r <= totalRows; r++) {

            String email =
                    xlutil.getCellData(
                    sheetName,
                    r,
                    0);

            // Skip empty rows
            if (email == null ||
                email.trim().isEmpty())
            {
                continue;
            }

            for (int c = 0; c < totalCells; c++) {

                temp[actualRows][c] =
                        xlutil.getCellData(
                        sheetName,
                        r,
                        c);
            }

            actualRows++;
        }

        // Final exact-sized array
        Object loginData[][] =
                new Object[actualRows][totalCells];

        for(int i=0; i<actualRows; i++)
        {
            for(int j=0; j<totalCells; j++)
            {
                loginData[i][j] = temp[i][j];
            }
        }

        return loginData;
    }
}