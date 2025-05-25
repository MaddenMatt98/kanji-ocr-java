package com.maddenmatt.kanjiocr.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.maddenmatt.kanjiocr.dto.ParsedTextDto;

public interface OCRService {

    String uploadImagetoS3(MultipartFile image) throws IOException;
    List<ParsedTextDto> getTextInImage(String s3ObjectKey);

}
