package spring.sheet.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import spring.sheet.domain.entity.ArticleEntity;
import spring.sheet.mvc.service.ArticleService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class ExcelCreator {

    private final ArticleService articleService;

    public ExcelCreator(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void createExcel(HttpServletResponse response, String date) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Row row = null;
        Cell cell = null;
        int rowIndex = 0;

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);

        List<ArticleEntity> articleList = articleService.articleList();

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(4);
        cell.setCellValue("현재 날짜");

        cell = row.createCell(6);
        cell.setCellValue("다운로드 날짜");

        row = sheet.createRow(rowIndex++);
        cell = row.createCell(4);
        cell.setCellValue(formatedNow);

        cell = row.createCell(6);
        if (date.isEmpty()) cell.setCellValue("null");
        else cell.setCellValue(date);

        log.info("선택 날짜 : {}", date);

//        row = sheet.createRow(rowIndex++);
        row = sheet.createRow(1 + rowIndex++);
        cell = row.createCell(0);
        cell.setCellValue("번호");

        cell = row.createCell(1);
        cell.setCellValue("제목");

        cell = row.createCell(2);
        cell.setCellValue("설명");

        cell = row.createCell(3);
        cell.setCellValue("작성시간");


        for (ArticleEntity i : articleList) {
            row = sheet.createRow(rowIndex++);
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
