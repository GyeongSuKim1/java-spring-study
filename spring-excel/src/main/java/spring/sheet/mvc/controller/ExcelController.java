package spring.sheet.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.service.ArticleService;
import spring.sheet.util.ExcelCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
public class ExcelController {

    private final ArticleService articleService;

    public ExcelController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 예제
    @RequestMapping(value = "/excel/download", method = RequestMethod.GET)
    public void excelDownload(HttpServletResponse response) throws IOException {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);
        System.out.println("time : " + formatedNow);

//        Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = null;
        Cell cell = null;
        int rowIndex = 0;

        // Header
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellValue("이름");

        cell = row.createCell(2);
        cell.setCellValue("제목");

        cell = row.createCell(3);
        cell.setCellValue(formatedNow);

        // Body
        for (int i = 0; i < 3; i++) {
            row = sheet.createRow(rowIndex++);
            cell = row.createCell(0);
            cell.setCellValue(i);

            cell = row.createCell(1);
            cell.setCellValue(i + "_title 샘플");

            cell = row.createCell(2);
            cell.setCellValue(i + "_content 샘플");
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=TestExcel.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }

    /**
     * 게시글 Excel
     */
    @RequestMapping(value = "article/excel", method = RequestMethod.GET)
    public void articleExcelDownload(HttpServletResponse response, @Param("date") String date) throws IOException {

        new ExcelCreator(articleService).createExcel(response, date);

    }
}
