package com.example.demo.utils;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {
    public String createExcelFileName(FilePart file, String newHeaderName) {
        String fileFormat = file.filename().split("\\.")[1];
        return String.format("%s.%s", newHeaderName, fileFormat);
    }
}
