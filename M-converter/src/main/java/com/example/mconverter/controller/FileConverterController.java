package com.example.mconverter.controller;

import com.example.mconverter.service.FileConverterService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/convert")
@CrossOrigin("*")
public class FileConverterController {

    private final FileConverterService service;

    public FileConverterController(FileConverterService service) {
        this.service = service;
    }

    @PostMapping("/pdf-to-word")
    public ResponseEntity<byte[]> pdfToWord(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] result = service.pdfToWord(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=result.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);
    }

    @PostMapping("/pdf-to-excel")
    public ResponseEntity<byte[]> pdfToExcel(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] result = service.pdfToExcel(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=result.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);
    }
}
