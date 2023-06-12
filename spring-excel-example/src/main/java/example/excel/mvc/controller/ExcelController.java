package example.excel.mvc.controller;

import example.excel.domain.entity.Article;
import example.excel.mvc.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class ExcelController {

    private ArticleService articleService;

    // 예제 1
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

    @RequestMapping(value = "/article/excel", method = RequestMethod.GET)
    public void articleExcel(HttpServletResponse response) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Row row = null;
        Cell cell = null;
        int rowIndex = 0;

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(0);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellValue("제목");

        cell = row.createCell(2);
        cell.setCellValue("설명");

        cell = row.createCell(3);
        cell.setCellValue("작성시간");

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(5);
        cell.setCellValue("다운로드 날짜");

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(5);
        cell.setCellValue(formatedNow);

        List<Article> articleList = articleService.articleList();
        log.info("ㅡ {}", articleList);

        for (Article i : articleList) {
            row = sheet.createRow(1 + rowIndex++);
            log.info("1ㅡ {}", i);
            log.info("2ㅡ {}", i.getIdx());
            cell = row.createCell(0);
            cell.setCellValue(i.getIdx());

            cell = row.createCell(1);
            cell.setCellValue(i.getTitle());

            cell = row.createCell(2);
            cell.setCellValue(i.getContent());

            cell = row.createCell(3);
            cell.setCellValue(i.getCreated_at().format(formatter));
        }


        // 컨텐츠 타입과 파일명 지정
        response.setContentType("ms-vnd/excel");
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
        response.setHeader("Content-Disposition", "attachment;filename=Article.xlsx");

        // Excel File Output
        wb.write(response.getOutputStream());
        wb.close();
    }

}
