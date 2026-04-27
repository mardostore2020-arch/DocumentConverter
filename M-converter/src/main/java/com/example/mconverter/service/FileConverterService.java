package com.example.mconverter.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Service
public class FileConverterService {

    // PDF ➜ WORD
    public byte[] pdfToWord(MultipartFile file) throws Exception {

        PDDocument pdf = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(pdf);
        pdf.close();

        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText(text);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();

        return out.toByteArray();
    }

    // PDF ➜ EXCEL
    public byte[] pdfToExcel(MultipartFile file) throws Exception {

        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("PDF Data");

        String[] lines = text.split("\n");
        int rowNum = 0;

        for (String line : lines) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(line);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return out.toByteArray();
    }
}
