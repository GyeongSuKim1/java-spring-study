package example.excel.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    // 예제 1
    @RequestMapping(value = "/excel/download", method = RequestMethod.GET)
    public void excelDownload(HttpServletResponse response) throws IOException {

//        Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowNum = 0;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellValue("이름");

        cell = row.createCell(2);
        cell.setCellValue("제목");

        // Body
        for (int i = 0; i < 3; i++) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(i);

            cell = row.createCell(1);
            cell.setCellValue(i + "_name");

            cell = row.createCell(2);
            cell.setCellValue(i + "_title");
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=TestExcel.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }
}
